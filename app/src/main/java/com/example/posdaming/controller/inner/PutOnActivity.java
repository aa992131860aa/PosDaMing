package com.example.posdaming.controller.inner;

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
import com.example.posdaming.json.DataJson;
import com.example.posdaming.json.MaterialItemJson;
import com.example.posdaming.utils.SharePreUtils;
import com.example.posdaming.utils.ToastUtil;
import com.example.posdaming.utils.URL;
import com.example.posdaming.view.TransferPopup;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 99213 on 2017/10/22.
 */

public class PutOnActivity extends BaseActivity implements View.OnClickListener, InnerAdapter.OnEditChangeListener, TransferPopup.OnClickChangeListener {

    private LinearLayout ll_scan;
    private TextView tv_scan_select;
    private LinearLayout ll_handle;
    private TextView tv_handle;
    private EditText edt_equ;
    private String TAG = "CheckActivity";
    private static final int REQ_CODE_PERMISSION = 0x1111;

    private List<MaterialItemJson.DataBean> orderitemsBeans;
    //默认仓库
    //ware_id":10,"ware_name":"外购收料仓","ware_no":"WG3-01"
    //仓库id
    private int wareId = 10;


    private EditText edt_no;
    private ImageView iv_search;
    private TextView tv_name;
    private TextView tv_confirm_name;
    private Button btn_save;
    private Button btn_confirm;

    //recyclerview
    private RecyclerView rv_content;
    private LinearLayoutManager mLinearLayoutManager;
    private InnerAdapter mStockInOutsourcingAdapter;
    //用户名
    private String mUserName;
    private int mUserId;

    //是否保存
    private boolean isSavePutOn = false;
    private String mOrderSave = "";

    //扫描的编号
    private String mOrderNo = "";
    private int mPosition;
    TransferPopup mTransferPopup;

    @Override
    protected void initVariable() {

    }

    @SuppressLint("CutPasteId")
    @Override
    protected void initView(Bundle savedInstanceState) {

        setContentView(R.layout.activity_put_on);
        ll_scan = (LinearLayout) findViewById(R.id.ll_scan);
        tv_scan_select = (TextView) findViewById(R.id.tv_scan_select);


        edt_no = (EditText) findViewById(R.id.edt_no);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        rv_content = (RecyclerView) findViewById(R.id.rv_content);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_confirm_name = (TextView) findViewById(R.id.tv_confirm_name);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        edt_equ = (EditText) findViewById(R.id.edt_equ);


        ll_handle = (LinearLayout) findViewById(R.id.ll_handle);
        tv_handle = (TextView) findViewById(R.id.tv_handle);
        rv_content = (RecyclerView) findViewById(R.id.rv_content);
        ll_scan.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        iv_search.setOnClickListener(this);
        edt_no.setOnClickListener(this);
        ll_handle.setOnClickListener(this);
    }


    @Override
    protected void initData() {

        recycler();


        mUserName = SharePreUtils.getString("user_name", "", this);
        mUserId = SharePreUtils.getInt("user_id", 0, this);

        tv_name.setText(mUserName);
        tv_confirm_name.setText(mUserName);

        isSavePutOn = SharePreUtils.getBoolean("isSavePutOn", false, this);


        if (isSavePutOn) {
            mOrderSave = SharePreUtils.getString("putOnSave", "", this);
            getItem(mOrderSave);
        }


    }

