package com.bella.mobilesafer.Util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by huangyaling on 2017/11/15.
 */
public class ToastUtil {
    public static void showToast(Context context,String toastMessage){
        Toast.makeText(context,toastMessage,Toast.LENGTH_SHORT).show();
    }
}
