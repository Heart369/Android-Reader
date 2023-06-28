package com.exam.zhouyaosen.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.adapter.recycle.UserHis_Adapter;
import com.exam.zhouyaosen.main.base.BaseActivity;
import com.exam.zhouyaosen.main.sqlite.Book_Sqlite;
import com.exam.zhouyaosen.main.sqlite.Bookshelf_Sqlite;
import com.exam.zhouyaosen.main.sqlite.Look_SQLite;
import com.exam.zhouyaosen.main.sqlite.bean.Book;
import com.exam.zhouyaosen.main.tool.Recy_item_jj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserHis_Activity extends BaseActivity {
    int flag;
    Look_SQLite look_sqLite;
    List<Book> allData = new ArrayList<>();
    Bookshelf_Sqlite shelf_sqlite;
    Book_Sqlite sqlite;
    RecyclerView recyclerView;
    RelativeLayout re_xz;
    CheckBox checkBox;
    TextView qx, del, gl,wsj;
    UserHis_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_his);
        recyclerView = findViewById(R.id.recy_ls);
        re_xz = findViewById(R.id.re_xz);
        re_xz.setVisibility(View.GONE);
        checkBox = findViewById(R.id.checkbox);
        wsj=findViewById(R.id.wsj);
        qx = findViewById(R.id.qx);
        del = findViewById(R.id.del);
        gl = findViewById(R.id.title_ri);
        click();
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(Recy_item_jj.LEFT_DECORATION, 7);
        stringIntegerHashMap.put(Recy_item_jj.TOP_DECORATION, 5);
        stringIntegerHashMap.put(Recy_item_jj.RIGHT_DECORATION, 7);
        recyclerView.addItemDecoration(new Recy_item_jj(stringIntegerHashMap));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", -1);
        sqlite();
    }

    private void sqlite() {
        if (flag == 1) {
            shelf_sqlite = new Bookshelf_Sqlite(this, "shelf.bd", null, 1);
            allData = shelf_sqlite.getAllData();
            setToolbar(R.id.toolbar_login, R.id.title_login, "书架管理");
            look();
        } else if (flag == 2) {
            sqlite = new Book_Sqlite(this, "book.bd", null, 1);
            allData = sqlite.getAllData();
            setToolbar(R.id.toolbar_login, R.id.title_login, "足迹管理");
            look();
        } else if (flag == 3) {
            look_sqLite = new Look_SQLite(this, "look.bd", null, 1);
            allData = look_sqLite.getData();
            setToolbar(R.id.toolbar_login, R.id.title_login, "最近在看");
            look();
        }
    }

    private void del_sqlite(List<Boolean> b) {
        if (flag == 1) {
            shelf_sqlite.delete(allData, b);
            allData=shelf_sqlite.getAllData();
            look();
        } else if (flag == 2) {
            sqlite.delete(allData, b);
            allData=sqlite.getAllData();
            look();
        } else if (flag == 3) {
            look_sqLite.delete(allData,b);
            allData=look_sqLite.getData();

        }
        if (allData.size()==0)
            wsj.setVisibility(View.VISIBLE);
        else   wsj.setVisibility(View.GONE);
        adapter.setAllData(allData);
        adapter.notifyDataSetChanged();
    }


    private void click() {
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setQx(!adapter.isQx());
                adapter.notifyDataSetChanged();
            }
        });
        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(!checkBox.isChecked());
                adapter.setQx(!adapter.isQx());
                adapter.notifyDataSetChanged();
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                del_sqlite(adapter.check);
                adapter.setGl(false);
                adapter.setQx(false);
                gl.setText("管理");
                re_xz.setVisibility(View.GONE);
                checkBox.setChecked(false);
                adapter.notifyDataSetChanged();
            }
        });

        gl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                re_xz.setVisibility(View.VISIBLE);
                adapter.setGl(!adapter.isGl());
                if (adapter.isGl())
                    gl.setText("取消");
                else {
                    adapter.setQx(false);
                    gl.setText("管理");
                    re_xz.setVisibility(View.GONE);
                    checkBox.setChecked(false);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void look() {
        if (allData.size()==0)
            wsj.setVisibility(View.VISIBLE);
        else   wsj.setVisibility(View.GONE);
        adapter = new UserHis_Adapter(this, allData);
        recyclerView.setAdapter(adapter);
        adapter.setClick(new UserHis_Adapter.click() {
            @Override
            public void onclick(int po) {
                Log.d("TAG", po + "");
                re_xz.setVisibility(View.VISIBLE);
            }
        });
    }
}