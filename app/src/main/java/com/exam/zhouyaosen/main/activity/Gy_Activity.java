package com.exam.zhouyaosen.main.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.base.BaseActivity;
import com.exam.zhouyaosen.main.base.Unfold;


public class Gy_Activity extends BaseActivity implements View.OnClickListener {
    RelativeLayout r1;
    LinearLayout qq, wx, bilibili, email, fx;
    Unfold unfold;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gy);
        TextView t = findViewById(R.id.version);

        bindid();
        setToolbar(R.id.toolbar_gy, R.id.title_gy, "关于");

        Glide.with(this)
                .load(R.drawable.ntm_ct)
                .transform(new RoundedCorners(50))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        r1.setBackground(resource);
                    }
                });


    }

    private void bindid() {
        qq = findViewById(R.id.qq);
        qq.setOnClickListener(this);
        wx = findViewById(R.id.wx);
        wx.setOnClickListener(this);
        r1 = findViewById(R.id.bbzd_re);
        bilibili = findViewById(R.id.bilibili);
        bilibili.setOnClickListener(this);
        email = findViewById(R.id.email);
        email.setOnClickListener(this);
        fx = findViewById(R.id.fx);
        fx.setOnClickListener(this);
        unfold = findViewById(R.id.u4);

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qq:
                try {
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=1306433549";
                    //uin是发送过去的qq号码
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Gy_Activity.this, "惊,竟然没有QQ？", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.wx:
                try {
                    Intent intent = new Intent();
                    ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(cmp);
                    // 获取系统剪贴板
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建一个 ClipData 对象，存储要写入剪切板的数据
                    ClipData clip = ClipData.newPlainText("label", "gg1306433549");
// 将 ClipData 对象写入系统剪贴板
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(this, "已写入剪切板", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } catch (Exception e) {
                    //若无法正常跳转，在此进行错误处理
                    Toast.makeText(Gy_Activity.this, "我的妈呀！都啥年代了，你居然没装微信！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bilibili:
                String url = "https://space.bilibili.com/323744711?spm_id_from=333.1007.0.0";
                //uin是发送过去的qq号码
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                break;
            case R.id.email:
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // 设置数据为发送电子邮件
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"a1306433549@163.com"}); // 添加电子邮件收件人
                startActivity(Intent.createChooser(intent, "Send email"));
                break;
            case R.id.fx:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "一款功能超全的原神辅助app快来试试吧");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share to..."));
        }
    }
}