package com.example.posdaming.controller.query;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acker.simplezxing.activity.CaptureActivity;
import com.example.posdaming.R;
import com.example.posdaming.controller.BaseActivity;
import com.example.posdaming.entity.BillType;
import com.example.posdaming.entity.Device;
import com.example.posdaming.json.EquipmentJson;
import com.example.posdaming.json.ItemFlowJson;
import com.example.posdaming.json.ItemJson;
import com.example.posdaming.json.WareListJson;
import com.example.posdaming.utils.ToastUtil;
import com.example.posdaming.utils.URL;
import com.example.posdaming.view.popupwindow.BillTypePopup;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 99213 on 2017/10/22.
 */

public class QueryActivity extends BaseActivity implements QueryAdapter.OnDetailClickListener, View.OnClickListener, CalendarPopup.OnClickChangeListener, BillTypePopup.OnItemClickListener {
    private LinearLayout ll_scan1;
    private TextView tv_scan_select1;
    private LinearLayout ll_handle1;
    private TextView tv_handle1;
    private ImageView iv_search1;
    private ImageView iv_time_search1;
    private EditText edt_no1;

    private LinearLayout ll_scan2;
    private TextView tv_scan_select2;
    private LinearLayout ll_handle2;
    private TextView tv_handle2;
    private ImageView iv_search2;
    private EditText edt_no2;
    private TextView tv_bill_type2;

    private LinearLayout ll_scan3;
    private TextView tv_scan_select3;
    private LinearLayout ll_handle3;
    private TextView tv_handle3;
    private ImageView iv_search3;
    private EditText edt_no3;
    private TextView tv_bill_type3;

    //单据查询
    RecyclerView rv_query;
    private List<ItemFlowJson.DataBean> mBills = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private QueryAdapter mQueryAdapter;

    //设备查询
    RecyclerView rv_device;
    private ArrayList<EquipmentJson.DataBean> mDevices = new ArrayList<>();
    private DeviceAdapter mDeviceAdapter;

    //开始日期,结束日期
    private TextView tv_start_date;
    private TextView tv_end_date;

    //点击的那个框
    private int mFlag = 0;

    private LinearLayout ll_bill_type2;
    private LinearLayout ll_bill_type3;


    //日期弹出框
    private CalendarPopup mCalendarPopup;
    private BillTypePopup mBillTypePopup;
    private int mPosition;
    private static final int REQ_CODE_PERMISSION = 0x1111;
    private List<BillType> mBillTypes = new ArrayList<>();
    private String TAG = "QueryActivity";
    private int mScanPosition;
    private int mBillTypePosition2;
    private int mBillTypePosition3;
    //mRecyclerView.setNestedScrollingEnabled(false);

    private TextView tv_name;
    private TextView tv_no;
    private TextView tv_unit;
    private TextView tv_num;
    private TextView tv_standard;
    private TextView tv_equ_no;
    private TextView tv_ware_no;


    @Override
    protected void initVariable() {
        recyclerQuery();
        recyclerDevice();
        mCalendarPopup = new CalendarPopup(this);
        mCalendarPopup.setOnClickChangeListener(this);
        getWare();
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_query);
        rv_query = (RecyclerView) findViewById(R.id.rv_query);
        rv_device = (RecyclerView) findViewById(R.id.rv_device);
        tv_start_date = (TextView) findViewById(R.id.tv_start_date);
        tv_end_date = (TextView) findViewById(R.id.tv_end_date);

        ll_scan1 = (LinearLayout) findViewById(R.id.ll_scan1);
        tv_scan_select1 = (TextView) findViewById(R.id.tv_scan_select1);
        ll_handle1 = (LinearLayout) findViewById(R.id.ll_handle1);
        tv_handle1 = (TextView) findViewById(R.id.tv_handle1);
        iv_search1 = (ImageView) findViewById(R.id.iv_search1);
        iv_time_search1 = (ImageView) findViewById(R.id.iv_time_search1);
        edt_no1 = (EditText) findViewById(R.id.edt_no1);

