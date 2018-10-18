package com.example.posdaming.controller.stockin;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acker.simplezxing.activity.CaptureActivity;
import com.example.posdaming.R;
import com.example.posdaming.controller.BaseActivity;
import com.example.posdaming.db.Material;
import com.example.posdaming.entity.BillType;
import com.example.posdaming.json.DataJson;
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

public class StockInOutsourcingActivity extends BaseActivity implements View.OnClickListener, StockInOutsourcingAdapter.OnEditChangeListener, BillTypePopup.OnItemClickListener {

    private LinearLayout ll_scan;
    private TextView tv_scan_select;
    private LinearLayout ll_handle;
    private TextView tv_handle;
    private String TAG = "StockInOutsourcingActivity";
    private static final int REQ_CODE_PERMISSION = 0x1111;
    private List<OrderJson.DataBean.OrderitemsBean> orderitemsBeans;
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
    private StockInOutsourcingAdapter mStockInOutsourcingAdapter;
    //用户名
    private String mUserName;
    private int mUserId;

    //是否保存
    private boolean isSaveStockOutsourcing = false;
    private String mOrderSave = "";

    //扫描的编号
    private String mOrderNo = "";
    private List<BillType> mBillTypes = new ArrayList<>();
    private TextView tv_bill_type;
    private TextView tv_bill_type_title;
    private LinearLayout ll_bill_type;
    private BillTypePopup mBillTypePopup;
    private int mBillTypePosition = 0;

    @Override
    protected void initVariable() {

    }

    @SuppressLint("CutPasteId")
    @Override
    protected void initView(Bundle savedInstanceState) {

        setContentView(R.layout.activity_stock_in_outsourcing);
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
        tv_bill_type = (TextView) findViewById(R.id.tv_bill_type);
        tv_bill_type_title = (TextView) findViewById(R.id.tv_bill_type_title);
        ll_bill_type = (LinearLayout) findViewById(R.id.ll_bill_type);


        ll_handle = (LinearLayout) findViewById(R.id.ll_handle);
        tv_handle = (TextView) findViewById(R.id.tv_handle);
        rv_content = (RecyclerView) findViewById(R.id.rv_content);
        ll_scan.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        edt_no.setOnClickListener(this);
        ll_handle.setOnClickListener(this);
        ll_bill_type.setOnClickListener(this);
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

        isSaveStockOutsourcing = SharePreUtils.getBoolean("isSaveStockOutsourcing", false, this);


        if (isSaveStockOutsourcing) {
            //mOrderSave = SharePreUtils.getString("orderSave", "", this);
            List<Material> lists = DataSupport.where("type = ?","orderSave").find(Material.class);
            if(lists.size()>0) {
                getOrder(lists.get(0).getNo(),lists,true);
            }
        }else{

        }

        getWare();

    }

    private void recycler() {

        mLinearLayoutManager = new LinearLayoutManager(this);
        mStockInOutsourcingAdapter = new StockInOutsourcingAdapter(this, new ArrayList<OrderJson.DataBean.OrderitemsBean>());
        //mStockInOutsourcingAdapter.setDetailClickListener(this);
        mStockInOutsourcingAdapter.setDetailClickListener(this);
        rv_content.setLayoutManager(mLinearLayoutManager);
        rv_content.setAdapter(mStockInOutsourcingAdapter);


    }

