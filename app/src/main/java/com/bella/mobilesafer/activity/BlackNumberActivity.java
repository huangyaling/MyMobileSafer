package com.bella.mobilesafer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.bella.mobilesafer.R;
import com.bella.mobilesafer.adapter.BlackContactAdapter;
import com.bella.mobilesafer.bean.BlackContactInfo;
import com.bella.mobilesafer.database.BlackNumberDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangyaling on 2017/12/20.
 */
public class BlackNumberActivity extends Activity implements View.OnClickListener{
    private FrameLayout mHaveBlackNumber;
    private FrameLayout mNoBlackNumber;

    private BlackNumberDao dao;
    private ListView mListView;
    private Button addbtn;
    private int pagenumber=0;
    private int pagesize=15;
    private int totalNumber;
    private List<BlackContactInfo> pageBlackNumber=new ArrayList<BlackContactInfo>();
    private BlackContactAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blacknumber_activity);
        initView();
        fillData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //数据发生变化时，刷新界面
        if(totalNumber!=dao.getTotalNumber()){
            if(dao.getTotalNumber()>0){
                mHaveBlackNumber.setVisibility(View.VISIBLE);
                mNoBlackNumber.setVisibility(View.GONE);
            }else{
                mHaveBlackNumber.setVisibility(View.GONE);
                mNoBlackNumber.setVisibility(View.VISIBLE);
            }
            pagenumber=0;
            pageBlackNumber.clear();
            pageBlackNumber.addAll(dao.getPageBlackNumber(pagenumber,pagesize));
            if(adapter!=null){
                adapter.notifyDataSetChanged();
            }

        }
    }

    public void initView(){
        mHaveBlackNumber= (FrameLayout) findViewById(R.id.fl_haveblacknumber);
        mNoBlackNumber= (FrameLayout) findViewById(R.id.fl_noblacknumber);
        mListView = (ListView) findViewById(R.id.lv_blacknumber);
        addbtn= (Button) findViewById(R.id.btn_addblacknumber);
        addbtn.setOnClickListener(this);
    }

    private void fillData(){
        dao = new BlackNumberDao(BlackNumberActivity.this);
        totalNumber=dao.getTotalNumber();
        if(totalNumber==0){
            mHaveBlackNumber.setVisibility(View.GONE);
            mNoBlackNumber.setVisibility(View.VISIBLE);
        }else if(totalNumber>0){
            mHaveBlackNumber.setVisibility(View.VISIBLE);
            mNoBlackNumber.setVisibility(View.GONE);
            pagenumber=0;
            if(pageBlackNumber.size()>0){
                pageBlackNumber.clear();
            }
            pageBlackNumber.addAll(dao.getPageBlackNumber(pagenumber,pagesize));
            if(adapter==null){
                adapter=new BlackContactAdapter(pageBlackNumber,BlackNumberActivity.this);
                adapter.setCallback(new BlackContactAdapter.BlackContactCallBack() {
                    @Override
                    public void DataSizeChanged() {
                        fillData();
                    }
                });
                mListView.setAdapter(adapter);
            }else{
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_addblacknumber:
                startActivity(new Intent(this,AddBlackNumberActivity.class));
                break;
        }
    }
}
