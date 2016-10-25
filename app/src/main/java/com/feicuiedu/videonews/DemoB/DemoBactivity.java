package com.feicuiedu.videonews.DemoB;


import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.feicuiedu.videonews.R;
import com.feicuiedu.videonews.VideoUrlRes;

import java.io.IOException;

import io.vov.vitamio.MediaPlayer;

public class DemoBactivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_b);

//        mediaPlayer = new MediaPlayer(DemoBactivity.this);

        surfaceView = (SurfaceView)findViewById(R.id.mindb_sfv);
        surfaceHolder = surfaceView.getHolder();
        //花屏处理!!!
        surfaceHolder.setFormat(PixelFormat.RGBA_8888);

        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    mediaPlayer = new MediaPlayer(DemoBactivity.this);
                    mediaPlayer.setDisplay(surfaceHolder);
                    mediaPlayer.setDataSource(VideoUrlRes.getTestVideo1());
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                        }
                    });

                    mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                        @Override public boolean onInfo(MediaPlayer mp, int what, int extra) {
                            // vitamio5.0，要进行audio处理,才能对在线视频进行播放!!!!
                            if (what == MediaPlayer.MEDIA_INFO_FILE_OPEN_OK) {
                                mediaPlayer.audioInitedOk(mediaPlayer.audioTrackInit());
                                return true;
                            }
                            return false;
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mediaPlayer.stop();
            }
        });
    }
}
