package videonews.videoplayer.part;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import videonews.videoplayer.R;

/**
 * @author z_j_l
 * @time 2016/10/25 14:34
 * @note ${TODP}
 */
public class SimpleVideoView extends FrameLayout {
    private static final int PROGRESS_MAX = 1000;//用于进度条长短进度控制

    private String videoPath;//视频播放的URL
    private Boolean isPlaying;//是否正在播放
    private Boolean isPrepared;//是否已经准备好

    private MediaPlayer mediaPlayer;

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private ImageView ivPreview;//预览图
    private ImageButton btnToggle;//暂停/播放
    private ProgressBar progressBar; //进度条

    public SimpleVideoView(Context context) {
        this(context, null);
    }

    public SimpleVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //进度条更新
    private Handler handler = new Handler() {
        /**
         * Subclasses must implement this to receive messages.
         *
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
            if (isPlaying) {
                //每200毫秒更新一次播放进度
                int progress = (int) (mediaPlayer.getCurrentPosition() * PROGRESS_MAX / mediaPlayer.getDuration());
                progressBar.setProgress(progress);
                handler.sendEmptyMessageDelayed(0, 200);
            }
        }
    };

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    //初始化
    private void init() {
        //Vitamio的初始化
        Vitamio.isInitialized(getContext());
        //inflate初始化当前视图内容
        LayoutInflater.from(getContext()).inflate(R.layout.view_simple_video_player, this, true);

        initSurfaceView(); //初始化SurfaceView
        initControllerView(); //初始化视频播放控制视图

    }

    //初始化视频播放控制视图
    private void initControllerView() {
        //预览图
        ivPreview = (ImageView) findViewById(R.id.ivPreview);
        //播放/暂停
        btnToggle = (ImageButton) findViewById(R.id.btnToggle);
        btnToggle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    pauseMediaPlayer();
                } else if (isPrepared) {
                    startMediaPlater();
                } else {
                    Toast.makeText(getContext(), "Can not play now", Toast.LENGTH_LONG).show();
                }
            }
        });
        //设置进度条
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(PROGRESS_MAX);

        //全屏播放按钮
        ImageButton btnFullScreen = (ImageButton) findViewById(R.id.btnFullScreen);
        btnFullScreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //全屏代码实现。。。
                Toast.makeText(getContext(), "Here is no fullsrceen funcation", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //点击开始方法
    private void startMediaPlater() {
        ivPreview.setVisibility(View.INVISIBLE);//预览图隐藏
        btnToggle.setImageResource(R.drawable.ic_pause);
        mediaPlayer.start();
        isPlaying = true;
        handler.sendEmptyMessage(0);
    }

    //点击暂停实施的方法
    private void pauseMediaPlayer() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        isPlaying = false;
        btnToggle.setImageResource(R.drawable.ic_play_arrow);
        handler.sendEmptyMessage(0);
    }

    //初始化SurfaceView
    private void initSurfaceView() {
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        //设置pixelformat
        surfaceHolder.setFormat(PixelFormat.RGBA_8888);
    }

    //生命周期初始化和暂停
    public void onResume() {
        initMediaPlayer();//初始化MediaPlayer,设置一系列监听
        prepareMediaPlayer();//准备MediaPlayer，更新UI
    }

    //准备MediaPlayer，更新UI
    private void prepareMediaPlayer() {
        try {
            mediaPlayer.reset();//重置
            mediaPlayer.setDataSource(videoPath);
            mediaPlayer.setLooping(true);//循环播放
            mediaPlayer.prepareAsync();
            //预览图可见
            ivPreview.setVisibility(View.VISIBLE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //初始化MediaPlayer,设置一系列监听
    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer(getContext());
        mediaPlayer.setDisplay(surfaceHolder);
        //准备情况的监听
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                isPrepared = true;
                startMediaPlater();
            }
        });

        //处理audio
        mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_FILE_OPEN_OK) {
                    mediaPlayer.audioInitedOk(mediaPlayer.audioTrackInit());
                    return true;
                }
                return false;
            }
        });

        //设置videosize变化监听
        mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                int layoutWidth = surfaceView.getWidth();
                int layoutHeight = layoutWidth * height / width;
                //更新surfaceView的size
                ViewGroup.LayoutParams layoutparams = surfaceView.getLayoutParams();
                layoutparams.width = layoutWidth;
                layoutparams.height = layoutHeight;
                surfaceView.setLayoutParams(layoutparams);
            }
        });
    }

    public void onPause() {
        
    }
}
