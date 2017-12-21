package com.bella.mobilesafer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bella.mobilesafer.R;

/**
 * Created by huangyaling on 2017/12/21.
 */
public class InsertPasswordDialog extends Dialog implements View.OnClickListener {
    public EditText mPasswordET;
    public Button btn_confirm;
    public Button btn_cancel;
    private CallBack inPwdCallback;
    public InsertPasswordDialog(Context context) {
        super(context,R.style.dialog_custom);
    }
    public void setInPwdCallback(CallBack callback){
        this.inPwdCallback=callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.insert_password_dialog);
        super.onCreate(savedInstanceState);
        initView();
    }

    public void initView(){
        mPasswordET= (EditText) findViewById(R.id.et_insert_password);
        btn_cancel= (Button) findViewById(R.id.btn_insert_cancel);
        btn_confirm= (Button) findViewById(R.id.btn_insert_confim);
        btn_confirm.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_insert_confim:
                inPwdCallback.ok();
                break;
            case R.id.btn_insert_cancel:
                inPwdCallback.cancel();
                break;
        }

    }
    public interface CallBack{
        void ok();
        void cancel();
    }
}
