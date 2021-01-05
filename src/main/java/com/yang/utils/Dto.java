package com.yang.utils;

/**
 * explain：一个没啥大用的实体
 *
 * @author yang
 * @date 2021/1/3
 */
public class Dto {

    private int status;

    private String msg;

    private Object data;

    public Dto() {
        this.status = Constants.SUCCESS;
    }

    public Dto(Object data) {
        this.status = Constants.SUCCESS;
        this.data = data;
    }

    public Dto(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Dto(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
