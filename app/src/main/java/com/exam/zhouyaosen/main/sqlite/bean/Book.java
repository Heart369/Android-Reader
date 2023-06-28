package com.exam.zhouyaosen.main.sqlite.bean;

import java.util.Comparator;

public class Book {
    String id,title,icon;
    long time;

    public Book(String id, String title, long time, String icon) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
