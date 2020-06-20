package com.ylean.mmspd.adapter.stroe;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.ylean.mmspd.R;
import com.ylean.mmspd.activity.store.ReportActivity;
import com.ylean.mmspd.fragment.store.CommGoodFragment;
import com.zxdc.utils.library.bean.Comment;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.MyGridView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用户评价adapter
 */
public class CommentsAdapter extends BaseAdapter {

    private Activity activity;
    private List<Comment.CommentBean> list;
    private List<ImageView> imgList=new ArrayList<>();
    //图片adapter
    private CommentImgAdapter commentImgAdapter;
    private CommGoodFragment.OnItemClickListener onItemClickListener;
    public CommentsAdapter(Activity activity, List<Comment.CommentBean> list, CommGoodFragment.OnItemClickListener onItemClickListener) {
        super();
        this.activity = activity;
        this.list = list;
        this.onItemClickListener=onItemClickListener;
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
            view = LayoutInflater.from(activity).inflate(R.layout.item_comment, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Comment.CommentBean commentBean = list.get(position);
        holder.tvName.setText(commentBean.getUsername());
        holder.tvTime.setText(commentBean.getCreatetime());
        //设置星级
        setXing(commentBean.getStar());
        holder.tvContent.setText(commentBean.getContent());
        //显示评论图片
        commentImgAdapter = new CommentImgAdapter(activity,commentBean.getImgs(),commentBean.getVideo());
        holder.gvIcon.setAdapter(commentImgAdapter);
        holder.tvGood.setText(commentBean.getSpuname());
        holder.tvCount.setText("x"+commentBean.getSpucount());


        //举报
        holder.tvReport.setTag(commentBean.getCommentid());
        holder.tvReport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                Intent intent = new Intent(activity, ReportActivity.class);
                intent.putExtra("commentId",v.getTag().toString());
                activity.startActivity(intent);
            }
        });

        //回复
        holder.tvReply.setTag(commentBean.getCommentid());
        holder.tvReply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                final int commentId= (int) v.getTag();
                View view = LayoutInflater.from(activity).inflate(R.layout.dialog_reply, null);
                final EditText etContent = view.findViewById(R.id.et_content);
                final PopupWindow popupWindow = DialogUtil.showPopWindow(view);
                popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                //弹出软键盘
                showInput(etContent);
                //发送
                view.findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String content = etContent.getText().toString().trim();
                        if (TextUtils.isEmpty(content)) {
                            ToastUtil.showLong("请输入回复的内容");
                            return;
                        }
                        onItemClickListener.onItemClick(commentId+","+content);
                        popupWindow.dismiss();
                    }
                });
                view.findViewById(R.id.rel_dialog).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        ;
                    }
                });
            }
        });
        return view;
    }


    /**
     * 设置星级
     */
    private void setXing(int index){
        imgList.clear();
        imgList.add(holder.imgXing1);
        imgList.add(holder.imgXing2);
        imgList.add(holder.imgXing3);
        imgList.add(holder.imgXing4);
        imgList.add(holder.imgXing5);
        for (int i=0;i<imgList.size();i++){
            if(i<index){
                imgList.get(i).setImageDrawable(activity.getResources().getDrawable(R.mipmap.xing_yes));
            }else{
                imgList.get(i).setImageDrawable(activity.getResources().getDrawable(R.mipmap.xing_no));
            }
        }
    }


    /**
     * 弹出软键盘
     *
     * @param editText
     */
    private void showInput(final EditText editText) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, 0);
            }
        },200);
    }


    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.img_xing1)
        ImageView imgXing1;
        @BindView(R.id.img_xing2)
        ImageView imgXing2;
        @BindView(R.id.img_xing3)
        ImageView imgXing3;
        @BindView(R.id.img_xing4)
        ImageView imgXing4;
        @BindView(R.id.img_xing5)
        ImageView imgXing5;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.gv_icon)
        MyGridView gvIcon;
        @BindView(R.id.tv_good)
        TextView tvGood;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.tv_report)
        TextView tvReport;
        @BindView(R.id.tv_reply)
        TextView tvReply;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
