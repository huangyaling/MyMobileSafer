package com.bella.mobilesafer.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bella.mobilesafer.R;


import java.util.ArrayList;

/**
 * Created by huangyaling on 2017/11/30.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private ArrayList<View> viewsContainer = new ArrayList<View>();

    public ViewPagerAdapter(Context context){
        View welcomeView = LayoutInflater.from(context).inflate(R.layout.viewpager_welcome,null);
        View simBindView = LayoutInflater.from(context).inflate(R.layout.viewpager_sim,null);
        View safeNumberView = LayoutInflater.from(context).inflate(R.layout.viewpager_number,null);
        View settingDoneView = LayoutInflater.from(context).inflate(R.layout.viewpager_done,null);
        viewsContainer.add(welcomeView);
        viewsContainer.add(simBindView);
        viewsContainer.add(safeNumberView);
        viewsContainer.add(settingDoneView);
    }
    //显示页面数量
    @Override
    public int getCount() {
        return viewsContainer.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    //初始化显示的条目对象
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager)container).addView(viewsContainer.get(position));
        return viewsContainer.get(position);
    }

    //销毁条目对象
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView(viewsContainer.get(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
