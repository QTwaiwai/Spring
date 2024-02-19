package com.example.spring.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spring.R;

public class MusicActivity extends AppCompatActivity {
    private ImageView iv;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        inital();
    }

    private void inital(){
        listView=findViewById(R.id.lv);
        iv=findViewById(R.id.iv_music_bg);
        iv.setAlpha(0.5f);
    }
}
