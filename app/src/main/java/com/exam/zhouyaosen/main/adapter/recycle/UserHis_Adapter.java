package com.exam.zhouyaosen.main.adapter.recycle;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.exam.zhouyaosen.main.activity.DetailActivity;
import com.exam.zhouyaosen.main.activity.UserHis_Activity;
import com.exam.zhouyaosen.main.sqlite.Book_Sqlite;
import com.exam.zhouyaosen.main.sqlite.Bookshelf_Sqlite;
import com.exam.zhouyaosen.main.sqlite.bean.Book;

import java.util.ArrayList;
import java.util.List;

public class UserHis_Adapter extends RecyclerView.Adapter<UserHis_Adapter.ViewHolder> {
    Context context;
    List<Book> allData ;
    boolean gl=false;
    boolean qx=false;
    click click;
    public List<Boolean> check;

    public void setAllData(List<Book> allData) {
        this.allData = allData;
    }

    public boolean isGl() {
        return gl;
    }

    public void setGl(boolean gl) {
        check=new ArrayList<>();
        for (int i = 0; i < allData.size(); i++) {
            check.add(false);
        }
        this.gl = gl;
    }

    public boolean isQx() {
        return qx;
    }

    public void setQx(boolean qx) {
        check=new ArrayList<>();
        for (int i = 0; i < allData.size(); i++) {
            check.add(qx);
        }
        this.qx = qx;
    }

    public void setClick(UserHis_Adapter.click click) {
        this.click = click;
    }

    public UserHis_Adapter(Context context, List<Book> allData) {
        this.context = context;
        this.allData = allData;
        check=new ArrayList<>();
        for (int i = 0; i < allData.size(); i++) {
            check.add(qx);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_recy_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book dataDTO = allData.get(position);
        Log.d("TAG","1234567878");
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("TAG",isChecked+"");
                check.set(holder.getAdapterPosition(),isChecked);
            }
        });
        if (gl)
            holder.checkBox.setVisibility(View.VISIBLE);
        else  holder.checkBox.setVisibility(View.GONE);
        if (qx)
            holder.checkBox.setChecked(true);
        else holder.checkBox.setChecked(false);
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
//                        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(holder.tp, "alpha", 0f, 1f);
//                        alphaAnimator.setDuration(500);
//                        alphaAnimator.start();
                    }
                });

        holder.wb.setText(dataDTO.getTitle());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (gl)
                    return false;
                gl=true;
                notifyDataSetChanged();
                if (click!=null)
                    click.onclick(holder.getAdapterPosition());
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gl){
                        holder.checkBox.setChecked(!holder.checkBox.isChecked());
                }else {
                    Intent intent=new Intent(context, DetailActivity.class);
                    intent.putExtra("id",dataDTO.getId());
                    intent.putExtra("title",dataDTO.getTitle());
                    intent.putExtra("icon",dataDTO.getIcon());
                    context.startActivity(intent);
                }
            }
        });
    }
    

    @Override
    public int getItemCount() {
        return allData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView wb;
        ImageView tp;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wb = itemView.findViewById(R.id.text_wb);
            tp = itemView.findViewById(R.id.image_hone);
            checkBox=itemView.findViewById(R.id.check);
            checkBox.setVisibility(View.GONE);
        }
    }
    public interface click{
     public void  onclick(int po);
    }
}
