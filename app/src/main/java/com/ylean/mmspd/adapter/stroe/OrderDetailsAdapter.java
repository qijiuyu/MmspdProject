package com.ylean.mmspd.adapter.stroe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ylean.mmspd.R;
import com.zxdc.utils.library.bean.Order;
import com.zxdc.utils.library.bean.OrderDetails;
import com.zxdc.utils.library.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品列表adapter
 */
public class OrderDetailsAdapter extends BaseAdapter {

    private Context context;
    private List<OrderDetails.SkulistBean> list;
    public OrderDetailsAdapter(Context context, List<OrderDetails.SkulistBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_goods, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final OrderDetails.SkulistBean skulistBean=list.get(position);
        holder.tvName.setText(skulistBean.getSpuname());
        holder.tvNum.setText("x"+skulistBean.getSpucount());
        holder.tvMoney.setText("¥" +Util.setDouble(skulistBean.getSpuprice(),2));
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_money)
        TextView tvMoney;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
