package com.ylean.mmspd.utils;

import android.text.TextUtils;
import android.widget.TextView;

import com.zxdc.utils.library.bean.Order;

/**
 * Created by Administrator on 2019/11/27.
 */

public class OrderStatus {

    public static final String str="{\n" +
            "\t\"code\": 200,\n" +
            "\t\"data\": [{\n" +
            "\t\t\"confirmdate\": \"\",\n" +
            "\t\t\"skucount\": 1,\n" +
            "\t\t\"address\": \"北京市 北京市 朝阳区 未来时大厦\",\n" +
            "\t\t\"cancelreason\": \"\",\n" +
            "\t\t\"deliverdate\": \"\",\n" +
            "\t\t\"orderid\": 115,\n" +
            "\t\t\"receivename\": \"周琳\",\n" +
            "\t\t\"freight\": 0,\n" +
            "\t\t\"discount\": 0,\n" +
            "\t\t\"ordernum\": \"O1575423864515\",\n" +
            "\t\t\"shreason\": \"\",\n" +
            "\t\t\"shtime\": \"\",\n" +
            "\t\t\"returnlist\": [],\n" +
            "\t\t\"supplierMoney\": 0,\n" +
            "\t\t\"ordernote\": \"无\",\n" +
            "\t\t\"phone\": \"18515283454\",\n" +
            "\t\t\"price\": 0,\n" +
            "\t\t\"skulist\": [{\n" +
            "\t\t\t\"changeskuid\": 0,\n" +
            "\t\t\t\"spuprice\": 0,\n" +
            "\t\t\t\"changespecsvalue\": \"\",\n" +
            "\t\t\t\"spucount\": 1,\n" +
            "\t\t\t\"detailid\": 9863,\n" +
            "\t\t\t\"spuid\": 28,\n" +
            "\t\t\t\"spuname\": \"折耳蓝猫\",\n" +
            "\t\t\t\"skuid\": 93,\n" +
            "\t\t\t\"specsvalue\": \"幼猫 母 \"\n" +
            "\t\t}],\n" +
            "\t\t\"addorderdate\": \"2019-12-04 09:44:28\",\n" +
            "\t\t\"actualpay\": 0,\n" +
            "\t\t\"status\": 5\n" +
            "\t},\n" +
            "{\n" +
            "\t\t\"confirmdate\": \"\",\n" +
            "\t\t\"skucount\": 1,\n" +
            "\t\t\"address\": \"北京市 北京市 朝阳区 未来时大厦\",\n" +
            "\t\t\"cancelreason\": \"\",\n" +
            "\t\t\"deliverdate\": \"\",\n" +
            "\t\t\"orderid\": 115,\n" +
            "\t\t\"receivename\": \"周琳\",\n" +
            "\t\t\"freight\": 0,\n" +
            "\t\t\"discount\": 0,\n" +
            "\t\t\"ordernum\": \"O1575423864515\",\n" +
            "\t\t\"shreason\": \"\",\n" +
            "\t\t\"shtime\": \"\",\n" +
            "\t\t\"returnlist\": [],\n" +
            "\t\t\"supplierMoney\": 0,\n" +
            "\t\t\"ordernote\": \"无\",\n" +
            "\t\t\"phone\": \"18515283454\",\n" +
            "\t\t\"price\": 0,\n" +
            "\t\t\"skulist\": [{\n" +
            "\t\t\t\"changeskuid\": 0,\n" +
            "\t\t\t\"spuprice\": 0,\n" +
            "\t\t\t\"changespecsvalue\": \"\",\n" +
            "\t\t\t\"spucount\": 1,\n" +
            "\t\t\t\"detailid\": 9863,\n" +
            "\t\t\t\"spuid\": 28,\n" +
            "\t\t\t\"spuname\": \"折耳蓝猫\",\n" +
            "\t\t\t\"skuid\": 93,\n" +
            "\t\t\t\"specsvalue\": \"幼猫 母 \"\n" +
            "\t\t}],\n" +
            "\t\t\"addorderdate\": \"2019-12-04 09:44:28\",\n" +
            "\t\t\"actualpay\": 0,\n" +
            "\t\t\"status\": 8\n" +
            "\t},{\n" +
            "\t\t\"confirmdate\": \"\",\n" +
            "\t\t\"skucount\": 1,\n" +
            "\t\t\"address\": \"北京市 北京市 朝阳区 未来时大厦\",\n" +
            "\t\t\"cancelreason\": \"\",\n" +
            "\t\t\"deliverdate\": \"\",\n" +
            "\t\t\"orderid\": 115,\n" +
            "\t\t\"receivename\": \"周琳\",\n" +
            "\t\t\"freight\": 0,\n" +
            "\t\t\"discount\": 0,\n" +
            "\t\t\"ordernum\": \"O1575423864515\",\n" +
            "\t\t\"shreason\": \"\",\n" +
            "\t\t\"shtime\": \"\",\n" +
            "\t\t\"returnlist\": [],\n" +
            "\t\t\"supplierMoney\": 0,\n" +
            "\t\t\"ordernote\": \"无\",\n" +
            "\t\t\"phone\": \"18515283454\",\n" +
            "\t\t\"price\": 0,\n" +
            "\t\t\"skulist\": [{\n" +
            "\t\t\t\"changeskuid\": 0,\n" +
            "\t\t\t\"spuprice\": 0,\n" +
            "\t\t\t\"changespecsvalue\": \"\",\n" +
            "\t\t\t\"spucount\": 1,\n" +
            "\t\t\t\"detailid\": 9863,\n" +
            "\t\t\t\"spuid\": 28,\n" +
            "\t\t\t\"spuname\": \"折耳蓝猫\",\n" +
            "\t\t\t\"skuid\": 93,\n" +
            "\t\t\t\"specsvalue\": \"幼猫 母 \"\n" +
            "\t\t}],\n" +
            "\t\t\"addorderdate\": \"2019-12-04 09:44:28\",\n" +
            "\t\t\"actualpay\": 0,\n" +
            "\t\t\"status\": 17\n" +
            "\t},{\n" +
            "\t\t\"confirmdate\": \"\",\n" +
            "\t\t\"skucount\": 1,\n" +
            "\t\t\"address\": \"北京市 北京市 朝阳区 未来时大厦\",\n" +
            "\t\t\"cancelreason\": \"\",\n" +
            "\t\t\"deliverdate\": \"\",\n" +
            "\t\t\"orderid\": 115,\n" +
            "\t\t\"receivename\": \"周琳\",\n" +
            "\t\t\"freight\": 0,\n" +
            "\t\t\"discount\": 0,\n" +
            "\t\t\"ordernum\": \"O1575423864515\",\n" +
            "\t\t\"shreason\": \"\",\n" +
            "\t\t\"shtime\": \"\",\n" +
            "\t\t\"returnlist\": [],\n" +
            "\t\t\"supplierMoney\": 0,\n" +
            "\t\t\"ordernote\": \"无\",\n" +
            "\t\t\"phone\": \"18515283454\",\n" +
            "\t\t\"price\": 0,\n" +
            "\t\t\"skulist\": [{\n" +
            "\t\t\t\"changeskuid\": 0,\n" +
            "\t\t\t\"spuprice\": 0,\n" +
            "\t\t\t\"changespecsvalue\": \"\",\n" +
            "\t\t\t\"spucount\": 1,\n" +
            "\t\t\t\"detailid\": 9863,\n" +
            "\t\t\t\"spuid\": 28,\n" +
            "\t\t\t\"spuname\": \"折耳蓝猫\",\n" +
            "\t\t\t\"skuid\": 93,\n" +
            "\t\t\t\"specsvalue\": \"幼猫 母 \"\n" +
            "\t\t}],\n" +
            "\t\t\"addorderdate\": \"2019-12-04 09:44:28\",\n" +
            "\t\t\"actualpay\": 0,\n" +
            "\t\t\"status\": 19\n" +
            "\t},{\n" +
            "\t\t\"confirmdate\": \"\",\n" +
            "\t\t\"skucount\": 1,\n" +
            "\t\t\"address\": \"北京市 北京市 朝阳区 未来时大厦\",\n" +
            "\t\t\"cancelreason\": \"\",\n" +
            "\t\t\"deliverdate\": \"\",\n" +
            "\t\t\"orderid\": 115,\n" +
            "\t\t\"receivename\": \"周琳\",\n" +
            "\t\t\"freight\": 0,\n" +
            "\t\t\"discount\": 0,\n" +
            "\t\t\"ordernum\": \"O1575423864515\",\n" +
            "\t\t\"shreason\": \"\",\n" +
            "\t\t\"shtime\": \"\",\n" +
            "\t\t\"returnlist\": [],\n" +
            "\t\t\"supplierMoney\": 0,\n" +
            "\t\t\"ordernote\": \"无\",\n" +
            "\t\t\"phone\": \"18515283454\",\n" +
            "\t\t\"price\": 0,\n" +
            "\t\t\"skulist\": [{\n" +
            "\t\t\t\"changeskuid\": 0,\n" +
            "\t\t\t\"spuprice\": 0,\n" +
            "\t\t\t\"changespecsvalue\": \"\",\n" +
            "\t\t\t\"spucount\": 1,\n" +
            "\t\t\t\"detailid\": 9863,\n" +
            "\t\t\t\"spuid\": 28,\n" +
            "\t\t\t\"spuname\": \"折耳蓝猫\",\n" +
            "\t\t\t\"skuid\": 93,\n" +
            "\t\t\t\"specsvalue\": \"幼猫 母 \"\n" +
            "\t\t}],\n" +
            "\t\t\"addorderdate\": \"2019-12-04 09:44:28\",\n" +
            "\t\t\"actualpay\": 0,\n" +
            "\t\t\"status\": 11\n" +
            "\t},{\n" +
            "\t\t\"confirmdate\": \"\",\n" +
            "\t\t\"skucount\": 1,\n" +
            "\t\t\"address\": \"北京市 北京市 朝阳区 未来时大厦\",\n" +
            "\t\t\"cancelreason\": \"\",\n" +
            "\t\t\"deliverdate\": \"\",\n" +
            "\t\t\"orderid\": 115,\n" +
            "\t\t\"receivename\": \"周琳\",\n" +
            "\t\t\"freight\": 0,\n" +
            "\t\t\"discount\": 0,\n" +
            "\t\t\"ordernum\": \"O1575423864515\",\n" +
            "\t\t\"shreason\": \"\",\n" +
            "\t\t\"shtime\": \"\",\n" +
            "\t\t\"returnlist\": [],\n" +
            "\t\t\"supplierMoney\": 0,\n" +
            "\t\t\"ordernote\": \"无\",\n" +
            "\t\t\"phone\": \"18515283454\",\n" +
            "\t\t\"price\": 0,\n" +
            "\t\t\"skulist\": [{\n" +
            "\t\t\t\"changeskuid\": 0,\n" +
            "\t\t\t\"spuprice\": 0,\n" +
            "\t\t\t\"changespecsvalue\": \"\",\n" +
            "\t\t\t\"spucount\": 1,\n" +
            "\t\t\t\"detailid\": 9863,\n" +
            "\t\t\t\"spuid\": 28,\n" +
            "\t\t\t\"spuname\": \"折耳蓝猫\",\n" +
            "\t\t\t\"skuid\": 93,\n" +
            "\t\t\t\"specsvalue\": \"幼猫 母 \"\n" +
            "\t\t}],\n" +
            "\t\t\"addorderdate\": \"2019-12-04 09:44:28\",\n" +
            "\t\t\"actualpay\": 0,\n" +
            "\t\t\"status\": 17\n" +
            "\t},{\n" +
            "\t\t\"confirmdate\": \"\",\n" +
            "\t\t\"skucount\": 1,\n" +
            "\t\t\"address\": \"北京市 北京市 朝阳区 未来时大厦\",\n" +
            "\t\t\"cancelreason\": \"\",\n" +
            "\t\t\"deliverdate\": \"\",\n" +
            "\t\t\"orderid\": 115,\n" +
            "\t\t\"receivename\": \"周琳\",\n" +
            "\t\t\"freight\": 0,\n" +
            "\t\t\"discount\": 0,\n" +
            "\t\t\"ordernum\": \"O1575423864515\",\n" +
            "\t\t\"shreason\": \"\",\n" +
            "\t\t\"shtime\": \"\",\n" +
            "\t\t\"returnlist\": [],\n" +
            "\t\t\"supplierMoney\": 0,\n" +
            "\t\t\"ordernote\": \"无\",\n" +
            "\t\t\"phone\": \"18515283454\",\n" +
            "\t\t\"price\": 0,\n" +
            "\t\t\"skulist\": [{\n" +
            "\t\t\t\"changeskuid\": 0,\n" +
            "\t\t\t\"spuprice\": 0,\n" +
            "\t\t\t\"changespecsvalue\": \"\",\n" +
            "\t\t\t\"spucount\": 1,\n" +
            "\t\t\t\"detailid\": 9863,\n" +
            "\t\t\t\"spuid\": 28,\n" +
            "\t\t\t\"spuname\": \"折耳蓝猫\",\n" +
            "\t\t\t\"skuid\": 93,\n" +
            "\t\t\t\"specsvalue\": \"幼猫 母 \"\n" +
            "\t\t}],\n" +
            "\t\t\"addorderdate\": \"2019-12-04 09:44:28\",\n" +
            "\t\t\"actualpay\": 0,\n" +
            "\t\t\"status\": 5\n" +
            "\t},{\n" +
            "\t\t\"confirmdate\": \"\",\n" +
            "\t\t\"skucount\": 1,\n" +
            "\t\t\"address\": \"北京市 北京市 朝阳区 未来时大厦\",\n" +
            "\t\t\"cancelreason\": \"\",\n" +
            "\t\t\"deliverdate\": \"\",\n" +
            "\t\t\"orderid\": 115,\n" +
            "\t\t\"receivename\": \"周琳\",\n" +
            "\t\t\"freight\": 0,\n" +
            "\t\t\"discount\": 0,\n" +
            "\t\t\"ordernum\": \"O1575423864515\",\n" +
            "\t\t\"shreason\": \"\",\n" +
            "\t\t\"shtime\": \"\",\n" +
            "\t\t\"returnlist\": [],\n" +
            "\t\t\"supplierMoney\": 0,\n" +
            "\t\t\"ordernote\": \"无\",\n" +
            "\t\t\"phone\": \"18515283454\",\n" +
            "\t\t\"price\": 0,\n" +
            "\t\t\"skulist\": [{\n" +
            "\t\t\t\"changeskuid\": 0,\n" +
            "\t\t\t\"spuprice\": 0,\n" +
            "\t\t\t\"changespecsvalue\": \"\",\n" +
            "\t\t\t\"spucount\": 1,\n" +
            "\t\t\t\"detailid\": 9863,\n" +
            "\t\t\t\"spuid\": 28,\n" +
            "\t\t\t\"spuname\": \"折耳蓝猫\",\n" +
            "\t\t\t\"skuid\": 93,\n" +
            "\t\t\t\"specsvalue\": \"幼猫 母 \"\n" +
            "\t\t}],\n" +
            "\t\t\"addorderdate\": \"2019-12-04 09:44:28\",\n" +
            "\t\t\"actualpay\": 0,\n" +
            "\t\t\"status\": 11\n" +
            "\t},{\n" +
            "\t\t\"confirmdate\": \"\",\n" +
            "\t\t\"skucount\": 1,\n" +
            "\t\t\"address\": \"北京市 北京市 朝阳区 未来时大厦\",\n" +
            "\t\t\"cancelreason\": \"\",\n" +
            "\t\t\"deliverdate\": \"\",\n" +
            "\t\t\"orderid\": 115,\n" +
            "\t\t\"receivename\": \"周琳\",\n" +
            "\t\t\"freight\": 0,\n" +
            "\t\t\"discount\": 0,\n" +
            "\t\t\"ordernum\": \"O1575423864515\",\n" +
            "\t\t\"shreason\": \"\",\n" +
            "\t\t\"shtime\": \"\",\n" +
            "\t\t\"returnlist\": [],\n" +
            "\t\t\"supplierMoney\": 0,\n" +
            "\t\t\"ordernote\": \"无\",\n" +
            "\t\t\"phone\": \"18515283454\",\n" +
            "\t\t\"price\": 0,\n" +
            "\t\t\"skulist\": [{\n" +
            "\t\t\t\"changeskuid\": 0,\n" +
            "\t\t\t\"spuprice\": 0,\n" +
            "\t\t\t\"changespecsvalue\": \"\",\n" +
            "\t\t\t\"spucount\": 1,\n" +
            "\t\t\t\"detailid\": 9863,\n" +
            "\t\t\t\"spuid\": 28,\n" +
            "\t\t\t\"spuname\": \"折耳蓝猫\",\n" +
            "\t\t\t\"skuid\": 93,\n" +
            "\t\t\t\"specsvalue\": \"幼猫 母 \"\n" +
            "\t\t}],\n" +
            "\t\t\"addorderdate\": \"2019-12-04 09:44:28\",\n" +
            "\t\t\"actualpay\": 0,\n" +
            "\t\t\"status\": 19\n" +
            "\t}],\n" +
            "\t\"pageIndex\": 1,\n" +
            "\t\"maxRow\": 1,\n" +
            "\t\"startTime\": \"2019-12-04 13:45:39\",\n" +
            "\t\"page\": 1,\n" +
            "\t\"desc\": \"查询成功\"\n" +
            "}";

