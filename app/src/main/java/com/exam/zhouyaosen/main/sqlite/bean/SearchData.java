package com.exam.zhouyaosen.main.sqlite.bean;

public class SearchData {
    String title,time;

    public SearchData(String title, String time) {
        this.title = title;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }
}
