package com.exam.zhouyaosen.main.adapter.recycle;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.network.javabean.Home_bean;

import java.util.ArrayList;
import java.util.List;

public class Home_adapter extends RecyclerView.Adapter<Home_adapter.ViewHolder> {
    Home_bean home_bean;
    Context context;
    List<Home_bean.DataDTO> data=new ArrayList<>();
    recy_int recy_int;

    public void setRecy_int(Home_adapter.recy_int recy_int) {
        this.recy_int = recy_int;
    }

    public List<Home_bean.DataDTO> getData() {
        return data;
    }

    public void setData(List<Home_bean.DataDTO> data) {
        this.data.addAll(data);
    }

    public Home_adapter(List<Home_bean.DataDTO> home_bean, Context context) {
        this.data.addAll(home_bean);
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.home_rect_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        animateTextView(holder.itemView);
        Home_bean.DataDTO dataDTO=data.get(position);
        String url=dataDTO.cover;
        url=url.replace("http","https");
        Glide.with(context)
                .asBitmap()
                .load(url)
                .transform(new RoundedCorners(15))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                       holder.tp.setImageBitmap(resource);
                        // 执行淡入动画
                        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(holder.tp, "alpha", 0f, 1f);
                        alphaAnimator.setDuration(500);
                        alphaAnimator.start();
                    }
                });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recy_int!=null)
                    recy_int.onclick(holder.getAdapterPosition(),dataDTO,holder.tp);
            }
        });
        holder.wb.setText(dataDTO.title);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView wb;
        ImageView tp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wb=itemView.findViewById(R.id.text_wb);
            tp=itemView.findViewById(R.id.image_hone);
        }
    }

    private void animateTextView(View myTextView) {
        // 初始位置在底部
        Log.d("TAG","1235");
        myTextView.setTranslationY(myTextView.getHeight());

        // 创建透明度动画，从0变为1
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(myTextView, "alpha", 0.2f, 1f);
        alphaAnimator.setDuration(1000);

        // 创建移动动画，从底部向上移动
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(myTextView, "translationY", myTextView.getHeight(), 0f);
        translationAnimator.setDuration(1000);
        translationAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        // 创建动画集合，将透明度和移动动画组合起来
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaAnimator, translationAnimator);
        animatorSet.start();
    }
    public interface recy_int{
        public void onclick(int po, Home_bean.DataDTO dataDTO,View v);
    }
}
