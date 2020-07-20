package com.example.sinemaapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sinemaapp.classes.PageFilm;
import com.example.sinemaapp.R;
import com.example.sinemaapp.model.Film;
import com.example.sinemaapp.model.VideoApi;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainFilmAdapter extends RecyclerView.Adapter<MainFilmAdapter.FilmViewHolder> {
    ArrayList<VideoApi> list;
    public MainFilmAdapter(ArrayList<VideoApi> list){
        this.list = list;
    }
    public class FilmViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name,tag,time_long,star;
        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_film);
            tag = itemView.findViewById(R.id.tag);
            img = itemView.findViewById(R.id.img);
            time_long = itemView.findViewById(R.id.time_long);
            star = itemView.findViewById(R.id.text_star);
        }
    }
    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.maket_recycler_view_film,parent,false);
        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FilmViewHolder holder, final int position) {
        VideoApi videoApi = list.get(position);

        String getTitle = videoApi.getSnippet().getTitle();
        String getTimeAdd = videoApi.getSnippet().getPublishedAt();
        String urlImg = videoApi.getSnippet().getThumbnails().getHigh().getUrl();
        holder.name.setText(getTitle);
        Picasso.get()
                .load(urlImg)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.img);
    }
    private String editStringTimeLong(String time_long) {
        String min,s,hour;
        time_long.replace("PT",""); //Удаляем формат времени

        if(time_long.contains("M"))
            min = opredMinS(time_long,"M");
        else
            min="00";
        if(time_long.contains("S"))
            s = opredMinS(time_long,"S");
        else
            s = "0";
        if(time_long.contains("H"))
            hour = opredMinS(time_long,"H");
        else
            hour = "";
        if(hour.equals(""))
            return min+":"+s;
        else
            return hour+":"+min+":"+s;
    }
    private String opredMinS(String time_long,String sim){
        String m = String.valueOf(time_long.charAt(time_long.indexOf(sim) - 1));
        String m1 = String.valueOf(time_long.charAt(time_long.indexOf(sim) - 2));
        if(!Character.isLetter(m1.charAt(0))){
            return m1+m;
        }else{
            return "0"+m;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
