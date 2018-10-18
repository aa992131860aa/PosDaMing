package com.example.posdaming.controller.stockin;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.posdaming.R;
import com.example.posdaming.controller.BaseActivity;
import com.example.posdaming.view.popupwindow.BillTypePopup;

/**
 * Created by 99213 on 2017/10/22.
 */

public class StockInOutsourceActivity extends BaseActivity implements View.OnClickListener{
    private static final String[] name = {"刘备", "关羽", "张飞", "曹操", "小乔"};

    private Spinner spinner;
    private ArrayAdapter<String> adapter;

    private TextView tv_bill_type;
    private TextView tv_bill_type_title;

    private LinearLayout ll_bill_type;

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_stock_in_outsource);
        tv_bill_type = (TextView) findViewById(R.id.tv_bill_type);
        tv_bill_type_title = (TextView) findViewById(R.id.tv_bill_type_title);
        ll_bill_type = (LinearLayout) findViewById(R.id.ll_bill_type);


        ll_bill_type.setOnClickListener(this);
        //ns_bill = (NiceSpinner) findViewById(R.id.ns_bill);
    }

    @Override
    protected void initData() {

//        spinner = (Spinner) findViewById(R.id.spinner);
//
//        //将可选内容与ArrayAdapter连接起来，simple_spinner_item是android系统自带样式
//        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,name);
//        //设置下拉列表的风格,simple_spinner_dropdown_item是android系统自带的样式，等会自定义修改
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        //将adapter 添加到spinner中
//        spinner.setAdapter(adapter);
//        //添加事件Spinner事件监听
//        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.ll_bill_type:
                new BillTypePopup(this,tv_bill_type_title).showPopupWindow(view);
                break;
        }
    }

    //使用数组形式操作
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {

        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
}
