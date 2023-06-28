package com.exam.zhouyaosen.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.exam.zhouyaosen.main.R;
import com.exam.zhouyaosen.main.activity.MainActivity;
import com.exam.zhouyaosen.main.adapter.fragment.MyPagerAdapter;
import com.exam.zhouyaosen.main.fragment.f1.Fragment_f1;
import com.exam.zhouyaosen.main.network.Http_fl;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Fragment_fl extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    MyPagerAdapter adapter;
    View viewZ;
    List<Fragment> fragments=new ArrayList<>();
    String[] title=new String[]{"穿越小说","都市小说","网游小说","修真小说","玄幻小说","科幻小说","其他"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragemnt_fl,container,false);
        tabLayout=view.findViewById(R.id.tab_layout);
        viewPager=view.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);
        viewZ=view.findViewById(R.id.zw);
        RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) viewZ.getLayoutParams();
        MainActivity activity= (MainActivity) getActivity();
        lp.height=activity.getStatusBarHeight();
        tabLayout.setupWithViewPager(viewPager,false);
        if (fragments.size()==0){
            for (String s : title) {
                fragments.add(new Fragment_f1(s,getContext(),"fictionType"));
            }
            adapter=new MyPagerAdapter(getActivity().getSupportFragmentManager(),fragments);
            viewPager.setAdapter(adapter);
        }else {
            viewPager.setAdapter(adapter);
        }
        return view;
    }
}
