package com.ylean.mmspd.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.ylean.mmspd.R;
import com.zxdc.utils.library.util.FileUtils;
import java.io.File;

/**
 * 本地相册单选图片
 */
public class SelectPhoto {

    //相册
    public static final int CODE_GALLERY_REQUEST = 0xa3;
    //拍照
    public static final int CODE_CAMERA_REQUEST = 0xa4;
    //裁剪
    public static final int CODE_RESULT_REQUEST = 0xa5;
    public static final String pai = FileUtils.getSdcardPath() + "pictures.jpg";
    //裁剪后的图片路径
    public static String crop;

    /**
     * 选择照片
     */
    public static  void selectPhoto(final Activity activity){
        crop = FileUtils.getSdcardPath()+System.currentTimeMillis()+".jpg";
        View view_choise = LayoutInflater.from(activity).inflate(R.layout.select_photo_dialog, null);
        final Dialog dialog  = new Dialog(activity, R.style.ActionSheetDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle(null);
        dialog.setCancelable(true);
        dialog.setContentView(view_choise);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.mystyle);  //添加动画
        dialog.show();
        //拍照
        view_choise.findViewById(R.id.textview_choseimg_camera).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(pai)));
                activity.startActivityForResult(intent, CODE_CAMERA_REQUEST);
            }
        });
        //本地相册选择
        view_choise.findViewById(R.id.textview_choseimg_gallery).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK);
                // 设置文件类型
                intent.setType("image/*");
                activity.startActivityForResult(intent, CODE_GALLERY_REQUEST);
            }
        });
    }


    /**
     * 裁剪原始的图片
     */
    public static void cropRawPhoto(Uri uri,Activity activity) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("output", Uri.fromFile(new File(crop)));
        // 设置裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", false);
        activity.startActivityForResult(intent, CODE_RESULT_REQUEST);
    }
}
