package com.example.posdaming.json;

/**
 * Created by 99213 on 2018/2/6.
 */

public class LoginJson {

    /**
     * is_success : true
     * msg : null
     * data : {"user_id":31,"user_passport":"chengzong","user_name":"成总经理","org_id":32,"org_name":"运营中心"}
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
         * user_id : 31
         * user_passport : chengzong
         * user_name : 成总经理
         * org_id : 32
         * org_name : 运营中心
         */

        private int user_id;
        private String user_passport;
        private String user_name;
        private int org_id;
        private String org_name;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUser_passport() {
            return user_passport;
        }

        public void setUser_passport(String user_passport) {
            this.user_passport = user_passport;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

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
    }
}
