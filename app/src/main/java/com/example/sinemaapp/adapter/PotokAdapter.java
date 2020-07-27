package com.example.sinemaapp.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sinemaapp.R;
import com.example.sinemaapp.classes.Errors;
import com.example.sinemaapp.classes.PageFilm;
import com.example.sinemaapp.classes.PlayerActivity;
import com.example.sinemaapp.classes.YouTubeApi;
import com.example.sinemaapp.model.FullinfoVideo;
import com.example.sinemaapp.model.VideoApi;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PotokAdapter extends RecyclerView.Adapter<PotokAdapter.PotockViewHolder> {
    private String base_url = "https://www.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics";
    private String video_id_url = "&id=";
    private String api_url = "&key=";
    List<VideoApi> list;
    public PotokAdapter(List<VideoApi> list,String api_key){
        this.list = list;
        api_url = api_url + api_key;
    }
    public class PotockViewHolder extends RecyclerView.ViewHolder {
        ImageView img,play,like,dislike;
        TextView name,des;
        public PotockViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            play = itemView.findViewById(R.id.play);
            like = itemView.findViewById(R.id.like);
            dislike = itemView.findViewById(R.id.dislike);
            name = itemView.findViewById(R.id.name_bt);
            des = itemView.findViewById(R.id.des_potok);
        }
    }
    @NonNull
    @Override
    public PotockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.maket_recycler_view_potok,parent,false);
        return new PotockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PotockViewHolder holder, final int position) {
        VideoApi videoApi = list.get(position);
        Picasso.get()
                .load(videoApi.getSnippet().getThumbnails().getHigh().getUrl())
                .into(holder.img);
        holder.name.setText(videoApi.getSnippet().getTitle());
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in_player = new Intent(holder.itemView.getContext(), PlayerActivity.class);
                in_player.putExtra("id_video",list.get(position).getId().getVideoId());
                holder.itemView.getContext().startActivity(in_player);
            }
        });
        Call<FullinfoVideo> date = YouTubeApi.getVideo().getInfoVideo(base_url+video_id_url+ list.get(position).getId().getVideoId()+api_url);
        date.enqueue(new Callback<FullinfoVideo>() {
            @Override
            public void onResponse(Call<FullinfoVideo> call, Response<FullinfoVideo> response) {
                if(response.errorBody() != null){
                    new Errors(null,response,"FullVideoApi",holder.itemView.getContext(),"onResponse");
                }else{
                    final FullinfoVideo fullinfoVideo = response.body();
                    holder.des.setText(list.get(position).getSnippet().getDescription());
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent in = new Intent(holder.itemView.getContext(), PageFilm.class);
                            in.putExtra("img",list.get(position).getSnippet().getThumbnails().getHigh().getUrl());
                            in.putExtra("name",list.get(position).getSnippet().getTitle());
                            in.putExtra("des",fullinfoVideo.getItems().get(0).getSnippetApi().getDescription());
                            in.putExtra("id_video",list.get(position).getId().getVideoId());
                            in.putExtra("like",fullinfoVideo.getItems().get(0).getStatisticsVideo().getLikeCount());
                            in.putExtra("dislike",fullinfoVideo.getItems().get(0).getStatisticsVideo().getDislikeCount());
                            in.putExtra("view",fullinfoVideo.getItems().get(0).getStatisticsVideo().getViewCount());
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) holder.itemView.getContext(),holder.img,"prev");
                            holder.itemView.getContext().startActivity(in,options.toBundle());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<FullinfoVideo> call, Throwable t) {
                new Errors(t,holder.itemView.getContext(),"onFailure");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
