package com.ylean.mmspd.adapter.stroe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ylean.mmspd.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用户评价中的商品adapter
 */
public class CommentGoodAdapter extends BaseAdapter {

    private Context context;

    public CommentGoodAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_comment_goods, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_good)
        TextView tvGood;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
