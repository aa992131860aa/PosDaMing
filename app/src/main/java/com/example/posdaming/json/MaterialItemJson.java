package com.example.posdaming.json;

/**
 * Created by 99213 on 2018/3/19.
 */

public class MaterialItemJson {

    /**
     * is_success : true
     * msg : null
     * data : {"wareitem_id":15,"item_no":"10101001","item_name":"后轮芯","item_specs":"日普","measure_unit":"千克","item_num":4,"is_doka":0,"is_doka_no":""}
     */

    private boolean is_success;
    private Object msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * wareitem_id : 15
         * item_no : 10101001
         * item_name : 后轮芯
         * item_specs : 日普
         * measure_unit : 千克
         * item_num : 4
         * is_doka : 0
         * is_doka_no :
         */

        private int wareitem_id;
        private String item_no;
        private String item_name;
        private String item_specs;
        private String measure_unit;
        private int item_num;
        private int num;
        private int is_doka;
        private String is_doka_no;

        public int getWareitem_id() {
            return wareitem_id;
        }

        public void setWareitem_id(int wareitem_id) {
            this.wareitem_id = wareitem_id;
        }

        public String getItem_no() {
            return item_no;
        }

        public void setItem_no(String item_no) {
            this.item_no = item_no;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getItem_specs() {
            return item_specs;
        }

        public void setItem_specs(String item_specs) {
            this.item_specs = item_specs;
        }

        public String getMeasure_unit() {
            return measure_unit;
        }

        public void setMeasure_unit(String measure_unit) {
            this.measure_unit = measure_unit;
        }

        public int getItem_num() {
            return item_num;
        }

        public void setItem_num(int item_num) {
            this.item_num = item_num;
        }

        public int getIs_doka() {
            return is_doka;
        }

        public void setIs_doka(int is_doka) {
            this.is_doka = is_doka;
        }

        public String getIs_doka_no() {
            return is_doka_no;
        }

        public void setIs_doka_no(String is_doka_no) {
            this.is_doka_no = is_doka_no;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
