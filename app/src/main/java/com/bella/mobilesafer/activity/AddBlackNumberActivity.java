package com.bella.mobilesafer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.bella.mobilesafer.R;
import com.bella.mobilesafer.bean.BlackContactInfo;
import com.bella.mobilesafer.database.BlackNumberDao;

/**
 * Created by huangyaling on 2017/12/20.
 */
public class AddBlackNumberActivity extends Activity implements View.OnClickListener{
    private CheckBox mSmsCB;
    private CheckBox mTelCB;
    private EditText mNumET;
    private EditText mNameET;
    private Button addBlock;
    private Button addFromContacts;
    private BlackNumberDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addblack_activity);
        initView();
    }
    private void initView(){
        mSmsCB= (CheckBox) findViewById(R.id.block_tel_sms);
        mTelCB= (CheckBox) findViewById(R.id.block_tel_cb);
        mNumET= (EditText) findViewById(R.id.et_black_number);
        mNameET= (EditText) findViewById(R.id.et_black_name);
        addBlock= (Button) findViewById(R.id.add_block_contact);
        addFromContacts= (Button) findViewById(R.id.add_block_fromcontacts);
        addBlock.setOnClickListener(this);
        addFromContacts.setOnClickListener(this);
        dao=new BlackNumberDao(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_block_contact:
                addBlackNum();
                break;
            case R.id.add_block_fromcontacts:
                break;
        }
    }

    //添加黑名单
    private void addBlackNum(){
        String number=mNumET.getText().toString().trim();
        String name=mNameET.getText().toString().trim();
        if(number==null||name==null){
            Toast.makeText(this,getString(R.string.block_isnull),Toast.LENGTH_SHORT).show();
            Log.d("huangyaling","message is null");
            return;
        }else{
            BlackContactInfo blackContactInfo=new BlackContactInfo();
            blackContactInfo.contactName=name;
            blackContactInfo.phoneNumber=number;
            Log.d("huangyaling","contactName="+blackContactInfo.contactName+"phonenumber="+blackContactInfo.phoneNumber);
            if(!mSmsCB.isChecked()&&mTelCB.isChecked()){
                blackContactInfo.mode=1;
            }else if(mSmsCB.isChecked()&&!mTelCB.isChecked()){
                blackContactInfo.mode=2;
            }else if(mSmsCB.isChecked()&&mTelCB.isChecked()){
                blackContactInfo.mode=3;
            }else{
               Toast.makeText(this,getString(R.string.block_type_isnull),Toast.LENGTH_SHORT).show();
               return;
            }
            if(!dao.isNumberExist(number)){
                dao.addContact(blackContactInfo);
            }else{
                Toast.makeText(this,getString(R.string.block_isExist),Toast.LENGTH_SHORT).show();
                return;
            }
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            String name=data.getStringExtra("name");
            String phone=data.getStringExtra("phone");
            mNumET.setText(phone);
            mNameET.setText(name);

        }
    }
}
