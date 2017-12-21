package com.bella.mobilesafer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bella.mobilesafer.bean.BlackContactInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangyaling on 2017/12/20.
 */
public class BlackNumberDao {
    private BlackNumberOpenHelper mBlackNumberOpenHelper;
    public BlackNumberDao(Context context){
        super();
        mBlackNumberOpenHelper = new BlackNumberOpenHelper(context);
    }

    //添加黑名单
    public boolean addContact(BlackContactInfo blackContactInfo){
        SQLiteDatabase db = mBlackNumberOpenHelper.getWritableDatabase();
        ContentValues values= new ContentValues();
        if(blackContactInfo.phoneNumber.startsWith("+86")){
            blackContactInfo.phoneNumber=blackContactInfo.phoneNumber.substring(3,blackContactInfo.phoneNumber.length());
        }
        values.put("number",blackContactInfo.phoneNumber);
        values.put("name",blackContactInfo.contactName);
        values.put("mode", blackContactInfo.mode);
        long rowid=db.insert("blacknumber",null,values);
        Log.d("huangyaling","rowid="+rowid);
        if(rowid==-1){
            return false;
        }else{
            return true;
        }
    }

    //删除黑名单
    public boolean deleteContact(BlackContactInfo blackContactInfo){
        SQLiteDatabase db = mBlackNumberOpenHelper.getWritableDatabase();
        int rownumber = db.delete("blacknumber", "number=?", new String[]{blackContactInfo.phoneNumber});
        if(rownumber==0){
            return false;
        }else{
            return true;
        }
    }

    //分页查询数据库的记录
    public List<BlackContactInfo> getPageBlackNumber(int pagerNumber,int pageSize){
        SQLiteDatabase db = mBlackNumberOpenHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select number,mode,name from blacknumber limit ? offset ?",
                new String[]{String.valueOf(pageSize), String.valueOf(pageSize * pagerNumber)});
        List<BlackContactInfo> mBlackContactInfos = new ArrayList<BlackContactInfo>();
        while(cursor.moveToNext()){
            BlackContactInfo info=new BlackContactInfo();
            info.phoneNumber=cursor.getString(0);
            info.mode=cursor.getInt(1);
            info.contactName=cursor.getString(2);
            mBlackContactInfos.add(info);
        }
        cursor.close();
        db.close();
        return mBlackContactInfos;
    }

    //判断黑名单是否已经存在
    public boolean isNumberExist(String number){
        SQLiteDatabase db = mBlackNumberOpenHelper.getWritableDatabase();
        Cursor cursor=db.query("blacknumber", null, "number=?", new String[]{number}, null, null, null);
        if(cursor.moveToNext()){
            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    //根据号码查询黑名单信息
    public int getBlackContactMode(String number){
        SQLiteDatabase db = mBlackNumberOpenHelper.getReadableDatabase();
        Cursor cursor=db.query("blacknumber", new String[]{"mode"}, "number=?", new String[]{number}, null, null, null);
        int mode=0;
        if(cursor.moveToNext()){
            mode=cursor.getInt(cursor.getColumnIndex("mode"));
        }
        cursor.close();
        db.close();
        return mode;
    }
    //获取数据库的总条目个数
    public int getTotalNumber(){
        SQLiteDatabase db = mBlackNumberOpenHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("select count(*) from blacknumber",null);
        cursor.moveToNext();
        int count=cursor.getInt(0);
        cursor.close();
        db.close();
        return count;

    }
}
