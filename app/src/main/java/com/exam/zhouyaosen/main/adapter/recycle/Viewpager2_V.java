package com.exam.zhouyaosen.main.adapter.recycle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.tool.NovelContentPage;

import java.util.List;

public class Viewpager2_V extends Viewpager2_adapter {
    String t1,t2,t3;
    public Viewpager2_V(Context context, List<NovelContentPage> pagination_l, List<NovelContentPage> pagination, List<NovelContentPage> pagination_n, int po, int textColor, int textsize, int lines, float letter) {
        super(context, pagination_l, pagination, pagination_n, po, textColor, textsize, lines, letter);
    }
    public Viewpager2_V(Context context,String t1,String t2,String t3, int po, int textColor, int textsize, int lines, float letter) {
        super(context,null, null, null, po, textColor, textsize, lines, letter);
        this.t1=t1;
        this.t2=t2;
        this.t3=t3;
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public Viewpager2_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewpager2_adapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.viewpagerv,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull Viewpager2_adapter.ViewHolder holder, int position) {
        if (position==0)
       holder.text.setText(t1);
        else if (position==1)
            holder.text.setText(t2);
        else holder.text.setText(t3);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    static class ViewHolder extends Viewpager2_adapter.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.text);
        }
    }

}
