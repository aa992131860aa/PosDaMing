package com.example.posdaming.controller.stockout;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.posdaming.R;
import com.example.posdaming.controller.BaseActivity;
import com.example.posdaming.view.popupwindow.BillTypePopup;

/**
 * Created by 99213 on 2017/10/22.
 */

public class QuickStockOutActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_bill_type;
    private TextView tv_bill_type_title;

    private LinearLayout ll_bill_type;

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_quick_stock_out);
        tv_bill_type = (TextView) findViewById(R.id.tv_bill_type);
        tv_bill_type_title = (TextView) findViewById(R.id.tv_bill_type_title);
        ll_bill_type = (LinearLayout) findViewById(R.id.ll_bill_type);


        ll_bill_type.setOnClickListener(this);

    }

    @Override
    protected void initData() {


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.ll_bill_type:
                new BillTypePopup(this,tv_bill_type_title).showPopupWindow(view);
                break;
        }
    }

}
