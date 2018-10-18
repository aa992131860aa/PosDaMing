package com.example.posdaming.controller.query;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.posdaming.R;
import com.example.posdaming.json.ItemFlowJson;

import java.util.List;

/**
 * Created by 99213 on 2017/7/26.
 */

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.MyHolder> {
    private Context mContext;
    private List<ItemFlowJson.DataBean> mLists;
    private OnDetailClickListener mHospitalChildListener;

    public interface OnDetailClickListener {
        void onQueryClick(View view, int position);
    }

    public void setDetailClickListener(OnDetailClickListener listener) {
        mHospitalChildListener = listener;
    }

    public QueryAdapter(Context context, List<ItemFlowJson.DataBean> lists) {
        mContext = context;
        mLists = lists;

    }

    public void refresh(List<ItemFlowJson.DataBean> lists) {
        mLists = lists;
        notifyDataSetChanged();
    }

    @Override
    public QueryAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.query_item, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final QueryAdapter.MyHolder holder, final int position) {


        holder.tv_bill_no.setText(mLists.get(position).getItemflow().getItemflow_no());
        holder.tv_bill_name.setText(mLists.get(position).getItemflow().getFlow_type_name());
        holder.tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHospitalChildListener.onQueryClick(view, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_bill_no;
        TextView tv_bill_name;
        TextView tv_detail;


        public MyHolder(View v) {
            super(v);

            tv_bill_no = (TextView) v.findViewById(R.id.tv_bill_no);
            tv_bill_name = (TextView) v.findViewById(R.id.tv_bill_name);
            tv_detail = (TextView) v.findViewById(R.id.tv_detail);
        }
    }
}