package com.exam.zhouyaosen.main.tool;

public class NovelContentPage {
    private int start_pos;
    private int end_pos;
    private int page_id;

    public NovelContentPage() {
    }

    public NovelContentPage(boolean isFirstPage, String title) {
        this.isFirstPage = isFirstPage;
        this.title = title;
    }

    private String page_content;
    private boolean isFirstPage;//当前页是否属于本章的首页
    private String title;//首页所包含的标题，仅在isFirstPage = true 时适用

    public int getStart_pos() {
        return start_pos;
    }

    public void setStart_pos(int start_pos) {
        this.start_pos = start_pos;
    }

    public int getEnd_pos() {
        return end_pos;
    }

    public void setEnd_pos(int end_pos) {
        this.end_pos = end_pos;
    }

    public int getPage_id() {
        return page_id;
    }

    public void setPage_id(int page_id) {
        this.page_id = page_id;
    }

    public String getPage_content() {
        return page_content;
    }

    public void setPage_content(String page_content) {
        this.page_content = page_content;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return page_content;
    }
}