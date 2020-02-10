package com.zxdc.utils.library.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/11/29.
 */

public class Business extends BaseBean {

    private BusinessBean data;

    public BusinessBean getData() {
        return data;
    }

    public void setData(BusinessBean data) {
        this.data = data;
    }

    public static class BusinessBean implements Serializable{
        private int takecoupon;
        private double todayprice;
        private double totalprice;
        private int usecoupon;
        private double wxkdj;
        private int wxordercount;
        private double yxkdj;
        private int yxordercount;

        public int getTakecoupon() {
            return takecoupon;
        }

        public void setTakecoupon(int takecoupon) {
            this.takecoupon = takecoupon;
        }

        public double getTodayprice() {
            return todayprice;
        }

        public void setTodayprice(double todayprice) {
            this.todayprice = todayprice;
        }

        public double getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(double totalprice) {
            this.totalprice = totalprice;
        }

        public int getUsecoupon() {
            return usecoupon;
        }

        public void setUsecoupon(int usecoupon) {
            this.usecoupon = usecoupon;
        }

        public double getWxkdj() {
            return wxkdj;
        }

        public void setWxkdj(double wxkdj) {
            this.wxkdj = wxkdj;
        }

        public int getWxordercount() {
            return wxordercount;
        }

        public void setWxordercount(int wxordercount) {
            this.wxordercount = wxordercount;
        }

        public double getYxkdj() {
            return yxkdj;
        }

        public void setYxkdj(double yxkdj) {
            this.yxkdj = yxkdj;
        }

        public int getYxordercount() {
            return yxordercount;
        }

        public void setYxordercount(int yxordercount) {
            this.yxordercount = yxordercount;
        }
    }
}
