package com.ylean.mmspd.activity.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.ylean.mmspd.R;
import com.ylean.mmspd.activity.order.OrderDetailsActivity;
import com.ylean.mmspd.adapter.stroe.AccoundDetailsAdapter;
import com.ylean.mmspd.eventbus.EventBusType;
import com.ylean.mmspd.eventbus.EventStatus;
import com.ylean.mmspd.persenter.AccoundDetailsPersenter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.Accound;
import com.zxdc.utils.library.util.Util;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.text.SimpleDateFormat;
import java.util.Date;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 账单明细
 * Created by Administrator on 2019/8/25.
 */

public class AccoundDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_online_num)
    TextView tvOnlineNum;
    @BindView(R.id.tv_online_money)
    TextView tvOnlineMoney;
    @BindView(R.id.tv_refund_num)
    TextView tvRefundNum;
    @BindView(R.id.tv_refund_money)
    TextView tvRefundMoney;
    @BindView(R.id.listView)
    ListView listView;
    private AccoundDetailsAdapter accoundDetailsAdapter;
    private AccoundDetailsPersenter accoundDetailsPersenter;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accound_details);
        ButterKnife.bind(this);
        initView();
    }


    /**
     * 初始化
     */
    private void initView(){
        //注册eventBus
        EventBus.getDefault().register(this);
        accoundDetailsPersenter=new AccoundDetailsPersenter(this);
        //获取当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time=dateFormat.format(new Date());
        tvSelect.setText(time);
        tvTime.setText(time+"收入");
        //获取账单明细
        accoundDetailsPersenter.getAccound(time);
    }


    /**
     * 按钮点击事件
     * @param view
     */
    @OnClick({R.id.lin_back, R.id.tv_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            //选择时间
            case R.id.tv_select:
                accoundDetailsPersenter.initCustomTimePicker(tvSelect);
                accoundDetailsPersenter.showTime(tvSelect);
                break;
            default:
                break;
        }
    }


    /**
     * EventBus注解
     */
    @Subscribe
    public void onEvent(EventBusType eventBusType) {
        switch (eventBusType.getStatus()) {
            //展示账单明细
            case EventStatus.GET_ACCOUND:
                 showAccound((Accound.AccoundBean) eventBusType.getObject());
                break;
            default:
                break;
        }
    }


    /**
     * 显示账单明细数据
     */
    private void showAccound(Accound.AccoundBean accoundBean){
        tvTime.setText(tvSelect.getText().toString()+"收入");
        tvMoney.setText("¥"+ Util.setDouble(accoundBean.getTotalmoney(),2));
        tvOnlineNum.setText("共"+accoundBean.getZxcount()+"笔");
        tvOnlineMoney.setText("¥"+ Util.setDouble(accoundBean.getZxmoney(),2));
        tvRefundNum.setText("共"+accoundBean.getTkcount()+"笔");
        tvRefundMoney.setText("¥"+ Util.setDouble(accoundBean.getTkmoney(),2));
        accoundDetailsAdapter=new AccoundDetailsAdapter(this,accoundBean.getOrderlist());
        listView.setAdapter(accoundDetailsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvCode=view.findViewById(R.id.tv_order_code);
                Intent intent=new Intent(AccoundDetailsActivity.this,OrderDetailsActivity.class);
                intent.putExtra("code",tvCode.getText().toString().trim());
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
