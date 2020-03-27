package com.ylean.mmspd.activity.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.ylean.mmspd.R;
import com.ylean.mmspd.utils.SelectPhoto;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.Store;
import com.zxdc.utils.library.http.HttpConstant;
import com.zxdc.utils.library.view.OvalImageViews;
import java.io.File;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 店铺信息
 * Created by Administrator on 2019/8/25.
 */

public class StoreInfoActivity extends BaseActivity {

    @BindView(R.id.img_icon)
    OvalImageViews imgIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.rel_status)
    RelativeLayout relStatus;
    //头像文件
    private File imgFile;
    //店铺对象
    private Store store;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info);
        ButterKnife.bind(this);
        initView();
    }


    /**
     * 初始化
     */
    private void initView(){
        store= (Store) getIntent().getSerializableExtra("store");
        if(store==null){
            return;
        }
        Glide.with(this).load(HttpConstant.IP+store.getData().getImgurl()).error(R.mipmap.default_head).override(59,59).centerCrop().into(imgIcon);
        tvName.setText(store.getData().getShopowner());
        tvMobile.setText(store.getData().getMobile());
        tvAddress.setText(store.getData().getAddress());
        if(store.getData().getStatus()==2){
            tvStatus.setText("营业中");
        }else{
            tvStatus.setText("暂停营业");
        }
    }


    /**
     * 按钮点击事件
     * @param view
     */
    @OnClick({R.id.lin_back, R.id.img_icon, R.id.rel_status})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            case R.id.img_icon:
//                SelectPhoto.selectPhoto(this);
                break;
            //更改店铺状态
            case R.id.rel_status:
                Intent intent=new Intent(this,StoreStatusActivity.class);
                intent.putExtra("status",store.getData().getStatus());
                startActivityForResult(intent,100);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //返回拍照图片
            case SelectPhoto.CODE_CAMERA_REQUEST:
                if (resultCode == RESULT_OK) {
                    File tempFile = new File(SelectPhoto.pai);
                    if (tempFile.isFile()) {
                        SelectPhoto.cropRawPhoto(Uri.fromFile(tempFile),this);
                    }
                }
                break;
            //返回相册选择图片
            case SelectPhoto.CODE_GALLERY_REQUEST:
                if (data != null) {
                    SelectPhoto.cropRawPhoto(data.getData(),this);
                }
                break;
            //返回裁剪的图片
            case SelectPhoto.CODE_RESULT_REQUEST:
                imgFile= new File(SelectPhoto.crop);
                Glide.with(StoreInfoActivity.this).load(Uri.fromFile(imgFile)).into(imgIcon);
                break;
            //营业状态回执
            case 100:
                 if(data==null){
                     return;
                 }
                 final int status=data.getIntExtra("status",0);
                 store.getData().setStatus(status);
                 if(status==2){
                     tvStatus.setText("营业中");
                 }else{
                    tvStatus.setText("暂停营业");
                 }
                 break;
            default:
                break;

        }
    }
}
