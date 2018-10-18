package com.example.posdaming.view.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.posdaming.R;
import com.example.posdaming.application.App;
import com.example.posdaming.entity.BillType;
import com.example.posdaming.utils.DisplayUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class BillTypePopup extends PopupWindow implements BillTypePopupAdapter.OnItemClickListener {
    private Context mContext;
    private int statusBarHeight = 0;
    private TextView mLeftView;
    private BillTypePopupAdapter mBillTypePopupAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView rv_content;
    private List<BillType> mBillTypes;
    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public BillTypePopup(final Activity context, TextView pTextView) {

        this(context, pTextView, 1);
    }


    public BillTypePopup(final Activity context, TextView pTextView, int multiple) {
        this(context, pTextView, multiple, new ArrayList<BillType>());

    }

    public BillTypePopup(final Activity context, TextView pTextView, int multiple, List<BillType> billTypes) {
        mContext = context;
        mLeftView = pTextView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.pop_bill_type, null);
        rv_content = (RecyclerView) v.findViewById(R.id.rv_content);
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mLeftView.measure(spec, spec);
        int measuredWidthTicketNum = mLeftView.getMeasuredWidth();
        //ToastUtil.show("width:"+measuredWidthTicketNum,mContext);

        this.setContentView(v);
        this.setWidth(App.SCREEN_WIDTH - multiple * measuredWidthTicketNum - DisplayUtil.dip2px(mContext, 16 + 5 + 5));
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        this.setBackgroundDrawable(new BitmapDrawable());
        mBillTypes = billTypes;
        recycler();

    }


    private void recycler() {

        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mBillTypePopupAdapter = new BillTypePopupAdapter(mContext, mBillTypes);
        //mStockInOutsourcingAdapter.setDetailClickListener(this);
        mBillTypePopupAdapter.setDetailClickListener(this);
        rv_content.setLayoutManager(mLinearLayoutManager);
        rv_content.setAdapter(mBillTypePopupAdapter);


    }

    private int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = mContext.getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }

    /**
     * 显示popupWindow
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
//            this.showAsDropDown(parent, -20, 5);

            this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        listener.onItemClick(view, position);
        dismiss();
    }


}