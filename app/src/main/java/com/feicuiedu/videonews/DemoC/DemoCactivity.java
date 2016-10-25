package com.feicuiedu.videonews.DemoC;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import com.feicuiedu.videonews.R;
import com.feicuiedu.videonews.VideoUrlRes;

public class DemoCactivity extends AppCompatActivity {
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_c);

        videoView = (VideoView) findViewById(R.id.minec_vv);
        videoView.setVideoPath(VideoUrlRes.getTestVideo1());

        MediaController mediaController = new MediaController(this);
        //显示上、下一首的按钮
        mediaController.setPrevNextListeners(null,null);

        videoView.setMediaController(mediaController);
        videoView.start();
    }
}
