package com.bella.mobilesafer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bella.mobilesafer.R;

/**
 * Created by huangyaling on 2017/12/21.
 */
public class SetUpPasswordDialog extends Dialog implements View.OnClickListener {
    private TextView mTitleTV;
    public EditText mFirstPWDET;
    public EditText mAffirmET;
    private MyCallBack myCallBack;
    private Button mConfirmBtn;
    private Button mCancelBtn;
    public interface MyCallBack{
        void ok();
        void cancle();
    }
    public SetUpPasswordDialog(Context context) {
        super(context, R.style.dialog_custom);
    }
    public void setCallBack(MyCallBack myCallBack){
        this.myCallBack=myCallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.password_setup_dialog);
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView(){
        mTitleTV= (TextView) findViewById(R.id.password_title);
        mFirstPWDET= (EditText) findViewById(R.id.et_password);
        mAffirmET= (EditText) findViewById(R.id.et_password_confirm);
        mConfirmBtn= (Button) findViewById(R.id.btn_confim);
        mCancelBtn= (Button) findViewById(R.id.btn_cancel);
        mConfirmBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
    }

    //设置对话框标题
    public void setTitle(String title){
        mTitleTV.setText(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confim:
                myCallBack.ok();
                break;
            case R.id.btn_cancel:
                myCallBack.cancle();
                break;
        }

    }
}
