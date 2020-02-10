package com.zxdc.utils.library.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/11/27.
 */

public class Order extends BaseBean {

   private List<OrderBean> data=new ArrayList<>();

    public List<OrderBean> getData() {
        return data;
    }

    public void setData(List<OrderBean> data) {
        this.data = data;
    }

    public static class OrderBean implements Serializable{
        private double actualpay;//实际支付金额
        private String addorderdate;//下单时间
        private String address;//收货人地址
        private String  cancelreason;//取消原因
        private String confirmdate;//收货时间
        private String deliverdate;//发货时间
        private double discount;//优惠金额
        private double freight;//运费
        private int orderid;//订单id
        private String ordernote;//订单备注
        private String ordernum;//订单编号
        private String phone;//收货人电话
        private double price;//订单金额（小计）
        private String receivename;//收货人姓名
        private List<ReturnListBean> returnlist=new ArrayList<>();//退单商品集合
        private String shreason;//售后原因
        private String  shtime;//申请售后时间
        private int skucount;//商品数量
        private List<SkulistBean> skulist=new ArrayList<>();//商品集合
        private int  status;//订单状态
        private double supplierMoney;//供应商应收金额

        public double getActualpay() {
            return actualpay;
        }

        public void setActualpay(double actualpay) {
            this.actualpay = actualpay;
        }

        public String getAddorderdate() {
            return addorderdate;
        }

        public void setAddorderdate(String addorderdate) {
            this.addorderdate = addorderdate;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCancelreason() {
            return cancelreason;
        }

        public void setCancelreason(String cancelreason) {
            this.cancelreason = cancelreason;
        }

        public String getConfirmdate() {
            return confirmdate;
        }

        public void setConfirmdate(String confirmdate) {
            this.confirmdate = confirmdate;
        }

        public String getDeliverdate() {
            return deliverdate;
        }

        public void setDeliverdate(String deliverdate) {
            this.deliverdate = deliverdate;
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

        public String getOrdernote() {
            return ordernote;
        }

        public void setOrdernote(String ordernote) {
            this.ordernote = ordernote;
        }

        public String getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(String ordernum) {
            this.ordernum = ordernum;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getReceivename() {
            return receivename;
        }

        public void setReceivename(String receivename) {
            this.receivename = receivename;
        }

        public List<ReturnListBean> getReturnlist() {
            return returnlist;
        }

        public void setReturnlist(List<ReturnListBean> returnlist) {
            this.returnlist = returnlist;
        }

        public String getShreason() {
            return shreason;
        }

        public void setShreason(String shreason) {
            this.shreason = shreason;
        }

        public String getShtime() {
            return shtime;
        }

        public void setShtime(String shtime) {
            this.shtime = shtime;
        }

        public int getSkucount() {
            return skucount;
        }

        public void setSkucount(int skucount) {
            this.skucount = skucount;
        }

        public List<SkulistBean> getSkulist() {
            return skulist;
        }

        public void setSkulist(List<SkulistBean> skulist) {
            this.skulist = skulist;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getSupplierMoney() {
            return supplierMoney;
        }

        public void setSupplierMoney(double supplierMoney) {
            this.supplierMoney = supplierMoney;
        }
    }


    public static class ReturnListBean implements Serializable{
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


        public SkulistBean(){}

        public SkulistBean(int changeskuid, int spucount, String spuname, double spuprice,String specsvalue) {
            this.changeskuid = changeskuid;
            this.spucount = spucount;
            this.spuname = spuname;
            this.spuprice = spuprice;
            this.specsvalue=specsvalue;
        }

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
