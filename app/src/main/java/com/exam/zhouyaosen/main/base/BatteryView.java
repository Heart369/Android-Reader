package com.exam.zhouyaosen.main.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BatteryView extends View {
    private static final int MAX_LEVEL = 100;
    private static final int MIN_LEVEL = 0;
    private int batteryLevel;
    private int batteryColor;

    public BatteryView(Context context) {
        super(context);
        init(null);
    }

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BatteryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        // 初始化操作，例如获取自定义属性
        batteryLevel = MAX_LEVEL;
        batteryColor = Color.BLACK; // 默认颜色为绿色
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 获取画布的宽度和高度
        int width = getWidth();
        int height = getHeight();

        // 计算电量图标的宽度和高度
        int batteryWidth = width - getPaddingLeft() - getPaddingRight();
        int batteryHeight = height - getPaddingTop() - getPaddingBottom();

        // 计算电量图标的绘制区域
        int batteryLeft = getPaddingLeft();
        int batteryTop = getPaddingTop();
        int batteryRight = batteryLeft + batteryWidth;
        int batteryBottom = batteryTop + batteryHeight;

        // 绘制电池外框
        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(2f);
        canvas.drawRect(batteryLeft, batteryTop, batteryRight, batteryBottom, borderPaint);

        // 计算电量填充区域的宽度
        int levelWidth = (int) ((batteryWidth - 4) * batteryLevel / 100f);

        // 绘制电量填充区域
        Paint levelPaint = new Paint();
        levelPaint.setStyle(Paint.Style.FILL);
        levelPaint.setColor(batteryColor); // 使用设置的颜色
        canvas.drawRect(batteryLeft + 2, batteryTop + 2, batteryLeft + 2 + levelWidth, batteryBottom - 2, levelPaint);
    }

    public void setBatteryLevel(int level) {
        if (level < MIN_LEVEL) {
            batteryLevel = MIN_LEVEL;
        } else if (level > MAX_LEVEL) {
            batteryLevel = MAX_LEVEL;
        } else {
            batteryLevel = level;
        }
        invalidate(); // 重新绘制视图
    }

    public void setBatteryColor(int color) {
        batteryColor = color;
        invalidate(); // 重新绘制视图
    }
}
