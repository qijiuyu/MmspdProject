package com.ylean.mmspd.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.ylean.mmspd.R;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.StatusBarUtils;
import com.zxdc.utils.library.util.ToastUtil;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 忘记密码
 * Created by Administrator on 2019/8/23.
 */

public class ForgetPwdActivity extends BaseActivity {

    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_sendCode)
    TextView tvSendCode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    //计数器
    private Timer mTimer;
    private int time = 0;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        ButterKnife.bind(this);
        //判断验证码秒数是否超过一分钟
        checkTime();
    }


    /**
     * 点击事件
     * @param view
     */
    @OnClick({R.id.lin_back, R.id.tv_sendCode, R.id.tv_confirm})
    public void onViewClicked(View view) {
        String mobile=etMobile.getText().toString().trim();
        String code=etCode.getText().toString().trim();
        String pwd=etPwd.getText().toString().trim();
        switch (view.getId()) {
            case R.id.lin_back:
                 finish();
                break;
            //发送验证码
            case R.id.tv_sendCode:
                 if(time>0){
                     return;
                 }
                 if(TextUtils.isEmpty(mobile)){
                     ToastUtil.showLong("请输入手机号!");
                     return;
                 }
                 if(mobile.length()<11){
                     ToastUtil.showLong("请输入完整的手机号!");
                     return;
                 }
                 //获取短信验证码
                 getSmsCode(mobile);
                break;
            //提交修改
            case R.id.tv_confirm:
                if(TextUtils.isEmpty(mobile)){
                    ToastUtil.showLong("请输入手机号!");
                    return;
                }
                if(mobile.length()<11){
                    ToastUtil.showLong("请输入完整的手机号!");
                    return;
                }
                if(TextUtils.isEmpty(code)){
                    ToastUtil.showLong("请输入验证码!");
                    return;
                }
                if(TextUtils.isEmpty(pwd)){
                    ToastUtil.showLong("请输入密码!");
                    return;
                }
                //忘记密码
                forgetPwd(code,pwd,mobile);
                break;
            default:
                break;
        }
    }



    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            BaseBean baseBean=null;
            switch (msg.what){
                //短信验证码回执
                case HandlerConstant.GET_SMS_CODE_SUCCESS:
                      baseBean= (BaseBean) msg.obj;
                      if(baseBean==null){
                          break;
                      }
                      if(baseBean.isSussess()){
                          startTime();
                      }
                      ToastUtil.showLong(baseBean.getDesc());
                      break;
                //设置密码回执
                case HandlerConstant.FORGET_PWD_SUCCESS:
                      baseBean= (BaseBean) msg.obj;
                      if(baseBean==null){
                          break;
                      }
                      if(baseBean.isSussess()){
                          finish();
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
     * 获取短信验证码
     */
    private void getSmsCode(String mobile){
        DialogUtil.showProgress(this,"获取验证码中...");
        HttpMethod.getSmsCode(mobile,"3",handler);
    }


    /**
     * 忘记密码
     */
    private void forgetPwd(String code,String newPwd,String mobile){
        DialogUtil.showProgress(this,"密码设置中...");
        HttpMethod.forgetPwd(code,newPwd,mobile,handler);
    }

    /**
     * 动态改变验证码秒数
     */
    private void startTime() {
        time=60;
        //保存计时时间
        SPUtil.getInstance(activity).addString("sms_code_time", String.valueOf((System.currentTimeMillis() + 60000)));
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (time <= 0) {
                    handler.post(new Runnable() {
                        public void run() {
                            mTimer.cancel();
                            tvSendCode.setText("获取验证码");
                            SPUtil.getInstance(activity).removeMessage("sms_code_time");
                        }
                    });
                } else {
                    --time;
                    handler.post(new Runnable() {
                        public void run() {
                            tvSendCode.setText(time + "秒");
                        }
                    });
                }
            }
        }, 0, 1000);
    }


    /**
     * 判断验证码秒数是否超过一分钟
     */
    private void checkTime() {
        String stoptime = SPUtil.getInstance(activity).getString("sms_code_time");
        if (!TextUtils.isEmpty(stoptime)) {
            int t = (int) ((Double.parseDouble(stoptime) - System.currentTimeMillis()) / 1000);
            if (t > 0) {
                time = t;
                startTime();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTimer!=null){
            mTimer.cancel();
            mTimer.purge();
            mTimer=null;
        }
        removeHandler(handler);
    }
}
