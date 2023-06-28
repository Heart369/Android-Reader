package com.exam.zhouyaosen.main.adapter.fragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class SearchPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    String[] title;

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    public SearchPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments,String[] title) {
        super(fragmentManager);
        this.fragmentList = fragments;
        this.title=title;
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
