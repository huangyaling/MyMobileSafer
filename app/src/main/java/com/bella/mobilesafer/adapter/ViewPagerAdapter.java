package com.bella.mobilesafer.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bella.mobilesafer.R;
import com.bella.mobilesafer.Util.ToastUtil;


import java.util.ArrayList;

/**
 * Created by huangyaling on 2017/11/30.
 */
public class ViewPagerAdapter extends PagerAdapter{
    private ArrayList<View> viewsContainer = new ArrayList<View>();
    public View welcomeView;
    public View simBindView;
    public View safeNumberView;
    public View settingDoneView;
    public Context mContext;

    public Button bindSIM_btn;

    public ViewPagerAdapter(Context context){
        this.mContext=context;
        welcomeView = LayoutInflater.from(context).inflate(R.layout.viewpager_welcome,null);
        simBindView = LayoutInflater.from(context).inflate(R.layout.viewpager_sim,null);
        safeNumberView = LayoutInflater.from(context).inflate(R.layout.viewpager_number,null);
        settingDoneView = LayoutInflater.from(context).inflate(R.layout.viewpager_done,null);
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
