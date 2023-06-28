package com.exam.zhouyaosen.main.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.activity.DetailActivity;
import com.exam.zhouyaosen.main.activity.MainActivity;
import com.exam.zhouyaosen.main.activity.SearchActivity;
import com.exam.zhouyaosen.main.adapter.defult.banner;
import com.exam.zhouyaosen.main.adapter.recycle.Home_adapter;
import com.exam.zhouyaosen.main.network.javabean.Home_bean;
import com.exam.zhouyaosen.main.tool.Recy_item_jj;
import com.geek.banner.Banner;
import com.geek.banner.loader.BannerEntry;
import com.geek.banner.loader.ImageLoader;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragment_main extends Fragment {
    RecyclerView recyclerView;
    Home_adapter adapter;
    RefreshLayout refreshLayout;
    List<Home_bean.DataDTO> dataDTOS=new ArrayList<>();
    LottieAnimationView lottie;
    Banner banner;
    int i=2;
    LinearLayout search;
    LinearLayout layout;
    View zw;
    List<com.exam.zhouyaosen.main.adapter.defult.banner> banners=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragemnt_home, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        layout=view.findViewById(R.id.linearLayout);
        lottie=view.findViewById(R.id.lottie_jz);
        banners.add(new banner(R.drawable.lbt_1));
        banners.add(new banner(R.drawable.lbt_2));
        banners.add(new banner(R.drawable.lbt_3));
        search=view.findViewById(R.id.search);
        zw=view.findViewById(R.id.zw);
        MainActivity mainActivity= (MainActivity) getActivity();
        RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) zw.getLayoutParams();
        lp.height=mainActivity.getStatusBarHeight();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);

                // 设置共享元素的转场名称
                String transitionName = "myTransition";
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), layout, transitionName);

                startActivity(intent, options.toBundle());
            }
        });
        if (dataDTOS.size()!=0)
            setRecyclerView();
        refreshLayout=view.findViewById(R.id.head);
        BezierRadarHeader header=new BezierRadarHeader(getContext());
        header.setAccentColor(Color.YELLOW);
        header.setPrimaryColor(0xFF51B6FA);
        refreshLayout.setRefreshHeader(header);
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                MainActivity activity= (MainActivity) getActivity();
                assert activity != null;
                activity.initdata(i++);
                adapter=null;
                dataDTOS=new ArrayList<>();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
               MainActivity activity= (MainActivity) getActivity();
                assert activity != null;
                activity.initdata(i++);

            }
        });
        banner=view.findViewById(R.id.banner);
        LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) banner.getLayoutParams();
        MainActivity activity= (MainActivity) getActivity();
        layoutParams.height= (int) (activity.getWidth()/2);
        banner.setBannerLoader(new ImageLoader() {
            @Override
            public void loadView(Context context, BannerEntry entry, int position, View imageView) {
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.drawable.lbt_1)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                ImageView imageView1= (ImageView) imageView;
                imageView1.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(context).load(entry.getBannerPath()).transform(new RoundedCorners(25)).apply(requestOptions).into(imageView1);
            }
        });

        banner.loadImagePaths(banners);
        return view;
    }
    public void Setdata(Home_bean bean){
        if (lottie.getAlpha()>0.1){
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(lottie, "alpha", 1f, 0f);
            alphaAnimator.setDuration(1000);
            alphaAnimator.start();
        }
        dataDTOS.addAll(bean.data);
        if (adapter==null)
            setRecyclerView();
        else {
            adapter.setData(bean.data);
            adapter.notifyItemRangeInserted(adapter.getData().size()-30,adapter.getData().size());
            Log.d("TAG","已经刷新"+dataDTOS.size());
            refreshLayout.finishLoadMore(true);//传入false表示加载失败
        }
    }
    public void setRecyclerView(){
        if (refreshLayout.isRefreshing()){
            refreshLayout.finishRefresh(true);//传入false表示刷新失败
        }
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        adapter=new Home_adapter(dataDTOS,getContext());
        stringIntegerHashMap.put(Recy_item_jj.LEFT_DECORATION, 7);
        stringIntegerHashMap.put(Recy_item_jj.TOP_DECORATION, 5);
        stringIntegerHashMap.put(Recy_item_jj.RIGHT_DECORATION, 7);
        recyclerView.addItemDecoration(new Recy_item_jj(stringIntegerHashMap));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setAdapter(adapter);
        adapter.setRecy_int(new Home_adapter.recy_int() {
            @Override
            public void onclick(int po, Home_bean.DataDTO dataDTO,View v) {
               Intent intent=new Intent(getActivity(), DetailActivity.class);
               intent.putExtra("id",dataDTO.fictionId);
               intent.putExtra("title",dataDTO.title);
               intent.putExtra("icon",dataDTO.cover);
               startActivity(intent);
            }
        });
    }
    public void  Nointer(){
        if (refreshLayout.isRefreshing()){
            refreshLayout.finishRefresh(false);//传入false表示刷新失败
        }
        if (refreshLayout.isLoading()){
            refreshLayout.finishLoadMore(true);//传入false表示加载失败
        }
        if (lottie.getAlpha()>0.5)
        lottie.setAnimation(R.raw.fouronefour);
        lottie.playAnimation();
    }

    @Override
    public void onResume() {

        super.onResume();
    }
}
