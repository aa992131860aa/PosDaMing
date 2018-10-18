package com.example.posdaming.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.example.posdaming.R;

import java.lang.reflect.Field;

public class TransferPopup extends PopupWindow {
    private Context mContext;
    private int statusBarHeight = 0;

    public TransferPopup(final Context context) {
        mContext = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.menu_layout, null);


        this.setContentView(v);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //ToastUtil.showToast(DisplayUtil.dip2px(context,50)*2+","+LocalApplication.getInstance().screenH,context);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        TextView tv_material = (TextView) v.findViewById(R.id.tv_material);
        TextView tv_equ = (TextView) v.findViewById(R.id.tv_equ);
        LinearLayout ll_content = (LinearLayout) v.findViewById(R.id.ll_content);


        ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        tv_material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onMaterialClick(1);
            }
        });
        tv_equ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMaterialClick(2);
            }
        });

        this.setFocusable(true);
        this.setOutsideTouchable(false);
        this.setBackgroundDrawable(new BitmapDrawable());


    }
    public TransferPopup(final Context context,String type) {
        mContext = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.menu_layout, null);


        this.setContentView(v);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //ToastUtil.showToast(DisplayUtil.dip2px(context,50)*2+","+LocalApplication.getInstance().screenH,context);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        TextView tv_material = (TextView) v.findViewById(R.id.tv_material);
        TextView tv_equ = (TextView) v.findViewById(R.id.tv_equ);
        TextView tv_line2 = (TextView)v.findViewById(R.id.tv_line2);
        TextView tv_equ3 = (TextView) v.findViewById(R.id.tv_equ3);
        LinearLayout ll_content = (LinearLayout) v.findViewById(R.id.ll_content);

              if("transfer".equals(type)){

                  tv_equ3.setVisibility(View.VISIBLE);
                  tv_line2.setVisibility(View.VISIBLE);

                  tv_equ.setText("来源设备编号");

                  tv_material.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          mListener.onMaterialClick(1);
                      }
                  });
                  tv_equ.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          mListener.onMaterialClick(2);
                      }
                  });
                  tv_equ3.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          mListener.onMaterialClick(3);
                      }
                  });
              }
              else if("speed".equals(type)){
                  tv_equ3.setVisibility(View.VISIBLE);
                  tv_line2.setVisibility(View.VISIBLE);
                  tv_equ.setText("采购单号");
                  tv_equ3.setText("生产任务单");
                  tv_material.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          mListener.onMaterialClick(1);
                      }
                  });
                  tv_equ.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          mListener.onMaterialClick(2);
                      }
                  });
                  tv_equ3.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          mListener.onMaterialClick(3);
                      }
                  });
              }
        ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });



        this.setFocusable(true);
        this.setOutsideTouchable(false);
        this.setBackgroundDrawable(new BitmapDrawable());


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
            this.showAtLocation(parent, Gravity.CENTER,0,0);
            //this.showPopupWindow(parent);
        } else {
            this.dismiss();
        }
    }


    public interface OnClickChangeListener {
         void onMaterialClick(int position);
         void onEquClick();
    }

    public void setOnClickChangeListener(OnClickChangeListener clickChangeListener) {
        mListener = clickChangeListener;
    }

    private OnClickChangeListener mListener;

}