package com.exam.zhouyaosen.main.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.adapter.recycle.SearchAdapter;
import com.exam.zhouyaosen.main.base.BaseActivity;
import com.exam.zhouyaosen.main.base.FlawLayout;
import com.exam.zhouyaosen.main.network.Http_fl;
import com.exam.zhouyaosen.main.network.javabean.Home_bean;
import com.exam.zhouyaosen.main.sqlite.Search_Sqlite;
import com.exam.zhouyaosen.main.sqlite.bean.SearchData;
import com.exam.zhouyaosen.main.tool.DividerItemDecoration;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {
    View view;
    ImageView exit;
    FlawLayout flawLayout;
    EditText editText;
    SearchAdapter adapter;
    RecyclerView recyclerView;
    Search_Sqlite search_sqlite;
    TextView search;
    ImageView del;
    boolean flag=true;
    LinearLayout layout;
    List<Home_bean.DataDTO> data = new ArrayList<>();
    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Home_bean bean = (Home_bean) msg.obj;
            data.clear();
            if (bean.data != null&&editText.getText().length()>0)
                data.addAll(bean.data);
            adapter.setData(data);
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);
        view = findViewById(R.id.zw);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
        lp.height = getStatusBarHeight();
        initview();
        initData();
        onclick();
    }

    private void onclick() {
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(layout, "alpha", 1f, 0f);
                alphaAnimator.setDuration(500);
                alphaAnimator.start();
                supportFinishAfterTransition();
            }
        });
        // 设置焦点
        editText.requestFocus();
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (flag&&keyCode==KeyEvent.KEYCODE_ENTER){
                    search.performClick();
                    flag=false;
                    return true;
                }
                return false;
            }
        });
// 显示键盘
        if (!isKeyboardOpen(this)){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }


        flawLayout.setOnclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView= (TextView) v;
                editText.setText(textView.getText().toString());
                search.performClick();
            }
        });
        flawLayout.setOnLongClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView= (TextView) v;
                search_sqlite.deleteData(textView.getText().toString());
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if (text.length() == 0) {
                    adapter.setData(new ArrayList<>());
                    adapter.notifyDataSetChanged();
                } else
                    request(text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=editText.getText().toString();
                if (s.length()>0){
                    search_sqlite.insertData(s,"123");
                    Intent intent=new Intent(SearchActivity.this,SearchZs_Activity.class);
                    intent.putExtra("title",s);
                    String transitionName = "myTransition";
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SearchActivity.this, layout, transitionName);
                    startActivity(intent, options.toBundle());
                }else {
                    Toast.makeText(SearchActivity.this, "请先输入想要查找的数据哦", Toast.LENGTH_SHORT).show();
                }
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flawLayout.SetData(new ArrayList<>());
                search_sqlite.deleteAllData();
            }
        });
    }

    private void initData() {
        search_sqlite=new Search_Sqlite(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, R.drawable.fgx);
        adapter = new SearchAdapter(this, data, new SearchAdapter.onclick() {
            @Override
            public void click(int po, String title) {
                editText.setText(title);
                search.performClick();
            }
        });
        List<String> data = new ArrayList<>();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(dividerItemDecoration);
        for (SearchData allDatum : search_sqlite.getAllData()) {
            data.add(allDatum.getTitle());
        }
        editText.setSelection(editText.getText().length());
        flawLayout.SetData(data);
    }

    private void initview() {
        flawLayout = findViewById(R.id.flaw);
        exit = findViewById(R.id.exit);
        editText = findViewById(R.id.edit);
        recyclerView = findViewById(R.id.recycler);
        search =findViewById(R.id.search_ss);
        del=findViewById(R.id.del);
        layout=findViewById(R.id.linearLayout);
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }

    public void request(String key) {
        Gson gson = new Gson();
        Http_fl fl = new Http_fl("title", key, "1", "30", new Http_fl.Http() {
            @Override
            public void resp(String data, boolean flag) {
                if (flag) {
                    Message message = new Message();
                    message.what = 1;
                    message.obj = gson.fromJson(data, Home_bean.class);
                    handler.sendMessage(message);
                }
            }
        });
        fl.start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        flag=true;
        editText.setSelection(editText.getText().length());
    }
    public  boolean isKeyboardOpen(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return inputMethodManager != null && inputMethodManager.isActive();
    }

}