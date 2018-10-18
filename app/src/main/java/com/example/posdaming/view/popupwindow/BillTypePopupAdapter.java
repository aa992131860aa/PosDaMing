package com.example.posdaming.view.popupwindow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.posdaming.R;
import com.example.posdaming.entity.BillType;

import java.util.List;

/**
 * Created by 99213 on 2017/7/26.
 */

public class BillTypePopupAdapter extends RecyclerView.Adapter<BillTypePopupAdapter.MyHolder> {
    private Context mContext;
    private List<BillType> mLists;
    private OnItemClickListener mHospitalChildListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setDetailClickListener(OnItemClickListener listener) {
        mHospitalChildListener = listener;
    }

    public BillTypePopupAdapter(Context context, List<BillType> lists) {
        mContext = context;
        mLists = lists;

    }

    public void refresh(List<BillType> lists) {
        mLists = lists;
        notifyDataSetChanged();
    }

    @Override
    public BillTypePopupAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_bill_type_item, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final BillTypePopupAdapter.MyHolder holder, final int position) {
        holder.tv_name.setText(mLists.get(position).getName());
        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHospitalChildListener.onItemClick(v,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_name;


        public MyHolder(View v) {
            super(v);
            tv_name = (TextView) v.findViewById(R.id.tv_name);


        }
    }
}