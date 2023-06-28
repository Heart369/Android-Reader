package com.exam.zhouyaosen.main.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.base.BaseActivity;
import com.exam.zhouyaosen.main.tool.Lucency;

public class Start_Activity extends BaseActivity {
TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Lucency lucency = new Lucency();
        lucency.settitle_a(this);
        t1=findViewById(R.id.text_sy);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(t1, "alpha", 0f, 1f);
        alphaAnimator.setDuration(2500);  // 设置动画持续时间为 2 秒
// 启动动画
        alphaAnimator.start();
        SharedPreferences preferences = getSharedPreferences("userdata", MODE_PRIVATE);
        String size;
        size = preferences.getString("cookie", "");
        SharedPreferences sharedPreferences=getSharedPreferences("color", Context.MODE_PRIVATE);
        if (sharedPreferences.getInt("RED",-1)==-1){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("BACKRED", 227);
            editor.putInt("BACKGREEN", 204);
            editor.putInt("BACKBLUE", 187);
            editor.putInt("RED", 14);
            editor.putInt("GREEN", 14);
            editor.putInt("BLUE", 14);
            editor.apply();
            SharedPreferences sharedPreferences2=getSharedPreferences("text", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor2 = sharedPreferences2.edit();
            editor2.putInt("size",20);
            editor2.putInt("line",3);
            editor2.putInt("letter",1);
            editor2.apply();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (size.length() < 1) {
                    Intent intent = new Intent(Start_Activity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Start_Activity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }).start();



    }
}