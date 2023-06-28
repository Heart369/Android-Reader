package com.exam.zhouyaosen.main.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeChangeReceiver extends BroadcastReceiver {

   click click;

    public TimeChangeReceiver(TimeChangeReceiver.click click) {
        this.click = click;
    }

    public interface click {
        public void onclick(String time);
}
    @Override
    public void onReceive(Context context, Intent intent) {
        // 获取当前时间
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentTime = sdf.format(new Date(currentTimeMillis));
        // 在TextView上显示当前时间
        click.onclick(currentTime);
    }
}
