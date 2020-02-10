package com.ylean.mmspd.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import com.ylean.mmspd.R;
import com.ylean.mmspd.activity.order.OrderManagerActivity;
import com.ylean.mmspd.activity.solve.SolveActivity;
import com.ylean.mmspd.activity.store.StoreManagerActivity;
import com.ylean.mmspd.activity.user.UserInfoActivity;
import com.ylean.mmspd.application.MyApplication;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.ActivitysLifecycle;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.StatusBarUtils;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.ClickLinearLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Administrator on 2019/8/23.
 */
public class TagActivity extends TabActivity {

    @BindView(R.id.img_solve)
    ImageView imgSolve;
    @BindView(R.id.tv_solve)
    TextView tvSolve;
    @BindView(R.id.lin_solve)
    ClickLinearLayout linSolve;
    @BindView(R.id.img_order)
    ImageView imgOrder;
    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.lin_order)
    ClickLinearLayout linOrder;
    @BindView(R.id.img_store)
    ImageView imgStore;
    @BindView(R.id.tv_store)
    TextView tvStore;
    @BindView(R.id.lin_store)
    ClickLinearLayout linStore;
    @BindView(R.id.img_user)
    ImageView imgUser;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.lin_user)
    ClickLinearLayout linUser;
    @BindView(android.R.id.tabhost)
    TabHost tabhost;
    private List<ImageView> imgList=new ArrayList<>();
    private List<TextView> tvList=new ArrayList<>();
    private int[] notClick=new int[]{R.mipmap.tab_dcl_no,R.mipmap.tab_ddgl_no,R.mipmap.tab_mdyy_no,R.mipmap.tab_me_no};
    private int[] yesClick=new int[]{R.mipmap.tab_dcl_yes,R.mipmap.tab_ddgl_yes,R.mipmap.tab_mdyy_yes,R.mipmap.tab_me_yes};
    private TabHost tabHost;
    // 按两次退出
    protected long exitTime = 0;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        ButterKnife.bind(this);
        initView();
        //设置推送
        setPush();
    }

    /**
     * 初始化
     */
    private void initView(){
        imgList.add(imgSolve);imgList.add(imgOrder);imgList.add(imgStore);imgList.add(imgUser);
        tvList.add(tvSolve);tvList.add(tvOrder);tvList.add(tvStore);tvList.add(tvUser);
        tabHost=this.getTabHost();
        TabHost.TabSpec spec;
        spec=tabHost.newTabSpec("待处理").setIndicator("待处理").setContent(new Intent(this, SolveActivity.class));
        tabHost.addTab(spec);
        spec=tabHost.newTabSpec("订单管理").setIndicator("订单管理").setContent(new Intent(this, OrderManagerActivity.class));
        tabHost.addTab(spec);
        spec=tabHost.newTabSpec("门店运营").setIndicator("门店运营").setContent(new Intent(this, StoreManagerActivity.class));
        tabHost.addTab(spec);
        spec=tabHost.newTabSpec("我的").setIndicator("我的").setContent(new Intent(this, UserInfoActivity.class));
        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);
    }


    /**
     * 按钮点击事件
     * @param view
     */
    @OnClick({R.id.lin_solve, R.id.lin_order, R.id.lin_store, R.id.lin_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_solve:
                updateTag(0);
                tabHost.setCurrentTabByTag("待处理");
                StatusBarUtils.setStatusBarColor(this,android.R.color.black);
                break;
            case R.id.lin_order:
                updateTag(1);
                tabHost.setCurrentTabByTag("订单管理");
                StatusBarUtils.setStatusBarColor(this,android.R.color.black);
                break;
            case R.id.lin_store:
                updateTag(2);
                tabHost.setCurrentTabByTag("门店运营");
                StatusBarUtils.setStatusBarColor(this,R.color.color_ff6d32);
                break;
            case R.id.lin_user:
                updateTag(3);
                tabHost.setCurrentTabByTag("我的");
                StatusBarUtils.setStatusBarColor(this,android.R.color.black);
                break;
            default:
                break;
        }
    }


    /**
     * 切换tab时，更新图片和文字颜色
     */
    private void updateTag(int type){
        for(int i=0;i<4;i++){
            if(i==type){
                imgList.get(i).setImageDrawable(getResources().getDrawable(yesClick[i]));
                tvList.get(i).setTextColor(getResources().getColor(R.color.color_333333));
            }else{
                imgList.get(i).setImageDrawable(getResources().getDrawable(notClick[i]));
                tvList.get(i).setTextColor(getResources().getColor(R.color.color_999999));
            }
        }
    }


    /**
     * 设置推送
     */
    private void setPush() {
        int userId=SPUtil.getInstance(this).getInteger(SPUtil.USERID);
        if(userId==0){
            return;
        }
        //设置极光推送的别名
        Set<String> tags = new HashSet<>();
        tags.add(String.valueOf(userId));
        JPushInterface.setAliasAndTags(getApplicationContext(), String.valueOf(userId), tags, mAliasCallback);
    }


    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code) {
                //设置别名成功
                case 0:
                    LogUtils.e("推送设置成功");
                    break;
                //设置别名失败
                case 6002:
                    LogUtils.e("推送设置失败");
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            setPush();
                        }
                    }, 30000);
                    break;
                default:
            }
        }
    };


    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN ) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showLong("再按一次退出程序!");
                exitTime = System.currentTimeMillis();
            } else {
                //关闭广播
                ActivitysLifecycle.getInstance().exit();
            }
            return false;
        }
        return super.dispatchKeyEvent(event);
    }
}
