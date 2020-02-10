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
import com.ylean.mmspd.application.MyApplication;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.Courier;
import com.zxdc.utils.library.bean.Login;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.StatusBarUtils;
import com.zxdc.utils.library.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录界面
 * 15833688881
 * 123456
 * Created by Administrator on 2019/8/23.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_forget)
    TextView etForget;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!TextUtils.isEmpty(SPUtil.getInstance(this).getString(SPUtil.TOKEN))){
            setClass(TagActivity.class);
            return;
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        StatusBarUtils.setStatusBarColor(this,R.color.color_ff6d32);
    }

    /**
     * 按钮点击事件
     * @param view
     */
    @OnClick({R.id.lin_back, R.id.et_forget, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                 finish();
                break;
            //忘记密码
            case R.id.et_forget:
                 setClass(ForgetPwdActivity.class);
                break;
            //登录
            case R.id.tv_login:
                 String mobile=etMobile.getText().toString().trim();
                 String pwd=etPwd.getText().toString().trim();
                 if(TextUtils.isEmpty(mobile)){
                     ToastUtil.showLong("请输入手机号!");
                     return;
                 }
                if(mobile.length()<11){
                    ToastUtil.showLong("请输入完整的手机号!");
                    return;
                }
                if(TextUtils.isEmpty(pwd)){
                    ToastUtil.showLong("请输入密码!");
                    return;
                }
                 //登录
                 login(mobile,pwd);
                 break;
            default:
                break;
        }
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                //登录回执
                case HandlerConstant.LOGIN_SUCCESS:
                      MyApplication.login= (Login) msg.obj;
                      if(MyApplication.login==null){
                          break;
                      }
                      if(MyApplication.login.isSussess()){
                          SPUtil.getInstance(LoginActivity.this).addString(SPUtil.TOKEN,MyApplication.login.getData().getToken());
                          SPUtil.getInstance(LoginActivity.this).addInt(SPUtil.USERID,MyApplication.login.getData().getId());
                          //获取物流公司
                          getCourier();
                      }
                      ToastUtil.showLong(MyApplication.login.getDesc());
                      break;
                case HandlerConstant.GET_COURIER_SUCCESS:
                     Courier courier= (Courier) msg.obj;
                     if(courier==null){
                         break;
                     }
                     if(courier.isSussess()){
                         SPUtil.getInstance(getApplicationContext()).addObject(SPUtil.COURIER,courier);
                         setClass(TagActivity.class);
                         finish();
                     }
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
     * 登录
     */
    private void login(String mobile,String pwd){
        DialogUtil.showProgress(this,"登录中...");
        HttpMethod.login(mobile,pwd,handler);
    }


    /**
     * 获取物流公司
     */
    private void getCourier(){
        DialogUtil.showProgress(this,"数据加载中...");
        HttpMethod.getCourier(handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
