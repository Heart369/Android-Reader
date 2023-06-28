package com.exam.zhouyaosen.main.adapter.defult;

import com.geek.banner.loader.BannerEntry;

public class banner implements BannerEntry {
    int dz;

    public banner(int dz) {
        this.dz = dz;
    }

    @Override
    public Object getBannerPath() {
        return dz;
    }

    @Override
    public String getIndicatorText() {
        return null;
    }
}
