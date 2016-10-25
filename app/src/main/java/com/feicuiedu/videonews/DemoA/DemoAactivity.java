package com.feicuiedu.videonews.DemoA;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.feicuiedu.videonews.R;

import java.io.IOException;

public class DemoAactivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demoa);

        //用SurfaceView上面的一个Surface来进行一个绘制处理，怎么才能拿到Surface
        //怎么才能将内容显示到Surface

        //SurfaceHolder = SurfaceView.getHolder
        surfaceView = (SurfaceView) findViewById(R.id.minea_sfv);
        surfaceHolder = surfaceView.getHolder();

        //视频画面的处理
        //        mediaPlayer.setDisplay(surfaceHolder);

        //只是放了一个SurfaceView的控件，但是它内部是通过Surface实现的

        //提供一个callback

        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDisplay(surfaceHolder);
                    mediaPlayer.setDataSource("http://o9ve1mre2.bkt.clouddn.com/raw_%E6%B8%A9%E7%BD%91%E7%94%B7%E5%8D%95%E5%86%B3%E8%B5%9B.mp4");
                    mediaPlayer.prepareAsync();
                    //对是否准备完成作监听
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
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
