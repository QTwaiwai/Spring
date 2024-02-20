package com.example.spring.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;

import android.os.Bundle;
import android.os.IBinder;

import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static java.lang.Integer.parseInt;

import com.example.spring.Frag1;
import com.example.spring.MusicService;
import com.example.spring.R;

public class MusicRunActivity extends AppCompatActivity {
    int i;
    private Button play;
    private Button pause;
    private Button continueplay;
    private Button exit;
    private TextView mTvSongName;
    private MusicService.MusicControl musicControl;
    private String name;
    private Intent intent1, intent2;
    private MyServiceConn conn;
    //记录服务是否被解绑，默认没有
    private boolean isUnbind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicrun);
        //获取从Frag1传来的信息
        intent1 = getIntent();
        inital();
        abc();
    }

    private void inital() {
        mTvSongName = findViewById(R.id.tv_musicrun_songname);
        play = findViewById(R.id.btn_musicrun_play);
        pause = findViewById(R.id.btn_musicrun_pause);
        continueplay = findViewById(R.id.btn_musicrun_continueplay);
        exit = findViewById(R.id.btn_musicrun_exit);
        name = intent1.getStringExtra("song");
        mTvSongName.setText(name);
        //从当前的Activity跳转到Service
        intent2 = new Intent(this, MusicService.class);
        conn = new MyServiceConn();//创建服务连接对象
        bindService(intent2, conn, BIND_AUTO_CREATE);//绑定服务
        //传入服务信息
        ImageView iv_music = (ImageView) findViewById(R.id.iv_musicrun_bg);
        ImageView iv_musicbg = (ImageView) findViewById(R.id.iv_musicrunbg);
        String position = intent1.getStringExtra("position");
        i = parseInt(position);
        iv_music.setImageResource(Frag1.icons[i]);
        iv_musicbg.setImageResource(Frag1.icons[i]);
        iv_musicbg.setAlpha(0.5f);
    }


    class MyServiceConn implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicControl = (MusicService.MusicControl) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    //判断服务是否被解绑
    private void unbind(boolean isUnbind) {
        if (!isUnbind) {
            musicControl.pausePlay();//音乐暂停播放
            unbindService(conn);//解绑服务
        }
    }

    //不同按钮点击事件
    private void abc() {
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicControl.play(i);
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicControl.pausePlay();
            }
        });
        continueplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicControl.continuePlay();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbind(isUnbind);
                isUnbind = true;
                finish();
            }
        });
    }

    //摧毁活动
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbind(isUnbind);//解绑服务
    }
}

