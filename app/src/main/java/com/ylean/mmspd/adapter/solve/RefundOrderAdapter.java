package com.ylean.mmspd.adapter.solve;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylean.mmspd.R;
import com.ylean.mmspd.eventbus.EventBusType;
import com.ylean.mmspd.eventbus.EventStatus;
import com.ylean.mmspd.utils.OrderStatus;
import com.zxdc.utils.library.bean.Order;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.util.Util;
import com.zxdc.utils.library.view.ClickTextView;
import com.zxdc.utils.library.view.MeasureListView;
import org.greenrobot.eventbus.EventBus;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 退款单adapter
 *
 * 5和8调用（退款/退货退款审核接口），  11调用（换货审核接口），   19调用（订单换货发货接口）   17（退货退款卖家收货）
 */
public class RefundOrderAdapter extends BaseAdapter {

    private Context context;
    private List<Order.OrderBean> list;
    //保存展示商品列表的下标
    public Map<Integer, Integer> maps = new HashMap<>();
    //保存展示退款原因布局的下标
    public Map<Integer, Integer> refundMap = new HashMap<>();
    //保存展示不同意输入框的下标
    public Map<Integer, Integer> noCanMap = new HashMap<>();
    //保存展示收货的快递单号输入框下标
    public Map<Integer,Integer>  shMap=new HashMap<>();
    public RefundOrderAdapter(Context context,List<Order.OrderBean> list) {
        super();
        this.context = context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list==null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    ViewHolder holder = null;
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_refund_order, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Order.OrderBean orderBean=list.get(position);
        holder.tvNumber.setText(String.valueOf(position+1));
        //显示当前订单状态
        OrderStatus.showStatus(holder.tvStatus,orderBean.getStatus());
        //显示当前订单说明
        OrderStatus.showShuoMing(holder.tvShuoMing,holder.tvShuoMing2,holder.tvRefundDes,orderBean);
        holder.tvUserName.setText(orderBean.getReceivename());
        holder.tvMobile.setText(orderBean.getPhone());
        holder.tvAddress.setText(orderBean.getAddress());
        holder.tvCount.setText("商品("+orderBean.getSkucount()+")");
        //显示商品列表
        GoodsAdapter goodsAdapter = new GoodsAdapter(context,orderBean.getSkulist(),orderBean.getReturnlist());
        holder.listGoods.setAdapter(goodsAdapter);
        holder.tvXjMoney.setText("¥" +Util.setDouble(orderBean.getPrice(),2));
        holder.tvYhMoney.setText("¥" +Util.setDouble(orderBean.getDiscount(),2));
        holder.tvPsMoney.setText("¥" +Util.setDouble(orderBean.getFreight(),2));
        holder.tvActualMoney.setText("¥" +Util.setDouble(orderBean.getActualpay(),2));
        holder.tvGetMoney.setText("¥"+ Util.setDouble(orderBean.getSupplierMoney(),2));
        holder.tvRefundTime.setText(orderBean.getShtime());
        holder.tvOrderTime.setText("下单时间："+orderBean.getAddorderdate());
        holder.tvOrderCode.setText("订单编号："+orderBean.getOrdernum());

        if(orderBean.getStatus()==19){
            holder.tvGetGood.setText("发货");
        }else{
            holder.tvGetGood.setText("同意");
        }

        //订单状态
        final int status=orderBean.getStatus();
        if(status<11){
            holder.rel1.setVisibility(View.VISIBLE);
            holder.rel2.setVisibility(View.GONE);
        }else{
            holder.rel1.setVisibility(View.GONE);
            holder.rel2.setVisibility(View.VISIBLE);
        }


        //展开或收起商品列表
        if (maps.get(position) != null) {
            holder.linGoods.setVisibility(View.VISIBLE);
            holder.tvGoodPlay.setText("收起");
            holder.imgGoodPlay.setImageDrawable(context.getResources().getDrawable(R.mipmap.up_next));
        } else {
            holder.linGoods.setVisibility(View.GONE);
            holder.tvGoodPlay.setText("展开");
            holder.imgGoodPlay.setImageDrawable(context.getResources().getDrawable(R.mipmap.down_next));
        }


        //展开或收起退款原因
        if (refundMap.get(position) != null) {
            holder.relRefund.setVisibility(View.VISIBLE);
            holder.tvRefundPlay.setText("收起");
            holder.imgRefundPlay.setImageDrawable(context.getResources().getDrawable(R.mipmap.up_next));
        } else {
            holder.relRefund.setVisibility(View.GONE);
            holder.tvRefundPlay.setText("展开");
            holder.imgRefundPlay.setImageDrawable(context.getResources().getDrawable(R.mipmap.down_next));
        }


        //点击展示或收起商品列表
        holder.linGoodPlay.setTag(position);
        holder.linGoodPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int position = (int) v.getTag();
                if (null == maps.get(position)) {
                    maps.put(position, position);
                } else {
                    maps.remove(position);
                }
                notifyDataSetChanged();
            }
        });

        //点击展示或收起退款原因
        holder.linRefundPlay.setTag(position);
        holder.linRefundPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int position = (int) v.getTag();
                if (null == refundMap.get(position)) {
                    refundMap.put(position, position);
                } else {
                    refundMap.remove(position);
                }
                notifyDataSetChanged();
            }
        });


        //展开不同意编辑框布局
        if (noCanMap.get(position) != null) {
            holder.etNoCan.setVisibility(View.VISIBLE);
            holder.tvNoCan.setVisibility(View.GONE);
            holder.tvCan.setText("发送");
        } else {
            holder.etNoCan.setVisibility(View.GONE);
            holder.tvNoCan.setVisibility(View.VISIBLE);
            holder.tvCan.setText("同意");
        }


        //展开收货编辑框布局
        if(shMap.get(orderBean.getOrderid())!=null){
            holder.etCode.setVisibility(View.VISIBLE);
            holder.tvGetGood.setText("发送");
        }else{
            holder.etCode.setVisibility(View.GONE);
            if(orderBean.getStatus()==19){
                holder.tvGetGood.setText("发货");
            }else{
                holder.tvGetGood.setText("同意");
            }
        }

        //点击不同意
        holder.tvNoCan.setTag(position);
        holder.tvNoCan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int position = (int) v.getTag();
                noCanMap.put(position, position);
                notifyDataSetChanged();
            }
        });

        //同意或发送不同意原因
        holder.tvCan.setTag(orderBean);
        holder.tvCan.setTag(R.id.tag1,holder.etNoCan);
        holder.tvCan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null || v.getTag(R.id.tag1)==null){
                    return;
                }
                Order.OrderBean orderBean= (Order.OrderBean) v.getTag();
                final int orderId=orderBean.getOrderid();
                final int status=orderBean.getStatus();
                final String msg=((ClickTextView)v).getText().toString().trim();
                if(msg.equals("同意")){
                    if(status==5 || status==8){
                        EventBus.getDefault().post(new EventBusType(EventStatus.RETURN_ORDER,orderId));
                    }
                    if(status==11){
                        EventBus.getDefault().post(new EventBusType(EventStatus.HUANHUO_ORDER,orderId));
                    }
                    return;
                }

                if(msg.equals("发送")){
                    EditText etNoCan= (EditText) v.getTag(R.id.tag1);
                    String reason=etNoCan.getText().toString().trim();
                    if(TextUtils.isEmpty(reason)){
                        ToastUtil.showLong("请输入不同意原因");
                        return;
                    }
                    if(status==5 || status==8){
                        EventBus.getDefault().post(new EventBusType(EventStatus.NO_RETURN_ORDER,orderId,reason));
                    }
                    if(status==11){
                        EventBus.getDefault().post(new EventBusType(EventStatus.NO_HUANHUO_ORDER,orderId,reason));
                    }
                }
            }
        });


        /**
         * 点击同意收货
         */
        holder.tvGetGood.setTag(orderBean);
        holder.tvGetGood.setTag(R.id.tag1,holder.etCode);
        holder.tvGetGood.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null || v.getTag(R.id.tag1)==null){
                    return;
                }
                Order.OrderBean orderBean= (Order.OrderBean) v.getTag();
                final int orderId=orderBean.getOrderid();
                final int status=orderBean.getStatus();
                final String msg=((ClickTextView)v).getText().toString().trim();
                if(status==17 && msg.equals("同意")){
                    EventBus.getDefault().post(new EventBusType(EventStatus.ORDER_STATUS_17,orderId));
                    return;
                }

                if(status==19 && msg.equals("发货")){
                    shMap.put(orderId,orderId);
                    notifyDataSetChanged();
                    return;
                }

                if(status==19 && msg.equals("发送")){
                    EditText etCode= (EditText) v.getTag(R.id.tag1);
                    String code=etCode.getText().toString();
                    if(TextUtils.isEmpty(code)){
                        ToastUtil.showLong("请输入快递单号(建议复制粘贴)");
                        return;
                    }
                    EventBus.getDefault().post(new EventBusType(EventStatus.ORDER_STATUS_19,orderId,code));
                }
            }
        });

        //复制订单号
        holder.tvCopy.setTag(orderBean.getOrdernum());
        holder.tvCopy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                Util.copyTxt(v.getTag().toString());
            }
        });

        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        @BindView(R.id.tv_mobile)
        TextView tvMobile;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.tv_good_play)
        TextView tvGoodPlay;
        @BindView(R.id.img_good_play)
        ImageView imgGoodPlay;
        @BindView(R.id.lin_good_play)
        LinearLayout linGoodPlay;
        @BindView(R.id.list_goods)
        MeasureListView listGoods;
        @BindView(R.id.tv_xj_money)
        TextView tvXjMoney;
        @BindView(R.id.tv_actual_money)
        TextView tvActualMoney;
        @BindView(R.id.tv_get_money)
        TextView tvGetMoney;
        @BindView(R.id.lin_goods)
        RelativeLayout linGoods;
        @BindView(R.id.tv_refund_play)
        TextView tvRefundPlay;
        @BindView(R.id.img_refund_play)
        ImageView imgRefundPlay;
        @BindView(R.id.lin_refund_play)
        LinearLayout linRefundPlay;
        @BindView(R.id.tv_refund_time)
        TextView tvRefundTime;
        @BindView(R.id.tv_refund_des)
        TextView tvRefundDes;
        @BindView(R.id.rel_refund)
        RelativeLayout relRefund;
        @BindView(R.id.et_no_can)
        EditText etNoCan;
        @BindView(R.id.tv_no_can)
        ClickTextView tvNoCan;
        @BindView(R.id.tv_can)
        ClickTextView tvCan;
        @BindView(R.id.tv_order_time)
        TextView tvOrderTime;
        @BindView(R.id.tv_order_code)
        TextView tvOrderCode;
        @BindView(R.id.tv_copy)
        TextView tvCopy;
        @BindView(R.id.et_code)
        EditText etCode;
        @BindView(R.id.tv_get_good)
        TextView tvGetGood;
        @BindView(R.id.rel_1)
        RelativeLayout rel1;
        @BindView(R.id.rel_2)
        RelativeLayout rel2;
        @BindView(R.id.tv_yh_money)
        TextView tvYhMoney;
        @BindView(R.id.tv_ps_money)
        TextView tvPsMoney;
        @BindView(R.id.tv_shuoming)
        TextView tvShuoMing;
        @BindView(R.id.tv_shuoming2)
        TextView tvShuoMing2;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
