package com.example.gameRecommend.utils;

import java.io.Serializable;

public class Result implements Serializable {
    private int code;
    private String msg;
    private Object data;

    public Result() {

    }

    public static Result build(){
        return new Result();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static Result success(int c, String m, Object d){
        return new Result(c,m,d);
    }
    public static Result success(Object d){
        return new Result(200,"ok",d);
    }
    public static Result success(String m){
        return new Result(200,m,null);
    }
    public static Result fail(int c, String m, Object d){
        return new Result(c,m,d);
    }
    public static Result fail(String m){
        return new Result(400,m, null);
    }
}
