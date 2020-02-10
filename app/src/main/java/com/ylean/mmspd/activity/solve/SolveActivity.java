package com.ylean.mmspd.activity.solve;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ylean.mmspd.R;
import com.ylean.mmspd.eventbus.EventBusType;
import com.ylean.mmspd.eventbus.EventStatus;
import com.ylean.mmspd.fragment.solve.NewOrderFragment;
import com.ylean.mmspd.fragment.solve.RefundOrderFragment;
import com.ylean.mmspd.view.PagerSlidingTabStrip;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.OrderNum;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 待处理
 * Created by Administrator on 2019/8/23.
 */

public class SolveActivity extends BaseActivity {

    @BindView(R.id.lin_new_order)
    LinearLayout linNewOrder;
    @BindView(R.id.lin_refund_order)
    LinearLayout linRefundOrder;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.tv_new_num)
    TextView tvNewNum;
    @BindView(R.id.tv_refunt_num)
    TextView tvRefuntNum;
    private DisplayMetrics dm;
    //新订单fragment
    private NewOrderFragment newOrderFragment = new NewOrderFragment();
    //退款单fragment
    private RefundOrderFragment refundOrderFragment = new RefundOrderFragment();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);
        ButterKnife.bind(this);
        initView();
        //注册eventBus
        EventBus.getDefault().register(this);
        //获取订单数量
        getOrderNum();
    }


    /**
     * 初始化
     */
    private void initView() {
        dm = getResources().getDisplayMetrics();
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(2);
        tabs.setViewPager(pager);
        setTabsValue();
    }

    /**
     * 对PagerSlidingTabStrip的各项属性进行赋值。
     */
    private void setTabsValue() {
        // 设置Tab是自动填充满屏幕的
        tabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        tabs.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab Indicator的高度
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2, dm));
        // 设置Tab标题文字的大小
        tabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 14, dm));
        // 设置Tab Indicator的颜色
        tabs.setIndicatorColorResource(R.color.color_ff6d32);
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        tabs.setTextColorResource(R.color.color_33333);
        tabs.setSelectedTextColorResource(R.color.color_33333);
        // 取消点击Tab时的背景色
        tabs.setTabBackground(0);
    }


    /**
     * 按钮点击事件
     *
     * @param view
     */
    @OnClick({R.id.lin_new_order, R.id.lin_refund_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //切换到新订单
            case R.id.lin_new_order:
                pager.setCurrentItem(0);
                break;
            //切换到退款单
            case R.id.lin_refund_order:
                pager.setCurrentItem(1);
                break;
            default:
                break;
        }
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = {"新订单", "退款单"};

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return newOrderFragment;
            } else {
                return refundOrderFragment;
            }
        }
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                //获取订单数量
                case HandlerConstant.GET_ORDER_NUM_SUCCESS:
                    OrderNum orderNum = (OrderNum) msg.obj;
                    if (orderNum == null) {
                        break;
                    }
                    if (orderNum.isSussess()) {
                        //新订单数量
                        if(orderNum.getData().getXdd()>0){
                            tvNewNum.setVisibility(View.VISIBLE);
                            tvNewNum.setText(String.valueOf(orderNum.getData().getXdd()));
                        }else{
                            tvNewNum.setVisibility(View.GONE);
                        }
                        //退款单数量
                        if(orderNum.getData().getTkd()>0){
                            tvRefuntNum.setVisibility(View.VISIBLE);
                            tvRefuntNum.setText(String.valueOf(orderNum.getData().getTkd()));
                        }else{
                            tvRefuntNum.setVisibility(View.GONE);
                        }
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
     * EventBus注解
     */
    @Subscribe
    public void onEvent(EventBusType eventBusType) {
        switch (eventBusType.getStatus()) {
            //刷新订单数量
            case EventStatus.REFRESH_ORDER_NUM:
                  getOrderNum();
                  break;
            default:
                break;
        }
    }


    /**
     * 获取订单数量
     */
    private void getOrderNum() {
        HttpMethod.getOrderNum(handler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
        EventBus.getDefault().unregister(this);
    }
}
