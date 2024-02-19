package com.example.spring.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import static java.lang.Integer.parseInt;

import com.example.spring.MusicService;
import com.example.spring.R;
import com.example.spring.frag1;

public class MusicRunActivity extends AppCompatActivity implements View.OnClickListener{
    //进度条
    private static SeekBar sb;
    private static TextView mTvProgress,mTvTotal,mTvSongName;
    //动画
    private ObjectAnimator animator;
    private MusicService.MusicControl musicControl;
    private String name;
    public static Myhandler handler=new Myhandler();
    private Intent intent1,intent2;
    private MyServiceConn conn;
    //记录服务是否被解绑，默认没有
    private boolean isUnbind =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicrun);
        //获取从frag1传来的信息
        intent1=getIntent();
        init();
    }
    private void init(){
        mTvProgress=findViewById(R.id.tv_musicrun_progress);
        mTvTotal=findViewById(R.id.tv_musicrun_total);
        sb=findViewById(R.id.sb_musicrun_sb);
        mTvSongName=findViewById(R.id.tv_musicrun_songname);
        //绑定控件的同时设置点击事件监听器
        findViewById(R.id.btn_musicrun_play).setOnClickListener(this);
        findViewById(R.id.btn_musicrun_pause).setOnClickListener(this);
        findViewById(R.id.btn_musicrun_continueplay).setOnClickListener(this);
        findViewById(R.id.btn_musicrun_exit).setOnClickListener(this);
        name=intent1.getStringExtra("name");
        mTvSongName.setText(name);
        //创建一个意图对象，是从当前的Activity跳转到Service
        intent2=new Intent(this,MusicService.class);
        conn=new MyServiceConn();//创建服务连接对象
        bindService(intent2,conn,BIND_AUTO_CREATE);//绑定服务
        //为滑动条添加事件监听，每个控件不同果然点击事件方法名都不同
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //这一行注解是保证API在KITKAT以上的模拟器才能顺利运行，也就是19以上
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //进当滑动条到末端时，结束动画
                if (progress==seekBar.getMax()){
                    animator.pause();//停止播放动画
                }
            }

            @Override
            //滑动条开始滑动时调用
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            //滑动条停止滑动时调用
            public void onStopTrackingTouch(SeekBar seekBar) {
                //根据拖动的进度改变音乐播放进度
                int progress=seekBar.getProgress();//获取seekBar的进度
                musicControl.seekTo(progress);//改变播放进度
            }
        });
        //声明并绑定音乐播放器的iv_music控件
        ImageView iv_music=findViewById(R.id.iv_musicrun_bg);
        String position= intent1.getStringExtra("position");
        //praseInt()就是将字符串变成整数类型
        int i=parseInt(position);
        iv_music.setImageResource(frag1.icons[i]);
        //rotation和0f,360.0f就设置了动画是从0°旋转到360°
        animator=ObjectAnimator.ofFloat(iv_music,"rotation",0f,360.0f);
        animator.setDuration(10000);//动画旋转一周的时间为10秒
        animator.setInterpolator(new LinearInterpolator());//匀速
        animator.setRepeatCount(-1);//-1表示设置动画无限循环
    }
    //handler机制，可以理解为线程间的通信，我获取到一个信息，然后把这个信息告诉你，就这么简单
    public static class Myhandler extends Handler{//创建消息处理器对象
        //在主线程中处理从子线程发送过来的消息
        @Override
        public void handleMessage(Message msg){
            Bundle bundle=msg.getData();//获取从子线程发送过来的音乐播放进度
            //获取当前进度currentPosition和总时长duration
            int duration=bundle.getInt("duration");
            int currentPosition=bundle.getInt("currentPosition");
            //对进度条进行设置
            sb.setMax(duration);
            sb.setProgress(currentPosition);
            //歌曲是多少分钟多少秒钟
            int minute=duration/1000/60;
            int second=duration/1000%60;
            String strMinute=null;
            String strSecond=null;
            if(minute<10){//如果歌曲的时间中的分钟小于10
                strMinute="0"+minute;//在分钟的前面加一个0
            }
            else{
                strMinute=minute+" ";
            }
            if (second<10){//如果歌曲中的秒钟小于10
                strSecond="0"+second;//在秒钟前面加一个0
            }
            else{
                strSecond=second+" ";
            }
            //显示当前歌曲已经播放的时间
            mTvProgress.setText(strMinute+":"+strSecond);
        }
    };
    //用于实现连接服务，比较模板化，不需要详细知道内容
    class MyServiceConn implements ServiceConnection{
        @Override
        public void onServiceConnected(ComponentName name, IBinder service){
            musicControl=(MusicService.MusicControl) service;
        }
        @Override
        public void onServiceDisconnected(ComponentName name){

        }
    }
    //判断服务是否被解绑
    private void unbind(boolean isUnbind){
        //如果解绑了
        if(!isUnbind){
            musicControl.pausePlay();//音乐暂停播放
            unbindService(conn);//解绑服务
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case 1000006:
                String position=intent1.getStringExtra("position");
                int i=parseInt(position);
                musicControl.play(i);
                animator.start();
                break;
            case 1000000:
                musicControl.pausePlay();
                animator.pause();
                break;
            case 1000001:
                musicControl.continuePlay();
                animator.start();
                break;
            case 1000020:
                unbind(isUnbind);
                isUnbind=true;
                finish();
                break;
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        unbind(isUnbind);//解绑服务
    }
}

