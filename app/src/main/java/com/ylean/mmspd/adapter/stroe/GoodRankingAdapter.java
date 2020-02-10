package com.ylean.mmspd.adapter.stroe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ylean.mmspd.R;
import com.zxdc.utils.library.bean.GoodRanking;
import com.zxdc.utils.library.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品排名
 */
public class GoodRankingAdapter extends BaseAdapter {

    private Context context;
    private List<GoodRanking.GoodRankingBean> list;
    public GoodRankingAdapter(Context context,List<GoodRanking.GoodRankingBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_good_ranking, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        switch (position){
            case 0:
                 holder.imgNumber.setVisibility(View.VISIBLE);
                 holder.tvNumber.setVisibility(View.GONE);
                 holder.imgNumber.setImageResource(R.mipmap.one);
                 break;
            case 1:
                holder.imgNumber.setVisibility(View.VISIBLE);
                holder.tvNumber.setVisibility(View.GONE);
                holder.imgNumber.setImageResource(R.mipmap.two);
                 break;
            case 2:
                holder.imgNumber.setVisibility(View.VISIBLE);
                holder.tvNumber.setVisibility(View.GONE);
                holder.imgNumber.setImageResource(R.mipmap.three);
                break;
            default:
                holder.imgNumber.setVisibility(View.GONE);
                holder.tvNumber.setVisibility(View.VISIBLE);
                holder.tvNumber.setText(String.valueOf(position+1));
                break;
        }
        GoodRanking.GoodRankingBean goodRankingBean=list.get(position);
        holder.tvName.setText(goodRankingBean.getSpuname());
        holder.tvDes.setText("总销售额（¥"+ Util.setDouble(goodRankingBean.getSaleprice(),2)+"）    销量（"+goodRankingBean.getSalecount()+"）");
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.img_number)
        ImageView imgNumber;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_des)
        TextView tvDes;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
