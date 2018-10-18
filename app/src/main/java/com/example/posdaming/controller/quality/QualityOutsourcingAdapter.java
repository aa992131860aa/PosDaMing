package com.example.posdaming.controller.quality;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.posdaming.R;
import com.example.posdaming.json.ItemFlowQualityJson;
import com.example.posdaming.json.QualityJson;

import java.util.List;

/**
 * Created by 99213 on 2017/7/26.
 */

public class QualityOutsourcingAdapter extends RecyclerView.Adapter<QualityOutsourcingAdapter.MyHolder> {
    private Context mContext;
    private List<ItemFlowQualityJson.DataBean.ItemflowitemsBean> mLists;
    private OnEditChangeListener mHospitalChildListener;
    private String mType;

    public interface OnEditChangeListener {


        void OnTextClick(View view, int position);
    }

    public void setDetailClickListener(OnEditChangeListener listener) {
        mHospitalChildListener = listener;
    }

    public QualityOutsourcingAdapter(Context context, List<ItemFlowQualityJson.DataBean.ItemflowitemsBean> lists) {
        mContext = context;
        mLists = lists;

    }


    public void refresh(List<ItemFlowQualityJson.DataBean.ItemflowitemsBean> lists) {
        mLists = lists;
        notifyDataSetChanged();
    }

    public void refresh(List<ItemFlowQualityJson.DataBean.ItemflowitemsBean> lists, String type) {
        mLists = lists;
        notifyDataSetChanged();
        mType = type;
    }

    @Override
    public QualityOutsourcingAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.quality_item, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final QualityOutsourcingAdapter.MyHolder holder, final int position) {

        holder.tv_name.setText(mLists.get(position).getItem_name());
        holder.tv_standard.setText(mLists.get(position).getItem_specs());
        holder.tv_num.setText(mLists.get(position).getItem_num()+"");
        holder.tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHospitalChildListener.OnTextClick(v,position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_standard;
        TextView tv_num;
        TextView tv_detail;


        public MyHolder(View v) {
            super(v);

            tv_name = (TextView) v.findViewById(R.id.tv_name);
            tv_standard = (TextView) v.findViewById(R.id.tv_standard);
            tv_num = (TextView) v.findViewById(R.id.tv_num);
            tv_detail = (TextView) v.findViewById(R.id.tv_detail);



        }
    }
}