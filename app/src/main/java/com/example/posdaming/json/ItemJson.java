package com.example.posdaming.json;

import java.util.List;

/**
 * Created by 99213 on 2018/3/18.
 */

public class ItemJson {

    /**
     * is_success : true
     * msg : null
     * data : [{"item_no":"10101001","item_name":"后轮芯","item_specs":"日普","measure_unit":"千克","item_num":"4","warearea_no":"WG2-13","equipment_no":"HUJ1711001"}]
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
         * item_no : 10101001
         * item_name : 后轮芯
         * item_specs : 日普
         * measure_unit : 千克
         * item_num : 4
         * warearea_no : WG2-13
         * equipment_no : HUJ1711001
         */

        private String item_no;
        private String item_name;
        private String item_specs;
        private String measure_unit;
        private String item_num;
        private String warearea_no;
        private String equipment_no;

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

        public String getItem_num() {
            return item_num;
        }

        public void setItem_num(String item_num) {
            this.item_num = item_num;
        }

        public String getWarearea_no() {
            return warearea_no;
        }

        public void setWarearea_no(String warearea_no) {
            this.warearea_no = warearea_no;
        }

        public String getEquipment_no() {
            return equipment_no;
        }

        public void setEquipment_no(String equipment_no) {
            this.equipment_no = equipment_no;
        }
    }
}
