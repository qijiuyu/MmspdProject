package com.zxdc.utils.library.http;



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

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HttpApi {

    @FormUrlEncoded
    @POST(HttpConstant.LOGIN)
    Call<Login> login(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.SMS_CODE)
    Call<BaseBean> getSmsCode(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.FORGET_PWD)
    Call<BaseBean> forgetPwd(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.EDIT_PWD)
    Call<BaseBean> editPwd(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.GET_ORDER_LIST)
    Call<Order> getOrderList(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.RETURN_ORDER)
    Call<BaseBean> returnOrder(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.GET_STORE_INFO)
    Call<Store> getStore(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.EDIT_STORE_STATUS)
    Call<BaseBean> editStatus(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.GOOD_SORT)
    Call<GoodRanking> goodRanking(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.HUAN_HUO)
    Call<BaseBean> huanHuo(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.GET_COMMENT_LIST)
    Call<Comment> getCommentList(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.COMMENT)
    Call<BaseBean> comment(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.FEEDBACK)
    Call<BaseBean> feedBack(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.GET_ACCOUND)
    Call<Accound> getAccound(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.GET_ORDER_DETAILS)
    Call<OrderDetails> getOrderDetails(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.GET_BUSINESS)
    Call<Business> getBusiness(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.FAHUO)
    Call<BaseBean> setShouHuo(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.GET_NEWS)
    Call<News> getNews(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.GET_REPORT_TYPE)
    Call<ReportType> getReport(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.REPORT)
    Call<BaseBean> report(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.GET_ORDER_NUM)
    Call<OrderNum> getOrderNum(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.TUI_HUO)
    Call<BaseBean> tuihuo(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.GET_COURIER)
    Call<Courier> getCourier(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.ORDER_FH)
    Call<BaseBean> fahuo(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.REFRESH_TOKEN)
    Call<BaseBean> refreshToken(@FieldMap Map<String, String> map);


}
