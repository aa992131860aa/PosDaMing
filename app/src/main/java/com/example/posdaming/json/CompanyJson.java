package com.example.posdaming.json;

import java.util.List;

/**
 * Created by 99213 on 2018/3/7.
 */

public class CompanyJson {


    /**
     * is_success : true
     * msg : null
     * data : [{"org_id":4,"org_name":"财务部","pid":3},{"org_id":7,"org_name":"采购部","pid":3},{"org_id":16,"org_name":"仓储部","pid":3},{"org_id":20,"org_name":"品质部","pid":3},{"org_id":29,"org_name":"压铸车间","pid":3},{"org_id":30,"org_name":"机装车间","pid":3},{"org_id":31,"org_name":"送料组","pid":30},{"org_id":32,"org_name":"运营中心","pid":3},{"org_id":33,"org_name":"生产部","pid":3},{"org_id":34,"org_name":"技术部","pid":3},{"org_id":35,"org_name":"销售部","pid":3},{"org_id":40,"org_name":"客户单位","pid":-1}]
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
         * org_id : 4
         * org_name : 财务部
         * pid : 3
         */

        private int org_id;
        private String org_name;
        private int pid;

        public int getOrg_id() {
            return org_id;
        }

        public void setOrg_id(int org_id) {
            this.org_id = org_id;
        }

        public String getOrg_name() {
            return org_name;
        }

        public void setOrg_name(String org_name) {
            this.org_name = org_name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }
    }
}
