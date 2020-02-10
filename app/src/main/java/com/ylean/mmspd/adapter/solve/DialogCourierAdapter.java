package com.ylean.mmspd.adapter.solve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ylean.mmspd.R;
import com.zxdc.utils.library.bean.Courier;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogCourierAdapter extends BaseAdapter {

    private Context context;
    //物流公司集合
    private List<Courier.CourierBean> courList;
    //选中的id
    public int index = -1;
    public DialogCourierAdapter(Context context, List<Courier.CourierBean> courList) {
        super();
        this.context = context;
        this.courList = courList;
    }

    @Override
    public int getCount() {
        return courList == null ? 0 : courList.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.item_dialog_courier, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Courier.CourierBean courierBean=courList.get(position);
        holder.tvName.setText(courierBean.getName());
        if(index==position){
            holder.imgOk.setVisibility(View.VISIBLE);
        }else{
            holder.imgOk.setVisibility(View.GONE);
        }
        holder.tvName.setTag(position);
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                index= (int) v.getTag();
                notifyDataSetChanged();
            }
        });
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.img_ok)
        ImageView imgOk;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
