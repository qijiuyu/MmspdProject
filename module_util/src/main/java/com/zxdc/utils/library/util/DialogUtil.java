package com.zxdc.utils.library.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import com.zxdc.utils.library.R;

import java.lang.reflect.Field;

/**
 * 弹框工具类
 */
public class DialogUtil {

    static ProgressDialog progressDialog = null;

    /**
     * loding弹框
     */
    public static void showProgress(Activity activity, String message) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.setMessage(message);
            return;
        }
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }


    /**
     * 取消进度条
     */
    public static void closeProgress() {
        if (progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }


    /**
     * dialog弹框
     */
    public static Dialog getDialog(Activity activity, View view){
        final Dialog dialog = new Dialog(activity, R.style.ActionSheetDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle(null);
        dialog.setCancelable(true);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);  //此处可以设置dialog显示的位置
//        window.setWindowAnimations(R.style.mystyle);  //添加动画
        dialog.show();
        return dialog;
    }


    /**
     * 屏幕底部弹框
     */
    @SuppressLint("WrongConstant")
    public static PopupWindow showPopWindow(View view){
        PopupWindow mPopuwindow = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        ColorDrawable cd = new ColorDrawable();
        mPopuwindow.setBackgroundDrawable(cd);
        mPopuwindow.setOutsideTouchable(true);
        mPopuwindow.setFocusable(true);
        mPopuwindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopuwindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //mPopuwindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0,0);
        //mPopuwindow.showAsDropDown(view2);
        return mPopuwindow;
    }

}
