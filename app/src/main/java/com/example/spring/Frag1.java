package com.example.spring;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.spring.Activity.MusicRunActivity;

public class Frag1 extends Fragment {
    private View view;
    //创建歌曲的String数组和歌手图片的int数组
    public static String[] song = {"不眠之夜", "孤雏"};
    public static String[] singer = {"张杰", "无名"};
    public static int[] icons = new int[]{R.drawable.music0, R.drawable.music1};

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.music_list, container, false);
        //创建listView列表并且绑定控件
        ListView listView = view.findViewById(R.id.lv);
        //实例化一个适配器
        MyBaseAdapter adapter = new MyBaseAdapter();
        //列表设置适配器
        listView.setAdapter(adapter);
        //列表元素的点击监听器
        listView.setOnItemClickListener((parent, view, position, id) -> {
            //从frag1跳转到MusicActivity
            Intent intent = new Intent(Frag1.this.getContext(), MusicRunActivity.class);
            //将歌曲名和歌曲的下标存入Intent对象
            intent.putExtra("song", song[position]);
            intent.putExtra("singer", singer[position]);
            intent.putExtra("position", String.valueOf(position));
            startActivity(intent);
        });
        return view;
    }

    class MyBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return song.length;
        }

        @Override
        public Object getItem(int i) {
            return song[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            //绑定好VIew，然后绑定控件
            @SuppressLint("ViewHolder") View view = View.inflate(Frag1.this.getContext(), R.layout.lv_musicinformation, null);
            TextView songname = view.findViewById(R.id.lv_songname);
            TextView singername = view.findViewById(R.id.lv_singername);
            ImageView iv = view.findViewById(R.id.lv_bg);
            //设置控件显示的内容，就是获取的歌曲名和歌手图片
            songname.setText(song[i]);
            singername.setText(singer[i]);
            iv.setImageResource(icons[i]);
            return view;
        }
    }

}

