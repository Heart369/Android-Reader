package com.exam.zhouyaosen.main.adapter.recycle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.base.BatteryView;
import com.exam.zhouyaosen.main.tool.Dp_Px;
import com.exam.zhouyaosen.main.tool.NovelContentPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Viewpager2_adapter extends RecyclerView.Adapter<Viewpager2_adapter.ViewHolder> {
    Context context;
    public List<NovelContentPage> pagination = new ArrayList<>();
    public List<NovelContentPage> p1;
    public List<NovelContentPage> p2;
    public List<NovelContentPage> p3;
    String title;
    int maxsize;
    int size;
    float evenx=0,eveny=0;
    int weight;
    Click click;
    int po;
    int textColor;
    int textSize;
    String time="12:00";
    int battery=60;
    float lines;
    float letter;

    public void setLetter(float letter) {
        this.letter = letter;
    }

    public float getLines() {
        return lines;
    }

    public void setLines(float lines) {
        this.lines = dp_px.dip2px(context,lines);
    }

    Dp_Px dp_px=new Dp_Px();
    public void setTime(String time) {
        if (!Objects.equals(time, this.time)){
            this.time = time;
           notifyDataSetChanged();
        }

    }

    public void setBattery(int battery) {
        if (battery!=this.battery){
            this.battery = battery;
            notifyDataSetChanged();
        }

    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;

    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setClick(Click click) {
        this.click = click;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Viewpager2_adapter(Context context, List<NovelContentPage> pagination_l, List<NovelContentPage> pagination, List<NovelContentPage> pagination_n,int po,int textColor,int textsize,int lines,float letter) {
        this.context = context;
        this.pagination.addAll(pagination_l);
        this.pagination.addAll(pagination);
        this.pagination.addAll(pagination_n);
        p1 = pagination_l;
        p2 = pagination;
        p3 = pagination_n;
        title = pagination.get(0).getTitle();
        maxsize = pagination.size();
        size = 0;
        this.po=po;
        this.textColor=textColor;
        this.textSize=textsize;
        this.lines=lines;
        this.lines=dp_px.dip2px(context,lines);
        this.letter=letter;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == p1.size() || position == p1.size() + p2.size())
            return 0;
        else return 1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (getItemViewType(viewType) == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.lookbook_home, parent, false);
        } else view = LayoutInflater.from(context).inflate(R.layout.lookbook_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position < p1.size()) {
            title = p1.get(0).getTitle();
            maxsize=p1.size()-1;
            po=-1;
            size=holder.getAdapterPosition();
        } else if (position > p1.size() + p2.size()) {
            title = p3.get(0).getTitle();
            maxsize=p3.size()-1;
            size=holder.getAdapterPosition()-p1.size()-p2.size();
            po=1;
        } else {
            title = p2.get(0).getTitle();
            maxsize=p2.size()-1;
            size=holder.getAdapterPosition()-p1.size();
            po=0;
        } if (position==0){
            if (p1.size()>0)
            holder.text.setText(p1.get(0).getTitle());
            else holder.text.setText(p2.get(0).getTitle());
            holder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
            holder.text.setLineSpacing(0,1.0f);
            holder.text.setLetterSpacing(0);
        }else
        if (position == p1.size()){
            holder.text.setText(pagination.get(position).getTitle());
            holder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
            holder.text.setLineSpacing(0,1.0f);
            holder.text.setLetterSpacing(0);
        }
        else if (position == p1.size() + p2.size()){
            holder.text.setText(pagination.get(position).getTitle());
            holder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
            holder.text.setLineSpacing(0,1.0f);
            holder.text.setLetterSpacing(0);
        }

        else{
            if (pagination.get(position).getPage_content().contains("我是一串测试文本"))
                pagination.get(position).setPage_content(pagination.get(position).getPage_content().replace("  我是一串测试文本，我是一串测试文本，我是一串测试文本，我是一串测试文本，我是一串测试文本，我是一串测试文本，",pagination.get(position).getTitle()+"\n\n"));
            holder.text.setText(pagination.get(position).getPage_content());
            holder.text.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
            holder.text.setLineSpacing(lines,1.0f);
            holder.text.setLetterSpacing(letter);
        }

        holder.title.setText(title);
        holder.zj.setText("当前章节进度"+size+"/"+maxsize);
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getX()==evenx&&eveny==event.getY()){
                    if (click==null)
                        return true;
                    if (evenx<weight/3){
                        click.Onclick(0);
                    }else if (evenx>weight/3*2){
                        click.Onclick(2);
                    }else {
                        click.Onclick(1);
                    }
                    evenx=0;eveny=0;
                }else {
                    evenx= event.getX();
                    eveny=event.getY();
                }
                return true;
            }
        });
        holder.zj.setTextColor(textColor);
        holder.title.setTextColor(textColor);
        holder.text.setTextColor(textColor);
        holder.time.setText(time);
        holder.batteryView.setBatteryLevel(battery);
        holder.time.setTextColor(textColor);
        holder.batteryView.setBatteryColor(textColor);
        click.Onpo(po);
    }

    @Override
    public int getItemCount() {
        return pagination.size();
    }

    public void setnext(List<NovelContentPage> pages) {
        pagination.addAll(pages);
        pagination.removeAll(p1);
        p1 = p2;
        p2 = p3;
        p3 = pages;
    }

    public void setdown(List<NovelContentPage> pages) {
        pagination.addAll(0, pages);
        pagination.removeAll(p3);
        p3 = p2;
        p2 = p1;
        p1 = pages;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text, title,zj,time;
        BatteryView batteryView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            batteryView=itemView.findViewById(R.id.batter);
            time=itemView.findViewById(R.id.time);
            text = itemView.findViewById(R.id.text);
            title = itemView.findViewById(R.id.title);
            zj=itemView.findViewById(R.id.zj);
        }
    }
    public interface Click{
        public void Onclick(int i);
        public void Onpo(int i);
    }
    public void  InitData( List<NovelContentPage> pagination_l, List<NovelContentPage> pagination, List<NovelContentPage> pagination_n){
        this.pagination=new ArrayList<>();
        this.pagination.addAll(pagination_l);
        this.pagination.addAll(pagination);
        this.pagination.addAll(pagination_n);
        p1 = pagination_l;
        p2 = pagination;
        p3 = pagination_n;
    }
}
