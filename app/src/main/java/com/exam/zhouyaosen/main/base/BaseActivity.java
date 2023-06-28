package com.exam.zhouyaosen.main.base;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.tool.Lucency;

public class BaseActivity extends AppCompatActivity {
    int b=0,is_finish=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Lucency lucency=new Lucency();
        lucency.light(this);

    }

    public void setIs_finish(int is_finish) {
        this.is_finish = is_finish;
    }

    public  void setToolbar(int id, int id2, String name){
        Toolbar toolbar=findViewById(id);
        setSupportActionBar(toolbar);
        setTitle(null);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                if (is_finish==0){
                    overridePendingTransition(R.anim.back,R.anim.closs);
                }
                b=1;
            }
        });
        TextView textView=findViewById(id2);
        textView.setText(name);
    }

    public void btl(){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        this.getWindow().setAttributes(lp);
    }
    public int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public int getHeight(){
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();

        int screenWidth = metrics.widthPixels; // 屏幕宽度（像素）
        int screenHeight = metrics.heightPixels; // 屏幕高度（像素）
        return screenHeight;
    }

    public int getWidth(){
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();

        int screenWidth = metrics.widthPixels; // 屏幕宽度（像素）
        int screenHeight = metrics.heightPixels; // 屏幕高度（像素）
        return screenWidth;
    }

    public void fin(){
        finish();
        overridePendingTransition(R.anim.back,R.anim.closs);
        b=1;
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (b==0){
            overridePendingTransition(R.anim.start,R.anim.back);
        }
        b=0;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        b=1;
        overridePendingTransition(R.anim.back,R.anim.closs);
    }

}
