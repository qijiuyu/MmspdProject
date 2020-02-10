package com.ylean.mmspd.activity.store;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ylean.mmspd.R;
import com.ylean.mmspd.eventbus.EventBusType;
import com.ylean.mmspd.eventbus.EventStatus;
import com.ylean.mmspd.fragment.store.DayRankingFragment;
import com.ylean.mmspd.fragment.store.SettingRankingFragment;
import com.ylean.mmspd.fragment.store.SevenRankingFragment;
import com.ylean.mmspd.persenter.GoodRankingPerSenter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.ClickTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品排名
 * Created by Administrator on 2019/8/24.
 */

public class GoodRankingActivity extends BaseActivity {

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
    @BindView(R.id.pager)
    ViewPager pager;
    //今天的商品排名
    private DayRankingFragment dayRankingFragment = new DayRankingFragment();
    //近七天的商品排名
    private SevenRankingFragment sevenRankingFragment = new SevenRankingFragment();
    //自定义商品排名
    private SettingRankingFragment settingRankingFragment = new SettingRankingFragment();
    private List<TextView> tvList = new ArrayList<>();

    private GoodRankingPerSenter goodRankingPerSenter;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_ranking);
        ButterKnife.bind(this);
        goodRankingPerSenter=new GoodRankingPerSenter(this);
        initView();
    }


    /**
     * 初始化
     */
    private void initView() {
        tvList.add(tvDay);
        tvList.add(tvSeven);
        tvList.add(tvSetting);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(3);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                updateTab(position);
            }

            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    /**
     * 按钮点击事件
     *
     * @param view
     */
    @OnClick({R.id.lin_back, R.id.tv_day, R.id.tv_seven, R.id.tv_setting,R.id.tv_start_time,R.id.tv_end_time,R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            //今天
            case R.id.tv_day:
                updateTab(0);
                pager.setCurrentItem(0);
                break;
            //近七天
            case R.id.tv_seven:
                updateTab(1);
                pager.setCurrentItem(1);
                break;
            //自定义
            case R.id.tv_setting:
                updateTab(2);
                pager.setCurrentItem(2);
                break;
            //开始时间
            case R.id.tv_start_time:
                goodRankingPerSenter.initCustomTimePicker(tvStartTime,1);
                goodRankingPerSenter.showTime(tvStartTime);
                break;
            //结束时间
            case R.id.tv_end_time:
                goodRankingPerSenter.initCustomTimePicker(tvEndTime,2);
                goodRankingPerSenter.showTime(tvEndTime);
                break;
            //确定
            case R.id.tv_confirm:
                 String startTime=tvStartTime.getText().toString().trim();
                 String endTime=tvEndTime.getText().toString().trim();
                 if(TextUtils.isEmpty(startTime)){
                     ToastUtil.showLong("请输入开始时间");
                     return;
                 }
                if(TextUtils.isEmpty(endTime)){
                    ToastUtil.showLong("请输入结束时间");
                    return;
                }
                if(!goodRankingPerSenter.judgeTime()){
                    ToastUtil.showLong("结束日期不能小于开始日期");
                    return;
                }
                EventBus.getDefault().post(new EventBusType(EventStatus.GET_GOODRANKING_BY_TIME,startTime+","+endTime));
                break;
            default:
                break;
        }
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return dayRankingFragment;
            }
            if (position == 1) {
                return sevenRankingFragment;
            }
            if (position == 2) {
                return settingRankingFragment;
            }
            return null;
        }
    }


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
}
