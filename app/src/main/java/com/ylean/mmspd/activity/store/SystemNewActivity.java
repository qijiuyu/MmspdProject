package com.ylean.mmspd.activity.store;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import com.ylean.mmspd.R;
import com.ylean.mmspd.adapter.stroe.SystemNewAdapter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.News;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.MyRefreshLayout;
import com.zxdc.utils.library.view.MyRefreshLayoutListener;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 系统消息
 * Created by Administrator on 2019/8/24.
 */

public class SystemNewActivity extends BaseActivity implements MyRefreshLayoutListener {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.re_list)
    MyRefreshLayout reList;
    private SystemNewAdapter systemNewAdapter;
    //页数
    private int page=1;
    private List<News.NewsBean> listAll=new ArrayList<>();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_new);
        ButterKnife.bind(this);
        initView();
        //获取系统消息
        DialogUtil.showProgress(this,"数据加载中...");
        getNews(HandlerConstant.GET_NEWS_SUCCESS1);
    }


    /**
     * 初始化
     */
    private void initView(){
        //刷新加载
        reList.setMyRefreshLayoutListener(this);
        systemNewAdapter=new SystemNewAdapter(this,listAll);
        listView.setAdapter(systemNewAdapter);
        listView.setDivider(null);
    }

    @OnClick(R.id.lin_back)
    public void onViewClicked() {
        finish();
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                //下刷
                case HandlerConstant.GET_NEWS_SUCCESS1:
                    reList.refreshComplete();
                    listAll.clear();
                    refresh((News) msg.obj);
                    break;
                //上拉
                case HandlerConstant.GET_NEWS_SUCCESS2:
                    reList.loadMoreComplete();
                    refresh((News) msg.obj);
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
    private void refresh(News news){
        if(news==null){
            return;
        }
        if(news.isSussess()){
            List<News.NewsBean> list=news.getData();
            listAll.addAll(list);
            systemNewAdapter.notifyDataSetChanged();
            if(list.size()<20){
                reList.setIsLoadingMoreEnabled(false);
            }
        }else{
            ToastUtil.showLong(news.getDesc());
        }
    }


    /**
     * 下刷
     * @param view
     */
    public void onRefresh(View view) {
        page=1;
        getNews(HandlerConstant.GET_NEWS_SUCCESS1);
    }

    /**
     * 上拉加载更多
     * @param view
     */
    public void onLoadMore(View view) {
        page++;
        getNews(HandlerConstant.GET_NEWS_SUCCESS2);
    }


    /**
     * 获取系统消息
     */
    private void getNews(int index){
        HttpMethod.getNews(String.valueOf(page),index,handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
