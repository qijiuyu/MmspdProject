package com.ylean.mmspd.persenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.CustomListener;
import com.ylean.mmspd.R;
import com.ylean.mmspd.eventbus.EventBusType;
import com.ylean.mmspd.eventbus.EventStatus;
import com.zxdc.utils.library.bean.Accound;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DateUtils;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.time.TimePickerView;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;

public class AccoundDetailsPersenter {

    private Activity activity;
    private TimePickerView timePickerView;

    public AccoundDetailsPersenter(Activity activity){
        this.activity=activity;
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                case HandlerConstant.GET_ACCOUND_SUCCESS:
                      final Accound accound= (Accound) msg.obj;
                      if(accound==null){
                          break;
                      }
                      if(accound.isSussess()){
                          EventBus.getDefault().post(new EventBusType(EventStatus.GET_ACCOUND,accound.getData()));
                      }
                      ToastUtil.showLong(accound.getDesc());
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
     * 初始化时间选择
     */
    public void initCustomTimePicker(final TextView tvTime) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2000, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(3000, 12, 31);
        //时间选择器 ，自定义布局
        timePickerView = new TimePickerView.Builder(activity, new TimePickerView.OnTimeSelectListener() {
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvTime.setText(DateUtils.getDay(date.getTime()));
            }
        }).setDate(selectedDate).setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_sign_time, new CustomListener() {
                    public void customLayout(View v) {
                        final TextView tvSubmit =v.findViewById(R.id.tv_finish);
                        TextView ivCancel = v.findViewById(R.id.tv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                timePickerView.returnData();
                                timePickerView.dismiss();
                                //获取账单明细
                                getAccound(tvTime.getText().toString());
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                timePickerView.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(activity.getResources().getColor(R.color.color_dbdbdb))
                .setTextColorCenter(activity.getResources().getColor(R.color.color_333333))
                .gravity(Gravity.CENTER)
                .build();
    }

    /**
     * 显示时间弹框
     * @param tvTime
     */
    public void showTime(TextView tvTime){
        timePickerView.show(tvTime);
    }



    /**
     * 获取账单明细
     */
    public void getAccound(String time){
        DialogUtil.showProgress(activity,"数据加载中...");
        HttpMethod.getAccound(time,handler);
    }

}