        ll_scan2 = (LinearLayout) findViewById(R.id.ll_scan2);
        tv_scan_select2 = (TextView) findViewById(R.id.tv_scan_select2);
        ll_handle2 = (LinearLayout) findViewById(R.id.ll_handle2);
        tv_handle2 = (TextView) findViewById(R.id.tv_handle2);
        iv_search2 = (ImageView) findViewById(R.id.iv_search2);
        edt_no2 = (EditText) findViewById(R.id.edt_no2);
        tv_bill_type2 = (TextView) findViewById(R.id.tv_bill_type2);

        ll_scan3 = (LinearLayout) findViewById(R.id.ll_scan3);
        tv_scan_select3 = (TextView) findViewById(R.id.tv_scan_select3);
        ll_handle3 = (LinearLayout) findViewById(R.id.ll_handle3);
        tv_handle3 = (TextView) findViewById(R.id.tv_handle3);
        iv_search3 = (ImageView) findViewById(R.id.iv_search3);
        edt_no3 = (EditText) findViewById(R.id.edt_no3);
        tv_bill_type3 = (TextView) findViewById(R.id.tv_bill_type3);

        ll_bill_type2 = (LinearLayout) findViewById(R.id.ll_bill_type2);
        ll_bill_type3 = (LinearLayout) findViewById(R.id.ll_bill_type3);

        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_no = (TextView) findViewById(R.id.tv_no);
        tv_unit = (TextView) findViewById(R.id.tv_unit);
        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_standard = (TextView) findViewById(R.id.tv_standard);
        tv_equ_no = (TextView) findViewById(R.id.tv_equ_no);
        tv_ware_no = (TextView) findViewById(R.id.tv_ware_no);

        ll_bill_type2.setOnClickListener(this);
        ll_bill_type3.setOnClickListener(this);
        tv_start_date.setOnClickListener(this);
        tv_end_date.setOnClickListener(this);

        ll_scan1.setOnClickListener(this);
        ll_handle1.setOnClickListener(this);
        iv_search1.setOnClickListener(this);
        iv_time_search1.setOnClickListener(this);
        edt_no1.setOnClickListener(this);

        ll_scan2.setOnClickListener(this);
        ll_handle2.setOnClickListener(this);
        iv_search2.setOnClickListener(this);
        edt_no2.setOnClickListener(this);

