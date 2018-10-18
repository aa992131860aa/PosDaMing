package com.example.posdaming.controller.query;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dsw.calendar.component.MonthView;
import com.dsw.calendar.theme.DefaultDayTheme;
import com.dsw.calendar.theme.IDayTheme;
import com.dsw.calendar.views.GridCalendarView;
import com.example.posdaming.R;


public class CalendarPopup extends PopupWindow {

    int position = 0;

    public CalendarPopup(final Activity context) {
        position = 0;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.calendar_pop, null);


        //TextView tv_calendar_pop_ok = (TextView) v.findViewById(R.id.tv_calendar_pop_ok);
        GridCalendarView gcv_calendar_pop = (GridCalendarView) v.findViewById(R.id.gcv_calendar_pop);

        IDayTheme dayTheme = new DefaultDayTheme();
        gcv_calendar_pop.setDayTheme(dayTheme);


        gcv_calendar_pop.setDateClick(new MonthView.IDateClick() {
            @Override
            public void onClickOnDate(int year, int month, int day) {
                  mListener.onCalendarClick(year+"-"+month+"-"+day);
            }
        });


        this.setContentView(v);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        this.setFocusable(true);
        this.setOutsideTouchable(false);
        this.setBackgroundDrawable(new BitmapDrawable());


    }

    /**
     * 显示popupWindow
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
//            this.showAsDropDown(parent, -20, 5);
            this.showAsDropDown(parent, 0, 1);
        } else {
            this.dismiss();
        }
    }


    public interface OnClickChangeListener {
        public void onCalendarClick(String time);
    }

    public void setOnClickChangeListener(OnClickChangeListener clickChangeListener) {
        mListener = clickChangeListener;
    }

    private OnClickChangeListener mListener;

}