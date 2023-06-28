package com.exam.zhouyaosen.main.fragment.f1;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.activity.DetailActivity;
import com.exam.zhouyaosen.main.activity.MainActivity;
import com.exam.zhouyaosen.main.adapter.recycle.RecycleF1_Adapter;
import com.exam.zhouyaosen.main.network.Http_fl;
import com.exam.zhouyaosen.main.network.javabean.Home_bean;
import com.google.gson.Gson;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_f1 extends Fragment {
    String title;
    Context context;
    RecyclerView recyclerView;
    RecycleF1_Adapter adapter;
    LottieAnimationView animationView;
    SmartRefreshLayout refreshLayout;
    List<Home_bean.DataDTO> dataDTOS=new ArrayList<>();
    int i=1;
    String flag;

    public Fragment_f1(String title, Context context,String flag) {
        this.title = title;
        this.context=context;
        this.flag=flag;
    }

    public Fragment_f1() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_f2,container,false);
        recyclerView=view.findViewById(R.id.recycler);
        refreshLayout=view.findViewById(R.id.head);
        animationView=view.findViewById(R.id.lottie_jz_2);
        BezierRadarHeader header=new BezierRadarHeader(getContext());
        header.setAccentColor(Color.YELLOW);
        header.setPrimaryColor(0xFF51B6FA);
        refreshLayout.setRefreshHeader(header);
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                request();
                adapter=null;
                dataDTOS=new ArrayList<>();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                request_jz();
            }
        });

        if (dataDTOS.size()==0)
            request();
        else {
            animationView.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    private void request() {
        Http_fl fl=new Http_fl(flag, title, String.valueOf(i++), "30", new Http_fl.Http() {
            @Override
            public void resp(String data, boolean flag) {
                if (refreshLayout.isRefreshing())
                    refreshLayout.finishRefresh(true);
                if (data!=null&&data.contains("</title></head>"))
                    return;
                Gson gson=new Gson();
                Home_bean bean=gson.fromJson(data,Home_bean.class);
                if (flag&&bean.data!=null){

                    dataDTOS.addAll(bean.data);
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            if (animationView.getAlpha()>0.1){
                                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(animationView, "alpha", 1f, 0f);
                                alphaAnimator.setDuration(1000);
                                alphaAnimator.start();
                            }
                            adapter=new RecycleF1_Adapter(dataDTOS, context, new RecycleF1_Adapter.onclick() {
                                @Override
                                public void click(int po, Home_bean.DataDTO dataDTO,View v) {
                                    Intent intent=new Intent(getActivity(), DetailActivity.class);
                                    intent.putExtra("id",dataDTO.fictionId);
                                    intent.putExtra("title",dataDTO.title);
                                    intent.putExtra("icon",dataDTO.cover);
                                    String transitionName = "myImage";
                                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), v, transitionName);
                                    startActivity(intent, options.toBundle());
                                }
                            });
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                        }
                    });
                }else {
                    animationView.post(new Runnable() {
                        @Override
                        public void run() {
                            animationView.setAnimation(R.raw.fouronefour);
                            animationView.playAnimation();
                        }
                    });

                }

            }
        });
        fl.start();
    }
    private void request_jz() {
        Http_fl fl=new Http_fl(flag, title, String.valueOf(i++), "30", new Http_fl.Http() {
            @Override
            public void resp(String data, boolean flag) {
                Gson gson=new Gson();
                Home_bean bean=gson.fromJson(data,Home_bean.class);
                if (flag&&bean.data!=null){
                    int s=dataDTOS.size();
                    dataDTOS.addAll(bean.data);
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.setDataDTOS(dataDTOS);
                            adapter.notifyItemChanged(s,dataDTOS.size());
                            refreshLayout.finishLoadMore(true);
                        }
                    });
                }else {
                    refreshLayout.finishLoadMore(false);
                    refreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
        fl.start();
    }
}
