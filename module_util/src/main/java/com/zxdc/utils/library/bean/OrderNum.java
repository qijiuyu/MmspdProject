package com.zxdc.utils.library.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/12/4.
 */

public class OrderNum extends BaseBean {

    private OrderNumBean data;

    public OrderNumBean getData() {
        return data;
    }

    public void setData(OrderNumBean data) {
        this.data = data;
    }

    public static class OrderNumBean implements Serializable{
        private int xdd;//新订单数量
        private int tkd;//退款单数量

        public int getXdd() {
            return xdd;
        }

        public void setXdd(int xdd) {
            this.xdd = xdd;
        }

        public int getTkd() {
            return tkd;
        }

        public void setTkd(int tkd) {
            this.tkd = tkd;
        }
    }
}
