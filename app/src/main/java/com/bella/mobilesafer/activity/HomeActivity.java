package com.bella.mobilesafer.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bella.mobilesafer.R;
import com.bella.mobilesafer.Util.MD5Utils;
import com.bella.mobilesafer.Util.TextUtils;
import com.bella.mobilesafer.Util.ToastUtil;
import com.bella.mobilesafer.adapter.GridViewAdapter;
import com.bella.mobilesafer.dialog.InsertPasswordDialog;
import com.bella.mobilesafer.dialog.SetUpPasswordDialog;

/**
 * Created by huangyaling on 2017/11/15.
 */
public class HomeActivity extends Activity{
    private GridView gv_funtion;

    protected final int MOBILE_SAFE = 0;
    protected final int TELEPHONE_SAFE = 1;
    protected final int SOFTWARE_MANAGER = 2;
    protected final int DATA_COUNTER = 3;
    protected final int PROCESS_MANAGER= 4;
    protected final int CACHE_CLEAN = 5;
    protected final int ANTI_VIRUS = 6;
    protected final int HIGHE_TOOLS = 7;
    protected final int SETTING_CENTRE = 8;

    private SharedPreferences mSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        initUI();
        initData();
        clickEvent();
    }

    public void initData(){
        gv_funtion.setAdapter(new GridViewAdapter(getApplication()));
        mSP=getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    public void initUI(){
        gv_funtion = (GridView) findViewById(R.id.gv_function);
    }

    public void clickEvent(){
        gv_funtion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case MOBILE_SAFE:
                        Log.d("huangyaling","isSetUpPassword="+isSetUpPassword());
                        if (isSetUpPassword()) {
                            showInsertPWDDialog();
                        } else {
                            setUpPasswordDialog();
                        }
                        break;
                    case TELEPHONE_SAFE:
                        Intent blackIntent = new Intent(getApplication(), BlackNumberActivity.class);
                        startActivity(blackIntent);
                        break;
                    case SOFTWARE_MANAGER://软件管理
                        Intent appIntent = new Intent(getApplication(), AppManagerActivity.class);
                        startActivity(appIntent);
                        break;
                    case DATA_COUNTER:
                        break;
                    case PROCESS_MANAGER:
                        break;
                    case CACHE_CLEAN:
                        Intent intent = new Intent(getApplication(), StroageCleanActivity.class);
                        startActivity(intent);
                        break;
                    case ANTI_VIRUS:
                        break;
                    case HIGHE_TOOLS:
                        break;
                    case SETTING_CENTRE:
                        Intent intent_clean = new Intent(getApplication(), SettingsActivity.class);
                        startActivity(intent_clean);
                        break;
                }
            }
        });
    }

    //设置密码对话框
    public void setUpPasswordDialog(){
        final SetUpPasswordDialog setPWDdialog=new SetUpPasswordDialog(HomeActivity.this);
        setPWDdialog.setCallBack(new SetUpPasswordDialog.MyCallBack() {
            @Override
            public void ok() {
                String firstPWD = setPWDdialog.mFirstPWDET.getText().toString().trim();
                String confirmPWD = setPWDdialog.mAffirmET.getText().toString().trim();
                //两次输入密码则保存
                if (!TextUtils.isEmpty(firstPWD) && !TextUtils.isEmpty(confirmPWD)) {
                    if (firstPWD.equals(confirmPWD)) {
                        savePassword(firstPWD);
                        setPWDdialog.cancel();
                        showInsertPWDDialog();
                    } else {
                        ToastUtil.showToast(HomeActivity.this, getString(R.string.password_confilct));
                    }
                } else {
                    ToastUtil.showToast(HomeActivity.this, getString(R.string.password_confilct));
                }
            }

            @Override
            public void cancle() {
                setPWDdialog.cancel();
            }
        });
        setPWDdialog.setCancelable(true);
        setPWDdialog.show();
    }

    //输入密码对话框
    public void showInsertPWDDialog(){
        final InsertPasswordDialog inPWDdialog = new InsertPasswordDialog(HomeActivity.this);
        inPWDdialog.setInPwdCallback(new InsertPasswordDialog.CallBack() {
            @Override
            public void ok() {
                String insertPWD=inPWDdialog.mPasswordET.getText().toString().trim();
                //密码一致则进入设置向导页面
                if(insertPWD.equals(getPassword())){
                    startActivity(new Intent(HomeActivity.this,MobileSecurity.class));
                    inPWDdialog.cancel();
                }else{
                    ToastUtil.showToast(HomeActivity.this,getString(R.string.password_error));
                }
            }
            @Override
            public void cancel() {
                inPWDdialog.cancel();
            }
        });
        inPWDdialog.setCancelable(true);
        inPWDdialog.show();
    }

    //保存密码
    public void savePassword(String password){
        SharedPreferences.Editor editor=mSP.edit();
        //editor.putString("MyMobileSafePWD", MD5Utils.encode(password));
        editor.putString("MyMobileSafePWD", password);
        editor.commit();
    }

    //获取密码
    public String getPassword(){
        return mSP.getString("MyMobileSafePWD",null);
    }

    //是否已经设置过密码
    public boolean isSetUpPassword(){
        String password=mSP.getString("MyMobileSafePWD",null);
        Log.d("huangyaling","pwd="+password);
        if(!TextUtils.isEmpty(password)){
            return true;
        }
        return false;
    }
}
