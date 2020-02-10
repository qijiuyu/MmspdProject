package com.ylean.mmspd.fragment.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.ylean.mmspd.R;
import com.ylean.mmspd.activity.order.OrderDetailsActivity;
import com.ylean.mmspd.activity.order.OrderManagerActivity;
import com.ylean.mmspd.adapter.order.RefundAdapter;
import com.ylean.mmspd.eventbus.EventBusType;
import com.ylean.mmspd.eventbus.EventStatus;
import com.zxdc.utils.library.base.BaseFragment;
import com.zxdc.utils.library.bean.Order;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.MyRefreshLayout;
import com.zxdc.utils.library.view.MyRefreshLayoutListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 已取消
 */
public class CancleFragment extends BaseFragment implements MyRefreshLayoutListener {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.re_list)
    MyRefreshLayout reList;
    Unbinder unbinder;
    private RefundAdapter refundAdapter;
    //页数
    private int page=1;
    //fragment是否可见
    private boolean isVisibleToUser=false;
    //订单数据集合
    private List<Order.OrderBean> listAll=new ArrayList<>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册eventBus
        EventBus.getDefault().register(this);
    }


    View view=null;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.listview, container, false);
        unbinder = ButterKnife.bind(this, view);
        //刷新加载
        reList.setMyRefreshLayoutListener(this);
        refundAdapter=new RefundAdapter(mActivity,listAll);
        listView.setAdapter(refundAdapter);
        listView.setDivider(null);
        //获取订单列表
        getOrderList(HandlerConstant.GET_RETURN_ORDER_SUCCESS1);
        return view;
    }

    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                //下刷
                case HandlerConstant.GET_RETURN_ORDER_SUCCESS1:
                    reList.refreshComplete();
                    listAll.clear();
                    refresh((Order) msg.obj);
                    break;
                //上拉
                case HandlerConstant.GET_RETURN_ORDER_SUCCESS2:
                    reList.loadMoreComplete();
                    refresh((Order) msg.obj);
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
     * 刷新界面数据
     */
    private void refresh(Order order){
        if(order==null){
            return;
        }
        if(order.isSussess()){
            List<Order.OrderBean> list=order.getData();
            listAll.addAll(list);
            refundAdapter.notifyDataSetChanged();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent intent=new Intent(mActivity, OrderDetailsActivity.class);
//                    intent.putExtra("code",listAll.get(position).getOrdernum());
//                    startActivity(intent);
                }
            });
            if(list.size()<20){
                reList.setIsLoadingMoreEnabled(false);
            }
        }else{
            ToastUtil.showLong(order.getDesc());
        }
    }


    /**
     * 下刷
     * @param view
     */
    public void onRefresh(View view) {
        page=1;
        HttpMethod.getOrderList(String.valueOf(page), OrderManagerActivity.keys,"6",HandlerConstant.GET_RETURN_ORDER_SUCCESS1,handler);
    }

    /**
     * 上拉加载更多
     * @param view
     */
    public void onLoadMore(View view) {
        page++;
        HttpMethod.getOrderList(String.valueOf(page), OrderManagerActivity.keys,"6",HandlerConstant.GET_RETURN_ORDER_SUCCESS2,handler);
    }


    /**
     * EventBus注解
     */
    @Subscribe
    public void onEvent(EventBusType eventBusType) {
        switch (eventBusType.getStatus()) {
            //搜索新的关键字
            case EventStatus.SEARCH_FRAGMENT_BY_KEYS:
                listAll.clear();
                refundAdapter.notifyDataSetChanged();
                onRefresh(null);
                break;
            default:
                break;
        }
    }



    /**
     * 获取订单列表
     */
    private void getOrderList(int index){
        if(isVisibleToUser && view!=null && listAll.size()==0){
            reList.startRefresh();
            HttpMethod.getOrderList(String.valueOf(page), OrderManagerActivity.keys,"6",index,handler);
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
        //获取订单列表
        getOrderList(HandlerConstant.GET_RETURN_ORDER_SUCCESS1);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        removeHandler(handler);
    }
}
