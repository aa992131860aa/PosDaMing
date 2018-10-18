package com.example.posdaming.controller.stockin;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.acker.simplezxing.activity.CaptureActivity;
import com.example.posdaming.R;
import com.example.posdaming.controller.BaseActivity;
import com.example.posdaming.controller.stockout.StockOutOtherActivity;
import com.example.posdaming.db.Material;
import com.example.posdaming.entity.BillType;
import com.example.posdaming.json.CompanyJson;
import com.example.posdaming.json.DataJson;
import com.example.posdaming.json.MaterialItemJson;
import com.example.posdaming.json.OrderJson;
import com.example.posdaming.json.WareListJson;
import com.example.posdaming.utils.SharePreUtils;
import com.example.posdaming.utils.ToastUtil;
import com.example.posdaming.utils.URL;
import com.example.posdaming.view.popupwindow.BillTypePopup;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 99213 on 2017/10/22.
 */

public class StockInFinishActivity extends BaseActivity implements View.OnClickListener, StockInOtherAdapter.OnEditChangeListener, BillTypePopup.OnItemClickListener {

    private LinearLayout ll_scan;
    private TextView tv_scan_select;
    private LinearLayout ll_handle;
    private TextView tv_handle;
    private String TAG = "StockInOutsourcingActivity";
    private static final int REQ_CODE_PERMISSION = 0x1111;
    private List<MaterialItemJson.DataBean> orderitemsBeans = new ArrayList<>();
    //默认仓库
    //ware_id":10,"ware_name":"外购收料仓","ware_no":"WG3-01"
    //仓库id
    private int wareId = 10;

    private TextView tv_date;
    private EditText edt_no;
    private ImageView iv_search;
    private TextView tv_name;
    private TextView tv_confirm_name;
    private Button btn_save;
    private Button btn_confirm;

    //recyclerview
    private RecyclerView rv_content;
    private LinearLayoutManager mLinearLayoutManager;
    private StockInOtherAdapter mStockInOutsourcingAdapter;
    //用户名
    private String mUserName;
    private int mUserId;

    //是否保存
    private boolean isSaveStockFinish = false;
    private String mOrderSave = "";

    //扫描的编号
    private String mOrderNo = "";


    private TextView tv_bill_type;
    private TextView tv_bill_type_title;

    private LinearLayout ll_bill_type;

    //单据类型
    private List<BillType> mBillTypes = new ArrayList<>();
    //交货单位
    private List<BillType> companies = new ArrayList<>();
    private TextView tv_company;
    private LinearLayout ll_company;
    private BillTypePopup mBillTypePopup;
    private TextView tv_company_title;
    private int mBillTypePosition = -1;
    private int mCompanyPosition = -1;
    private int mPosition = 0;
    //源单类型
    private int connType = 10;//生产任务单

    private List<BillType> mBillTypes3 =new ArrayList<>();
    private TextView tv_bill_type3;
    private TextView tv_bill_type_title3;
    private  LinearLayout ll_bill_type3;
    private BillTypePopup mBillTypePopup3;
    private int mBillTypePosition3 = 0;
    List<Material> mSaveLists = new ArrayList<>();

