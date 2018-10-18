package com.example.posdaming.controller.stockin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.posdaming.R;
import com.example.posdaming.json.MaterialItemJson;
import com.example.posdaming.json.OrderJson;

import java.util.List;

/**
 * Created by 99213 on 2017/7/26.
 */

public class StockInOtherAdapter extends RecyclerView.Adapter<StockInOtherAdapter.MyHolder> {
    private Context mContext;
    private List<MaterialItemJson.DataBean> mLists;
    private OnEditChangeListener mHospitalChildListener;
    private String mType;

    public interface OnEditChangeListener {
        void OnEditChange(View view, int position, String num);
    }

    public void setDetailClickListener(OnEditChangeListener listener) {
        mHospitalChildListener = listener;
    }

    public StockInOtherAdapter(Context context, List<MaterialItemJson.DataBean> lists) {
        mContext = context;
        mLists = lists;

    }


    public void refresh(List<MaterialItemJson.DataBean> lists) {
        mLists = lists;
        notifyDataSetChanged();
    }

    public void refresh(List<MaterialItemJson.DataBean> lists, String type) {
        mLists = lists;
        notifyDataSetChanged();
        mType = type;
    }

    @Override
    public StockInOtherAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.stock_in_outsourcing_item, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final StockInOtherAdapter.MyHolder holder, final int position) {

          holder.tv_name.setText(mLists.get(position).getItem_name());
        holder.tv_unit.setText(mLists.get(position).getMeasure_unit());
        holder.edt_num.setText(mLists.get(position).getNum()+"");

        holder.edt_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!"".equals(s.toString())) {
                    mHospitalChildListener.OnEditChange(holder.edt_num, position, s.toString());
                }
            }
        });


            holder.tv_spec.setVisibility(View.VISIBLE);
            holder.tv_spec.setText(mLists.get(position).getItem_num()+"");


    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        EditText edt_num;
        TextView tv_unit;
        TextView tv_spec;

        public MyHolder(View v) {
            super(v);
            tv_name = (TextView) v.findViewById(R.id.tv_name);
            edt_num = (EditText) v.findViewById(R.id.edt_num);
            tv_unit = (TextView) v.findViewById(R.id.tv_unit);
            tv_spec = (TextView) v.findViewById(R.id.tv_spec);

        }
    }
}