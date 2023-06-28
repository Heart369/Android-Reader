package com.exam.zhouyaosen.main.adapter.recycle;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.activity.DetailActivity;
import com.exam.zhouyaosen.main.network.Http_chapter;
import com.exam.zhouyaosen.main.network.javabean.Home_bean;
import com.exam.zhouyaosen.main.sqlite.bean.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class His_adapter extends RecyclerView.Adapter<His_adapter.ViewHolder> {
    Home_bean home_bean;
    Context context;
    List<Book> data;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setData(List<Book> data) {
        this.data = data;
        Comparator<Book> comparator = Comparator.comparingLong(Book::getTime).reversed();

        // 使用Collections类的sort方法对列表进行排序
        Collections.sort(data, comparator);
    }

    public List<Book> getData() {
        return data;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public His_adapter(List<Book> home_bean, Context context) {
        data = home_bean;
        this.context = context;
        // 使用Comparator接口实现自定义比较器
        Comparator<Book> comparator = Comparator.comparingLong(Book::getTime).reversed();

        // 使用Collections类的sort方法对列表进行排序
        Collections.sort(data, comparator);
        for (Book datum : data) {
            Log.d("TAGS",datum.getTime()+","+datum.getTitle());
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_rect_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        animateTextView(holder.itemView);
        Book dataDTO = data.get(position);
        Log.d("TAG","1234567878");
        String url = dataDTO.getIcon();
        if (!url.contains("https"))
            url = url.replace("http", "https");
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
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("id",dataDTO.getId());
                intent.putExtra("title",dataDTO.getTitle());
                intent.putExtra("icon",dataDTO.getIcon());
                context.startActivity(intent);
            }
        });
        holder.wb.setText(dataDTO.getTitle());
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
            wb = itemView.findViewById(R.id.text_wb);
            tp = itemView.findViewById(R.id.image_hone);
        }
    }

    private void animateTextView(View myTextView) {
        // 初始位置在底部
        Log.d("TAG", "1235");
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

}
