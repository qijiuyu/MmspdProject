package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/29.
 */

public class OrderDetails extends BaseBean {

    private OrderDetailsBean data;

    public OrderDetailsBean getData() {
        return data;
    }

    public void setData(OrderDetailsBean data) {
        this.data = data;
    }

    public static class OrderDetailsBean implements Serializable{
        private String addorderdate;//下单时间
        private String confirmdate;//收货时间
        private double discount;//优惠金额
        private double freight;//运费
        private int orderid;
        private String ordernum;//订单编号
        private String shopname;//店铺名称
        private String skucount;//商品数量
        private List<SkulistBean> skulist=new ArrayList<>();
        private double supplierMoney;//供应商应收金额
        private String expressname;//配送方式

        public String getAddorderdate() {
            return addorderdate;
        }

        public void setAddorderdate(String addorderdate) {
            this.addorderdate = addorderdate;
        }

        public String getConfirmdate() {
            return confirmdate;
        }

        public void setConfirmdate(String confirmdate) {
            this.confirmdate = confirmdate;
        }

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }

        public double getFreight() {
            return freight;
        }

        public void setFreight(double freight) {
            this.freight = freight;
        }

        public int getOrderid() {
            return orderid;
        }

        public void setOrderid(int orderid) {
            this.orderid = orderid;
        }

        public String getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(String ordernum) {
            this.ordernum = ordernum;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getSkucount() {
            return skucount;
        }

        public void setSkucount(String skucount) {
            this.skucount = skucount;
        }

        public List<SkulistBean> getSkulist() {
            return skulist;
        }

        public void setSkulist(List<SkulistBean> skulist) {
            this.skulist = skulist;
        }

        public double getSupplierMoney() {
            return supplierMoney;
        }

        public void setSupplierMoney(double supplierMoney) {
            this.supplierMoney = supplierMoney;
        }

        public String getExpressname() {
            return expressname;
        }

        public void setExpressname(String expressname) {
            this.expressname = expressname;
        }
    }


    public static class SkulistBean implements Serializable{
        private int changeskuid;//更换商品id
        private String  changespecsvalue;//更换商品规格值
        private int detailid;//订单明细id
        private int skuid;//商品id
        private String specsvalue;//商品规格值
        private int spucount;//商品数量
        private int spuid;//商品id
        private String spuname;//商品名称
        private double spuprice;//商品价格

        public int getChangeskuid() {
            return changeskuid;
        }

        public void setChangeskuid(int changeskuid) {
            this.changeskuid = changeskuid;
        }

        public String getChangespecsvalue() {
            return changespecsvalue;
        }

        public void setChangespecsvalue(String changespecsvalue) {
            this.changespecsvalue = changespecsvalue;
        }

        public int getDetailid() {
            return detailid;
        }

        public void setDetailid(int detailid) {
            this.detailid = detailid;
        }

        public int getSkuid() {
            return skuid;
        }

        public void setSkuid(int skuid) {
            this.skuid = skuid;
        }

        public String getSpecsvalue() {
            return specsvalue;
        }

        public void setSpecsvalue(String specsvalue) {
            this.specsvalue = specsvalue;
        }

        public int getSpucount() {
            return spucount;
        }

        public void setSpucount(int spucount) {
            this.spucount = spucount;
        }

        public int getSpuid() {
            return spuid;
        }

        public void setSpuid(int spuid) {
            this.spuid = spuid;
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
}
