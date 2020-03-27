package com.ylean.mmspd.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ylean.mmspd.R;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.Store;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.OvalImageViews;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的
 * Created by Administrator on 2019/8/23.
 */

public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.img_icon)
    OvalImageViews imgIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.rel_store)
    RelativeLayout relStore;
    @BindView(R.id.rel_feedback)
    RelativeLayout relFeedback;
    @BindView(R.id.rel_account)
    RelativeLayout relAccount;
    @BindView(R.id.rel_abount)
    RelativeLayout relAbount;
    @BindView(R.id.img_news)
    ImageView imgNews;
    //true：打开消息提醒，  false：不打开
    private boolean isOpen=true;
    //店铺对象
    private Store store;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
    }


    /**
     * 按钮点击事件
     * @param view
     */
    @OnClick({R.id.img_news,R.id.rel_store, R.id.rel_feedback, R.id.rel_account, R.id.rel_abount})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //是否打开消息提醒
            case R.id.img_news:
                 if(isOpen){
                     isOpen=false;
                     imgNews.setImageResource(R.mipmap.close_news);
                     SPUtil.getInstance(this).addInt(SPUtil.JPUSH,1);
                 }else{
                     isOpen=true;
                     imgNews.setImageResource(R.mipmap.open_news);
                     SPUtil.getInstance(this).addInt(SPUtil.JPUSH,0);
                 }
                 break;
            //店铺信息
            case R.id.rel_store:
                Intent intent=new Intent(this,StoreInfoActivity.class);
                intent.putExtra("store",store);
                startActivity(intent);
                break;
            //意见反馈
            case R.id.rel_feedback:
                setClass(FeedBackActivity.class);
                break;
            //账号设置
            case R.id.rel_account:
                Intent intent2=new Intent(this,AccountActivity.class);
                intent2.putExtra("mobile",store.getData().getMobile());
                startActivity(intent2);
                break;
            //联系平台
            case R.id.rel_abount:
                break;
            default:
                break;
        }
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                //获取店铺信息
                case HandlerConstant.GET_STORE_INFO_SUCCESS:
                      store= (Store) msg.obj;
                      if(store==null){
                          break;
                      }
                      if(store.isSussess()){
                          //显示店铺信息
                          showStoreInfo();
                      }else{
                          ToastUtil.showLong(store.getDesc());
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
     * 显示店铺信息
     */
    private void showStoreInfo(){
        Glide.with(this).load(HttpConstant.IP+store.getData().getImgurl()).error(R.mipmap.default_head).override(59,59).centerCrop().into(imgIcon);
        tvName.setText(store.getData().getName());
        if(store.getData().getStatus()==2){
            tvStatus.setText("营业中");
        }else{
            tvStatus.setText("暂停营业");
        }
        final int isPush= SPUtil.getInstance(this).getInteger(SPUtil.JPUSH);
        if(isPush==1){
            imgNews.setImageDrawable(getResources().getDrawable(R.mipmap.close_news));
        }else{
            imgNews.setImageDrawable(getResources().getDrawable(R.mipmap.open_news));
        }
    }


    /**
     * 获取店铺信息
     */
    private void getStore(){
        HttpMethod.getStore(handler);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //获取店铺信息
        getStore();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
