package com.example.sinemaapp.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sinemaapp.classes.PageFilm;
import com.example.sinemaapp.R;
import com.example.sinemaapp.classes.YouTubeApi;
import com.example.sinemaapp.model.Film;
import com.example.sinemaapp.model.FullinfoVideo;
import com.example.sinemaapp.model.ModelMain;
import com.example.sinemaapp.model.VideoApi;
import com.example.sinemaapp.model.VideoApiFull;
import com.squareup.picasso.Cache;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFilmAdapter extends RecyclerView.Adapter<MainFilmAdapter.FilmViewHolder> {
    ArrayList<VideoApi> list;
    private String base_url = "https://www.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics";
    private String video_id_url = "&id=";
    private String api_url = "&key=";
    public MainFilmAdapter(ArrayList<VideoApi> list,String api_url){
        this.list = list;
        this.api_url = this.api_url + api_url;
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
        String urlImg = videoApi.getSnippet().getThumbnails().getHigh().getUrl();
        holder.name.setText(getTitle);
        Picasso.get()
                .load(urlImg)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.img);
        View v = holder.img;
        //String url = base_url+video_id_url+ list.get(position).getId().getVideoId()+api_url;
        Call<FullinfoVideo> date = YouTubeApi.getVideo().getInfoVideo(base_url+video_id_url+ list.get(position).getId().getVideoId()+api_url);
        date.enqueue(new Callback<FullinfoVideo>() {
            @Override
            public void onResponse(Call<FullinfoVideo> call, Response<FullinfoVideo> response) {
                if(response.errorBody() != null){
                    Toast.makeText(holder.img.getContext(),"Error Call<FullinfoVideo>: "+ response.errorBody().toString()+" is in onResponse", Toast.LENGTH_SHORT).show();
                    Toast.makeText(holder.img.getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }else{
                    FullinfoVideo fullinfoVideo = response.body();
                    VideoApiFull videoApiFull = fullinfoVideo.getItems().get(0);
                    final String des = videoApiFull.getSnippetApi().getDescription();
                    String timeLong = videoApiFull.getContentDetails().getDuration();
                    final String like = videoApiFull.getStatisticsVideo().getLikeCount();
                    final String dislike = videoApiFull.getStatisticsVideo().getDislikeCount();
                    final String views = videoApiFull.getStatisticsVideo().getViewCount();
                    final String stars = editStar(like,dislike);
                    Random random = new Random();
                    String tag = videoApiFull.getSnippetApi().getTags().get(random.nextInt(videoApiFull.getSnippetApi().getTags().size()));
                    holder.tag.setText(tag);
                    holder.time_long.setText(editStringTimeLong(timeLong));
                    holder.star.setText(stars);

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent in = new Intent((Activity)holder.itemView.getContext(),PageFilm.class);
                            in.putExtra("img",list.get(position).getSnippet().getThumbnails().getHigh().getUrl());
                            in.putExtra("name",list.get(position).getSnippet().getTitle());
                            in.putExtra("des",des);
                            in.putExtra("id_video",list.get(position).getId().getVideoId());
                            in.putExtra("like",like);
                            in.putExtra("dislike",dislike);
                            in.putExtra("view",views);
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) holder.itemView.getContext(),holder.img,"prev");
                            holder.itemView.getContext().startActivity(in,options.toBundle());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<FullinfoVideo> call, Throwable t) {
                Toast.makeText(holder.img.getContext(),"Error Call<FullinfoVideo>: "+t.getMessage()+" is in onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String editStar (String like,String dislike){
        int l = Integer.parseInt(like);
        int d = Integer.parseInt(dislike);
        return String.valueOf((float)(l*100/(l+d))*10/100);
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
    private void getMoreInfoVideo (String id_video,String api){
        video_id_url+= id_video;
        api_url+=api;

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

}
