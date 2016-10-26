package videonews.videoplayer.full;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import io.vov.vitamio.widget.MediaController;
import videonews.videoplayer.R;

/**
 * @author z_j_l
 * @time 2016/10/26 16:02
 * @note ${TODP}
 */
public class CustomMediaController extends MediaController {
    private MediaPlayerControl mediaPlayerControl;//自定义视频控制器

    private final AudioManager audioManager;//音频管理
    private Window window;//视频亮度管理

    private final int maxVolume;//最大音量
    private int currentVolume;//当前音量
    private float cuurentBrightness;//当前亮度

    public CustomMediaController(Context context) {
        super(context);
        //得到音频管理器
        audioManager = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
        //最大音量
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //视频亮度
        window = ((Activity)context).getWindow();
    }

    //通过重写此方法，自定义layout
    @Override
    protected View makeControllerView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_custom_video_controller,this,true);
        initView(view);
        return view;
    }

    //让自定义的Controller可以控制mediaPlayer
    @Override
    public void setMediaPlayer(MediaPlayerControl player) {
        super.setMediaPlayer(player);
        mediaPlayerControl = player;
    }

    private void initView(View view){
        //forward快进
        ImageButton btnFastForward = (ImageButton)view.findViewById(R.id.btnFastForward);
        btnFastForward.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                long postion = mediaPlayerControl.getCurrentPosition();
                postion += 5000;//快进5s
                //快进的位置是否大于最大长度
                if(postion >= mediaPlayerControl.getDuration()){
                    postion = mediaPlayerControl.getDuration();
                }
                mediaPlayerControl.seekTo(postion);
            }
        });
        //rewind快退
        ImageButton btnFastRewind = (ImageButton)view.findViewById(R.id.btnFastRewind);
        btnFastRewind.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                long postion = mediaPlayerControl.getCurrentPosition();
                postion -= 5000;
                //快退的位置是否小于0s
                if(postion <=0){
                    postion = 0;
                }
                mediaPlayerControl.seekTo(postion);
            }
        });
        //调整视图(左边调节亮度，右边调节音量)
        final View adjustView = view.findViewById(R.id.adjustView);
        //依赖GestureDetector来进行滑屏调整音量和亮度的手势处理
        final GestureDetector gestureDetector = new GestureDetector(getContext(),
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                        float startX = e1.getX();//开始的X轴位置
                        float startY = e1.getY();//开始的Y轴位置
                        float endX = e2.getX();//结束的X轴位置
                        float endY = e2.getY();//结束的Y轴位置
                        float width = adjustView.getWidth();//得到整个视图的宽
                        float height = adjustView.getHeight();//高
                        float percentage = (startY - endY)/height;//得到高度滑动百分比
                        //左侧的亮度调整控制
                        if(startX < width / 5){
                            //调整亮度
//                            adjustRightness(percentage);
                        }
                        //右侧的音量调整控制
                        else if(startX > width * 4 / 5){
                            //调整音量
//                            adjustVolume(percentage);
                        }

                        return super.onScroll(e1, e2, distanceX, distanceY);
                    }
                });

    }
}
