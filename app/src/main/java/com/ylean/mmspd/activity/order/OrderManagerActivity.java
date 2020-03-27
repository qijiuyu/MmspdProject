package com.ylean.mmspd.activity.order;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ylean.mmspd.R;
import com.ylean.mmspd.eventbus.EventBusType;
import com.ylean.mmspd.eventbus.EventStatus;
import com.ylean.mmspd.fragment.order.CancleFragment;
import com.ylean.mmspd.fragment.order.CompleteFragment;
import com.ylean.mmspd.fragment.order.OngoingFragment;
import com.ylean.mmspd.fragment.order.RefundFragment;
import com.ylean.mmspd.view.PagerSlidingTabStrip;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订单管理
 * Created by Administrator on 2019/8/23.
 */

public class OrderManagerActivity extends BaseActivity implements TextView.OnEditorActionListener {

    @BindView(R.id.et_key)
    EditText etKey;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.img_close)
    ImageView imgClose;
    private DisplayMetrics dm;
    //进行中的订单
    private OngoingFragment ongoingFragment=new OngoingFragment();
    //已完成的订单
    private CompleteFragment completeFragment=new CompleteFragment();
    //已退款的订单
    private RefundFragment refundFragment=new RefundFragment();
    //已取消的订单
    private CancleFragment cancleFragment=new CancleFragment();
    //要搜索的关键字
    public static String keys;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manager);
        ButterKnife.bind(this);
        initView();
    }


    /**
     * 初始化
     */
    private void initView() {
        etKey.setOnEditorActionListener(this);
        dm = getResources().getDisplayMetrics();
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(4);
        tabs.setViewPager(pager);
        setTabsValue();
        etKey.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>0){
                    imgClose.setVisibility(View.VISIBLE);
                }else{
                    imgClose.setVisibility(View.GONE);
                }
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                etKey.setText(null);
            }
        });
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            keys = etKey.getText().toString().trim();
            if (TextUtils.isEmpty(keys)) {
                ToastUtil.showLong("请输入您要搜索的关键字！");
                return false;
            }
            //关闭键盘
            lockKey(etKey);
            EventBus.getDefault().post(new EventBusType(EventStatus.SEARCH_FRAGMENT_BY_KEYS));
        }
        return false;
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


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = {"进行中", "已完成","已退款","已取消"};

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
                return ongoingFragment;
            }
            if (position == 1) {
                return completeFragment;
            }
            if (position == 2) {
                return refundFragment;
            }
            if (position == 3) {
                return cancleFragment;
            }
            return null;
        }
    }
}
