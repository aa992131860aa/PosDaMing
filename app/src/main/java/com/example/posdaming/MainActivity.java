package com.example.posdaming;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.posdaming.controller.inner.CheckActivity;
import com.example.posdaming.controller.inner.PutOnActivity;
import com.example.posdaming.controller.inner.TransferActivity;
import com.example.posdaming.controller.quality.QualityOutsourceActivity;
import com.example.posdaming.controller.quality.QualityOutsourcingActivity;
import com.example.posdaming.controller.query.QueryActivity;
import com.example.posdaming.controller.stockin.StockInFinishActivity;
import com.example.posdaming.controller.stockin.StockInOtherActivity;
import com.example.posdaming.controller.stockin.StockInOutsourceActivity;
import com.example.posdaming.controller.stockin.StockInOutsourcingActivity;
import com.example.posdaming.controller.stockin.StockInProcessActivity;
import com.example.posdaming.controller.stockout.QuickStockOutActivity;
import com.example.posdaming.controller.stockout.StockOutActivity;
import com.example.posdaming.controller.stockout.StockOutNormalActivity;
import com.example.posdaming.controller.stockout.StockOutOtherActivity;
import com.example.posdaming.controller.stockout.StockOutOutsourceActivity;
import com.example.posdaming.controller.stockout.StockOutSpeedActivity;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建表
        LitePal.getDatabase();

    }




    public void stockOutSpeedClick(View view) {
        startActivity(new Intent(this, StockOutSpeedActivity.class));
    }

    /**
     * 普通领料
     * @param view
     */
    public void stockOutNormalClick(View view) {
        startActivity(new Intent(this, StockOutNormalActivity.class));
    }

    /**
     * 其他出库
     * @param view
     */
    public void stockOutOtherClick(View view) {
        startActivity(new Intent(this, StockOutOtherActivity.class));
    }

    /**
     * 委外出库
     * @param view
     */
    public void stockOutOutsourceClick(View view) {
        startActivity(new Intent(this, StockOutOutsourceActivity.class));
    }

    /**
     * 外购入库
     * @param view
     */
    public void outsourcingClick(View view) {
        startActivity(new Intent(this, StockInOutsourcingActivity.class));
    }

    public void outsourceClick(View view) {
        startActivity(new Intent(this, StockInOutsourceActivity.class));
    }

    /**
     * 完工入库
     * @param view
     */
    public void finishClick(View view) {
        startActivity(new Intent(this, StockInFinishActivity.class));
    }

    /**
     * 其他入库
     */

    public void otherClick(View view) {
        startActivity(new Intent(this, StockInOtherActivity.class));
    }

    public void processClick(View view) {
        startActivity(new Intent(this, StockInProcessActivity.class));
    }

    /**
     * 外购质检
     * @param view
     */
    public void qualityOutsourcingClick(View view) {
        startActivity(new Intent(this, QualityOutsourcingActivity.class));
    }

    public void qualityOutsourceClick(View view) {
        startActivity(new Intent(this, QualityOutsourceActivity.class));
    }

    public void transferClick(View view) {
        startActivity(new Intent(this, TransferActivity.class));
    }

    public void checkClick(View view) {
        startActivity(new Intent(this, CheckActivity.class));
    }

    public void putOnClick(View view) {
        startActivity(new Intent(this, PutOnActivity.class));
    }

    public void stockOutManagerClick(View view) {
        startActivity(new Intent(this, QuickStockOutActivity.class));
    }

    public void putOutClick(View view) {
        startActivity(new Intent(this, StockOutActivity.class));
    }

    public void queryClick(View view) {
        startActivity(new Intent(this, QueryActivity.class));
    }
}
