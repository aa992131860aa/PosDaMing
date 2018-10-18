package com.example.posdaming.controller.query;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.posdaming.R;
import com.example.posdaming.json.EquipmentJson;

import java.util.List;

/**
 * Created by 99213 on 2017/7/26.
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.MyHolder> {
    private Context mContext;
    private List<EquipmentJson.DataBean> mLists;
    private OnDetailClickListener mHospitalChildListener;

    public interface OnDetailClickListener {
        void onDeviceClick(View view, int position);
    }

    public void setDetailClickListener(OnDetailClickListener listener) {
        mHospitalChildListener = listener;
    }

    public DeviceAdapter(Context context, List<EquipmentJson.DataBean> lists) {
        mContext = context;
        mLists = lists;

    }

    public void refresh(List<EquipmentJson.DataBean> lists) {
        mLists = lists;
        notifyDataSetChanged();
    }

    @Override
    public DeviceAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.device_item, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final DeviceAdapter.MyHolder holder, final int position) {
        holder.tv_no.setText(mLists.get(position).getEquipment_no());
        holder.tv_name.setText(mLists.get(position).getEquipment_name());
        holder.tv_date.setText(mLists.get(position).getStart_time().split(" ")[0]);
        holder.tv_status.setText(mLists.get(position).getEquipment_status_str());
        holder.tv_ware_no.setText(mLists.get(position).getWarearea_no());
        holder.tv_ware.setText(mLists.get(position).getWare_name());
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView tv_no;
        TextView tv_name;
        TextView tv_date;
        TextView tv_status;
        TextView tv_ware_no;
        TextView tv_ware;

        public MyHolder(View v) {
            super(v);
            tv_no = (TextView) v.findViewById(R.id.tv_no);
            tv_name = (TextView) v.findViewById(R.id.tv_name);
            tv_date = (TextView) v.findViewById(R.id.tv_date);
            tv_status = (TextView) v.findViewById(R.id.tv_status);
            tv_ware_no = (TextView) v.findViewById(R.id.tv_ware_no);
            tv_ware = (TextView) v.findViewById(R.id.tv_ware);
        }
    }
}