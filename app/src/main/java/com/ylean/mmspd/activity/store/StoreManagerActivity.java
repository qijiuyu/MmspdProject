package com.ylean.mmspd.activity.store;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import com.ylean.mmspd.R;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.Business;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 门店运营
 * Created by Administrator on 2019/8/23.
 */

public class StoreManagerActivity extends BaseActivity {

    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_day_money)
    TextView tvDayMoney;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_manager);
        ButterKnife.bind(this);
    }


    /**
     * 按钮点击事件
     * @param view
     */
    @OnClick({ R.id.rel_business,R.id.tv_ranking, R.id.tv_comment, R.id.tv_message, R.id.tv_bill})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //营业总览
            case R.id.rel_business:
                 setClass(BusinessTotalActivity.class);
                 break;
            //商品排名
            case R.id.tv_ranking:
                 setClass(GoodRankingActivity.class);
                break;
            //用户评价
            case R.id.tv_comment:
                setClass(CommentsActivity.class);
                break;
            //系统消息
            case R.id.tv_message:
                setClass(SystemNewActivity.class);
                break;
            //账单明细
            case R.id.tv_bill:
                setClass(AccoundDetailsActivity.class);
                break;
            default:
                break;
        }
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case HandlerConstant.GET_BUSINESS_SUCCESS:
                     final Business business= (Business) msg.obj;
                     if(business==null || business.getData()==null){
                         break;
                     }
                     if(business.isSussess()){
                         tvTotalMoney.setText(Util.setDouble(business.getData().getTotalprice(),2));
                         tvDayMoney.setText(Util.setDouble(business.getData().getTodayprice(),2));
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


    /**
     * 获取营业额
     */
    private void getBusiness(){
        HttpMethod.getBusiness("1",null,null,handler);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取营业额
        getBusiness();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
