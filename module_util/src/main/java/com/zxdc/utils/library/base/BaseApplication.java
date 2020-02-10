package com.zxdc.utils.library.base;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.zxdc.utils.library.bean.Login;
import com.zxdc.utils.library.util.SPUtil;

public class BaseApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        BaseApplication.context = context;
    }
}
