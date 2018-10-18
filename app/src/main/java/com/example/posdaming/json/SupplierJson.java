package com.example.posdaming.json;

import java.util.List;

/**
 * Created by 99213 on 2018/3/11.
 */

public class SupplierJson {

    /**
     * is_success : true
     * msg : null
     * data : [{"supplier_id":8,"supplier_name":"铁岭市康宁橡胶制品厂"},{"supplier_id":9,"supplier_name":"浙江蓝色微电子有点公司"},{"supplier_id":10,"supplier_name":"浙江永康铁牛"}]
     */

    private boolean is_success;
    private Object msg;
    private List<DataBean> data;

    public boolean isIs_success() {
        return is_success;
    }

    public void setIs_success(boolean is_success) {
        this.is_success = is_success;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * supplier_id : 8
         * supplier_name : 铁岭市康宁橡胶制品厂
         */

        private int supplier_id;
        private String supplier_name;

        public int getSupplier_id() {
            return supplier_id;
        }

        public void setSupplier_id(int supplier_id) {
            this.supplier_id = supplier_id;
        }

        public String getSupplier_name() {
            return supplier_name;
        }

        public void setSupplier_name(String supplier_name) {
            this.supplier_name = supplier_name;
        }
    }
}