    public static void showStatus(TextView tvStatus,int status){
        switch (status){
            case 0:
                 tvStatus.setText("待付款");
                 break;
            case 1:
                tvStatus.setText("待发货");
                break;
            case 2:
                tvStatus.setText("已发货");
                break;
            case 3:
                tvStatus.setText("已收货");
                break;
            case 5:
                tvStatus.setText("申请退款中");
                break;
            case 6:
                tvStatus.setText("退款卖家同意");
                break;
            case 7:
                tvStatus.setText("退款卖家不同意");
                break;
            case 8:
                tvStatus.setText("申请退货退款中");
                break;
            case 9:
                tvStatus.setText("退货退款卖家同意");
                break;
            case 10:
                tvStatus.setText("退货退款卖家不同意");
                break;
            case 11:
                tvStatus.setText("申请换货中");
                break;
            case 12:
                tvStatus.setText("换货卖家同意");
                break;
            case 13:
                tvStatus.setText("换货卖家不同意");
                break;
            case 14:
                tvStatus.setText("已完成");
                break;
            case 15:
                tvStatus.setText("已取消");
                break;
            case 17:
                tvStatus.setText("退货退款等待卖家收货");
                break;
            case 18:
                tvStatus.setText("退货退款卖家已收货");
                break;
            case 19:
                tvStatus.setText("换货等待卖家收货");
                break;
            case 20:
                tvStatus.setText("换货卖家已发货");
                break;
            case 21:
                tvStatus.setText("换货买家已收货");
                break;
            default:
                break;
        }
    }


