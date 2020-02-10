package com.zxdc.utils.library.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/11/27.
 */

public class Login extends BaseBean {

    private LoginBean data;

    public LoginBean getData() {
        return data;
    }

    public void setData(LoginBean data) {
        this.data = data;
    }

    public static class LoginBean implements Serializable{
        //用户id
        private int id;
        //店铺id
        private int shopid;
        //手机号
        private String mobile;
        //账号类型（3：商家，4：技师）
        private int type;
        private String token;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getShopid() {
            return shopid;
        }

        public void setShopid(int shopid) {
            this.shopid = shopid;
        }
    }
}
