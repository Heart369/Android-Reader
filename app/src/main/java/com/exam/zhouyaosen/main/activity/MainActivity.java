package com.exam.zhouyaosen.main.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.adapter.fragment.MyPagerAdapter;
import com.exam.zhouyaosen.main.base.BaseActivity;
import com.exam.zhouyaosen.main.fragment.Fragment_fl;
import com.exam.zhouyaosen.main.fragment.Fragment_main;
import com.exam.zhouyaosen.main.fragment.Fragment_user;
import com.exam.zhouyaosen.main.network.Http_fl;
import com.exam.zhouyaosen.main.network.javabean.Home_bean;
import com.exam.zhouyaosen.main.tool.Dp_Px;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Home_bean bean= (Home_bean) msg.obj;
                    Log.d("TAG",msg.what+","+msg.obj);
                    f1.Setdata(bean);
                    break;
                case 2:
                    Toast.makeText(MainActivity.this, "检查网络连接", Toast.LENGTH_SHORT).show();
                    f1.Nointer();
            }
        }
    };
    List<Fragment> fragments;
    ViewPager viewPager;
    BottomNavigationView navigationView;
    Fragment_main f1;
    Fragment_fl f2;
    Fragment_user f3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        click();
        initdata(1);
        Dp_Px dp_px=new Dp_Px();
        Log.d("TAG",getHeight()+","+dp_px.dip2px(this,200)+","+getWidth());
    }

    public void initdata(int i) {
        Log.d("YAG","我被调用了");
        Http_fl http_fl = new Http_fl("fictionType", "小说", String.valueOf(i), "30", new Http_fl.Http() {
            @Override
            public void resp(String data, boolean flag) {
                if (flag){
                    if (data.contains("</title></head>"))
                        return;
                    Gson gson = new Gson();
                    Home_bean bean = gson.fromJson(data, Home_bean.class);
                    Message message = new Message();
                    message.what=1;
                    message.obj=bean;
                    handler.sendMessage(message);
                }else {
                    Message message = new Message();
                    message.what=2;
                    handler.sendMessage(message);
                }

            }
        });
        http_fl.start();
    }

    private void click() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigationView.getMenu().getItem(position).setChecked(true);
                if (position==2)
                    f3.updata();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        viewPager.setCurrentItem(0, true);
                        return true;
                    case R.id.navigation_explore:
                        viewPager.setCurrentItem(1, true);
                        return true;
                    case R.id.navigation_profile:
                        viewPager.setCurrentItem(2, true);
                        return true;
                }
                return false;
            }
        });
    }

    private void initview() {
        viewPager = findViewById(R.id.viewpager);
        fragments = new ArrayList<>();
        f1 = new Fragment_main();
        fragments.add(f1);
        f2=new Fragment_fl();
        fragments.add(f2);
        f3=new Fragment_user();
        fragments.add(f3);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        navigationView = findViewById(R.id.bottomNavigationView);
    }


    @Override
    protected void onRestart() {
        f3.updata();
        super.onRestart();
    }
}