    /**
     * 显示说明
     * @param tvShuoMing
     */
    public static void showShuoMing(TextView tvShuoMing,TextView tvShuoMing2,TextView tvRefundDes,Order.OrderBean orderBean){
        switch (orderBean.getStatus()){
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                 tvShuoMing.setText("退款原因");
                tvShuoMing2.setText("客户发起退款");
                if(tvRefundDes!=null){
                    tvRefundDes.setText("退款原因："+orderBean.getShreason());
                }
                 break;
            case 11:
            case 12:
            case 13:
            case 19:
            case 20:
            case 21:
                tvShuoMing.setText("换货原因");
                tvShuoMing2.setText("客户发起换货");
                if(tvRefundDes!=null){
                    tvRefundDes.setText("换货原因："+orderBean.getShreason());
                }
                 break;
            case 16:
            case 18:
                tvShuoMing.setText("退款换货原因");
                tvShuoMing2.setText("客户发起退款换货");
                if(tvRefundDes!=null){
                    tvRefundDes.setText("退款换货原因："+orderBean.getShreason());
                }
                 break;
            default:
                break;
        }
        if(tvRefundDes!=null){
            if(!TextUtils.isEmpty(orderBean.getOrdernote()) && !orderBean.getOrdernote().equals("无")){
                tvRefundDes.append("\n\n备注："+orderBean.getOrdernote());
            }
        }
    }

}
