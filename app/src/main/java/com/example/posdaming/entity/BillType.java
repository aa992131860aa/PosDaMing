package com.example.posdaming.entity;

/**
 * Created by 99213 on 2018/3/6.
 */

public class BillType {
    private int id;
    private String name;
    private int pid;

    public BillType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public BillType(int id, String name, int pid) {
        this.id = id;
        this.name = name;
        this.pid = pid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
