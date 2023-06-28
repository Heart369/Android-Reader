package com.exam.zhouyaosen.main.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.adapter.recycle.Detail_adapter;
import com.exam.zhouyaosen.main.base.BaseActivity;
import com.exam.zhouyaosen.main.network.Http_chapter;
import com.exam.zhouyaosen.main.network.javabean.Chatpter;
import com.exam.zhouyaosen.main.sqlite.Book_Sqlite;
import com.exam.zhouyaosen.main.sqlite.Bookshelf_Sqlite;
import com.exam.zhouyaosen.main.sqlite.bean.Book;
import com.google.gson.Gson;

import java.util.List;

public class DetailActivity extends BaseActivity {
    String id, title, icon;
    ImageView image_title;
    TextView text_title,text_zz,text_jj,start,addsj;
    RecyclerView recyclerView;
    Book_Sqlite sqlite;
    LottieAnimationView lottie;
    boolean f=false;
    String data;
    Bookshelf_Sqlite shelf_sqlite;
    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    Gson gson = new Gson();
                     data = (String) msg.obj;
                    if (data!=null&&data.contains("</title></head>"))
                        return;
                    Chatpter chatpter = gson.fromJson(data, Chatpter.class);
                    updata(chatpter);
                    start.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(DetailActivity.this,LookBookActivity.class);
                            intent.putExtra("zj",data);
                            startActivity(intent);
                        }
                    });
                    lottie.setVisibility(View.GONE);
                    break;
                case 2:
                    Log.d("TAG","错误");
                    break;
            }
        }
    };

    private void updata(Chatpter chatpter) {
        text_title.setText(chatpter.data.title);
        text_jj.setText(chatpter.data.descs);
        text_zz.setText(chatpter.data.author);
        Detail_adapter adapter=new Detail_adapter(chatpter, this, new Detail_adapter.onclick() {
            @Override
            public void click(int po) {
                Intent intent=new Intent(DetailActivity.this,LookBookActivity.class);
                intent.putExtra("po",po);
                intent.putExtra("zj",data);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        id = intent.getStringExtra("id");
        icon = intent.getStringExtra("icon");
        setToolbar(R.id.toolbar_login, R.id.title_login, title);
        sqlite=new Book_Sqlite(this,"book.bd",null,1);
        shelf_sqlite=new Bookshelf_Sqlite(this,"shelf.bd",null,1);
        initView();
        initData();
        initclick();
    }

    private void initclick() {
        addsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!f){
                    long timestampInMilliseconds = System.currentTimeMillis();
                    shelf_sqlite.addData(id,icon,title, String.valueOf(timestampInMilliseconds));
                    addsj.setText("移出书架");
                    f=true;
                }else {
                    shelf_sqlite.deleteData(id);
                    addsj.setText("加入书架");
                    f=false;
                }

            }
        });

    }

    private void initData() {
        request(id);
        if (!icon.contains("https"))
        icon = icon.replace("http", "https");
        Glide.with(this)
                .load(icon)
                .transform(new RoundedCorners(100))
                .into(image_title);
        long timestampInMilliseconds = System.currentTimeMillis();
        sqlite.addData(id,icon,title, String.valueOf(timestampInMilliseconds));
        if (shelf_sqlite.checkTitleExists(title)){
            addsj.setText("移出书架");
            f=true;
        }
    }

    private void request(String id) {
        Http_chapter chapter = new Http_chapter(id, handler);
        chapter.start();
    }

    private void initView() {
        lottie=findViewById(R.id.lottie);
        image_title = findViewById(R.id.image_title);
        text_title=findViewById(R.id.title);
        text_zz=findViewById(R.id.textView2);
        text_jj=findViewById(R.id.textView);
        recyclerView=findViewById(R.id.recycler);
        start=findViewById(R.id.start);
        addsj=findViewById(R.id.addsj);
    }
}