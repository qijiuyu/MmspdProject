package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/28.
 */

public class Accound extends BaseBean {

    private AccoundBean data;

    public AccoundBean getData() {
        return data;
    }

    public void setData(AccoundBean data) {
        this.data = data;
    }

    public static class AccoundBean implements Serializable{
        private int tkcount;
        private double tkmoney;
        private double totalmoney;
        private int zxcount;
        private double zxmoney;
        private List<OrderList> orderlist=new ArrayList<>();

        public int getTkcount() {
            return tkcount;
        }

        public void setTkcount(int tkcount) {
            this.tkcount = tkcount;
        }

        public double getTkmoney() {
            return tkmoney;
        }

        public void setTkmoney(double tkmoney) {
            this.tkmoney = tkmoney;
        }

        public double getTotalmoney() {
            return totalmoney;
        }

        public void setTotalmoney(double totalmoney) {
            this.totalmoney = totalmoney;
        }

        public int getZxcount() {
            return zxcount;
        }

        public void setZxcount(int zxcount) {
            this.zxcount = zxcount;
        }

        public double getZxmoney() {
            return zxmoney;
        }

        public void setZxmoney(double zxmoney) {
            this.zxmoney = zxmoney;
        }

        public List<OrderList> getOrderlist() {
            return orderlist;
        }

        public void setOrderlist(List<OrderList> orderlist) {
            this.orderlist = orderlist;
        }
    }


    public static class OrderList implements Serializable{
        private String code;
        private double price;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
