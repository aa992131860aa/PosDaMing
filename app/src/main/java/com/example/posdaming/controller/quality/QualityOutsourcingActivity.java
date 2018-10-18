package com.example.posdaming.controller.quality;

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
import com.example.posdaming.json.ItemFlowQualityJson;
import com.example.posdaming.json.QualityJson;
import com.example.posdaming.utils.SharePreUtils;
import com.example.posdaming.utils.ToastUtil;
import com.example.posdaming.utils.URL;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 99213 on 2017/10/22.
 */

public class QualityOutsourcingActivity extends BaseActivity implements View.OnClickListener, QualityOutsourcingAdapter.OnEditChangeListener {

    private LinearLayout ll_scan;
    private TextView tv_scan_select;
    private LinearLayout ll_handle;
    private TextView tv_handle;
    private String TAG = "StockInOutsourcingActivity";
    private static final int REQ_CODE_PERMISSION = 0x1111;

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
    private QualityOutsourcingAdapter mStockInOutsourcingAdapter;
    //用户名
    private String mUserName;
    private int mUserId;

    //是否保存
    private boolean isSaveQualityOutSourcing = false;
    private String mOrderSave = "";

    //扫描的编号
    private String mOrderNo = "";
    private TextView tv_type;
    private List<ItemFlowQualityJson.DataBean.ItemflowitemsBean> mQualityitemsBeans;

    @Override
    protected void initVariable() {

    }

    @SuppressLint("CutPasteId")
    @Override
    protected void initView(Bundle savedInstanceState) {

        setContentView(R.layout.activity_quality_outsourcing);
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
        tv_type = (TextView) findViewById(R.id.tv_type);


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
        //日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //tv_date.setText(sdf.format(new Date()));

        mUserName = SharePreUtils.getString("user_name", "", this);
        mUserId = SharePreUtils.getInt("user_id", 0, this);

        tv_name.setText(mUserName);
        tv_confirm_name.setText(mUserName);

        isSaveQualityOutSourcing = SharePreUtils.getBoolean("isSaveQualityOutSourcing", false, this);


        if (isSaveQualityOutSourcing) {
            mOrderSave = SharePreUtils.getString("qualityOutsourcingSave", "", this);
            edt_no.setText(mOrderSave);
            getOrder(mOrderSave);
        }


    }

    private void recycler() {

        mLinearLayoutManager = new LinearLayoutManager(this);
        mStockInOutsourcingAdapter = new QualityOutsourcingAdapter(this, new ArrayList<ItemFlowQualityJson.DataBean.ItemflowitemsBean>());
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
     * 获取库存单据信息
     * itemflow_no 单据号，类型：string
     */
    private void getOrder(final String itemNo) {
        RequestParams params = new RequestParams(URL.HOST);
        params.addBodyParameter(URL.ACCESS_KEY, URL.ACCESS_VALUE);
        params.addBodyParameter("method", "itemflow.get");
        params.addBodyParameter("itemflow_no", itemNo);
        //Log.e(TAG, itemNo);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ItemFlowQualityJson itemFlowJson = new Gson().fromJson(result, ItemFlowQualityJson.class);
                if (itemFlowJson.isIs_success()) {
                    mQualityitemsBeans = new ArrayList<>();
                    tv_date.setText(itemFlowJson.getData().getItemflow().getCreated().split(" ")[0]);
                    tv_type.setText(itemFlowJson.getData().getItemflow().getFlow_type_name());
                    mQualityitemsBeans = itemFlowJson.getData().getItemflowitems();
                    mStockInOutsourcingAdapter.refresh(mQualityitemsBeans);

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.show("扫描失败,请重新扫描", QualityOutsourcingActivity.this);
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
     * user_id 操作员人id，类型：int
     * qualityitem_id 质检项id，类型：int
     * result1 检验结果1
     * result2 检验结果2
     * result3 检验结果3
     * result4 检验结果4
     * result5 检验结果5
     * decide 判定结果(0:不合格,1:合格)
     */
    private void submit(int qualityItemId, String result1, String result2, String result3, String result4, String result5, int decide) {
        RequestParams params = new RequestParams(URL.HOST);
        params.addParameter(URL.ACCESS_KEY, URL.ACCESS_VALUE);
        params.addParameter("method", "quality.item.update");
        params.addParameter("user_id", mUserId);
        params.addParameter("qualityitem_id", qualityItemId);
        params.addParameter("result1", result1);
        params.addParameter("result2", result2);
        params.addParameter("result3", result3);
        params.addParameter("result4", result4);
        params.addParameter("result5", result5);
        params.addParameter("decide", decide);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, result);


                DataJson dataJson = new Gson().fromJson(result, DataJson.class);
                if (dataJson.isIs_success()) {
                    SharePreUtils.putBoolean("isSaveQualityOutSourcing", false, QualityOutsourcingActivity.this);
                    mStockInOutsourcingAdapter.refresh(mQualityitemsBeans);
                    ToastUtil.show("质检成功", QualityOutsourcingActivity.this);
                    //finish();
                } else {
                    if (!TextUtils.isEmpty(dataJson.getMsg())) {
                        ToastUtil.show(dataJson.getMsg(), QualityOutsourcingActivity.this);
                    } else {
                        ToastUtil.show("质检失败", QualityOutsourcingActivity.this);
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
                tv_scan_select.setBackgroundResource(R.drawable.radio_button_checked);
                tv_handle.setBackgroundResource(R.drawable.radio_button_uncheck);
                if (ContextCompat.checkSelfPermission(QualityOutsourcingActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Do not have the permission of camera, request it.
                    ActivityCompat.requestPermissions(QualityOutsourcingActivity.this, new String[]{Manifest.permission.CAMERA}, REQ_CODE_PERMISSION);
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
                    getOrder(edt_no.getText().toString());
                } else {
                    ToastUtil.show("请输入任务条码", this);
                }
                break;
            //保存
            case R.id.btn_save:
                SharePreUtils.putBoolean("isSaveQualityOutSourcing", true, this);
                SharePreUtils.putString("qualityOutsourcingSave", edt_no.getText().toString(), this);
                ToastUtil.show("已保存成功", this);
                finish();
                break;
            //条码
            case R.id.edt_no:
                edt_no.setFocusableInTouchMode(true);
                edt_no.setFocusable(true);
                break;
            //提交
//            case R.id.btn_confirm:
//                if (mQualityitemsBeans != null && mQualityitemsBeans.size() > 0) {
//                    submit();
//                } else {
//                    ToastUtil.show("暂无质检信息,请添加", this);
//                }
//
//                break;
        }
    }

    private void startCaptureActivityForResult() {
        Intent intent = new Intent(QualityOutsourcingActivity.this, CaptureActivity.class);
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
                    ToastUtil.show("请在app设置界面开启照相权限", QualityOutsourcingActivity.this);
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
                        edt_no.setText(result);
                        //ToastUtil.show(result, QualityOutsourcingActivity.this);
                        getOrder(result);

                        break;
                    case RESULT_CANCELED:
                        if (data != null) {
                            // for some reason camera is not working correctly
                            //tvResult.setText(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT));
                            //ToastUtil.showToast(data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT), MainActivity.this);
                        }
                        ToastUtil.show("取消扫描", QualityOutsourcingActivity.this);
                        break;
                }
                break;
        }
    }





    @Override
    public void OnTextClick(View view, int position) {
       Intent intent = new Intent(this,QualityOutsourcingDetailActivity.class);
       intent.putExtra("fid",mQualityitemsBeans.get(position).getFid());
       intent.putExtra("name",mQualityitemsBeans.get(position).getItem_name());
       startActivity(intent);
    }
}
