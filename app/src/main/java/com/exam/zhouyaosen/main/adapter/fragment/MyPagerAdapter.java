package com.exam.zhouyaosen.main.adapter.fragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    String[] title=new String[]{"穿越小说","都市小说","网游小说","修真小说","玄幻小说","科幻小说","其他"};
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    public MyPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
        super(fragmentManager);
        this.fragmentList = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
