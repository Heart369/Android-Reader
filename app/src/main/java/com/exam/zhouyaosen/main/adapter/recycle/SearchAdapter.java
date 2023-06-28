package com.exam.zhouyaosen.main.adapter.recycle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.network.javabean.Home_bean;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    Context context;
    List<Home_bean.DataDTO> data;
    onclick onclick;
    public void setData(List<Home_bean.DataDTO> data) {
        this.data = data;
    }

    public SearchAdapter(Context context, List<Home_bean.DataDTO> data, SearchAdapter.onclick onclick) {
        this.context = context;
        this.data = data;
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
        holder.textView.setText(data.get(position).title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.click(holder.getAdapterPosition(),holder.textView.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
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
