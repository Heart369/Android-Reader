package com.exam.zhouyaosen.main.adapter.recycle;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.network.Http_chapter;
import com.exam.zhouyaosen.main.network.javabean.Chatpter;

public class Lookbook_adapter extends RecyclerView.Adapter<Lookbook_adapter.ViewHolder> {
    Context context;
    Chatpter chatpter;
    onclick onclick;
        int po;

    public int getPo() {
        return po;
    }

    public void setPo(int po) {
        if (this.po!=po){
            this.po = po;
            notifyDataSetChanged();
        }

    }

    public Lookbook_adapter(int po, Context context, Chatpter chatpter, Lookbook_adapter.onclick onclick) {
        this.po=po;
        this.context = context;
        this.chatpter = chatpter;
        this.onclick = onclick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.search_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(chatpter.data.chapterList.get(position).title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                po=holder.getAdapterPosition();
                onclick.click(holder.getAdapterPosition(),holder.textView.getText().toString());
                notifyDataSetChanged();
            }
        });
        if (position==po)
            holder.textView.setTextColor(Color.BLUE);
        else  holder.textView.setTextColor(Color.BLACK);
    }

    @Override
    public int getItemCount() {
        return chatpter.data.chapterList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.title);
        }
    }
    public interface onclick{
        public void click(int po,String title);
    }
}
