package com.example.posdaming.json;

/**
 * Created by 99213 on 2018/3/20.
 */

public class DataJson {

    /**
     * is_success : false
     * msg : 设备不存在
     * data : null
     */

    private boolean is_success;
    private String msg;
    private Object data;

    public boolean isIs_success() {
        return is_success;
    }

    public void setIs_success(boolean is_success) {
        this.is_success = is_success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
