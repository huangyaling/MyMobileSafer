package com.bella.mobilesafer.receiver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException;
import android.net.Uri;
import android.os.Handler;
import android.telephony.TelephonyManager;

import com.bella.mobilesafer.database.BlackNumberDao;

/**
 * Created by huangyaling on 2017/12/21.
 */
public class InterceptCallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sp=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        boolean BlackNumStatus=sp.getBoolean("BlackNumStatus",true);
        if(!BlackNumStatus){
            return;
        }
        BlackNumberDao dao=new BlackNumberDao(context);
        if(!intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
            String mIncomingNumber="";
            TelephonyManager tManager= (TelephonyManager) context.getSystemService(Service.TELECOM_SERVICE);
            switch (tManager.getCallState()){
                case  TelephonyManager.CALL_STATE_RINGING:
                    int blackContactMode=dao.getBlackContactMode(mIncomingNumber);
                    if(blackContactMode==1||blackContactMode==3){
                        Uri uri=Uri.parse("content://call-log/calls");
                        //context.getContentResolver().registerContentObserver(uri,true,);
                    }
            }
        }
    }

    //通过内容观察者观察数据库变化
    private class CallLogObserver extends ContentObserver{
        private  String incomingNumber;
        private Context context;

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public CallLogObserver(Handler handler,String incomingNumber,Context context) {
            super(handler);
        }
    }
}
