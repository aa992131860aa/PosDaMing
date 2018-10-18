package com.example.posdaming.controller.quality;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.posdaming.R;
import com.example.posdaming.controller.BaseActivity;
import com.example.posdaming.json.DataJson;
import com.example.posdaming.json.QualityJson;
import com.example.posdaming.utils.SharePreUtils;
import com.example.posdaming.utils.ToastUtil;
import com.example.posdaming.utils.URL;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 99213 on 2017/10/22.
 */

public class QualityOutsourcingDetailActivity extends BaseActivity implements View.OnClickListener, QualityOutsourcingDetailAdapter.OnEditChangeListener {


    private String TAG = "StockInOutsourcingActivity";
    private LinearLayoutManager mLinearLayoutManager;

    private QualityOutsourcingDetailAdapter mStockInOutsourcingAdapter;

    private TextView tv_name;
    private List<QualityJson.DataBean.QualityitemsBean> mQualityitemsBeans;
    private RecyclerView rv_content;
    private int mUserId;

    @Override
    protected void initVariable() {

    }

    @SuppressLint("CutPasteId")
    @Override
    protected void initView(Bundle savedInstanceState) {

        setContentView(R.layout.activity_quality_outsourcing_detail);

        tv_name = (TextView) findViewById(R.id.tv_name);
        rv_content = (RecyclerView) findViewById(R.id.rv_content);

    }


    @Override
    protected void initData() {
        mUserId = SharePreUtils.getInt("user_id", 0, this);
        recycler();

        getQuality(getIntent().getIntExtra("fid",0));
        tv_name.setText(getIntent().getStringExtra("name"));
    }

    private void recycler() {

        mLinearLayoutManager = new LinearLayoutManager(this);
        mStockInOutsourcingAdapter = new QualityOutsourcingDetailAdapter(this, new ArrayList<QualityJson.DataBean.QualityitemsBean>());
        //mStockInOutsourcingAdapter.setDetailClickListener(this);
        mStockInOutsourcingAdapter.setDetailClickListener(this);
        rv_content.setLayoutManager(mLinearLayoutManager);
        rv_content.setAdapter(mStockInOutsourcingAdapter);


    }


    /**
     * 获取入库单物料质检项信息
     * fid 单据物料项id，类型：int
     */
    private void getQuality(final int fid) {
        RequestParams params = new RequestParams(URL.HOST);
        params.addParameter(URL.ACCESS_KEY, URL.ACCESS_VALUE);
        params.addParameter("method", "quality.item.get");
        params.addParameter("fid", fid);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, result);
                QualityJson qualityJson = new Gson().fromJson(result, QualityJson.class);
                if (qualityJson.isIs_success()) {
                    mQualityitemsBeans = new ArrayList<>();
                    mQualityitemsBeans.addAll(qualityJson.getData().getQualityitems());
                    mStockInOutsourcingAdapter.refresh(mQualityitemsBeans);
                } else {
                    ToastUtil.show("获取入库单物料质检项信息失败", QualityOutsourcingDetailActivity.this);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG, ex.getMessage());
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
                    SharePreUtils.putBoolean("isSaveQualityOutSourcing", false, QualityOutsourcingDetailActivity.this);
                    mStockInOutsourcingAdapter.refresh(mQualityitemsBeans);
                    ToastUtil.show("质检成功", QualityOutsourcingDetailActivity.this);
                    //finish();
                } else {
                    if (!TextUtils.isEmpty(dataJson.getMsg())) {
                        ToastUtil.show(dataJson.getMsg(), QualityOutsourcingDetailActivity.this);
                    } else {
                        ToastUtil.show("质检失败", QualityOutsourcingDetailActivity.this);
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

        }
    }


    @Override
    public void OnEditChange(int position, String result, int location) {
        if (location == 1) {
            mQualityitemsBeans.get(position).setResult1(result);
        } else if (location == 2) {
            mQualityitemsBeans.get(position).setResult2(result);
        } else if (location == 3) {
            mQualityitemsBeans.get(position).setResult3(result);
        } else if (location == 4) {
            mQualityitemsBeans.get(position).setResult4(result);
        } else if (location == 5) {
            mQualityitemsBeans.get(position).setResult5(result);
        }
    }

    @Override
    public void OnButtonClick(View view, int position, int num) {
        mQualityitemsBeans.get(position).setDecide(num);
        QualityJson.DataBean.QualityitemsBean qualityitemsBean = mQualityitemsBeans.get(position);
        submit(qualityitemsBean.getQualityitem_id(), qualityitemsBean.getResult1(), qualityitemsBean.getResult2(), qualityitemsBean.getResult3(), qualityitemsBean.getResult4(), qualityitemsBean.getResult5(), qualityitemsBean.getDecide());

    }
}
