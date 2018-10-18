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
import com.example.posdaming.json.QualityJson;

import java.util.List;

/**
 * Created by 99213 on 2017/7/26.
 */

public class QualityOutsourcingDetailAdapter extends RecyclerView.Adapter<QualityOutsourcingDetailAdapter.MyHolder> {
    private Context mContext;
    private List<QualityJson.DataBean.QualityitemsBean> mLists;
    private OnEditChangeListener mHospitalChildListener;
    private String mType;

    public interface OnEditChangeListener {
        void OnEditChange(int position, String result, int location);

        void OnButtonClick(View view, int position, int num);
    }

    public void setDetailClickListener(OnEditChangeListener listener) {
        mHospitalChildListener = listener;
    }

    public QualityOutsourcingDetailAdapter(Context context, List<QualityJson.DataBean.QualityitemsBean> lists) {
        mContext = context;
        mLists = lists;

    }


    public void refresh(List<QualityJson.DataBean.QualityitemsBean> lists) {
        mLists = lists;
        notifyDataSetChanged();
    }

    public void refresh(List<QualityJson.DataBean.QualityitemsBean> lists, String type) {
        mLists = lists;
        notifyDataSetChanged();
        mType = type;
    }

    @Override
    public QualityOutsourcingDetailAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.quality_detail_item, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final QualityOutsourcingDetailAdapter.MyHolder holder, final int position) {

        holder.tv_name.setText(mLists.get(position).getQuality_name());
        holder.tv_size.setText(mLists.get(position).getQuality_size());

        holder.tv_result1.setText(mLists.get(position).getResult1());
        holder.tv_result2.setText(mLists.get(position).getResult2());
        holder.tv_result3.setText(mLists.get(position).getResult3());
        holder.tv_result4.setText(mLists.get(position).getResult4());
        holder.tv_result5.setText(mLists.get(position).getResult5());

        if (mLists.get(position).getDecide() == 0) {
            holder.btn_no.setTextColor(mContext.getResources().getColor(R.color.highlight));
            holder.btn_pass.setTextColor(mContext.getResources().getColor(R.color.font_black_6));
        } else {
            holder.btn_pass.setTextColor(mContext.getResources().getColor(R.color.highlight));
            holder.btn_no.setTextColor(mContext.getResources().getColor(R.color.font_black_6));
        }

        holder.btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHospitalChildListener.OnButtonClick(v, position, 0);
            }
        });
        holder.btn_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHospitalChildListener.OnButtonClick(v, position, 1);
            }
        });
        editChange(holder.tv_result1, position,1);
        editChange(holder.tv_result2, position,2);
        editChange(holder.tv_result3, position,3);
        editChange(holder.tv_result4, position,4);
        editChange(holder.tv_result5, position,5);

    }

    private void editChange(EditText editText, final int position,final int location) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mHospitalChildListener.OnEditChange(position, s.toString(),location);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_size;

        EditText tv_result1;
        EditText tv_result2;
        EditText tv_result3;
        EditText tv_result4;
        EditText tv_result5;


        Button btn_pass;
        Button btn_no;

        public MyHolder(View v) {
            super(v);

            tv_name = (TextView) v.findViewById(R.id.tv_name);
            tv_size = (TextView) v.findViewById(R.id.tv_size);

            tv_result1 = (EditText) v.findViewById(R.id.tv_result1);
            tv_result2 = (EditText) v.findViewById(R.id.tv_result2);
            tv_result3 = (EditText) v.findViewById(R.id.tv_result3);
            tv_result4 = (EditText) v.findViewById(R.id.tv_result4);
            tv_result5 = (EditText) v.findViewById(R.id.tv_result5);

            btn_pass = (Button) v.findViewById(R.id.btn_pass);
            btn_no = (Button) v.findViewById(R.id.btn_no);


        }
    }
}