package com.example.sinemaapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.dingmouren.layoutmanagergroup.echelon.EchelonLayoutManager;
import com.example.sinemaapp.R;
import com.example.sinemaapp.adapter.PotokAdapter;
import com.example.sinemaapp.classes.Errors;
import com.example.sinemaapp.classes.YouTubeApi;
import com.example.sinemaapp.model.ModelMain;
import com.example.sinemaapp.model.SnippetApi;
import com.example.sinemaapp.model.VideoApi;
import com.example.sinemaapp.model.VideoID;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Potok_fragment extends Fragment {
    private String api;
    private RecyclerView recyclerView;
    private List<VideoApi> list = new ArrayList<>();
    private String YouTube_Api;
    private String Channel_id = "UCoAEj6XaIzqxQ5C5OUIGcZA";
    private String Channel_url = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&channelId=" + Channel_id + "&maxResults=20&key=";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_potok_fragment,container,false);
        YouTube_Api = getString(R.string.api);
        Channel_url += YouTube_Api;
        recyclerView = view.findViewById(R.id.rec);
        api = getString(R.string.api);
        getCallInfo();
        recyclerView.setAdapter(new PotokAdapter(list,api));
        recyclerView.setLayoutManager(new EchelonLayoutManager(recyclerView.getContext()));
        return view;
    }
    private void getCallInfo (){
        Call<ModelMain> date = YouTubeApi.getVideo().getMainVideo(Channel_url);
        date.enqueue(new Callback<ModelMain>() {
            @Override
            public void onResponse(Call<ModelMain> call, Response<ModelMain> response) {
                if(response.errorBody() != null){
                    new Errors(response,null,"ModelMain",recyclerView.getContext(),"onResponse Potok_fragment");
                }else{
                    ModelMain modelMain = response.body();
                    for (int i = 0; i < modelMain.getItems().size(); i++) {
                        if (modelMain.getItems().get(i).getId().getVideoId() != null)
                            list.add(modelMain.getItems().get(i));
                    }
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ModelMain> call, Throwable t) {
                new Errors(t,recyclerView.getContext(),"onFailure Potok_fragment");
            }
        });
    }
}
