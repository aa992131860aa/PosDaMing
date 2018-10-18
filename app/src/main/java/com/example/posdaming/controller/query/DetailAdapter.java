package com.example.posdaming.controller.query;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.posdaming.R;
import com.example.posdaming.entity.Device;
import com.example.posdaming.json.ItemFlowJson;

import java.util.List;

/**
 * Created by 99213 on 2017/7/26.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyHolder> {
    private Context mContext;
    private List<ItemFlowJson.DataBean.ItemflowitemsBean> mLists;
    private  OnDetailClickListener mHospitalChildListener;

    public interface OnDetailClickListener {
        void onDeviceClick(View view, int position);
    }

    public void setDetailClickListener(OnDetailClickListener listener) {
        mHospitalChildListener = listener;
    }

    public DetailAdapter(Context context, List<ItemFlowJson.DataBean.ItemflowitemsBean> lists) {
        mContext = context;
        mLists = lists;

    }

    public void refresh(List<ItemFlowJson.DataBean.ItemflowitemsBean> lists) {
        mLists = lists;
        notifyDataSetChanged();
    }

    @Override
    public DetailAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.device_item_item, parent, false);
         MyHolder myHolder = new  MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final DetailAdapter.MyHolder holder, final int position) {
        holder.tv_no.setText(mLists.get(position).getItem_no());
        holder.tv_name.setText(mLists.get(position).getItem_name());
        holder.tv_num.setText(mLists.get(position).getItem_num()+"");
        holder.tv_unit.setText(mLists.get(position).getItem_specs());

    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
       TextView tv_no;
       TextView tv_name;
       TextView tv_num;
       TextView tv_unit;



        public MyHolder(View v) {
            super(v);
           tv_no = (TextView) v.findViewById(R.id.tv_no);
           tv_name = (TextView) v.findViewById(R.id.tv_name);
           tv_num = (TextView) v.findViewById(R.id.tv_num);
           tv_unit = (TextView) v.findViewById(R.id.tv_unit);

        }
    }
}