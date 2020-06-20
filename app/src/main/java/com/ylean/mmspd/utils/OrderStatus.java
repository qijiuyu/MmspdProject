package com.ylean.mmspd.utils;

import android.text.TextUtils;
import android.widget.TextView;

import com.zxdc.utils.library.bean.Order;

/**
 * Created by Administrator on 2019/11/27.
 */

public class OrderStatus {

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
                 tvShuoMing.setText("退款说明");
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
                tvShuoMing.setText("换货说明");
                tvShuoMing2.setText("客户发起换货");
                if(tvRefundDes!=null){
                    tvRefundDes.setText("换货原因："+orderBean.getShreason());
                }
                 break;
            case 16:
            case 18:
                tvShuoMing.setText("退款换货说明");
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
