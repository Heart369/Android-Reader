package com.exam.zhouyaosen.main.network;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Http_fl extends Thread{
    String  option,key,from,size;
    Http http;

    public Http_fl(String option, String key, String from, String size, Http http) {
        this.option = option;
        this.key = key;
        this.from = from;
        this.size = size;
        this.http = http;
    }

    @Override
    public void run() {
        OkHttpClient httpClient=new OkHttpClient();
        Request getRequest = new Request.Builder()
                .url("https://api.pingcc.cn/fiction/search/"+option+"/"+key+"/"+from+"/"+size)
                .get()
                .build();
        Call call= httpClient.newCall(getRequest);
        Random random=new Random();
        double d=random.nextDouble();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                http.resp(null,false);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data=response.body().string();
                    if (data.contains("请求过于")){
                        try {
                            Thread.sleep((long) (d*2000));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        run();
                    }
                    else
                    http.resp(data,true);
            }
        });
    }

   public interface  Http{
        public void resp(String data,boolean flag);
   }

    public void setOption(String option) {
        this.option = option;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getOption() {
        return option;
    }

    public String getKey() {
        return key;
    }

    public String getFrom() {
        return from;
    }

    public String getSize() {
        return size;
    }

    public Http getHttp() {
        return http;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setHttp(Http http) {
        this.http = http;
    }
}
