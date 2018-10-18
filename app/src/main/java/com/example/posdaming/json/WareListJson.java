package com.example.posdaming.json;

import java.util.List;

/**
 * Created by 99213 on 2018/3/4.
 */

public class WareListJson {

    /**
     * is_success : true
     * msg : null
     * data : [{"ware_id":1,"ware_name":"大明仓库","ware_no":"N20121","is_child":0,"pid":-1,"type":10,"type_name":""},{"ware_id":2,"ware_name":"装配线边仓","ware_no":"WG2-13","is_child":1,"pid":1,"type":50,"type_name":"装配线边仓"},{"ware_id":10,"ware_name":"外购收料仓","ware_no":"WG3-01","is_child":1,"pid":1,"type":30,"type_name":"外购收料仓"},{"ware_id":11,"ware_name":"机加工线边仓","ware_no":"WG4-01","is_child":1,"pid":1,"type":40,"type_name":"机加工线边仓"},{"ware_id":12,"ware_name":"不良品仓","ware_no":"WG5-01","is_child":1,"pid":1,"type":60,"type_name":"不良品仓"}]
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
         * ware_id : 1
         * ware_name : 大明仓库
         * ware_no : N20121
         * is_child : 0
         * pid : -1
         * type : 10
         * type_name :
         */

        private int ware_id;
        private String ware_name;
        private String ware_no;
        private int is_child;
        private int pid;
        private int type;
        private String type_name;

        public int getWare_id() {
            return ware_id;
        }

        public void setWare_id(int ware_id) {
            this.ware_id = ware_id;
        }

        public String getWare_name() {
            return ware_name;
        }

        public void setWare_name(String ware_name) {
            this.ware_name = ware_name;
        }

        public String getWare_no() {
            return ware_no;
        }

        public void setWare_no(String ware_no) {
            this.ware_no = ware_no;
        }

        public int getIs_child() {
            return is_child;
        }

        public void setIs_child(int is_child) {
            this.is_child = is_child;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }
    }
}
