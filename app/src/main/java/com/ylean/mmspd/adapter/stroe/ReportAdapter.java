package com.ylean.mmspd.adapter.stroe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ylean.mmspd.R;
import com.zxdc.utils.library.bean.ReportType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 举报
 */
public class ReportAdapter extends BaseAdapter {

    private Context context;
    private List<ReportType.ReportTypeBean> list;
    //选中的下标
    public int index;
    public ReportAdapter(Context context, List<ReportType.ReportTypeBean> list) {
        super();
        this.context = context;
        this.list = list;
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

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_report, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ReportType.ReportTypeBean reportTypeBean=list.get(position);
        holder.tvName.setText(reportTypeBean.getName());
        if(position==index){
            holder.imgStatus.setImageResource(R.mipmap.yes_select);
        }else{
            holder.imgStatus.setImageResource(R.mipmap.no_select);
        }
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.img_status)
        ImageView imgStatus;
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 设置选中的下标
     * @param index
     */
    public void setIndex(int index){
        this.index=index;
        ReportAdapter.this.notifyDataSetChanged();
    }
}
