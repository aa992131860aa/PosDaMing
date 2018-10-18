package com.example.posdaming.db;

import org.litepal.crud.DataSupport;

/**
 * Created by 99213 on 2018/3/31.
 */

public class Material extends DataSupport {
   //类型,保存的类型
   private String type;
   //编号
   private String no;
   private int num;
   //事件的编号
   private int getWareitemId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getGetWareitemId() {
        return getWareitemId;
    }

    public void setGetWareitemId(int getWareitemId) {
        this.getWareitemId = getWareitemId;
    }
}
