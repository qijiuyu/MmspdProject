package com.ylean.mmspd.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylean.mmspd.R;
import com.ylean.mmspd.activity.LoginActivity;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.view.ClickTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 账号设置
 * Created by Administrator on 2019/8/25.
 */

public class AccountActivity extends BaseActivity {


    @BindView(R.id.lin_back)
    LinearLayout linBack;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.rel_edit_pwd)
    RelativeLayout relEditPwd;
    @BindView(R.id.tv_out_login)
    ClickTextView tvOutLogin;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.lin_back, R.id.rel_edit_pwd, R.id.tv_out_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            case R.id.rel_edit_pwd:
                setClass(EditPwdActivity.class);
                break;
            case R.id.tv_out_login:
                SPUtil.getInstance(this).removeAll();
                Intent intent=new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
