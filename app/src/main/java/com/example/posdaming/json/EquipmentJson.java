package com.example.posdaming.json;

/**
 * Created by 99213 on 2018/3/19.
 */

public class EquipmentJson {

    /**
     * is_success : true
     * msg : null
     * data : {"equipment_id":1,"equipment_no":"HUJ1711001","equipment_name":"货架","equipment_size":"10.0000","equipment_status":10,"equipment_status_str":"使用中","start_time":"2017/11/11 0:00:00","ware_id":2,"ware_name":"装配线边仓","warearea_id":15,"warearea_no":"WG2-13"}
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
         * equipment_id : 1
         * equipment_no : HUJ1711001
         * equipment_name : 货架
         * equipment_size : 10.0000
         * equipment_status : 10
         * equipment_status_str : 使用中
         * start_time : 2017/11/11 0:00:00
         * ware_id : 2
         * ware_name : 装配线边仓
         * warearea_id : 15
         * warearea_no : WG2-13
         */

        private int equipment_id;
        private String equipment_no;
        private String equipment_name;
        private String equipment_size;
        private int equipment_status;
        private String equipment_status_str;
        private String start_time;
        private int ware_id;
        private String ware_name;
        private int warearea_id;
        private String warearea_no;

        public int getEquipment_id() {
            return equipment_id;
        }

        public void setEquipment_id(int equipment_id) {
            this.equipment_id = equipment_id;
        }

        public String getEquipment_no() {
            return equipment_no;
        }

        public void setEquipment_no(String equipment_no) {
            this.equipment_no = equipment_no;
        }

        public String getEquipment_name() {
            return equipment_name;
        }

        public void setEquipment_name(String equipment_name) {
            this.equipment_name = equipment_name;
        }

        public String getEquipment_size() {
            return equipment_size;
        }

        public void setEquipment_size(String equipment_size) {
            this.equipment_size = equipment_size;
        }

        public int getEquipment_status() {
            return equipment_status;
        }

        public void setEquipment_status(int equipment_status) {
            this.equipment_status = equipment_status;
        }

        public String getEquipment_status_str() {
            return equipment_status_str;
        }

        public void setEquipment_status_str(String equipment_status_str) {
            this.equipment_status_str = equipment_status_str;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

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

        public int getWarearea_id() {
            return warearea_id;
        }

        public void setWarearea_id(int warearea_id) {
            this.warearea_id = warearea_id;
        }

        public String getWarearea_no() {
            return warearea_no;
        }

        public void setWarearea_no(String warearea_no) {
            this.warearea_no = warearea_no;
        }
    }
}
