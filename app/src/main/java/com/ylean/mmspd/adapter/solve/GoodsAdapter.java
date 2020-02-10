package com.ylean.mmspd.adapter.solve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylean.mmspd.R;
import com.zxdc.utils.library.bean.Order;
import com.zxdc.utils.library.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品列表adapter
 */
public class GoodsAdapter extends BaseAdapter {

    private Context context;
    private List<Order.SkulistBean> skuList;
    private List<Order.ReturnListBean> returnList;
    private List<Order.SkulistBean> list = new ArrayList<>();

    public GoodsAdapter(Context context, List<Order.SkulistBean> skuList, List<Order.ReturnListBean> returnList) {
        super();
        this.context = context;
        this.skuList = skuList;
        this.returnList = returnList;
        //解析
        parsingData();
    }


    /**
     * 解析
     */
    private void parsingData() {
        if (returnList == null || returnList.size() == 0) {
            list.addAll(skuList);
            return;
        }
        for (int i = 0; i < skuList.size(); i++) {
            list.add(skuList.get(i));
            for (int k = 0; k < returnList.size(); k++) {
                final Order.ReturnListBean returnListBean = returnList.get(k);
                if (returnListBean.getSkuid() == skuList.get(i).getSkuid()) {
                    //有值表示是“换”
                    if (returnListBean.getChangeskuid() != 0) {
                        Order.SkulistBean skulistBean = new Order.SkulistBean(returnListBean.getChangeskuid(), returnListBean.getSpucount(), returnListBean.getSpuname(), returnListBean.getSpuprice(),returnListBean.getSpecsvalue());
                        list.add(skulistBean);
                    } else {
                        skuList.get(i).setChangeskuid(-1);
                    }
                }
            }
        }
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
        final Order.SkulistBean skulistBean = list.get(position);
        if(skulistBean.getChangeskuid()>0){
            holder.rel1.setVisibility(View.GONE);
            holder.rel2.setVisibility(View.VISIBLE);
            holder.tvName2.setText(skulistBean.getSpuname()+"["+skulistBean.getSpecsvalue()+"]");
            holder.imgStatus2.setImageResource(R.mipmap.huan);
        }else{
            holder.rel1.setVisibility(View.VISIBLE);
            holder.rel2.setVisibility(View.GONE);
            if(skulistBean.getChangeskuid()==0){
                holder.imgStatus.setVisibility(View.GONE);
            }else{
                holder.imgStatus.setVisibility(View.VISIBLE);
                holder.imgStatus.setImageResource(R.mipmap.tui);
            }
            holder.tvName.setText(skulistBean.getSpuname()+"["+skulistBean.getSpecsvalue()+"]");
            holder.tvNum.setText("x" + skulistBean.getSpucount());
            holder.tvMoney.setText("¥" + Util.setDouble(skulistBean.getSpuprice(), 2));
        }
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.rel_1)
        RelativeLayout rel1;
        @BindView(R.id.img_status)
        ImageView imgStatus;
        @BindView(R.id.img_status2)
        ImageView imgStatus2;
        @BindView(R.id.tv_name2)
        TextView tvName2;
        @BindView(R.id.rel_2)
        RelativeLayout rel2;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
