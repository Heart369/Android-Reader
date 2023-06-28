package com.exam.zhouyaosen.main.activity;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.base.BaseActivity;
import com.exam.zhouyaosen.main.sqlite.Uset_SQLite;
import com.exam.zhouyaosen.main.tool.Lucency;
import com.exam.zhouyaosen.main.tool.Time;

public class LoginActivity extends BaseActivity {
    LinearLayout login;
    EditText pass, email;
    TextView text_zc, title, title_2;
    Button button;
    SQLiteDatabase database;
    boolean flag = true;
    Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Lucency lucency = new Lucency();
        lucency.light(this);
        Uset_SQLite sqLite = new Uset_SQLite(this, "uset.bd", null, 1);
       database = sqLite.getWritableDatabase();
        login = findViewById(R.id.linlay_inputs);
        pass = findViewById(R.id.password);
        email = findViewById(R.id.email);
        title = findViewById(R.id.title);
        title_2 = findViewById(R.id.title_2);
        button = findViewById(R.id.login_button);
        text_zc = findViewById(R.id.tv_forgotPassword);
        text_zc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setText("");
                pass.setText("");
                if (flag) {
                    text_zc.setText("已有账户？单击登录");
                    flag = false;
                    title_2.setText("注册");
                    button.setText("注册并登录");
                } else {
                    text_zc.setText("没有账号？点击此处注册");
                    flag = true;
                    title_2.setText("登录");
                    button.setText("Login");
                }

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = email.getText().toString();
                String p = pass.getText().toString();
                if (e.length() == 0 || p.length() == 0) {
                    Toast.makeText(LoginActivity.this, "请输入账户或密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!e.contains("@")) {
                    Toast.makeText(LoginActivity.this, "请输入正确的邮箱格式", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (flag) {
                    Cursor cursor = database.query("js", new String[]{"uid", "pass"}, "uid=?", new String[]{e}, null, null, null);
                    cursor.moveToFirst();
                    if (cursor.getCount() == 0)
                        Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                    else {
                      String password=cursor.getString(cursor.getColumnIndex("pass"));
                      if (password.equals(p)){
                          SharedPreferences preferences=getSharedPreferences("userdata",MODE_PRIVATE);
                          preferences.edit().putString("cookie",e).apply();
                          Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                          startActivity(intent);
                          finish();
                      }else {
                          Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                      }
                    }
                } else {
                    if (p.length()<6){
                        Toast.makeText(LoginActivity.this, "密码必须大于6位", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Cursor cursor = database.query("js", new String[]{"uid", "pass"}, "uid=?", new String[]{e}, null, null, null);
                    cursor.moveToFirst();
                    ContentValues values = new ContentValues();
                    values.put("uid", e);
                    values.put("pass", p);
                    if (cursor.getCount()==0){
                        values.put("name","若叶（默认)");
                        database.insert("js", null, values);
                        Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    }else {
                        database.update("js",values,"uid=?",new String[]{e});
                    }
                    SharedPreferences preferences=getSharedPreferences("userdata",MODE_PRIVATE);
                    Time time=new Time();
                    preferences.edit().putString("cookie",e).apply();
                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }

    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }
}