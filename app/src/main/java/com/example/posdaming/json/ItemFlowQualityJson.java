package com.example.posdaming.json;

import java.util.List;

/**
 * Created by 99213 on 2018/3/22.
 */

public class ItemFlowQualityJson {

    /**
     * is_success : true
     * msg : null
     * data : {"itemflow":{"itemflow_id":174,"itemflow_no":"1118030001","flow_type":11,"flow_type_name":"采购入库单","ware_id":1,"ware_child_id":2,"created":"03/04/2018 23:15:03","user_creator":"31","status":10,"status_desc":"初始","is_quality":0},"itemflowitems":[{"fid":173,"wareitem_id":15,"item_name":"后轮芯","item_no":"10101001","item_specs":"日普","measure_unit":"千克","item_num":0,"is_quality":0}]}
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
         * itemflow : {"itemflow_id":174,"itemflow_no":"1118030001","flow_type":11,"flow_type_name":"采购入库单","ware_id":1,"ware_child_id":2,"created":"03/04/2018 23:15:03","user_creator":"31","status":10,"status_desc":"初始","is_quality":0}
         * itemflowitems : [{"fid":173,"wareitem_id":15,"item_name":"后轮芯","item_no":"10101001","item_specs":"日普","measure_unit":"千克","item_num":0,"is_quality":0}]
         */

        private ItemflowBean itemflow;
        private List<ItemflowitemsBean> itemflowitems;

        public ItemflowBean getItemflow() {
            return itemflow;
        }

        public void setItemflow(ItemflowBean itemflow) {
            this.itemflow = itemflow;
        }

        public List<ItemflowitemsBean> getItemflowitems() {
            return itemflowitems;
        }

        public void setItemflowitems(List<ItemflowitemsBean> itemflowitems) {
            this.itemflowitems = itemflowitems;
        }

        public static class ItemflowBean {
            /**
             * itemflow_id : 174
             * itemflow_no : 1118030001
             * flow_type : 11
             * flow_type_name : 采购入库单
             * ware_id : 1
             * ware_child_id : 2
             * created : 03/04/2018 23:15:03
             * user_creator : 31
             * status : 10
             * status_desc : 初始
             * is_quality : 0
             */

            private int itemflow_id;
            private String itemflow_no;
            private int flow_type;
            private String flow_type_name;
            private int ware_id;
            private int ware_child_id;
            private String created;
            private String user_creator;
            private int status;
            private String status_desc;
            private int is_quality;

            public int getItemflow_id() {
                return itemflow_id;
            }

            public void setItemflow_id(int itemflow_id) {
                this.itemflow_id = itemflow_id;
            }

            public String getItemflow_no() {
                return itemflow_no;
            }

            public void setItemflow_no(String itemflow_no) {
                this.itemflow_no = itemflow_no;
            }

            public int getFlow_type() {
                return flow_type;
            }

            public void setFlow_type(int flow_type) {
                this.flow_type = flow_type;
            }

            public String getFlow_type_name() {
                return flow_type_name;
            }

            public void setFlow_type_name(String flow_type_name) {
                this.flow_type_name = flow_type_name;
            }

            public int getWare_id() {
                return ware_id;
            }

            public void setWare_id(int ware_id) {
                this.ware_id = ware_id;
            }

            public int getWare_child_id() {
                return ware_child_id;
            }

            public void setWare_child_id(int ware_child_id) {
                this.ware_child_id = ware_child_id;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getUser_creator() {
                return user_creator;
            }

            public void setUser_creator(String user_creator) {
                this.user_creator = user_creator;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getStatus_desc() {
                return status_desc;
            }

            public void setStatus_desc(String status_desc) {
                this.status_desc = status_desc;
            }

            public int getIs_quality() {
                return is_quality;
            }

            public void setIs_quality(int is_quality) {
                this.is_quality = is_quality;
            }
        }

        public static class ItemflowitemsBean {
            /**
             * fid : 173
             * wareitem_id : 15
             * item_name : 后轮芯
             * item_no : 10101001
             * item_specs : 日普
             * measure_unit : 千克
             * item_num : 0
             * is_quality : 0
             */

            private int fid;
            private int wareitem_id;
            private String item_name;
            private String item_no;
            private String item_specs;
            private String measure_unit;
            private int item_num;
            private int is_quality;

            public int getFid() {
                return fid;
            }

            public void setFid(int fid) {
                this.fid = fid;
            }

            public int getWareitem_id() {
                return wareitem_id;
            }

            public void setWareitem_id(int wareitem_id) {
                this.wareitem_id = wareitem_id;
            }

            public String getItem_name() {
                return item_name;
            }

            public void setItem_name(String item_name) {
                this.item_name = item_name;
            }

            public String getItem_no() {
                return item_no;
            }

            public void setItem_no(String item_no) {
                this.item_no = item_no;
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

            public int getIs_quality() {
                return is_quality;
            }

            public void setIs_quality(int is_quality) {
                this.is_quality = is_quality;
            }
        }
    }
}
