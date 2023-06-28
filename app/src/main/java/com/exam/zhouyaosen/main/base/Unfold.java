package com.exam.zhouyaosen.main.base;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.exam.zhouyaosen.main.R;


public class Unfold extends ConstraintLayout {
    ConstraintLayout layout;
    TextView wb, title;
    ImageView imageView;
    Spanned plaintext;

    public void setPlaintext_url(Spanned plaintext) {
        this.plaintext = plaintext;
        wb.setText(plaintext);
    }

    public Unfold(@NonNull Context context) {
        this(context, null);
    }

    public Unfold(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Unfold(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.unfold, this, true);
        bindid();
        csh(context, attrs);
        bindclick();
    }

    private void csh(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Unfold);
        String title_text = typedArray.getString(R.styleable.Unfold_text);
        title.setText(title_text);
        String wb_text = typedArray.getString(R.styleable.Unfold_plaintext);
        wb.setText(wb_text);
        Drawable drawable = typedArray.getDrawable(R.styleable.Unfold_title_back);
        layout.setBackground(drawable);
        typedArray.recycle();
    }

    private void bindclick() {
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wb.getVisibility() == GONE) {
                    wb.setVisibility(View.INVISIBLE);
                    wb.post(new Runnable() {
                        @Override
                        public void run() {
                            wb.setVisibility(View.VISIBLE);
                            expandView(wb, 30, wb.getHeight(), 500, 1);
                        }
                    });
                } else {
                    expandView(wb, wb.getHeight(), 0, 500, 0);
                }
            }
        });
    }

    private void bindid() {
        layout = findViewById(R.id.constraintLayout);
        wb = findViewById(R.id.wb);
        imageView = findViewById(R.id.imageView);
        title = findViewById(R.id.title);
    }

    public void expandView(final View view, final int startHeight, final int endHeight, int duration, int flag) {
        // 创建 ValueAnimator 对象
        ValueAnimator animator = ValueAnimator.ofInt(startHeight, endHeight);
        // 设置动画时间
        animator.setDuration(duration);
        if (flag == 0) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(500); // 设置动画时间为1秒
            alphaAnimation.setFillAfter(true);
            wb.startAnimation(alphaAnimation); // 启动动画
            RotateAnimation rotateAnimation = new RotateAnimation(180, 0,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(600);
            rotateAnimation.setFillAfter(true);
            imageView.startAnimation(rotateAnimation);

        } else {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(500); // 设置动画时间为1秒
            alphaAnimation.setFillAfter(true);
            wb.startAnimation(alphaAnimation); // 启动动画
            RotateAnimation rotateAnimation = new RotateAnimation(0, 180,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(600);
            rotateAnimation.setFillAfter(true);
            imageView.startAnimation(rotateAnimation);

        }
        // 添加动画监听器
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams lp = view.getLayoutParams();
                lp.height = height;
                view.setLayoutParams(lp);

            }
        });

        // 开始动画
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (flag == 0)
                    view.setVisibility(GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        // 显示textView
    }

    public int getTextViewHeight(TextView textView) {
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        int targetHeight = textView.getMeasuredHeight();
        // 临时让TextView测量一下换行后的宽高

        return targetHeight;
    }
}