    /**
     * 获取仓库列表
     */
    private void getWareList() {
        RequestParams params = new RequestParams(URL.HOST);
        params.addBodyParameter(URL.ACCESS_KEY, URL.ACCESS_VALUE);
        params.addBodyParameter("method", "ware.list.get");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, result);
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
                    int temp = 0;
                    for (int i = 0; i < wareListJsons.size(); i++) {
                        if (wareListJsons.get(i).getIs_child() == 0) {
                            continue;
                        }
                        mBillTypes.add(new BillType(wareListJsons.get(i).getWare_id(), wareListJsons.get(i).getWare_name(), wareListJsons.get(i).getPid()));
                        if (temp == 0) {
                            temp++;
                            tv_bill_type.setText(wareListJsons.get(i).getWare_name());
                        }
                    }
                } else {
                    ToastUtil.show("获取入库仓库失败,请重新获取", StockInOutsourcingActivity.this);
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
     * 采购单号
     */
    private void getOrder(final String itemNo, final List<Material> lists, final boolean isSave) {
        RequestParams params = new RequestParams(URL.HOST);
        params.addBodyParameter(URL.ACCESS_KEY, URL.ACCESS_VALUE);
        params.addBodyParameter("method", "order.get");
        params.addBodyParameter("order_no", itemNo);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                OrderJson orderJson = new Gson().fromJson(result, OrderJson.class);
                Log.e(TAG, result);
                if (orderJson.isIs_success()) {
                    orderitemsBeans = orderJson.getData().getOrderitems();
//                    for (int i = 0; i < orderitemsBeans.size(); i++) {
//                        orderitemsBeans.get(i).setItem_num(0);
//                    }
                    if(isSave){
                        for (int i = 0; i < lists.size(); i++) {
                            orderitemsBeans.get(i).setItem_num(lists.get(i).getNum());
                        }
                    }
                    mStockInOutsourcingAdapter.refresh(orderitemsBeans);
                    edt_no.setText(itemNo);
                    edt_no.setSelection(itemNo.length());
                    mOrderNo = itemNo;
                } else {
                    mStockInOutsourcingAdapter.refresh(new ArrayList<OrderJson.DataBean.OrderitemsBean>());
                    ToastUtil.show("扫描失败,请重新扫描", StockInOutsourcingActivity.this);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.show("扫描失败,请重新扫描", StockInOutsourcingActivity.this);
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
     * receiving.buy.add
     * user_id创建人id，类型：int
     * order_no采购单号，类型：string
     * ware_id仓库id，类型：int
     * wareitemnums库存物料清单(多个物料，用","隔开)(wareitem_id:num,wareitem_id1:num1)，类型：string
     */
    private void submit() {
        RequestParams params = new RequestParams(URL.HOST);
        params.addParameter(URL.ACCESS_KEY, URL.ACCESS_VALUE);
        params.addParameter("method", "receiving.buy.add");
        params.addParameter("user_id", mUserId);
        params.addParameter("order_no", mOrderNo);
        params.addParameter("ware_id", mBillTypes.get(mBillTypePosition).getId());

        String wareitemnums = "";
        for (int i = 0; i < orderitemsBeans.size(); i++) {
            wareitemnums += orderitemsBeans.get(i).getWareitem_id() + ":" + orderitemsBeans.get(i).getItem_num();
            if (i != orderitemsBeans.size() - 1) {
                wareitemnums += ",";
            }
        }
        Log.e(TAG, wareitemnums+","+mBillTypes.get(mBillTypePosition).getId()+","+mOrderNo+","+mUserId);
        params.addParameter("wareitemnums", wareitemnums);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, result);


                DataJson dataJson = new Gson().fromJson(result, DataJson.class);
                if (dataJson.isIs_success()) {
                    SharePreUtils.putBoolean("isSaveStockOutsourcing", false, StockInOutsourcingActivity.this);
                    ToastUtil.show("入库成功", StockInOutsourcingActivity.this);
                    finish();
                    DataSupport.deleteAll(Material.class,"type=?","orderSave");
                } else {
                    if (!TextUtils.isEmpty(dataJson.getMsg())) {
                        ToastUtil.show(dataJson.getMsg(), StockInOutsourcingActivity.this);
                    } else {
                        ToastUtil.show("入库失败", StockInOutsourcingActivity.this);
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
            case R.id.ll_bill_type:
                mBillTypePopup = new BillTypePopup(this, tv_bill_type_title, 1, mBillTypes);
                mBillTypePopup.setOnItemClickListener(this);
                mBillTypePopup.showPopupWindow(view);

                break;
            case R.id.ll_scan:
                tv_scan_select.setBackgroundResource(R.drawable.radio_button_checked);
                tv_handle.setBackgroundResource(R.drawable.radio_button_uncheck);
                if (ContextCompat.checkSelfPermission(StockInOutsourcingActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Do not have the permission of camera, request it.
                    ActivityCompat.requestPermissions(StockInOutsourcingActivity.this, new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);
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
                    getOrder(edt_no.getText().toString(),null,false);
                } else {
                    ToastUtil.show("请输入任务条码", this);
                }
                break;
            //保存
            case R.id.btn_save:
                if (orderitemsBeans != null && orderitemsBeans.size() > 0) {
                    SharePreUtils.putBoolean("isSaveStockOutsourcing", true, this);
                    SharePreUtils.putString("orderSave", edt_no.getText().toString(), this);
                    int del = DataSupport.deleteAll(Material.class,"type=?","orderSave");

                    for(int i=0;i<orderitemsBeans.size();i++){
                        Material material = new Material();
                        material.setType("orderSave");
                        material.setNo(edt_no.getText().toString());
                        material.setNum(orderitemsBeans.get(i).getItem_num());
                        material.setGetWareitemId(orderitemsBeans.get(i).getWareitem_id());
                        material.save();
                    }
                    ToastUtil.show("已保存成功", this);
                    finish();
                } else {
                    ToastUtil.show("暂无物料信息,请添加", this);
                }

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
        Intent intent = new Intent(StockInOutsourcingActivity.this, CaptureActivity.class);
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
                    ToastUtil.show("请在app设置界面开启照相权限", StockInOutsourcingActivity.this);
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
                        //ToastUtil.show(result, StockInOutsourcingActivity.this);
                        getOrder(result,null,false);

                        break;
                    case RESULT_CANCELED:
                        if (data != null) {
                            // for some reason camera is not working correctly
                            //tvResult.setText(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                            //ToastUtil.showToast(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT), MainActivity.this);
                        }
                        ToastUtil.show("取消扫描", StockInOutsourcingActivity.this);
                        break;
                }
                break;
        }
    }

    @Override
    public void OnEditChange(View view, int position, String num) {
        orderitemsBeans.get(position).setItem_num(Integer.parseInt(num));
    }

    @Override
    public void onItemClick(View v, int position) {
        mBillTypePosition = position;
        tv_bill_type.setText(mBillTypes.get(position).getName());
    }
}
