package com.feicuiedu.videonews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import videonews.videoplayer.part.SimpleVideoView;

/**
 * @author z_j_l
 * @time 2016/10/26 14:07
 * @note ${TODP}
 */
public class TestActivity extends AppCompatActivity{
    private SimpleVideoView simpleVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_acitivity);

        simpleVideoView = (SimpleVideoView) findViewById(R.id.test_svv);
        simpleVideoView.setVideoPath(VideoUrlRes.getTestVideo1());
    }

    @Override
    protected void onResume() {
        super.onResume();
        simpleVideoView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        simpleVideoView.onPause();
    }
}
