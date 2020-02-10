package com.zxdc.utils.library.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/1/7.
 */

public class GoodList implements Serializable {

    private int changeskuid;//更换商品id
    private int spucount;//商品数量
    private String spuname;//商品名称
    private double spuprice;//商品价格

    public GoodList(){}

    public GoodList(int changeskuid, int spucount, String spuname, double spuprice) {
        this.changeskuid = changeskuid;
        this.spucount = spucount;
        this.spuname = spuname;
        this.spuprice = spuprice;
    }

    public int getChangeskuid() {
        return changeskuid;
    }

    public void setChangeskuid(int changeskuid) {
        this.changeskuid = changeskuid;
    }

    public int getSpucount() {
        return spucount;
    }

    public void setSpucount(int spucount) {
        this.spucount = spucount;
    }

    public String getSpuname() {
        return spuname;
    }

    public void setSpuname(String spuname) {
        this.spuname = spuname;
    }

    public double getSpuprice() {
        return spuprice;
    }

    public void setSpuprice(double spuprice) {
        this.spuprice = spuprice;
    }
}
