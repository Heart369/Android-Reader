package com.exam.zhouyaosen.main.adapter.recycle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.network.javabean.Chatpter;

public class Detail_adapter extends RecyclerView.Adapter<Detail_adapter.ViewHolder> {
    Chatpter chatpter;
    Context context;
    onclick onclick;


    public Detail_adapter(Chatpter chatpter, Context context,onclick onclick) {
        this.chatpter = chatpter;
        this.context = context;
        this.onclick=onclick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.search_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chatpter.DataDTO.ChapterListDTO  dto=chatpter.data.chapterList.get(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onclick.click(holder.getAdapterPosition());
                }
            });
            holder.textView.setText(dto.title);
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
        public void click(int po);
   }

}
