package com.exam.zhouyaosen.main.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.TestLooperManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.adapter.fragment.SearchPagerAdapter;
import com.exam.zhouyaosen.main.base.BaseActivity;
import com.exam.zhouyaosen.main.fragment.f1.Fragment_f1;
import com.exam.zhouyaosen.main.network.Http_fl;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchZs_Activity extends BaseActivity {
    View view;
    EditText editText;
    ImageView exit;
    TextView search;
    List<Fragment> fragments = new ArrayList<>();
    ViewPager viewPager;
    TabLayout tabLayout;
    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_zs);
        view = findViewById(R.id.zw);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
        lp.height = getStatusBarHeight();
        initView();
        initData();
        click();
    }

    private void click() {
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supportFinishAfterTransition();

            }
        });
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 当EditText获取焦点时执行的代码
                    // 例如：显示提示信息或改变背景颜色
                        supportFinishAfterTransition();
                } else {
                    // 当EditText失去焦点时执行的代码
                    // 例如：隐藏提示信息或恢复背景颜色
                }
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        editText.setText(title);
        fragments.add(new Fragment_f1(title, this, "title"));
        fragments.add(new Fragment_f1(title, this, "author"));
        fragments.add(new Fragment_f1(title, this, "fictionType"));
        tabLayout.setupWithViewPager(viewPager);
        SearchPagerAdapter adapter = new SearchPagerAdapter(getSupportFragmentManager(), fragments, new String[]{"相关作品", "相关作者", "相关分类"});
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
    }

    private void initView() {
        editText = findViewById(R.id.edit);
        exit = findViewById(R.id.exit);
        search = findViewById(R.id.search_ss);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tab_layout);
    }

}