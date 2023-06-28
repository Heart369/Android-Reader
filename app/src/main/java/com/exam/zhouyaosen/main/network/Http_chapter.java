package com.exam.zhouyaosen.main.network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Http_chapter extends Thread{
    String  fictionId;
    Handler handler;

    public Http_chapter(String fictionId, Handler handler) {
        this.fictionId = fictionId;
        this.handler = handler;
    }

    @Override
    public void run() {
        OkHttpClient httpClient=new OkHttpClient();
        Request getRequest = new Request.Builder()
                .url("https://api.pingcc.cn/fictionChapter/search/"+fictionId)
                .get()
                .build();
        Call call= httpClient.newCall(getRequest);
        Random random=new Random();
        double d=random.nextDouble();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Message message=new Message();
                message.what=2;
                handler.sendMessage(message);
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
                else {
                    Message message=new Message();
                    message.what=1;
                    message.obj= data;
                    handler.sendMessage(message);
                }

            }
        });
    }

}
