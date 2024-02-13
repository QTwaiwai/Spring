package com.example.spring;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spring.Database.MusicInformation;

import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.InnerHolder> {
    private ArrayList<MusicInformation> data;
    public RvAdapter(ArrayList<MusicInformation> data){
        this.data=data;
    }
    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InnerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_musicinformation,
                parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        holder.tvSongName.setText(data.get(position).getSongName());
        holder.tvSingerName.setText(data.get(position).getSingerName());
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    public static class InnerHolder extends RecyclerView.ViewHolder {
        TextView tvSongName;
        TextView tvSingerName;
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            tvSongName=itemView.findViewById(R.id.rv_songname);
            tvSingerName=itemView.findViewById(R.id.rv_singername);
        }
    }
}