        ll_scan3.setOnClickListener(this);
        ll_handle3.setOnClickListener(this);
        iv_search3.setOnClickListener(this);
        edt_no3.setOnClickListener(this);


    }

    private void recyclerQuery() {

        mLinearLayoutManager = new LinearLayoutManager(this);
        mQueryAdapter = new QueryAdapter(this, mBills);
        mQueryAdapter.setDetailClickListener(this);
        rv_query.setLayoutManager(mLinearLayoutManager);
        rv_query.setAdapter(mQueryAdapter);
        rv_query.setNestedScrollingEnabled(false);


    }

    private void recyclerDevice() {
//        mDevices.add(new Device());
//        mDevices.add(new Device());
        mLinearLayoutManager = new LinearLayoutManager(this);
        mDeviceAdapter = new DeviceAdapter(this, mDevices);
        //mDeviceAdapter.setDetailClickListener(this);
        rv_device.setLayoutManager(mLinearLayoutManager);
        rv_device.setAdapter(mDeviceAdapter);
        rv_device.setNestedScrollingEnabled(false);


    }

    @Override
    protected void initData() {


        mQueryAdapter.refresh(mBills);
    }

    @Override
    public void onQueryClick(View view, int position) {
        Intent intent = new Intent(this, QueryDetailActivity.class);
        intent.putExtra("detail", mBills.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start_date:
                mFlag = 0;
                mCalendarPopup.showPopupWindow(view);
                break;
            case R.id.tv_end_date:
                mFlag = 1;
                mCalendarPopup.showPopupWindow(view);
                break;
            case R.id.ll_bill_type2:
                mBillTypePopup = new BillTypePopup(this, tv_bill_type2, 1, mBillTypes);
                mBillTypePopup.setOnItemClickListener(this);
                mBillTypePopup.showPopupWindow(view);
                mPosition = 2;
                break;
            case R.id.ll_bill_type3:
                mBillTypePopup = new BillTypePopup(this, tv_bill_type3, 1, mBillTypes);
                mBillTypePopup.setOnItemClickListener(this);
                mBillTypePopup.showPopupWindow(view);
                mPosition = 3;
                break;
            case R.id.ll_scan1:
                mScanPosition = 1;
                tv_scan_select1.setBackgroundResource(R.drawable.radio_button_checked);
                tv_handle1.setBackgroundResource(R.drawable.radio_button_uncheck);
                if (ContextCompat.checkSelfPermission(QueryActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Do not have the permission of camera, request it.
                    ActivityCompat.requestPermissions(QueryActivity.this, new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);
                } else {
                    // Have gotten the permission
                    startCaptureActivityForResult();
                }
                break;
            //手动输入
            case R.id.ll_handle1:
                tv_scan_select1.setBackgroundResource(R.drawable.radio_button_uncheck);
                tv_handle1.setBackgroundResource(R.drawable.radio_button_checked);
                showSoftInputFromWindow(this, edt_no1);
                break;
            //搜索
            case R.id.iv_search1:
                if (!TextUtils.isEmpty(edt_no1.getText().toString().trim())) {
                    getOrder(edt_no1.getText().toString(), tv_start_date.getText().toString(), tv_end_date.getText().toString());
                } else {
                    ToastUtil.show("请单据条码", this);
                }
                break;

            //条码
            case R.id.edt_no1:
                edt_no1.setFocusableInTouchMode(true);
                edt_no1.setFocusable(true);
                break;
            //搜索
            case R.id.iv_time_search1:
                if (!TextUtils.isEmpty(edt_no1.getText().toString().trim())) {
                    getOrder(edt_no1.getText().toString(), tv_start_date.getText().toString(), tv_end_date.getText().toString());
                } else {
                    ToastUtil.show("请单据条码", this);
                }
                break;


            case R.id.ll_scan2:
                mScanPosition = 2;
                tv_scan_select1.setBackgroundResource(R.drawable.radio_button_checked);
                tv_handle1.setBackgroundResource(R.drawable.radio_button_uncheck);
                if (ContextCompat.checkSelfPermission(QueryActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Do not have the permission of camera, request it.
                    ActivityCompat.requestPermissions(QueryActivity.this, new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);
                } else {
                    // Have gotten the permission
                    startCaptureActivityForResult();
                }
                break;
            //手动输入
            case R.id.ll_handle2:
                tv_scan_select2.setBackgroundResource(R.drawable.radio_button_uncheck);
                tv_handle2.setBackgroundResource(R.drawable.radio_button_checked);
                showSoftInputFromWindow(this, edt_no2);
                break;
            //搜索
            case R.id.iv_search2:
                if (!TextUtils.isEmpty(edt_no2.getText().toString().trim())) {
                    getItem(edt_no2.getText().toString(), mBillTypes.get(mBillTypePosition2).getId());
                } else {
                    ToastUtil.show("请单据条码", this);
                }
                break;

            //条码
            case R.id.edt_no2:
                edt_no2.setFocusableInTouchMode(true);
                edt_no2.setFocusable(true);
                break;


            case R.id.ll_scan3:
                mScanPosition = 3;
                tv_scan_select3.setBackgroundResource(R.drawable.radio_button_checked);
                tv_handle3.setBackgroundResource(R.drawable.radio_button_uncheck);
                if (ContextCompat.checkSelfPermission(QueryActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Do not have the permission of camera, request it.
                    ActivityCompat.requestPermissions(QueryActivity.this, new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);
                } else {
                    // Have gotten the permission
                    startCaptureActivityForResult();
                }
                break;
            //手动输入
            case R.id.ll_handle3:
                tv_scan_select3.setBackgroundResource(R.drawable.radio_button_uncheck);
                tv_handle3.setBackgroundResource(R.drawable.radio_button_checked);
                showSoftInputFromWindow(this, edt_no3);
                break;
            //搜索
            case R.id.iv_search3:
                if (!TextUtils.isEmpty(edt_no3.getText().toString().trim())) {
                    getEquipment(edt_no3.getText().toString());
                } else {
                    ToastUtil.show("请单据条码", this);
                }
                break;

            //条码
            case R.id.edt_no3:
                edt_no2.setFocusableInTouchMode(true);
                edt_no2.setFocusable(true);
                break;
        }
    }

    /**
     * org.list.get
     * 获取仓库
     */
    private void getWare() {
        RequestParams params = new RequestParams(URL.HOST);
        params.addBodyParameter(URL.ACCESS_KEY, URL.ACCESS_VALUE);
        params.addBodyParameter("method", "ware.list.get");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, result);
                WareListJson wareListJson = new Gson().fromJson(result, WareListJson.class);
                if (wareListJson.isIs_success()) {
                    List<WareListJson.DataBean> wareListJsons = wareListJson.getData();
                    int temp =0;
                    for (int i = 0; i < wareListJsons.size(); i++) {
                        if(wareListJsons.get(i).getIs_child()==0){
                            continue;
                        }
                        mBillTypes.add(new BillType(wareListJsons.get(i).getWare_id(), wareListJsons.get(i).getWare_name(), wareListJsons.get(i).getPid()));
                        if (temp == 0) {
                            temp++;
                            tv_bill_type2.setText(wareListJsons.get(i).getWare_name());
                            tv_bill_type3.setText(wareListJsons.get(i).getWare_name());
                        }
                    }
                } else {
                    ToastUtil.show("获取交货单位失败,请重新获取", QueryActivity.this);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void startCaptureActivityForResult() {
        Intent intent = new Intent(QueryActivity.this, CaptureActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(CaptureActivity.KEY_NEED_BEEP, CaptureActivity.VALUE_BEEP);
        bundle.putBoolean(CaptureActivity.KEY_NEED_VIBRATION, CaptureActivity.VALUE_VIBRATION);
        bundle.putBoolean(CaptureActivity.KEY_NEED_EXPOSURE, CaptureActivity.VALUE_NO_EXPOSURE);
        bundle.putByte(CaptureActivity.KEY_FLASHLIGHT_MODE, CaptureActivity.VALUE_FLASHLIGHT_OFF);
        bundle.putByte(CaptureActivity.KEY_ORIENTATION_MODE, CaptureActivity.VALUE_ORIENTATION_AUTO);
        intent.putExtra(CaptureActivity.EXTRA_SETTING_BUNDLE, bundle);
        startActivityForResult(intent, CaptureActivity.REQ_CODE);
        //finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CaptureActivity.REQ_CODE:
                switch (resultCode) {
                    case RESULT_OK:


                        String result = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
                        //ToastUtil.show(result, StockOutOutsourceActivity.this);

                        if (mScanPosition == 1) {
                            getOrder(result, tv_start_date.getText().toString(), tv_end_date.getText().toString());
                            edt_no1.setText(result);
                        } else if (mScanPosition == 2) {
                            getItem(result, mBillTypes.get(mBillTypePosition2).getId());
                            edt_no2.setText(result);
                        } else if (mScanPosition == 3) {
                            getEquipment(result);
                            edt_no3.setText(result);
                        }
                        break;
                    case RESULT_CANCELED:
                        if (data != null) {
                            // for some reason camera is not working correctly
                            //tvResult.setText(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                            //ToastUtil.showToast(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT), MainActivity.this);
                        }
                        ToastUtil.show("取消扫描", QueryActivity.this);
                        break;
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_CODE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // User agree the permission
                    startCaptureActivityForResult();
                } else {
                    // User disagree the permission
                    ToastUtil.show("请在app设置界面开启照相权限", QueryActivity.this);
                }
            }
            break;
        }
    }

    @Override
    public void onCalendarClick(String time) {
        if (mFlag == 0) {
            tv_start_date.setText(time);
        } else {
            tv_end_date.setText(time);
        }
        mCalendarPopup.dismiss();
    }

    /**
     * 查询单据信息
     * itemflow_no单据号，类型：string (必填，支持模糊)
     * start_time起始时间，类型：datetime （选填）
     * end_time结束时间，类型：datetime （选填）
     */
    private void getOrder(final String itemNo, String startTime, String endTime) {
        RequestParams params = new RequestParams(URL.HOST);
        params.addBodyParameter(URL.ACCESS_KEY, URL.ACCESS_VALUE);
        params.addBodyParameter("method", "itemflow.list.get");
        params.addBodyParameter("itemflow_no", itemNo);
        if (!TextUtils.isEmpty(startTime)) {
            params.addBodyParameter("start_time", startTime);
        }
        if (!TextUtils.isEmpty(endTime)) {
            params.addBodyParameter("end_time", endTime);
        }

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, result);
                // OrderJson orderJson = new Gson().fromJson(result, OrderJson.class);
                ItemFlowJson itemFlowJson = new Gson().fromJson(result, ItemFlowJson.class);
                if (itemFlowJson.isIs_success()) {
                    mQueryAdapter.refresh(itemFlowJson.getData());
                    mBills = itemFlowJson.getData();
                } else {
                    mQueryAdapter.refresh(new ArrayList<ItemFlowJson.DataBean>());
                    ToastUtil.show("暂无查询数据", QueryActivity.this);
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG, ex.getMessage());
                ToastUtil.show("扫描失败,请重新扫描", QueryActivity.this);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    /**
     * 分仓物料查询
     * item_no物料编码，类型：string
     * ware_id仓库id，类型：int
     */
    private void getItem(final String itemNo, int wareId) {
        RequestParams params = new RequestParams(URL.HOST);
        params.addBodyParameter(URL.ACCESS_KEY, URL.ACCESS_VALUE);
        params.addBodyParameter("method", "item.ware.get");
        params.addBodyParameter("item_no", itemNo);
        params.addParameter("ware_id", wareId);
        Log.e(TAG, "wareId:" + wareId);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, result);
                ItemJson itemJson = new Gson().fromJson(result, ItemJson.class);
                if (itemJson.isIs_success()) {
                    ItemJson.DataBean item = itemJson.getData().get(0);
                    tv_no.setText(item.getItem_no());
                    tv_name.setText(item.getItem_name());
                    tv_num.setText(item.getItem_num());
                    tv_unit.setText(item.getMeasure_unit());
                    tv_standard.setText(item.getItem_specs());
                    tv_equ_no.setText(item.getEquipment_no());
                    tv_ware_no.setText(item.getWarearea_no());
                } else {
                    tv_no.setText("");
                    tv_name.setText("");
                    tv_num.setText("");
                    tv_unit.setText("");
                    tv_standard.setText("");
                    tv_equ_no.setText("");
                    tv_ware_no.setText("");
                    ToastUtil.show("暂无该物料信息", QueryActivity.this);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG, ex.getMessage());
                ToastUtil.show("扫描失败,请重新扫描", QueryActivity.this);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    /**
     * 获取设备列表
     * equipment_no设备编号，类型：string
     */
    private void getEquipment(final String itemNo) {
        RequestParams params = new RequestParams(URL.HOST);
        params.addBodyParameter(URL.ACCESS_KEY, URL.ACCESS_VALUE);
        params.addBodyParameter("method", "equipment.get");
        params.addBodyParameter("equipment_no", itemNo);


        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, result);

                EquipmentJson equipmentJson = new Gson().fromJson(result, EquipmentJson.class);
                if (equipmentJson.isIs_success()) {
                    mDevices = new ArrayList<>();
                    mDevices.add(equipmentJson.getData());
                    mDeviceAdapter.refresh(mDevices);
                } else {
                    ToastUtil.show("暂无设备信息", QueryActivity.this);
                    mDeviceAdapter.refresh(new ArrayList<EquipmentJson.DataBean>());
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG, ex.getMessage());
                ToastUtil.show("扫描失败,请重新扫描", QueryActivity.this);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    @Override
    public void onItemClick(View v, int position) {
        if (mPosition == 2) {
            mBillTypePosition2 = position;
            tv_bill_type2.setText(mBillTypes.get(position).getName());
        } else if (mPosition == 3) {
            mBillTypePosition3 = position;
            tv_bill_type3.setText(mBillTypes.get(position).getName());
        }
    }
}
