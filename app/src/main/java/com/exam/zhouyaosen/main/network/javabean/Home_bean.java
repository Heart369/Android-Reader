package com.exam.zhouyaosen.main.network.javabean;

import java.util.List;

public class Home_bean {

    public String msg;
    public int code;
    public int count;
    public List<DataDTO> data;

    public static class DataDTO {
        public String fictionId;
        public String title;
        public String author;
        public String fictionType;
        public String descs;
        public String cover;
        public String updateTime;
    }
}