    private void recycler() {

        mLinearLayoutManager = new LinearLayoutManager(this);
        mStockInOutsourcingAdapter = new InnerAdapter(this, new ArrayList<MaterialItemJson.DataBean>());
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
     * 获取单个物料信息
     */
    private void getItem(final String itemNo) {
        RequestParams params = new RequestParams(URL.HOST);
        params.addBodyParameter(URL.ACCESS_KEY, URL.ACCESS_VALUE);
        params.addBodyParameter("method", "item.get");
        params.addBodyParameter("item_no", itemNo);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, result);
                MaterialItemJson orderJson = new Gson().fromJson(result, MaterialItemJson.class);

                if (orderJson.isIs_success()) {
                    orderitemsBeans = new ArrayList<>();
                    orderitemsBeans.add(orderJson.getData());
                    for (int i = 0; i < orderitemsBeans.size(); i++) {
                        orderitemsBeans.get(i).setItem_num(0);
                    }

                    mStockInOutsourcingAdapter.refresh(orderitemsBeans);
                    edt_no.setText(itemNo);
                    edt_no.setSelection(itemNo.length());
                    mOrderNo = itemNo;
                } else {
                    mStockInOutsourcingAdapter.refresh(new ArrayList<MaterialItemJson.DataBean>());
                    ToastUtil.show("暂无物料", PutOnActivity.this);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG, ex.getMessage());
                ToastUtil.show("扫描失败,请重新扫描", PutOnActivity.this);
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
     * putaway.add
     * user_id创建人id，类型：int
     * item_no物料编号，类型：string
     * item_num上架数量，类型：int
     * equipment_no设备编号，类型：string
     */
    private void submit() {
        RequestParams params = new RequestParams(URL.HOST);
        params.addParameter(URL.ACCESS_KEY, URL.ACCESS_VALUE);
        params.addParameter("method", "putaway.add");
        params.addParameter("user_id", mUserId);
        params.addParameter("item_no", mOrderNo);
        params.addParameter("item_num", orderitemsBeans.get(0).getItem_num());
        params.addParameter("equipment_no", edt_equ.getText().toString());


        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, result);

                DataJson dataJson = new Gson().fromJson(result, DataJson.class);
                if (dataJson.isIs_success()) {
                    SharePreUtils.putBoolean("isSavePutOn", false, PutOnActivity.this);
                    ToastUtil.show("上架成功", PutOnActivity.this);
                    finish();
                } else {
                    if(!TextUtils.isEmpty(dataJson.getMsg())) {
                        ToastUtil.show(dataJson.getMsg(), PutOnActivity.this);
                    }else{
                        ToastUtil.show("上架失败", PutOnActivity.this);
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
            case R.id.ll_scan:
                mTransferPopup = new TransferPopup(this);
                mTransferPopup.showPopupWindow(ll_handle);
                mTransferPopup.setOnClickChangeListener(this);

                tv_scan_select.setBackgroundResource(R.drawable.radio_button_checked);
                tv_handle.setBackgroundResource(R.drawable.radio_button_uncheck);

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
                    getItem(edt_no.getText().toString());
                } else {
                    ToastUtil.show("请输入物料条码", this);
                }
                break;
            //保存
            case R.id.btn_save:
                SharePreUtils.putBoolean("isSavePutOn", true, this);
                SharePreUtils.putString("putOnSave", edt_no.getText().toString(), this);
                ToastUtil.show("已保存成功", this);
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
                    if ("".equals(edt_equ.getText().toString().trim())) {
                        ToastUtil.show("请输入设备编号", this);
                    } else {
                        submit();
                    }
                } else {
                    ToastUtil.show("暂无物料信息,请添加", this);
                }

                break;
        }
    }

    private void startCaptureActivityForResult() {
        Intent intent = new Intent(PutOnActivity.this, CaptureActivity.class);
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
                    ToastUtil.show("请在app设置界面开启照相权限", PutOnActivity.this);
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
                        if (mPosition == 1) {

                            //ToastUtil.show(result, PutOnActivity.this);
                            getItem(result);
                        } else if (mPosition == 2) {
                            edt_equ.setText(result);
                        }

                        break;
                    case RESULT_CANCELED:
                        if (data != null) {
                            // for some reason camera is not working correctly
                            //tvResult.setText(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                            //ToastUtil.showToast(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT), MainActivity.this);
                        }
                        ToastUtil.show("取消扫描", PutOnActivity.this);
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
    public void onMaterialClick(int position) {


        if (position == 1) {
            mPosition = 1;
        } else {
            mPosition = 2;
        }
        mTransferPopup.dismiss();
        if (ContextCompat.checkSelfPermission(PutOnActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Do not have the permission of camera, request it.
            ActivityCompat.requestPermissions(PutOnActivity.this, new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);
        } else {
            // Have gotten the permission
            startCaptureActivityForResult();
        }
    }

    @Override
    public void onEquClick() {

//        mTransferPopup.dismiss();
//        if (ContextCompat.checkSelfPermission(PutOnActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            // Do not have the permission of camera, request it.
//            ActivityCompat.requestPermissions(PutOnActivity.this, new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);
//        } else {
//            // Have gotten the permission
//            startCaptureActivityForResult();
//        }
    }
}
