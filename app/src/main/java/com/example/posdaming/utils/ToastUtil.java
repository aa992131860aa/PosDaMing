package com.example.posdaming.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 99213 on 2017/10/31.
 */

public class ToastUtil {
    private static Toast mToast;

    public static void show(String content, Context context){
        if(mToast==null){
            mToast = Toast.makeText(context,content,Toast.LENGTH_SHORT);
        }else{
            mToast.setText(content);
        }
        mToast.show();
    }

}
