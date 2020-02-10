package com.ylean.mmspd.adapter.stroe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ylean.mmspd.R;
import com.zxdc.utils.library.bean.Accound;
import com.zxdc.utils.library.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 账单明细
 */
public class AccoundDetailsAdapter extends BaseAdapter {

    private Context context;
    private List<Accound.OrderList> list;
    public AccoundDetailsAdapter(Context context,List<Accound.OrderList> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_accound_details, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Accound.OrderList orderList=list.get(position);
        holder.tvOrderCode.setText(orderList.getCode());
        holder.tvMoney.setText("¥"+ Util.setDouble(orderList.getPrice(),2));
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_order_code)
        TextView tvOrderCode;
        @BindView(R.id.tv_money)
        TextView tvMoney;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
