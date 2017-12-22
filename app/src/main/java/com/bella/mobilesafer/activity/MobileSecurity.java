package com.bella.mobilesafer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bella.mobilesafer.R;
import com.bella.mobilesafer.Util.ToastUtil;
import com.bella.mobilesafer.adapter.ViewPagerAdapter;
import com.bella.mobilesafer.view.DepthPageTransformer;

/**
 * Created by huangyaling on 2017/11/30.
 */
public class MobileSecurity extends Activity implements ViewPager.OnPageChangeListener{
    private ViewPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private LinearLayout mLinearLayout;
    private int mPagerindex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_security_viewpager);
        initView();
        mLinearLayout.getChildAt(mPagerindex).setEnabled(true);
        mViewPager.addOnPageChangeListener(this);
    }

    private void initView(){
        mViewPager = (ViewPager) findViewById(R.id.safe_settings_viewpager);
        //添加viewpager指示器
        mLinearLayout = (LinearLayout) findViewById(R.id.viewpager_linear);
        for(int i=0;i<4;i++){
            View view = new View(MobileSecurity.this);
            view.setBackgroundResource(R.drawable.background);
            view.setEnabled(false);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(30, 30);
            layoutParams.leftMargin = 10;
            mLinearLayout.addView(view,layoutParams);
        }
        mAdapter = new ViewPagerAdapter(this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mLinearLayout.getChildAt(mPagerindex).setEnabled(false);
        mLinearLayout.getChildAt(position).setEnabled(true);
        mPagerindex=position;
        Log.d("huangyaling","mPagerindex="+mPagerindex);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void bindSIM(){
        ToastUtil.showToast(this,"jjjjjj");

    }
}
