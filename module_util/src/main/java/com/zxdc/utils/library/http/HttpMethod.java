package com.zxdc.utils.library.http;

import android.os.Handler;
import android.text.TextUtils;

import com.zxdc.utils.library.bean.Accound;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.bean.Business;
import com.zxdc.utils.library.bean.Comment;
import com.zxdc.utils.library.bean.Courier;
import com.zxdc.utils.library.bean.GoodRanking;
import com.zxdc.utils.library.bean.Login;
import com.zxdc.utils.library.bean.News;
import com.zxdc.utils.library.bean.Order;
import com.zxdc.utils.library.bean.OrderDetails;
import com.zxdc.utils.library.bean.OrderNum;
import com.zxdc.utils.library.bean.ReportType;
import com.zxdc.utils.library.bean.Store;
import com.zxdc.utils.library.http.base.BaseRequst;
import com.zxdc.utils.library.http.base.Http;
import com.zxdc.utils.library.util.LogUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class HttpMethod extends BaseRequst {

    private static String size="20";

    /**
     * 登录
     */
    public static void login(String phone,String password, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("clienttype","1");
        map.put("phone", phone);
        map.put("password", password);
        Http.getRetrofit().create(HttpApi.class).login(map).enqueue(new Callback<Login>() {
            public void onResponse(Call<Login> call, Response<Login> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.LOGIN_SUCCESS, response.body());
            }
            public void onFailure(Call<Login> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取短信验证码
     */
    public static void getSmsCode(String phone,String smstype, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("clienttype", "1");
        map.put("phone", phone);
        map.put("smstype",smstype);
        Http.getRetrofit().create(HttpApi.class).getSmsCode(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_SMS_CODE_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 忘记密码
     */
    public static void forgetPwd(String code,String newpass,String phone, final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("newpass", newpass);
        map.put("phone",phone);
        Http.getRetrofit().create(HttpApi.class).forgetPwd(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.FORGET_PWD_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 修改密码
     */
    public static void editPwd(String newpass,String oldpass,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("newpass", newpass);
        map.put("oldpass", oldpass);
        Http.getRetrofit().create(HttpApi.class).editPwd(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.EDIT_PWD_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取订单列表
     */
    public static void getOrderList(String page,String keyword,String status,final int index,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("page", page);
        map.put("size", size);
        if(!TextUtils.isEmpty(keyword)){
            map.put("keyword",keyword);
        }
        map.put("status",status);
        Http.getRetrofit().create(HttpApi.class).getOrderList(map).enqueue(new Callback<Order>() {
            public void onResponse(Call<Order> call, Response<Order> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<Order> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 退款/退货退款审核
     */
    public static void returnOrder(String isagree,String orderid,String reason,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("isagree", isagree);
        map.put("orderid", orderid);
        if(!TextUtils.isEmpty(reason)){
            map.put("reason",reason);
        }
        Http.getRetrofit().create(HttpApi.class).returnOrder(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.RETURN_ORDER_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取店铺信息
     */
    public static void getStore(final Handler handler) {
        Map<String, String> map = new HashMap<>();
        Http.getRetrofit().create(HttpApi.class).getStore(map).enqueue(new Callback<Store>() {
            public void onResponse(Call<Store> call, Response<Store> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_STORE_INFO_SUCCESS, response.body());
            }
            public void onFailure(Call<Store> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 设置店铺营业状态
     */
    public static void editStatus(String status,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("status",status);
        Http.getRetrofit().create(HttpApi.class).editStatus(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.EDIT_STORE_STATUS_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取商品排名数据
     */
    public static void goodRanking(String timetype,String end,String start,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("timetype",timetype);
        if(!TextUtils.isEmpty(end)){
            map.put("end",end);
        }
        if(!TextUtils.isEmpty(start)){
            map.put("start",start);
        }
        Http.getRetrofit().create(HttpApi.class).goodRanking(map).enqueue(new Callback<GoodRanking>() {
            public void onResponse(Call<GoodRanking> call, Response<GoodRanking> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_GOOD_RANKING_SUCCESS, response.body());
            }
            public void onFailure(Call<GoodRanking> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 换货审核
     */
    public static void huanHuo(String isagree,String orderid,String reason,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("isagree", isagree);
        map.put("orderid", orderid);
        if(!TextUtils.isEmpty(reason)){
            map.put("reason",reason);
        }
        Http.getRetrofit().create(HttpApi.class).huanHuo(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.RETURN_ORDER_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取评论列表
     */
    public static void getCommentList(String page,String type,final int index,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("page", page);
        map.put("size", size);
        map.put("type",type);
        Http.getRetrofit().create(HttpApi.class).getCommentList(map).enqueue(new Callback<Comment>() {
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<Comment> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 评论回复
     */
    public static void comment(String commentid,String content,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("commentid", commentid);
        map.put("content", content);
        Http.getRetrofit().create(HttpApi.class).comment(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.COMMENT_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 反馈
     */
    public static void feedBack(String content,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("content", content);
        Http.getRetrofit().create(HttpApi.class).feedBack(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.FEEDBACK_SUCCEESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取账单明细
     */
    public static void getAccound(String datatime,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("datatime", datatime);
        Http.getRetrofit().create(HttpApi.class).getAccound(map).enqueue(new Callback<Accound>() {
            public void onResponse(Call<Accound> call, Response<Accound> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_ACCOUND_SUCCESS, response.body());
            }
            public void onFailure(Call<Accound> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取订单详情
     */
    public static void getOrderDetails(String code,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        Http.getRetrofit().create(HttpApi.class).getOrderDetails(map).enqueue(new Callback<OrderDetails>() {
            public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_ORDER_DETAILS_SUCCESS, response.body());
            }
            public void onFailure(Call<OrderDetails> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取营业额
     */
    public static void getBusiness(String timetype,String end,String start,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("timetype", timetype);
        if(!TextUtils.isEmpty(end)){
            map.put("end",end);
        }
        if(!TextUtils.isEmpty(start)){
            map.put("start",start);
        }
        Http.getRetrofit().create(HttpApi.class).getBusiness(map).enqueue(new Callback<Business>() {
            public void onResponse(Call<Business> call, Response<Business> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_BUSINESS_SUCCESS, response.body());
            }
            public void onFailure(Call<Business> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 换货/发货
     */
    public static void setShouHuo(String logisticscode,String orderid,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("logisticscode", logisticscode);
        map.put("orderid",orderid);
        Http.getRetrofit().create(HttpApi.class).setShouHuo(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.RETURN_ORDER_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取系统消息
     */
    public static void getNews(String page,final int index,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("type","5");
        map.put("pageindex", page);
        map.put("pagesize", size);
        Http.getRetrofit().create(HttpApi.class).getNews(map).enqueue(new Callback<News>() {
            public void onResponse(Call<News> call, Response<News> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<News> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取举报类型
     */
    public static void getReport(final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("type","3");
        Http.getRetrofit().create(HttpApi.class).getReport(map).enqueue(new Callback<ReportType>() {
            public void onResponse(Call<ReportType> call, Response<ReportType> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_REPORT_TYPE_SUCCESS, response.body());
            }
            public void onFailure(Call<ReportType> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 举报
     */
    public static void report(String contentid,String reporttype,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("contentid",contentid);
        map.put("contenttype","0");
        map.put("reporttype",reporttype);
        Http.getRetrofit().create(HttpApi.class).report(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.REPORT_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取订单数量
     */
    public static void getOrderNum(final Handler handler) {
        Map<String, String> map = new HashMap<>();
        Http.getRetrofit().create(HttpApi.class).getOrderNum(map).enqueue(new Callback<OrderNum>() {
            public void onResponse(Call<OrderNum> call, Response<OrderNum> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_ORDER_NUM_SUCCESS, response.body());
            }
            public void onFailure(Call<OrderNum> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 退货退款卖家收货
     */
    public static void tuihuo(String orderid,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("orderid",orderid);
        Http.getRetrofit().create(HttpApi.class).tuihuo(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.RETURN_ORDER_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取物流公司
     */
    public static void getCourier(final Handler handler) {
        Map<String, String> map = new HashMap<>();
        Http.getRetrofit().create(HttpApi.class).getCourier(map).enqueue(new Callback<Courier>() {
            public void onResponse(Call<Courier> call, Response<Courier> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_COURIER_SUCCESS, response.body());
            }
            public void onFailure(Call<Courier> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 订单发货
     */
    public static void fahuo(String expressname,String logisticscode,String logisticsname,String orderid,final Handler handler) {
        Map<String, String> map = new HashMap<>();
        map.put("expressname",expressname);
        map.put("logisticscode",logisticscode);
        map.put("logisticsname",logisticsname);
        map.put("orderid",orderid);
        Http.getRetrofit().create(HttpApi.class).fahuo(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_ORDER_FH_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }

}
