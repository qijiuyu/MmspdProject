package com.ylean.mmspd.application;

import com.zxdc.utils.library.base.BaseApplication;
import com.zxdc.utils.library.bean.Login;
import com.zxdc.utils.library.util.ActivitysLifecycle;
import com.zxdc.utils.library.util.SPUtil;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends BaseApplication {

    public static Login login=null;
    public void onCreate() {
        super.onCreate();
        setContext(this);
        initPush();
        //管理Activity
        registerActivityLifecycleCallbacks(ActivitysLifecycle.getInstance());
    }


    /**
     * 初始化消息推送
     */
    private void initPush(){
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        final int jpush= SPUtil.getInstance(this).getInteger(SPUtil.JPUSH);
        if(jpush==1){
            JPushInterface.stopPush(this);      //停止推送
        }else{
            JPushInterface.resumePush(this);  		// 恢复推送
        }
    }
}
