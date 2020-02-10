package com.ylean.mmspd.activity.store;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ylean.mmspd.R;
import com.ylean.mmspd.persenter.BusinessTotalPersenter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.Business;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.util.Util;
import com.zxdc.utils.library.view.ClickTextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 营业总览
 * Created by Administrator on 2019/8/24.
 */

public class BusinessTotalActivity extends BaseActivity {

    @BindView(R.id.lin_back)
    LinearLayout linBack;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_seven)
    TextView tvSeven;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_confirm)
    ClickTextView tvConfirm;
    @BindView(R.id.lin_time)
    LinearLayout linTime;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_day_money)
    TextView tvDayMoney;
    @BindView(R.id.tv_coupons)
    TextView tvCoupons;
    @BindView(R.id.tv_play_coupons)
    TextView tvPlayCoupons;
    @BindView(R.id.tv_orders_ok)
    TextView tvOrdersOk;
    @BindView(R.id.tv_price_ok)
    TextView tvPriceOk;
    @BindView(R.id.tv_orders_no)
    TextView tvOrdersNo;
    @BindView(R.id.tv_price_no)
    TextView tvPriceNo;
    private BusinessTotalPersenter businessTotalPersenter;
    private List<TextView> tvList = new ArrayList<>();
    //开始于结束时间
    private String startTime,endTime;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_total);
        ButterKnife.bind(this);
        businessTotalPersenter=new BusinessTotalPersenter(this);
        initView();
        //获取营业额
        getBusiness("1");
    }


    /**
     * 初始化
     */
    private void initView(){
        tvList.add(tvDay);
        tvList.add(tvSeven);
        tvList.add(tvSetting);
    }

    @OnClick({R.id.lin_back, R.id.tv_day, R.id.tv_seven, R.id.tv_setting, R.id.tv_start_time, R.id.tv_end_time, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            //今天
            case R.id.tv_day:
                updateTab(0);
                getBusiness("1");
                break;
            //近七天
            case R.id.tv_seven:
                updateTab(1);
                getBusiness("2");
                break;
            //自定义
            case R.id.tv_setting:
                updateTab(2);
                break;
            //开始时间
            case R.id.tv_start_time:
                businessTotalPersenter.initCustomTimePicker(tvStartTime,1);
                businessTotalPersenter.showTime(tvStartTime);
                break;
            //结束时间
            case R.id.tv_end_time:
                businessTotalPersenter.initCustomTimePicker(tvEndTime,2);
                businessTotalPersenter.showTime(tvEndTime);
                break;
            //确定
            case R.id.tv_confirm:
                 startTime=tvStartTime.getText().toString().trim();
                 endTime=tvEndTime.getText().toString().trim();
                if(TextUtils.isEmpty(startTime)){
                    ToastUtil.showLong("请输入开始时间");
                    return;
                }
                if(TextUtils.isEmpty(endTime)){
                    ToastUtil.showLong("请输入结束时间");
                    return;
                }
                if(!businessTotalPersenter.judgeTime()){
                    ToastUtil.showLong("结束日期不能小于开始日期");
                    return;
                }
                getBusiness("3");
                break;
        }
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                case HandlerConstant.GET_BUSINESS_SUCCESS:
                    final Business business= (Business) msg.obj;
                    if(business==null || business.getData()==null){
                        break;
                    }
                    if(business.isSussess()){
                        tvTotalMoney.setText(Util.setDouble(business.getData().getTotalprice(),2));
                        tvDayMoney.setText(Util.setDouble(business.getData().getTodayprice(),2));
                        tvCoupons.setText(String.valueOf(business.getData().getTakecoupon()));
                        tvPlayCoupons.setText(String.valueOf(business.getData().getUsecoupon()));
                        tvOrdersOk.setText(String.valueOf(business.getData().getYxordercount()));
                        tvPriceOk.setText(Util.setDouble(business.getData().getYxkdj(),2));
                        tvOrdersNo.setText(String.valueOf(business.getData().getWxordercount()));
                        tvPriceNo.setText(Util.setDouble(business.getData().getWxkdj(),2));
                    }
                    break;
                case HandlerConstant.REQUST_ERROR:
                    ToastUtil.showLong(msg.obj==null ? "异常错误信息" : msg.obj.toString());
                    break;
                default:
                    break;
            }
            return false;
        }
    });



    private void updateTab(int index) {
        for (int i = 0; i < 3; i++) {
            TextView textView = tvList.get(i);
            if (i == index) {
                textView.setTextColor(getResources().getColor(android.R.color.white));
                textView.setBackgroundResource(R.drawable.bg_select);
            } else {
                textView.setTextColor(getResources().getColor(R.color.color_33333));
                textView.setBackgroundResource(R.drawable.bg_no_select);
            }
        }
        if(index==2){
            linTime.setVisibility(View.VISIBLE);
        }else{
            linTime.setVisibility(View.GONE);
        }
    }


    /**
     * 获取营业额
     */
    private void getBusiness(String type){
        DialogUtil.showProgress(this,"数据加载中");
        HttpMethod.getBusiness(type,endTime,startTime,handler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
