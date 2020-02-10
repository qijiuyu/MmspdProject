package com.ylean.mmspd.adapter.stroe;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.ylean.mmspd.R;
import com.ylean.mmspd.activity.store.PlayVideoActivity;
import com.ylean.mmspd.activity.store.ShowImgActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 用户评价中的图片adapter
 */
public class CommentImgAdapter extends BaseAdapter {

    private Context context;
    private String strImgs;
    //图片链接
    private String[] imgs;
    //视频连接
    private String videoUrl;
    public CommentImgAdapter(Context context,String strImgs,String videoUrl) {
        super();
        this.context = context;
        this.strImgs=strImgs;
        this.videoUrl=videoUrl;
        if(!TextUtils.isEmpty(strImgs)){
            imgs=strImgs.split(",");
        }
    }

    @Override
    public int getCount() {
        return imgs==null ? 0 : imgs.length;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_comment_img, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //显示图片
        String imgUrl=imgs[position];
        holder.imgIcon.setTag(R.id.imageid,imgUrl);
        if(holder.imgIcon.getTag(R.id.imageid)!=null && imgUrl==holder.imgIcon.getTag(R.id.imageid)){
            Glide.with(context).load(imgUrl).override(82,82).centerCrop().into(holder.imgIcon);
        }
        /**
         * 点击图片
         */
        holder.imgIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent();
                if(TextUtils.isEmpty(videoUrl)){
                    intent.setClass(context, ShowImgActivity.class);
                    intent.putExtra("strImgs",strImgs);
                }else{
                    intent.setClass(context, PlayVideoActivity.class);
                    intent.putExtra("videoUrl",videoUrl);
                }
                context.startActivity(intent);
            }
        });
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.img_icon)
        ImageView imgIcon;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
