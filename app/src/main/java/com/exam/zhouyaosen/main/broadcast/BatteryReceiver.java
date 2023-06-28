package com.exam.zhouyaosen.main.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

public class BatteryReceiver extends BroadcastReceiver {
onclick onclick;

    public BatteryReceiver(BatteryReceiver.onclick onclick) {
        this.onclick = onclick;
    }

    public interface onclick{
        public void click(float battery);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // 获取电池的当前电量和总容量
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        // 计算电池电量的百分比
        float batteryPercentage = level / (float) scale * 100;
        onclick.click(batteryPercentage);
        // 将电量显示在TextView上

    }
}
