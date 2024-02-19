package com.example.spring;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;

import android.os.IBinder;

//这是一个Service服务类
public class MusicService extends Service {
    //声明一个MediaPlayer引用
    private  MediaPlayer player;
    //构造函数
    public MusicService() {}
    @Override
    public  IBinder onBind(Intent intent){
        return new MusicControl();
    }
    @Override
    public void onCreate(){
        player=new MediaPlayer();
        super.onCreate();
    }
    //Binder是一种跨进程的通信方式
    public class MusicControl extends Binder{
        public void play(int i){//String path
            Uri uri=Uri.parse("android.resource://"+getPackageName()+"/raw/"+"music"+i);
            try{
                //重置音乐播放器
                player.reset();
                //加载多媒体文件
                player=MediaPlayer.create(getApplicationContext(),uri);
                player.start();//播放音乐
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        public void pausePlay(){
            player.pause();//暂停播放音乐
        }
        public void continuePlay(){
            player.start();//继续播放音乐
        }
    }
    //销毁多媒体播放器
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(player==null) return;
        if(player.isPlaying()) player.stop();//停止播放音乐
        player.release();//释放占用的资源
        player=null;//将player置为空
    }
}

