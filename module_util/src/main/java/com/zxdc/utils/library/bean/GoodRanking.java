package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/27.
 */

public class GoodRanking extends BaseBean {

    private List<GoodRankingBean> data=new ArrayList<>();

    public List<GoodRankingBean> getData() {
        return data;
    }

    public void setData(List<GoodRankingBean> data) {
        this.data = data;
    }

    public static class GoodRankingBean implements Serializable{
        private int id;
        private int salecount;
        private double saleprice;
        private String spuname;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSalecount() {
            return salecount;
        }

        public void setSalecount(int salecount) {
            this.salecount = salecount;
        }

        public double getSaleprice() {
            return saleprice;
        }

        public void setSaleprice(double saleprice) {
            this.saleprice = saleprice;
        }

        public String getSpuname() {
            return spuname;
        }

        public void setSpuname(String spuname) {
            this.spuname = spuname;
        }
    }
}
