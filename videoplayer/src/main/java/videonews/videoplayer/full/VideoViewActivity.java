package videonews.videoplayer.full;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import videonews.videoplayer.R;

public class VideoViewActivity extends AppCompatActivity {
    private static final String KEY_VIDEO_PATH = "";

    private VideoView videoView;
    private ImageView ivLoading;//缓冲图项
    private TextView tvBufferInfo;//缓冲信息(35%   300kb/s)

    private MediaPlayer mediaPlayer;
    private int bufferPercent;
    private int downloadSpeed;

    //启动当前activity
    public static void open(Context context,String videoPath){
        Intent intent = new Intent(context,VideoViewActivity.class);
        intent.putExtra(KEY_VIDEO_PATH,videoPath);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //设置背景
        getWindow().setBackgroundDrawableResource(android.R.color.black);

        setContentView(R.layout.activity_video_view);

        //缓冲视图、视频视图
        initBufferView();
        initVideoView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //videoPath设置给videoView
        videoView.setVideoPath(getIntent().getStringExtra(KEY_VIDEO_PATH));
    }

    @Override
    protected void onPause() {
        super.onPause();
        //释放videoView
        videoView.stopPlayback();
        //video = mediaPlayer + surfaceview
    }

    //初始化缓冲视图
    private void initBufferView() {
        tvBufferInfo = (TextView)findViewById(R.id.tvBufferInfo);
        ivLoading = (ImageView)findViewById(R.id.ivLoading);
        tvBufferInfo.setVisibility(View.INVISIBLE);
        ivLoading.setVisibility(View.INVISIBLE);
    }

    //初始化视频视图
    private void initVideoView() {
        Vitamio.isInitialized(this);//初始化Vitamio
        videoView = (VideoView)findViewById(R.id.videoView);
        //设置视图控制器
        videoView.setMediaController(new MediaController(this));
        videoView.setKeepScreenOn(true);//视频常亮
        videoView.requestFocus();//获得焦点

        //缓冲监听三步骤
        //第一步，设置缓冲大小
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer = mp;
                mediaPlayer.setBufferSize(512 * 1024);
            }
        });
        //第二步,设置信息监听
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what){
                    //开始缓冲
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        //显示缓冲视图
                        showBufferView();
                        if(videoView.isPlaying()){
                            videoView.pause();
                        }
                        return  true;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END://缓冲结束
                        //隐藏视图
                        hideBufferView();
                        videoView.start();
                        return true;
                    case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED://缓冲是，更新下载速度
                        downloadSpeed =extra;
                        //更新缓冲视图
                        updateBufferView();
                        return true;
                }
                return false;
            }
        });
        //第三步
        videoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                bufferPercent = percent;
                //更新缓冲
                updateBufferView();
            }
        });
    }

    //显示缓冲视图
    private void showBufferView(){
        tvBufferInfo.setVisibility(View.VISIBLE);
        ivLoading.setVisibility(View.VISIBLE);
        downloadSpeed = 0;
        bufferPercent = 0;
    }

    //隐藏缓冲视图
    private void hideBufferView(){
        tvBufferInfo.setVisibility(View.INVISIBLE);
        ivLoading.setVisibility(View.INVISIBLE);
    }

    //更新缓冲视图
    private void updateBufferView(){
        String info = bufferPercent + "%   " + downloadSpeed +"kb/s";
        tvBufferInfo.setText(info);
    }
}
