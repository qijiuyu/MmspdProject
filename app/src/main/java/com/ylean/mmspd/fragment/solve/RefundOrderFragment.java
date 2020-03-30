package com.ylean.mmspd.fragment.solve;

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
import com.ylean.mmspd.adapter.solve.RefundOrderAdapter;
import com.ylean.mmspd.eventbus.EventBusType;
import com.ylean.mmspd.eventbus.EventStatus;
import com.zxdc.utils.library.base.BaseFragment;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.bean.Order;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
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
 * 退款单fragment
 * Created by Administrator on 2019/8/23.
 */

public class RefundOrderFragment extends BaseFragment implements MyRefreshLayoutListener {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.re_list)
    MyRefreshLayout reList;
    Unbinder unbinder;
    private RefundOrderAdapter refundOrderAdapter;
    //页数
    private int page=1;
    //fragment是否可见
    private boolean isVisibleToUser=false;
    //订单数据集合
    private List<Order.OrderBean> listAll=new ArrayList<>();
    //订单id
    private int orderId;
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
        refundOrderAdapter=new RefundOrderAdapter(mActivity,listAll);
        listView.setAdapter(refundOrderAdapter);
        listView.setDivider(null);
        //获取订单列表
        getOrderList(HandlerConstant.GET_RETURN_ORDER_SUCCESS1);
        return view;
    }

    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
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
                //退款操作回执
                case HandlerConstant.RETURN_ORDER_SUCCESS:
                      final BaseBean baseBean= (BaseBean) msg.obj;
                      if(baseBean==null){
                          break;
                      }
                      if(baseBean.isSussess()){
                          for (int i=0,len=listAll.size();i<len;i++){
                                if(listAll.get(i).getOrderid()==orderId){
                                    listAll.remove(i);
                                    refundOrderAdapter.maps.clear();
                                    refundOrderAdapter.refundMap.clear();
                                    refundOrderAdapter.noCanMap.clear();
                                    refundOrderAdapter.shMap.clear();
                                    break;
                                }
                          }
                          refundOrderAdapter.notifyDataSetChanged();
                          //刷新订单数量
                          EventBus.getDefault().post(new EventBusType(EventStatus.REFRESH_ORDER_NUM));
                      }
                      ToastUtil.showLong(baseBean.getDesc());
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
            refundOrderAdapter.notifyDataSetChanged();
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
     * EventBus注解
     */
    @Subscribe
    public void onEvent(EventBusType eventBusType) {
        switch (eventBusType.getStatus()) {
            //同意退款
            case EventStatus.RETURN_ORDER:
                 orderId= (int) eventBusType.getObject();
                 returnOrder("1",null,1);
                 break;
            //不同意退款
            case EventStatus.NO_RETURN_ORDER:
                  orderId= (int) eventBusType.getObject();
                  returnOrder("0",eventBusType.getMsg(),1);
                  break;
            //同意换货
            case EventStatus.HUANHUO_ORDER:
                  orderId= (int) eventBusType.getObject();
                  returnOrder("1",null,2);
                  break;
            //不同意换货
            case EventStatus.NO_HUANHUO_ORDER:
                  orderId= (int) eventBusType.getObject();
                  returnOrder("0",eventBusType.getMsg(),2);
                  break;
            //换货等待卖家收货
            case EventStatus.ORDER_STATUS_19:
                  orderId= (int) eventBusType.getObject();
                  setShouHuo(eventBusType.getMsg());
                  break;
            //退货退款卖家收货
            case EventStatus.ORDER_STATUS_17:
                  orderId= (int) eventBusType.getObject();
                  tuihuo();
                  break;
            default:
                break;

        }
    }


    /**
     * 下刷
     * @param view
     */
    public void onRefresh(View view) {
        page=1;
        HttpMethod.getOrderList(String.valueOf(page),null,"2",HandlerConstant.GET_RETURN_ORDER_SUCCESS1,handler);
    }

    /**
     * 上拉加载更多
     * @param view
     */
    public void onLoadMore(View view) {
        page++;
        HttpMethod.getOrderList(String.valueOf(page),null,"2",HandlerConstant.GET_RETURN_ORDER_SUCCESS2,handler);
    }



    /**
     * 获取订单列表
     */
    private void getOrderList(int index){
        if(isVisibleToUser && view!=null && listAll.size()==0){
            reList.startRefresh();
        }
    }


    /**
     * 退款/退货退款审核
     */
    private void returnOrder(String isagree,String reason,int type){
        DialogUtil.showProgress(mActivity,"操作中...");
        if(type==1){
            HttpMethod.returnOrder(isagree,String.valueOf(orderId),reason,handler);
        }else{
            HttpMethod.huanHuo(isagree,String.valueOf(orderId),reason,handler);
        }
    }

    /**
     * 换货/发货
     */
    private void setShouHuo(String code){
        DialogUtil.showProgress(mActivity,"发送中...");
        HttpMethod.setShouHuo(code,String.valueOf(orderId),handler);
    }


    /**
     * 退货退款卖家收货
     */
    private void tuihuo(){
        DialogUtil.showProgress(mActivity,"发送中...");
        HttpMethod.tuihuo(String.valueOf(orderId),handler);
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
        removeHandler(handler);
        EventBus.getDefault().unregister(this);
    }
}
