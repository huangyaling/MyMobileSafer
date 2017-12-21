package com.bella.mobilesafer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by huangyaling on 2017/12/20.
 * 创建blackNumber.db和blacknumber表，具有id（自增主键），name,number三个字段
 */
public class BlackNumberOpenHelper extends SQLiteOpenHelper {
    public BlackNumberOpenHelper(Context context){
        super(context,"blackNumber.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("huangyaling","create blacknumber table");
        db.execSQL("create table blacknumber (id integer primary key autoincrement," +
                "number varchar(20)," +
                "name varchar(255)," +
                "mode integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
