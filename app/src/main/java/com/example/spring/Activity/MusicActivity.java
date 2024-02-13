package com.example.spring.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spring.Database.MusicInformation;
import com.example.spring.R;
import com.example.spring.RvAdapter;

import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageView mIvbackground;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initView();
        initData();
        onPause();
    }

    private void initData() {
        ArrayList<MusicInformation> data = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            MusicInformation song = new MusicInformation();
            song.setSongName("歌曲" + i);
            song.setSingerName("歌手" + i);
            data.add(song);
        }
        RvAdapter rvAdapter = new RvAdapter(data);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerview);
        mIvbackground=findViewById(R.id.iv_music_bg);
        mIvbackground.setAlpha(0.5f);
    }
}
