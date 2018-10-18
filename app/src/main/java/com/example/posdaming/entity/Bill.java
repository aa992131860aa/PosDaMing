package com.example.posdaming.entity;

/**
 * Created by 99213 on 2018/1/16.
 */

public class Bill {
    private String billNo;
    private String billName;
    private BillDetail billDetail;
    //private String

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public BillDetail getBillDetail() {
        return billDetail;
    }

    public void setBillDetail(BillDetail billDetail) {
        this.billDetail = billDetail;
    }
}
