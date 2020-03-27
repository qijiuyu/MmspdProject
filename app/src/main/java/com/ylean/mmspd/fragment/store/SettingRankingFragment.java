package com.ylean.mmspd.fragment.store;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ylean.mmspd.R;
import com.ylean.mmspd.adapter.stroe.GoodRankingAdapter;
import com.ylean.mmspd.eventbus.EventBusType;
import com.ylean.mmspd.eventbus.EventStatus;
import com.zxdc.utils.library.base.BaseFragment;
import com.zxdc.utils.library.bean.GoodRanking;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.MyRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 自定义商品排名fragment
 * Created by Administrator on 2019/8/23.
 */

public class SettingRankingFragment extends BaseFragment {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.re_list)
    MyRefreshLayout reList;
    Unbinder unbinder;
    @BindView(R.id.lin_no)
    LinearLayout linNo;
    private GoodRankingAdapter goodRankingAdapter;
    private List<GoodRanking.GoodRankingBean> list = new ArrayList<>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册eventBus
        EventBus.getDefault().register(this);
    }


    View view = null;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.listview, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what) {
                case HandlerConstant.GET_GOOD_RANKING_SUCCESS:
                    final GoodRanking goodRanking = (GoodRanking) msg.obj;
                    if (goodRanking == null) {
                        break;
                    }
                    if (goodRanking.isSussess()) {
                        list.addAll(goodRanking.getData());
                        goodRankingAdapter = new GoodRankingAdapter(mActivity, list);
                        listView.setAdapter(goodRankingAdapter);
                        //没有数据展示的视图
                        if(list.size()>0){
                            linNo.setVisibility(View.GONE);
                        }else{
                            linNo.setVisibility(View.VISIBLE);
                        }
                    } else {
                        ToastUtil.showLong(goodRanking.getDesc());
                    }
                    break;
                case HandlerConstant.REQUST_ERROR:
                    ToastUtil.showLong(msg.obj == null ? "异常错误信息" : msg.obj.toString());
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
    private String startTime,endTime;
    @Subscribe
    public void onEvent(EventBusType eventBusType) {
        switch (eventBusType.getStatus()) {
            case EventStatus.GET_GOODRANKING_BY_TIME:
                final String time = (String) eventBusType.getObject();
                if (TextUtils.isEmpty(time)) {
                    return;
                }
                String[] str = time.split(",");
                if(str[0].equals(startTime) && str[1].equals(endTime)){
                    return;
                }
                startTime=str[0];
                endTime=str[1];
                //获取商品排名数据
                goodRanking(str[1], str[0]);
                break;
            default:
                break;
        }
    }


    /**
     * 获取商品排名数据
     */
    private void goodRanking(String end, String sta) {
        DialogUtil.showProgress(mActivity, "数据查询中...");
        HttpMethod.goodRanking("3", end, sta, handler);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
