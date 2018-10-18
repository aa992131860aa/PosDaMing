package com.example.posdaming.controller.query;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.posdaming.R;
import com.example.posdaming.controller.BaseActivity;
import com.example.posdaming.json.ItemFlowJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 99213 on 2017/10/22.
 */

public class QueryDetailActivity extends BaseActivity {
    private LinearLayoutManager mLinearLayoutManager;
    private DetailAdapter mDetailAdapter;
    private RecyclerView rv_content;
    private List<ItemFlowJson.DataBean.ItemflowitemsBean> mList = new ArrayList<>();
    private ItemFlowJson.DataBean mDetail;

    private TextView tv_no;
    private TextView tv_name;
    private TextView tv_status;
    private TextView tv_date;

    @Override
    protected void initVariable() {

        mDetail = (ItemFlowJson.DataBean) getIntent().getSerializableExtra("detail");

        recyclerQuery();
        tv_no.setText(mDetail.getItemflow().getItemflow_no());
        tv_name.setText(mDetail.getItemflow().getFlow_type_name());
        tv_status.setText(mDetail.getItemflow().getStatus_desc());
        tv_date.setText(mDetail.getItemflow().getCreated().split(" ")[0]);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_query_detail);
        rv_content = (RecyclerView) findViewById(R.id.rv_content);
        tv_no = (TextView) findViewById(R.id.tv_no);
        tv_name = (TextView) findViewById(R.id.tv_no);
        tv_date = (TextView) findViewById(R.id.tv_no);
        tv_status = (TextView) findViewById(R.id.tv_no);


    }

    private void recyclerQuery() {

        mLinearLayoutManager = new LinearLayoutManager(this);
        mDetailAdapter = new DetailAdapter(this, mDetail.getItemflowitems());

        rv_content.setLayoutManager(mLinearLayoutManager);
        rv_content.setAdapter(mDetailAdapter);


    }

    @Override
    protected void initData() {

    }
}
