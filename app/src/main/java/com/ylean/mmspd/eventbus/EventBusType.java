package com.ylean.mmspd.eventbus;

public class EventBusType {

    private int status;

    private Object object;

    private String msg;

    public EventBusType(int status){
        this.status=status;
    }

    public EventBusType(int status,Object object){
        this.status=status;
        this.object=object;
    }

    public EventBusType(int status,Object object,String msg){
        this.status=status;
        this.object=object;
        this.msg=msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
