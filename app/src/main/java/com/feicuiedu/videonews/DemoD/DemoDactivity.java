package com.feicuiedu.videonews.DemoD;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.feicuiedu.videonews.R;
import com.feicuiedu.videonews.VideoUrlRes;

import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class DemoDactivity extends AppCompatActivity {
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_d);

        videoView = (VideoView)findViewById(R.id.mined_vv);

        videoView.setVideoPath(VideoUrlRes.getTestVideo1());

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);

        videoView.start();
    }
}
