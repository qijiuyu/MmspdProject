package com.ylean.mmspd.activity.store;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ylean.mmspd.R;
import com.ylean.mmspd.fragment.store.CommBadFragment;
import com.ylean.mmspd.fragment.store.CommGoodFragment;
import com.ylean.mmspd.fragment.store.CommMiddleFragment;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.view.ClickTextView;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户评价
 * Created by Administrator on 2019/8/24.
 */

public class CommentsActivity extends BaseActivity {

    @BindView(R.id.tv_good_com)
    ClickTextView tvGoodCom;
    @BindView(R.id.tv_middle_com)
    ClickTextView tvMiddleCom;
    @BindView(R.id.tv_bad_com)
    ClickTextView tvBadCom;
    @BindView(R.id.pager)
    ViewPager pager;
    private List<TextView> tvList = new ArrayList<>();
//    //好评的fragment
//    private CommGoodFragment commGoodFragment=new CommGoodFragment();
//    //中评的fragment
//    private CommMiddleFragment commMiddleFragment=new CommMiddleFragment();
//    //差评的fragment
//    private CommBadFragment commBadFragment=new CommBadFragment();
    public int pagerIndex=1;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);
        initView();
    }


    /**
     * 初始化
     */
    private void initView(){
        tvList.add(tvGoodCom);tvList.add(tvMiddleCom);tvList.add(tvBadCom);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(3);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                pagerIndex=(position+1);
                updateTab(position);
            }

            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.lin_back, R.id.tv_good_com, R.id.tv_middle_com, R.id.tv_bad_com})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            //好评
            case R.id.tv_good_com:
                updateTab(0);
                pager.setCurrentItem(0);
                break;
            //中评
            case R.id.tv_middle_com:
                updateTab(1);
                pager.setCurrentItem(1);
                break;
            //差评
            case R.id.tv_bad_com:
                updateTab(2);
                pager.setCurrentItem(2);
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
//            if (position == 0) {
//                return commGoodFragment;
//            }
//            if (position == 1) {
//                return commMiddleFragment;
//            }
//            if (position == 2) {
//                return commBadFragment;
//            }
            return new CommGoodFragment();
        }
    }


    private void updateTab(int index) {
        pagerIndex=(index+1);
        for (int i = 0; i < 3; i++) {
            TextView textView = tvList.get(i);
            if (i == index) {
                textView.setTextColor(getResources().getColor(android.R.color.white));
                textView.setBackgroundResource(R.drawable.bg_comment_yes);
            } else {
                textView.setTextColor(getResources().getColor(R.color.color_33333));
                textView.setBackgroundResource(R.drawable.bg_comment_no);
            }
        }
    }



}
