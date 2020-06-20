package com.ylean.mmspd.activity.order;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ylean.mmspd.R;
import com.ylean.mmspd.adapter.stroe.OrderDetailsAdapter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.OrderDetails;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.StatusBarUtils;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.util.Util;
import com.zxdc.utils.library.view.MeasureListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 订单详情页
 * Created by Administrator on 2019/8/26.
 */

public class OrderDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.listView)
    MeasureListView listView;
    @BindView(R.id.tv_delivery_time)
    TextView tvDeliveryTime;
    @BindView(R.id.tv_coupons_time)
    TextView tvCouponsTime;
    @BindView(R.id.rel_money)
    RelativeLayout relMoney;
    @BindView(R.id.tv_order_code)
    TextView tvOrderCode;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_received_time)
    TextView tvReceivedTime;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.tv_actual_money)
    TextView tvActualMoney;
    //订单号
    private String code;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        StatusBarUtils.setStatusBarColor(this,R.color.color_ffc600);
        code=getIntent().getStringExtra("code");
        //获取订单详情
        getOrderDetails();
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                case HandlerConstant.GET_ORDER_DETAILS_SUCCESS:
                      final OrderDetails orderDetails= (OrderDetails) msg.obj;
                      if(orderDetails==null){
                          break;
                      }
                      if(orderDetails.isSussess()){
                          //展示详情数据
                          showOrderDetails(orderDetails.getData());
                      }else{
                          ToastUtil.showLong(orderDetails.getDesc());
                      }
                      break;
                case HandlerConstant.REQUST_ERROR:
                    ToastUtil.showLong(msg.obj.toString());
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    /**
     * 展示详情数据
     * @param orderDetailsBean
     */
    private void showOrderDetails(OrderDetails.OrderDetailsBean orderDetailsBean){
        if(orderDetailsBean==null){
            return;
        }
        tvNumber.setText("商品 ("+orderDetailsBean.getSkucount()+")");
        //显示商品列表
        OrderDetailsAdapter goodsAdapter = new OrderDetailsAdapter(this,orderDetailsBean.getSkulist());
        listView.setAdapter(goodsAdapter);
        tvDeliveryTime.setText("+¥" + Util.setDouble(orderDetailsBean.getFreight(),2));
        tvCouponsTime.setText("-¥" +Util.setDouble(orderDetailsBean.getDiscount(),2));
        tvActualMoney.setText("¥"+Util.setDouble(orderDetailsBean.getSupplierMoney(),2));
        tvOrderCode.setText(orderDetailsBean.getOrdernum());
        tvOrderTime.setText(orderDetailsBean.getAddorderdate());
        tvReceivedTime.setText(orderDetailsBean.getConfirmdate());
        tvPay.setText(orderDetailsBean.getExpressname());
    }


    @OnClick(R.id.lin_back)
    public void onViewClicked() {
        finish();
    }


    /**
     * 获取订单详情
     */
    private void getOrderDetails(){
        DialogUtil.showProgress(this,"数据加载中...");
        HttpMethod.getOrderDetails(code,handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
