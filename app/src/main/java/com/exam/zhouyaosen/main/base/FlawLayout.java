package com.exam.zhouyaosen.main.base;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.tool.Dp_Px;

import java.util.ArrayList;
import java.util.List;

public class FlawLayout extends ViewGroup {
    List<String> data = new ArrayList<>();
    Dp_Px dp_px=new Dp_Px();
    OnClickListener onClickListener;
    OnClickListener onClickListener_long;
    int padding_left=10;
    int padding_top=5;
    int padding_right=10;
    int padding_bottom=5;

    public void setPadding_left(int padding_left) {
        this.padding_left = padding_left;
    }

    public void setPadding_top(int padding_top) {
        this.padding_top = padding_top;
    }

    public void setPadding_right(int padding_right) {
        this.padding_right = padding_right;
    }

    public void setPadding_bottom(int padding_bottom) {
        this.padding_bottom = padding_bottom;
    }

    private List<List<View>> lines = new ArrayList<>();

    public FlawLayout(Context context) {
        this(context, null);
    }

    public FlawLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlawLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size_width = MeasureSpec.getSize(widthMeasureSpec);
        int size_height = MeasureSpec.getSize(heightMeasureSpec);
        int count = getChildCount();
        if (count == 0)
            return;
        int Childwidth = MeasureSpec.makeMeasureSpec(size_width, MeasureSpec.AT_MOST);
        int Childheigth = MeasureSpec.makeMeasureSpec(size_height, MeasureSpec.AT_MOST);
        lines.clear();
        List<View> line = new ArrayList<>();
        lines.add(line);
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, Childwidth, Childheigth);
            if (line.size() == 0) {
                line.add(child);
            } else {
                boolean h = check(line, child, size_width);
                if (h) {
                    line.add(child);
                } else {
                    line = new ArrayList<>();
                    line.add(child);
                    lines.add(line);
                }
            }
        }
        int height = getChildAt(0).getMeasuredHeight();
        height=height * lines.size()+ dp_px.dip2px(getContext(),padding_top+padding_bottom)*lines.size();
        setMeasuredDimension(size_width,height );

    }

    private boolean check(List<View> line, View child, int parent) {
        int wei = child.getMeasuredWidth();
        int hz = 0;
        int weight=dp_px.dip2px(getContext(),padding_left+padding_right);
        for (View view : line) {
            hz += view.getMeasuredWidth()+weight;
        }
        return (wei + hz) <= parent+weight;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        if (count==0)
            return;
        View first=getChildAt(0);
        int padding_l=dp_px.dip2px(getContext(),padding_left);
        int padding_t=dp_px.dip2px(getContext(),padding_top);
        int padding_b=dp_px.dip2px(getContext(),padding_bottom);
        int padding_r= dp_px.dip2px(getContext(),padding_right);
        int left = padding_l;
        int top = padding_t;
        int right = 0;
        int bottom = first.getMeasuredHeight()+padding_b;
        Log.d("TAG",lines.size()+"");
        for (List<View> line : lines) {
            for (View view : line) {
                int weight = view.getMeasuredWidth();
                int height = view.getMeasuredHeight();
                right += weight+padding_l;
                if (right+padding_l>getMeasuredWidth()){
                    right-=padding_r;
                    Log.d("TAG",left+","+getMeasuredWidth());
                }

                view.layout(left, top, right, bottom);
                left=right+padding_l;
            }
            top+=first.getMeasuredHeight()+padding_t;
            left=padding_l;
            right=0;
            bottom+=first.getMeasuredHeight()+padding_b;
        }
    }

    public void SetData(List<String> data) {
        this.data.clear();
        this.data.addAll(data);
        Create();
    }

    private void Create() {
        removeAllViews();
        for (String text : data) {
            TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.mytext,null,false);
            textView.setText(text);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener!=null){
                        onClickListener.onClick(v);
                    }
                }
            });
            textView.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    removeViewInLayout(v);
                    if (onClickListener_long!=null)
                        onClickListener_long.onClick(v);
                    invalidate();
                    return true;
                }
            });
            // TODO 此处设置更多的属性
            addView(textView);
        }
    }

    public void setOnclick(OnClickListener onclick){
        this.onClickListener=onclick;
    }
    public void setOnLongClick(OnClickListener onclick){
        this.onClickListener_long=onclick;
    }
}
