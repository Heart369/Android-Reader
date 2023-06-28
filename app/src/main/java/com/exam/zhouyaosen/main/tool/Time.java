package com.exam.zhouyaosen.main.tool;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Time {
String year, month,day,week,time,hour, mini;

    public Time() {

        SimpleDateFormat myFmt3 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E ");
        Date now = new Date();
        String data =myFmt3.format(now);
        year = data.substring(0,5);
        month = data.substring(5,8);
        day = data.substring(8,11);
        time = data.substring(11,21);
        hour =data.substring(12,14);
        mini=data.substring(15,17);
        week = data.substring(22,24);
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getWeek() {
        return week;
    }

    public String getTime() {
        return time;
    }

    public String getHour() {
        return hour;
    }

    public String getmini() {
        return mini;
    }
}