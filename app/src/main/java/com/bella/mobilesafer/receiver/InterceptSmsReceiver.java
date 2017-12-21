package com.bella.mobilesafer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsMessage;

import com.bella.mobilesafer.database.BlackNumberDao;

/**
 * Created by huangyaling on 2017/12/21.
 */
public class InterceptSmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences msp=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        boolean BlackNumStatus=msp.getBoolean("BlackNumStatus",true);
        if(!BlackNumStatus){
            return;
        }
        //打开黑名单功能，则终止广播
        BlackNumberDao dao= new BlackNumberDao(context);
        Object[] objects= (Object[]) intent.getExtras().get("pdu");
        for(Object object:objects){
            SmsMessage smsMessage=SmsMessage.createFromPdu((byte[]) object);
            String sender=smsMessage.getOriginatingAddress();
            String body=smsMessage.getMessageBody();
            if(sender.startsWith("+86")){
                sender=sender.substring(3,sender.length());
            }
            int mode=dao.getBlackContactMode(sender);
            if(mode==2||mode==3){
                abortBroadcast();
            }
        }
    }
}
