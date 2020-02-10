package com.ylean.mmspd.adapter.solve;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ylean.mmspd.R;
import com.ylean.mmspd.eventbus.EventBusType;
import com.ylean.mmspd.eventbus.EventStatus;
import com.ylean.mmspd.utils.OrderStatus;
import com.zxdc.utils.library.bean.Courier;
import com.zxdc.utils.library.bean.Order;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.SPUtil;
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
 * 新订单adapter
 */
public class NewOrderAdapter extends BaseAdapter {

    private Activity context;
    private List<Order.OrderBean> list;
    //物流公司集合
    private List<Courier.CourierBean> courList;
    //保存展示商品列表的下标
    public Map<Integer, Integer> maps = new HashMap<>();
    //保存显示编辑快递单号的下标
    public Map<Integer, Integer> fhMap = new HashMap<>();
    public static Map<Integer,String> codeMap=new HashMap<>();
    public static Map<Integer,String> courierMap=new HashMap<>();
    public NewOrderAdapter(Activity context,List<Order.OrderBean> list) {
        super();
        this.context = context;
        this.list=list;
        final Courier courier= (Courier) SPUtil.getInstance(context).getObject(SPUtil.COURIER,Courier.class);
        if(courier!=null){
            courList=courier.getData();
        }
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
            view = LayoutInflater.from(context).inflate(R.layout.item_new_order, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.position=position;
        final Order.OrderBean orderBean=list.get(position);
        holder.tvNumber.setText(String.valueOf(position+1));
        //显示当前订单状态
        OrderStatus.showStatus(holder.tvStatus,orderBean.getStatus());
        holder.tvUserName.setText(orderBean.getReceivename());
        holder.tvMobile.setText(orderBean.getPhone());
        holder.tvAddress.setText(orderBean.getAddress());
        holder.tvCount.setText("商品("+orderBean.getSkucount()+")");
        holder.tvRemark.setText("备注："+orderBean.getOrdernote());

        //显示商品列表
        GoodsAdapter goodsAdapter = new GoodsAdapter(context,orderBean.getSkulist(),orderBean.getReturnlist());
        holder.listGoods.setAdapter(goodsAdapter);
        holder.tvXjMoney.setText("¥" +Util.setDouble(orderBean.getPrice(),2));
        holder.tvYhMoney.setText("¥" +Util.setDouble(orderBean.getDiscount(),2));
        holder.tvPsMoney.setText("¥" +Util.setDouble(orderBean.getFreight(),2));
        holder.tvActualMoney.setText("¥" +Util.setDouble(orderBean.getActualpay(),2));
        holder.tvGetMoney.setText("¥"+ Util.setDouble(orderBean.getSupplierMoney(),2));
        holder.tvOrderTime.setText("下单时间："+orderBean.getAddorderdate());
        holder.tvOrderCode.setText("订单编号："+orderBean.getOrdernum());

        //展开或收起商品列表
        if (maps.get(position) != null) {
            holder.linGoods.setVisibility(View.VISIBLE);
            holder.tvPlay.setText("收起");
            holder.imgPlay.setImageDrawable(context.getResources().getDrawable(R.mipmap.up_next));
        } else {
            holder.linGoods.setVisibility(View.GONE);
            holder.tvPlay.setText("展开");
            holder.imgPlay.setImageDrawable(context.getResources().getDrawable(R.mipmap.down_next));
        }

        //展开编辑快递单号的布局
        if(fhMap.get(orderBean.getOrderid())!=null){
            holder.tvDelivery.setText("确定");
            holder.linCode.setVisibility(View.VISIBLE);
        }else{
            holder.tvDelivery.setText("发货");
            holder.linCode.setVisibility(View.GONE);
        }

        //点击展示或收起商品列表
        holder.linPlay.setTag(position);
        holder.linPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int position = (int) v.getTag();
                if (null == maps.get(position)) {
                    maps.put(position, position);
                } else {
                    maps.remove(position);
                }
                NewOrderAdapter.this.notifyDataSetChanged();
            }
        });


        /**
         * 选择物流公司
         */
        holder.tvCourier.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectCourier((TextView) v);
            }
        });

        //设置物流公司，订单号
        holder.tvCourier.setText(courierMap.get(position));
        holder.etCode.setText(codeMap.get(position));


        //发货
        holder.tvDelivery.setTag(R.id.tag0,orderBean);
        holder.tvDelivery.setTag(R.id.tag1,holder.tvCourier);
        holder.tvDelivery.setTag(R.id.tag2,holder.etCode);
        holder.tvDelivery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag(R.id.tag0)==null || v.getTag(R.id.tag1)==null || v.getTag(R.id.tag2)==null){
                    return;
                }
                final Order.OrderBean orderBean= (Order.OrderBean) v.getTag(R.id.tag0);
                final String name=((ClickTextView)v).getText().toString().trim();
                if(name.equals("发货")){
                    fhMap.put(orderBean.getOrderid(), orderBean.getOrderid());
                    notifyDataSetChanged();
                    return;
                }

                if(name.equals("确定")){
                    //获取物流公司对象
                    TextView tvCourier= (TextView) v.getTag(R.id.tag1);
                    Courier.CourierBean courierBean=null;
                    if(tvCourier.getTag()!=null){
                        courierBean= (Courier.CourierBean) tvCourier.getTag();
                    }
                    if(TextUtils.isEmpty(tvCourier.getText().toString())){
                        ToastUtil.showLong("请选择物流公司");
                        return;
                    }
                    EditText etCode= (EditText) v.getTag(R.id.tag2);
                    String code=etCode.getText().toString().trim();
                    if(TextUtils.isEmpty(code)){
                        ToastUtil.showLong("请输入快递单号");
                        return;
                    }
                    EventBus.getDefault().post(new EventBusType(EventStatus.ORDER_STATUS_1,courierBean,orderBean.getOrderid()+","+code));
                    //隐藏键盘
                    lockKey(etCode);
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


    /**
     * 隐藏键盘
     */
    public void lockKey(EditText et) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }


    /**
     * 选择快递公司
     * @param textView
     */
    private void selectCourier(final TextView textView){
        if(courList==null || courList.size()==0){
            return;
        }
        View view=LayoutInflater.from(context).inflate(R.layout.dialog_select_courier,null);
        ListView listView=view.findViewById(R.id.listView);
        final DialogCourierAdapter dialogCourierAdapter=new DialogCourierAdapter(context,courList);
        listView.setAdapter(dialogCourierAdapter);
        final PopupWindow mPopuwindow=DialogUtil.showPopWindow(view);
        mPopuwindow.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0,0);
        view.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final int index=dialogCourierAdapter.index;
                if(index==-1){
                    ToastUtil.showLong("请选择物流公司");
                    return;
                }
                Courier.CourierBean courierBean=courList.get(index);
                textView.setText(courierBean.getName());
                textView.setTag(courierBean);
                mPopuwindow.dismiss();
            }
        });

    }


    static class ViewHolder {
        int position;
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
        @BindView(R.id.tv_play)
        TextView tvPlay;
        @BindView(R.id.img_play)
        ImageView imgPlay;
        @BindView(R.id.lin_play)
        LinearLayout linPlay;
        @BindView(R.id.tv_remark)
        TextView tvRemark;
        @BindView(R.id.list_goods)
        MeasureListView listGoods;
        @BindView(R.id.tv_xj_money)
        TextView tvXjMoney;
        @BindView(R.id.tv_yh_money)
        TextView tvYhMoney;
        @BindView(R.id.tv_ps_money)
        TextView tvPsMoney;
        @BindView(R.id.tv_actual_money)
        TextView tvActualMoney;
        @BindView(R.id.tv_get_money)
        TextView tvGetMoney;
        @BindView(R.id.lin_goods)
        RelativeLayout linGoods;
        @BindView(R.id.et_code)
        EditText etCode;
        @BindView(R.id.lin_code)
        LinearLayout linCode;
        @BindView(R.id.tv_delivery)
        ClickTextView tvDelivery;
        @BindView(R.id.tv_order_time)
        TextView tvOrderTime;
        @BindView(R.id.tv_order_code)
        TextView tvOrderCode;
        @BindView(R.id.tv_copy)
        TextView tvCopy;
        @BindView(R.id.tv_courier)
        TextView tvCourier;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            tvCourier.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                public void afterTextChanged(Editable s) {
                    courierMap.put(position,s.toString());
                }
            });

            etCode.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                public void afterTextChanged(Editable s) {
                    codeMap.put(position,s.toString());
                }
            });
        }
    }
}
