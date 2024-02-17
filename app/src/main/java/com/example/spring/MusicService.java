package com.example.spring;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import com.example.spring.Activity.MusicRunActivity;

import java.util.Timer;
import java.util.TimerTask;


public class MusicService extends Service {
    private MediaPlayer player;
    private Timer timer;

    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MusicControl();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //创见音乐播放器
        player = new MediaPlayer();
    }

    //计时器设置播放进度条
    public void addTimer() {
        if (timer == null) {
            timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (player == null) return;
                    int duration = player.getDuration();//歌曲总时长
                    int currentPosition = player.getCurrentPosition();//当前进度
                    //将消息发送到歌曲运行
                    Message msg =MusicRunActivity.handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putInt("duration", duration);
                    bundle.putInt("currentPosition", currentPosition);
                    msg.setData(bundle);
                    MusicRunActivity.handler.sendMessage(msg);
                }
            };
            timer.schedule(task, 5, 500);//重复执行
        }
    }

    public class MusicControl extends Binder {
        public void play(int i) {
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + "music" + i);
            try {
                //重置播放器
                player.reset();
                //添加多媒体文件
                player = MediaPlayer.create(getApplicationContext(), uri);
                player.start();
                addTimer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void pausePlay() {
            player.pause();
        }

        public void continuePlay() {
            player.start();
        }

        public void seekTo(int progress) {
            player.seekTo(progress);//设置音乐的播放位置
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(player==null) return;
        if(player.isPlaying()) player.stop();//停止播放音乐
        player.release();
        player=null;
    }
}