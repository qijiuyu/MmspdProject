package com.ylean.mmspd.activity.store;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.ylean.mmspd.R;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.http.HttpConstant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/11/28.
 */

public class PlayVideoActivity extends BaseActivity {

    @BindView(R.id.videoView)
    VideoView videoView;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        String videoUrl=getIntent().getStringExtra("videoUrl");
        //设置有进度条可以拖动快进
        MediaController localMediaController = new MediaController(this);
        videoView.setMediaController(localMediaController);
        videoView.setVideoPath(HttpConstant.IP+videoUrl);
        videoView.requestFocus();
        videoView.start();

        findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayVideoActivity.this.finish();
            }
        });
    }
}