    @Override
    protected void initVariable() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_stock_in_finish);
        tv_bill_type = (TextView) findViewById(R.id.tv_bill_type);
        tv_bill_type_title = (TextView) findViewById(R.id.tv_bill_type_title);
        ll_bill_type = (LinearLayout) findViewById(R.id.ll_bill_type);


        ll_scan = (LinearLayout) findViewById(R.id.ll_scan);
        tv_scan_select = (TextView) findViewById(R.id.tv_scan_select);

        tv_date = (TextView) findViewById(R.id.tv_date);
        edt_no = (EditText) findViewById(R.id.edt_no);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        rv_content = (RecyclerView) findViewById(R.id.rv_content);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_confirm_name = (TextView) findViewById(R.id.tv_confirm_name);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        tv_company = (TextView) findViewById(R.id.tv_company);
        ll_company = (LinearLayout) findViewById(R.id.ll_company);
        tv_company_title = (TextView) findViewById(R.id.tv_company_title);
        ll_handle = (LinearLayout) findViewById(R.id.ll_handle);
        tv_handle = (TextView) findViewById(R.id.tv_handle);
        rv_content = (RecyclerView) findViewById(R.id.rv_content);


        tv_bill_type3 = (TextView) findViewById(R.id.tv_bill_type3);
        tv_bill_type_title3 = (TextView) findViewById(R.id.tv_bill_type_title3);
        ll_bill_type3 = (LinearLayout) findViewById(R.id.ll_bill_type3);


        ll_bill_type3.setOnClickListener(this);

        ll_scan.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        edt_no.setOnClickListener(this);
        ll_handle.setOnClickListener(this);
        ll_bill_type.setOnClickListener(this);
        ll_company.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        recycler();
        //日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        tv_date.setText(sdf.format(new Date()));

        mUserName = SharePreUtils.getString("user_name", "", this);
        mUserId = SharePreUtils.getInt("user_id", 0, this);

        tv_name.setText(mUserName);
        tv_confirm_name.setText(mUserName);

        isSaveStockFinish = SharePreUtils.getBoolean("isSaveStockFinish", false, this);


        if (isSaveStockFinish) {
            //mOrderSave = SharePreUtils.getString("orderSaveFinish", "", this);
            //getOrder(mOrderSave);
            mSaveLists = DataSupport.where("type=?", "orderSaveFinish").find(Material.class);
            for (int i = 0; i < mSaveLists.size(); i++) {
                getItem(mSaveLists.get(i).getNo(), i);
            }
        }

        mBillTypes.add(new BillType(10, "成品入库"));
        mBillTypes.add(new BillType(20, "工序半成品入库"));

        tv_bill_type.setText("成品入库");
        mBillTypePosition = 0;
        mCompanyPosition = 0;
        mBillTypePosition3 = 0;
        getCompany();
        getWare();
    }

    private void recycler() {

        mLinearLayoutManager = new LinearLayoutManager(this);
        mStockInOutsourcingAdapter = new StockInOtherAdapter(this, orderitemsBeans);
        mStockInOutsourcingAdapter.setDetailClickListener(this);
        rv_content.setLayoutManager(mLinearLayoutManager);
        rv_content.setAdapter(mStockInOutsourcingAdapter);


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
                WareListJson wareListJson = new Gson().fromJson(result, WareListJson.class);
                if (wareListJson.isIs_success()) {
                    List<WareListJson.DataBean> wareListJsons = wareListJson.getData();
                    int temp = 0;
                    for (int i = 0; i < wareListJsons.size(); i++) {
                        if (wareListJsons.get(i).getIs_child() == 0) {
                            continue;
                        }
                        mBillTypes3.add(new BillType(wareListJsons.get(i).getWare_id(), wareListJsons.get(i).getWare_name(), wareListJsons.get(i).getPid()));
                        if (temp == 0) {
                            temp++;
                            tv_bill_type3.setText(wareListJsons.get(i).getWare_name());
                        }
                    }
                } else {
                    ToastUtil.show("获取入库仓库失败,请重新获取", StockInFinishActivity.this);
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
    /**
     * org.list.get
     * 获取公司名称
     */
    private void getCompany() {
        RequestParams params = new RequestParams(URL.HOST);
        params.addBodyParameter(URL.ACCESS_KEY, URL.ACCESS_VALUE);
        params.addBodyParameter("method", "org.list.get");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CompanyJson companyJson = new Gson().fromJson(result, CompanyJson.class);
                if (companyJson.isIs_success()) {
                    List<CompanyJson.DataBean> compnayJsons = companyJson.getData();
                    for (int i = 0; i < compnayJsons.size(); i++) {
                        companies.add(new BillType(compnayJsons.get(i).getOrg_id(), compnayJsons.get(i).getOrg_name(), compnayJsons.get(i).getPid()));
                        if (i == 0) {
                            tv_company.setText(compnayJsons.get(i).getOrg_name());
                        }
                    }
                } else {
                    ToastUtil.show("获取交货单位失败,请重新获取", StockInFinishActivity.this);
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

//    /**
//     * 采购单号
//     */
//    private void getOrder(final String itemNo) {
//        RequestParams params = new RequestParams(URL.HOST);
//        params.addBodyParameter(URL.ACCESS_KEY, URL.ACCESS_VALUE);
//        params.addBodyParameter("method", "order.get");
//        params.addBodyParameter("order_no", itemNo);
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                OrderJson orderJson = new Gson().fromJson(result, OrderJson.class);
//                Log.e(TAG, result);
//                if (orderJson.isIs_success()) {
//                    orderitemsBeans = orderJson.getData().getOrderitems();
//                    for (int i = 0; i < orderitemsBeans.size(); i++) {
//                        orderitemsBeans.get(i).setItem_num(0);
//                    }
//
//                    mStockInOutsourcingAdapter.refresh(orderitemsBeans, "finish");
//                    edt_no.setText(itemNo);
//                    edt_no.setSelection(itemNo.length());
//                    mOrderNo = itemNo;
//                } else {
//                    mStockInOutsourcingAdapter.refresh(new ArrayList<OrderJson.DataBean.OrderitemsBean>());
//                    ToastUtil.show("扫描失败,请重新扫描", StockInFinishActivity.this);
//                }
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                ToastUtil.show("扫描失败,请重新扫描", StockInFinishActivity.this);
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
//
//    }

    /**
     * user_id创建人id，类型：int
     * flow_type单据类型 (13:其他入库单,15:退料入库单)，类型：int
     * ware_id收货仓库id，类型：int
     * org_id交货单位，类型：int
     * wareitemnums库存物料清单(多个物料，用","隔开)(wareitem_id:num,wareitem_id1:num1)，类型：string
     */
    private void submit() {
        RequestParams params = new RequestParams(URL.HOST);
        params.addParameter(URL.ACCESS_KEY, URL.ACCESS_VALUE);
        params.addParameter("method", "receiving.fulfil.add");
        params.addParameter("user_id", mUserId);
        params.addParameter("ware_id", mBillTypes3.get(mBillTypePosition3).getId());
        params.addParameter("fulfil_type", mBillTypes.get(mBillTypePosition).getId());
        params.addParameter("conn_type", connType);
        params.addParameter("org_id", companies.get(mCompanyPosition).getId());

        String wareitemnums = "";
        for (int i = 0; i < orderitemsBeans.size(); i++) {
            wareitemnums += orderitemsBeans.get(i).getWareitem_id() + ":" + orderitemsBeans.get(i).getNum();
            if (i != orderitemsBeans.size() - 1) {
                wareitemnums += ",";
            }
        }
        Log.e(TAG, wareitemnums);
        params.addParameter("wareitemnums", wareitemnums);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, result);

                DataJson dataJson = new Gson().fromJson(result, DataJson.class);
                if (dataJson.isIs_success()) {
                    SharePreUtils.putBoolean("isSaveStockFinish", false, StockInFinishActivity.this);
                    ToastUtil.show("入库成功", StockInFinishActivity.this);
                    finish();
                } else {
                    if(!TextUtils.isEmpty(dataJson.getMsg())) {
                        ToastUtil.show(dataJson.getMsg(), StockInFinishActivity.this);
                    }else{
                        ToastUtil.show("入库失败", StockInFinishActivity.this);
                    }
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_bill_type3:
                mPosition = 3;
                mBillTypePopup3 = new BillTypePopup(this, tv_bill_type_title3, 1, mBillTypes3);
                mBillTypePopup3.setOnItemClickListener(this);
                mBillTypePopup3.showPopupWindow(view);
                break;
            case R.id.ll_bill_type:
                mBillTypePopup = new BillTypePopup(this, tv_bill_type_title, 1, mBillTypes);
                mBillTypePopup.setOnItemClickListener(this);
                mBillTypePopup.showPopupWindow(view);
                mPosition = 0;
                break;
            case R.id.ll_company:
                mBillTypePopup = new BillTypePopup(this, tv_company_title, 1, companies);
                mBillTypePopup.setOnItemClickListener(this);
                mBillTypePopup.showPopupWindow(view);
                mPosition = 1;
                break;
            case R.id.ll_scan:
                tv_scan_select.setBackgroundResource(R.drawable.radio_button_checked);
                tv_handle.setBackgroundResource(R.drawable.radio_button_uncheck);
                if (ContextCompat.checkSelfPermission(StockInFinishActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Do not have the permission of camera, request it.
                    ActivityCompat.requestPermissions(StockInFinishActivity.this, new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);
                } else {
                    // Have gotten the permission
                    startCaptureActivityForResult();
                }
                break;
            //手动输入
            case R.id.ll_handle:
                tv_scan_select.setBackgroundResource(R.drawable.radio_button_uncheck);
                tv_handle.setBackgroundResource(R.drawable.radio_button_checked);
                showSoftInputFromWindow(this, edt_no);
                break;
            //搜索
            case R.id.iv_search:
                if (!TextUtils.isEmpty(edt_no.getText().toString().trim())) {
                    getItem(edt_no.getText().toString(),-1);
                } else {
                    ToastUtil.show("请输入任务条码", this);
                }
                break;
            //保存
            case R.id.btn_save:
                SharePreUtils.putBoolean("isSaveStockFinish", true, this);
                //SharePreUtils.putString("orderSaveFinish", edt_no.getText().toString(), this);

                DataSupport.deleteAll(Material.class, "type = ?", "orderSaveFinish");
                if (orderitemsBeans.size() > 0) {
                    for (int i = 0; i < orderitemsBeans.size(); i++) {
                        Material material = new Material();
                        material.setType("orderSaveFinish");
                        material.setGetWareitemId(orderitemsBeans.get(i).getWareitem_id());
                        material.setNo(orderitemsBeans.get(i).getItem_no());
                        material.setNum(orderitemsBeans.get(i).getNum());
                        material.save();
                    }
                    ToastUtil.show("已保存成功", this);
                } else {
                    ToastUtil.show("暂无物料保存", this);
                }
                finish();
                break;
            //条码
            case R.id.edt_no:
                edt_no.setFocusableInTouchMode(true);
                edt_no.setFocusable(true);
                break;
            //提交
            case R.id.btn_confirm:
                if (orderitemsBeans != null && orderitemsBeans.size() > 0) {
                    submit();
                } else {
                    ToastUtil.show("暂无物料信息,请添加", this);
                }

                break;
        }
    }

    private void startCaptureActivityForResult() {
        Intent intent = new Intent(StockInFinishActivity.this, CaptureActivity.class);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_CODE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // User agree the permission
                    startCaptureActivityForResult();
                } else {
                    // User disagree the permission
                    ToastUtil.show("请在app设置界面开启照相权限", StockInFinishActivity.this);
                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CaptureActivity.REQ_CODE:
                switch (resultCode) {
                    case RESULT_OK:


                        String result = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
                        //ToastUtil.show(result, StockInFinishActivity.this);
                        getItem(result,-1);

                        break;
                    case RESULT_CANCELED:
                        if (data != null) {
                            // for some reason camera is not working correctly
                            //tvResult.setText(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                            //ToastUtil.showToast(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT), MainActivity.this);
                        }
                        ToastUtil.show("取消扫描", StockInFinishActivity.this);
                        break;
                }
                break;
        }
    }

    @Override
    public void OnEditChange(View view, int position, String num) {
        orderitemsBeans.get(position).setNum(Integer.parseInt(num));
        //ToastUtil.show("gg",this);
    }

    @Override
    public void onItemClick(View view, int position) {
        if (mPosition == 0) {
            mBillTypePosition = position;
            tv_bill_type.setText(mBillTypes.get(position).getName());
        } else if (mPosition == 1) {
            mCompanyPosition = position;
            tv_company.setText(companies.get(position).getName());
        }else if (mPosition==3){
            mBillTypePosition3 = position;
            tv_bill_type3.setText(mBillTypes3.get(position).getName());
        }
    }
    /**
     * 获取单个物料信息
     */
    private void getItem(final String itemNo, final int position) {
        final RequestParams params = new RequestParams(URL.HOST);
        params.addBodyParameter(URL.ACCESS_KEY, URL.ACCESS_VALUE);
        params.addBodyParameter("method", "item.get");
        params.addBodyParameter("item_no", itemNo);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, result);

                MaterialItemJson materialItemJson = new Gson().fromJson(result, MaterialItemJson.class);
                if (materialItemJson.isIs_success()) {
                    boolean isRepeat = false;
                    for (int i = 0; i < orderitemsBeans.size(); i++) {
                        if (materialItemJson.getData().getWareitem_id() == orderitemsBeans.get(i).getWareitem_id()) {
                            isRepeat = true;
                            break;
                        }
                    }
                    if (position >= 0) {
                        materialItemJson.getData().setNum(mSaveLists.get(position).getNum());
                    }
                    if (!isRepeat) {
                        orderitemsBeans.add(materialItemJson.getData());
                    }
                    edt_no.setText(itemNo);
                    mStockInOutsourcingAdapter.refresh(orderitemsBeans);
                } else {
                    ToastUtil.show("没有该物料。", StockInFinishActivity.this);
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG, ex.getMessage());
                ToastUtil.show("扫描失败,请重新扫描", StockInFinishActivity.this);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
}
