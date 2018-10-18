package com.example.posdaming.view.custom;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.posdaming.R;

/**
 * Created by 99213 on 2017/10/22.
 */

public class TitleView extends LinearLayout {
    //布局的根布局
    private View rootView;
    private TextView tvTitle;
    private LinearLayout llBack;
    private RelativeLayout rlMain;
    private int bg;

    //获取的title
    private String mTitle;

    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        rootView = LayoutInflater.from(context).inflate(R.layout.view_title, this);
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        llBack = (LinearLayout) rootView.findViewById(R.id.ll_back);
        rlMain = (RelativeLayout) rootView.findViewById(R.id.rl_contact_person_title);
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.TitleView);
        mTitle = t.getString(R.styleable.TitleView_title);

        //设置title
        tvTitle.setText(mTitle);
        //设置返回键
        llBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
            }
        });


    }
}
