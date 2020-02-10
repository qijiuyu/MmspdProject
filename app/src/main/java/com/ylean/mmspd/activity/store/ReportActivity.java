package com.ylean.mmspd.activity.store;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ylean.mmspd.R;
import com.ylean.mmspd.adapter.stroe.ReportAdapter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.bean.ReportType;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 举报
 * Created by Administrator on 2019/8/25.
 */

public class ReportActivity extends BaseActivity {

    @BindView(R.id.listView)
    ListView listView;
    private ReportAdapter reportAdapter;
    //举报类型集合
    private List<ReportType.ReportTypeBean> list = new ArrayList<>();
    //举报内容id
    private String commentId;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        initView();
        //获取举报类型
        getReport();
    }

    /**
     * 初始化
     */
    private void initView() {
        commentId=getIntent().getStringExtra("commentId");
        reportAdapter = new ReportAdapter(this, list);
        listView.setAdapter(reportAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                reportAdapter.setIndex(position);
            }
        });
    }


    @OnClick({R.id.lin_back, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                 finish();
                break;
            //举报
            case R.id.tv_confirm:
                report();
                break;
            default:
                break;
        }
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what) {
                //获取举报类型
                case HandlerConstant.GET_REPORT_TYPE_SUCCESS:
                    final ReportType reportType = (ReportType) msg.obj;
                    if (reportType == null) {
                        break;
                    }
                    if (reportType.isSussess()) {
                        list.addAll(reportType.getData());
                        reportAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showLong(reportType.getDesc());
                    }
                    break;
                //举报回执
                case HandlerConstant.REPORT_SUCCESS:
                    final BaseBean baseBean = (BaseBean) msg.obj;
                    if (baseBean == null) {
                        break;
                    }
                    if (baseBean.isSussess()) {
                        finish();
                    }
                    ToastUtil.showLong(baseBean.getDesc());
                    break;
                case HandlerConstant.REQUST_ERROR:
                    ToastUtil.showLong(msg.obj == null ? "异常错误信息" : msg.obj.toString());
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    /**
     * 获取举报类型
     */
    private void getReport() {
        DialogUtil.showProgress(this, "数据加载中...");
        HttpMethod.getReport(handler);
    }


    /**
     * 举报
     */
    public void report() {
        if (list.size() == 0) {
            return;
        }
        ReportType.ReportTypeBean reportTypeBean = list.get(reportAdapter.index);
        DialogUtil.showProgress(this, "举报中...");
        HttpMethod.report(commentId, String.valueOf(reportTypeBean.getId()), handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }

}
