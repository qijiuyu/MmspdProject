package com.zxdc.utils.library.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/11/27.
 */

public class Store extends BaseBean {

    private StoreBean data;

    public StoreBean getData() {
        return data;
    }

    public void setData(StoreBean data) {
        this.data = data;
    }

    public static class StoreBean implements Serializable{
        private int id;
        private String address;
        private String imgurl;
        private String mobile;
        private String name;
        private String shopowner;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShopowner() {
            return shopowner;
        }

        public void setShopowner(String shopowner) {
            this.shopowner = shopowner;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
