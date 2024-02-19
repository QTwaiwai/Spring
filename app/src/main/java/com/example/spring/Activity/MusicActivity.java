package com.example.spring.Activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.spring.R;
import com.example.spring.Frag1;

public class MusicActivity extends AppCompatActivity {
    private ImageView iv;
    private FragmentManager fm;
    private FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        inital();
        fm=getSupportFragmentManager();
        ft=fm.beginTransaction();
        ft.replace(R.id.fg_musiclist,new Frag1());
        ft.commit();
    }

    private void inital(){
        iv=findViewById(R.id.iv_music_bg);
        iv.setAlpha(0.5f);
    }
}
