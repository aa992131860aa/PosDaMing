package com.example.posdaming.application;

import android.app.Application;
import android.util.DisplayMetrics;

import com.example.posdaming.view.loadSir.callback.CustomCallback;
import com.example.posdaming.view.loadSir.callback.EmptyCallback;
import com.example.posdaming.view.loadSir.callback.ErrorCallback;
import com.example.posdaming.view.loadSir.callback.LoadingCallback;
import com.example.posdaming.view.loadSir.callback.TimeoutCallback;
import com.kingja.loadsir.core.LoadSir;


import org.litepal.LitePal;
import org.xutils.x;

/**
 * Created by 99213 on 2017/10/19.
 */

public class App extends Application {
    public static int SCREEN_WIDTH;
    @Override
    public void onCreate() {
        super.onCreate();
        //Xutils3 初始化
        x.Ext.init(this);
        // 设置是否输出debug
        x.Ext.setDebug(true);

        //加载动画
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())

                .setDefaultCallback(LoadingCallback.class)
                .commit();

        // 得到屏幕的宽度和高度
        DisplayMetrics dm = getResources().getDisplayMetrics();
        SCREEN_WIDTH = dm.widthPixels;

        //创建数据库
        LitePal.initialize(this);
    }



}
