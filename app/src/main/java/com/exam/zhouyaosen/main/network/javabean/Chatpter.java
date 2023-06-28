package com.exam.zhouyaosen.main.network.javabean;

import java.util.List;

public class Chatpter {

    public String msg;
    public int code;
    public int count;
    public DataDTO data;

    public static class DataDTO {
        public String fictionId;
        public String title;
        public String descs;
        public String cover;
        public String author;
        public String fictionType;
        public String updateTime;
        public List<ChapterListDTO> chapterList;

        public static class ChapterListDTO {
            public String title;
            public String chapterId;
        }
    }
}
