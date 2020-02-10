package com.ylean.mmspd.eventbus;

public class EventStatus {
    //同意退货
    public static final int RETURN_ORDER=100;

    //不同意退货
    public static final int NO_RETURN_ORDER=101;

    //自定义查询商品排名
    public static final int GET_GOODRANKING_BY_TIME=102;

    //同意换货
    public static final int HUANHUO_ORDER=103;

    //不同意换货
    public static final int NO_HUANHUO_ORDER=104;

    //展示账单明细数据
    public static final int GET_ACCOUND=105;

    //换货/发货
    public static final int SET_FA_HUO=106;

    //按关键字搜索订单
    public static final int SEARCH_FRAGMENT_BY_KEYS=107;

    //刷新订单数量
    public static final int REFRESH_ORDER_NUM=108;

    //调用（订单换货发货接口）
    public static final int ORDER_STATUS_19=109;

    //退货退款卖家收货
    public static final int ORDER_STATUS_17=110;

    //订单发货
    public static final int ORDER_STATUS_1=111;



}
