package com.exam.zhouyaosen.main.adapter.recycle;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
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

public class RecycleF1_Adapter extends RecyclerView.Adapter<RecycleF1_Adapter.ViewHolder> {
    List<Home_bean.DataDTO> dataDTOS=new ArrayList<>();
    Context context;
    onclick onclick;

    public List<Home_bean.DataDTO> getDataDTOS() {
        return dataDTOS;
    }

    public void setDataDTOS(List<Home_bean.DataDTO> dataDTOS) {
        this.dataDTOS = dataDTOS;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public RecycleF1_Adapter(List<Home_bean.DataDTO> dataDTOS, Context context,onclick onclick) {
        this.dataDTOS = dataDTOS;
        this.context = context;
        this.onclick=onclick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recy_fl,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        dh(holder.itemView);
        Home_bean.DataDTO dataDTO=dataDTOS.get(position);
        String url=dataDTO.cover;
        url=url.replaceAll("http","https");
        Glide.with(context)
                .asBitmap()
                .load(url)
                .transform(new RoundedCorners(20))
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
        holder.title.setText(dataDTO.title);
        holder.sm.setText(dataDTO.descs);
        holder.zz.setText(dataDTO.author+" · "+dataDTO.fictionType);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.click(holder.getAdapterPosition(),dataDTO,holder.tp);
            }
        });
    }

    private void dh(View itemView) {
        float startScaleX = 0f;
        float startScaleY = 0f;
        float endScaleX = 1f;
        float endScaleY = 1f;
// 创建缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                startScaleX, endScaleX, startScaleY, endScaleY,
                ScaleAnimation.RELATIVE_TO_SELF, 0f,
                ScaleAnimation.RELATIVE_TO_SELF, 0f
        );
// 设置动画持续时间
        scaleAnimation.setDuration(500);
// 启动动画
        itemView.startAnimation(scaleAnimation);
    }

    @Override
    public int getItemCount() {
        return dataDTOS.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView tp;
        TextView title,sm,zz;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tp=itemView.findViewById(R.id.image_f1);
            title=itemView.findViewById(R.id.title);
            sm=itemView.findViewById(R.id.jj);
            zz=itemView.findViewById(R.id.zz);
        }
    }
    public interface onclick{
        public void click(int po, Home_bean.DataDTO dataDTO,View v);
    }
}
