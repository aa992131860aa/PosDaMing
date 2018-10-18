package com.example.posdaming.json;

import java.util.List;

/**
 * Created by 99213 on 2018/3/4.
 */

public class OrderJson {


    /**
     * is_success : true
     * msg : null
     * data : {"order":{"order_no":"339869765090","order_status":10},"orderitems":[{"item_name":"后轮芯","item_no":"10101001","item_specs":"日普","measure_unit":"千克","item_num":30,"confirm_num":0},{"item_name":"后轮芯","item_no":"10101001","item_specs":"日普","measure_unit":"千克","item_num":30,"confirm_num":0}]}
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
         * order : {"order_no":"339869765090","order_status":10}
         * orderitems : [{"item_name":"后轮芯","item_no":"10101001","item_specs":"日普","measure_unit":"千克","item_num":30,"confirm_num":0},{"item_name":"后轮芯","item_no":"10101001","item_specs":"日普","measure_unit":"千克","item_num":30,"confirm_num":0}]
         */

        private OrderBean order;
        private List<OrderitemsBean> orderitems;

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public List<OrderitemsBean> getOrderitems() {
            return orderitems;
        }

        public void setOrderitems(List<OrderitemsBean> orderitems) {
            this.orderitems = orderitems;
        }

        public static class OrderBean {
            /**
             * order_no : 339869765090
             * order_status : 10
             */

            private String order_no;
            private int order_status;

            public String getOrder_no() {
                return order_no;
            }

            public void setOrder_no(String order_no) {
                this.order_no = order_no;
            }

            public int getOrder_status() {
                return order_status;
            }

            public void setOrder_status(int order_status) {
                this.order_status = order_status;
            }
        }

        public static class OrderitemsBean {
            /**
             * item_name : 后轮芯
             * item_no : 10101001
             * item_specs : 日普
             * measure_unit : 千克
             * item_num : 30
             * confirm_num : 0
             */
           private int wareitem_id;
            private String item_name;
            private String item_no;
            private String item_specs;
            private String measure_unit;
            private int item_num;
            private int confirm_num;
            //保存item_num被初始化为0时的数量
            private int item_num_temp;

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

            public int getItem_num_temp() {
                return item_num_temp;
            }

            public void setItem_num_temp(int item_num_temp) {
                this.item_num_temp = item_num_temp;
            }

            public int getConfirm_num() {
                return confirm_num;
            }

            public void setConfirm_num(int confirm_num) {
                this.confirm_num = confirm_num;
            }
        }
    }
}
