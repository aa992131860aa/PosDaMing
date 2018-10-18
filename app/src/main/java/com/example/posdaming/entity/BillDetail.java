package com.example.posdaming.entity;

/**
 * Created by 99213 on 2018/1/16.
 */

public class BillDetail {
    private String billNo;
    private String billName;
    private String materialName;
    private String meterialCode;
    private int num;
    private String unit;
    private String createDate;
    private String billStatus;


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

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMeterialCode() {
        return meterialCode;
    }

    public void setMeterialCode(String meterialCode) {
        this.meterialCode = meterialCode;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }
}
