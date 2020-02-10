package com.ylean.mmspd.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ylean.mmspd.R;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.ClickTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 营业状态调整
 * Created by Administrator on 2019/8/25.
 */

public class StoreStatusActivity extends BaseActivity {

    @BindView(R.id.img_status)
    ImageView imgStatus;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_update)
    ClickTextView tvUpdate;
    //营业状态
    private int status;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_status);
        ButterKnife.bind(this);
        status=getIntent().getIntExtra("status",0);
        showStatus();
    }


    /**
     * 初始化
     */
    private void showStatus(){
        if(status==2){
            tvStatus.setText("营业中");
            imgStatus.setImageResource(R.mipmap.open_status);
            tvUpdate.setText("停止营业");
        }else{
            tvStatus.setText("暂停营业");
            imgStatus.setImageResource(R.mipmap.close_status);
            tvUpdate.setText("恢复营业");
        }
    }

    @OnClick({R.id.lin_back, R.id.tv_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finishActivity();
                break;
            //修改营业状态
            case R.id.tv_update:
                 if(status==2){
                     editStatus("3");
                 }else{
                     editStatus("2");
                 }
                break;
            default:
                break;
        }
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                case HandlerConstant.EDIT_STORE_STATUS_SUCCESS:
                      final BaseBean baseBean= (BaseBean) msg.obj;
                      if(baseBean==null){
                          break;
                      }
                      if(baseBean.isSussess()){
                          if(status==2){
                              status=3;
                          }else{
                              status=2;
                          }
                          showStatus();
                      }
                      ToastUtil.showLong(baseBean.getDesc());
                      break;
                case HandlerConstant.REQUST_ERROR:
                    ToastUtil.showLong(msg.obj.toString());
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    /**
     * 设置店铺营业状态
     */
    private void editStatus(String status){
        DialogUtil.showProgress(this,"操作中...");
        HttpMethod.editStatus(status,handler);
    }


    private void finishActivity(){
        Intent intent=new Intent();
        intent.putExtra("status",status);
        setResult(100,intent);
        finish();
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
             finishActivity();
             return false;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
