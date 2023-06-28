package com.exam.zhouyaosen.main.tool;

import android.content.Context;

public class Dp_Px {

    public  int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
        //dp_px
    }

    public  int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
        //px_dp
    }

}
