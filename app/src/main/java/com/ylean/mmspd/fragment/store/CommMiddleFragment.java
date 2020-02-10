package com.ylean.mmspd.fragment.store;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ylean.mmspd.R;
import com.ylean.mmspd.activity.store.CommentsActivity;
import com.ylean.mmspd.adapter.stroe.CommentsAdapter;
import com.zxdc.utils.library.base.BaseFragment;
import com.zxdc.utils.library.bean.Comment;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.MyRefreshLayout;
import com.zxdc.utils.library.view.MyRefreshLayoutListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 中评fragment
 * Created by Administrator on 2019/8/23.
 */

public class CommMiddleFragment extends BaseFragment implements MyRefreshLayoutListener {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.re_list)
    MyRefreshLayout reList;
    Unbinder unbinder;
    private CommentsAdapter commentsAdapter;
    //页数
    private int page=1;
    //fragment是否可见
    private boolean isVisibleToUser=false;
    //订单数据集合
    private List<Comment.CommentBean> listAll=new ArrayList<>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    View view=null;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.listview, container, false);
        unbinder = ButterKnife.bind(this, view);
        //刷新加载
        reList.setMyRefreshLayoutListener(this);
//        commentsAdapter=new CommentsAdapter(mActivity,listAll);
        listView.setAdapter(commentsAdapter);
        listView.setDivider(null);
        //获取评论列表
        if(listAll.size()==0){
            getCommentList(HandlerConstant.GET_COMMENT_LIST_SUCCESS1);
        }
        return view;
    }

    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                //下刷
                case HandlerConstant.GET_COMMENT_LIST_SUCCESS1:
                    reList.refreshComplete();
                    listAll.clear();
                    refresh((Comment) msg.obj);
                    break;
                //上拉
                case HandlerConstant.GET_COMMENT_LIST_SUCCESS2:
                    reList.loadMoreComplete();
                    refresh((Comment) msg.obj);
                    break;
                case HandlerConstant.REQUST_ERROR:
                    ToastUtil.showLong(msg.obj==null ? "异常错误信息" : msg.obj.toString());
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    /**
     * 刷新界面数据
     */
    private void refresh(Comment comment){
        if(comment==null){
            return;
        }
        if(comment.isSussess()){
            List<Comment.CommentBean> list=comment.getData();
            listAll.addAll(list);
            commentsAdapter.notifyDataSetChanged();
            if(list.size()<20){
                reList.setIsLoadingMoreEnabled(false);
            }
        }else{
            ToastUtil.showLong(comment.getDesc());
        }
    }


    /**
     * 下刷
     * @param view
     */
    public void onRefresh(View view) {
        page=1;
        getCommentList(HandlerConstant.GET_COMMENT_LIST_SUCCESS1);
    }

    /**
     * 上拉加载更多
     * @param view
     */
    public void onLoadMore(View view) {
        page++;
        getCommentList(HandlerConstant.GET_COMMENT_LIST_SUCCESS2);
    }



    /**
     * 获取评论列表
     */
    private void getCommentList(int index){
        if(isVisibleToUser && view!=null){
            HttpMethod.getCommentList(String.valueOf(page),"2",index,handler);
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
        //获取评论列表
        if(listAll.size()==0){
            getCommentList(HandlerConstant.GET_COMMENT_LIST_SUCCESS1);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
