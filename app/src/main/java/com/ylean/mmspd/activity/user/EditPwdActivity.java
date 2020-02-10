package com.ylean.mmspd.activity.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.ylean.mmspd.R;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改登录密码
 * Created by Administrator on 2019/8/25.
 */
public class EditPwdActivity extends BaseActivity {

    @BindView(R.id.et_old_pwd)
    EditText etOldPwd;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.et_confirm_pwd)
    EditText etConfirmPwd;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pwd);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.lin_back, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                 finish();
                break;
            case R.id.tv_confirm:
                String oldPwd=etOldPwd.getText().toString().trim();
                String newPwd=etNewPwd.getText().toString().trim();
                String confirmPwd=etConfirmPwd.getText().toString().trim();
                if(TextUtils.isEmpty(oldPwd)){
                    ToastUtil.showLong("请输入旧密码");
                    return;
                }
                if(TextUtils.isEmpty(newPwd)){
                    ToastUtil.showLong("请输入新密码");
                    return;
                }
                if(TextUtils.isEmpty(confirmPwd)){
                    ToastUtil.showLong("请再次输入密码");
                    return;
                }
                if(!newPwd.equals(confirmPwd)){
                    ToastUtil.showLong("两次输入的密码不一致");
                    return;
                }
                //修改密码
                editPwd(oldPwd,newPwd);
                break;
        }
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                //密码修改回执
                case HandlerConstant.EDIT_PWD_SUCCESS:
                      BaseBean baseBean= (BaseBean) msg.obj;
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
     * 修改密码
     */
    private void editPwd(String oldPwd,String newPwd){
        DialogUtil.showProgress(this,"密码修改中...");
        HttpMethod.editPwd(newPwd,oldPwd,handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
