package com.example.sinemaapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sinemaapp.R;
import com.example.sinemaapp.classes.ScrollRec;
import com.example.sinemaapp.adapter.MainFilmAdapter;
import com.example.sinemaapp.classes.FireBaseConnect;
import com.example.sinemaapp.classes.YouTubeApi;
import com.example.sinemaapp.model.Film;
import com.example.sinemaapp.model.ModelMain;
import com.example.sinemaapp.model.User;
import com.example.sinemaapp.model.VideoApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_fragment extends Fragment implements View.OnClickListener {
    private static String YouTube_Api = "AIzaSyB2P4Q7d234l-EI_oO6dAi-BlbMpuOg0CE";
    private String Channel_id = "UCoAEj6XaIzqxQ5C5OUIGcZA";
    private String Channel_url = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&channelId=" + Channel_id + "&maxResults=20&key=" + YouTube_Api;
    private String nextToken = "&pageToken=";
    String token = "";
    private Toolbar toolbar;
    private boolean scroll = true;
    private ArrayList<VideoApi> list = new ArrayList<VideoApi>();
    private RecyclerView recyclerView;
    private Context wrapper;
    private Button button_tag, button_stars, button_date;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_fragment, container, false);
        wrapper = new ContextThemeWrapper(inflater.getContext(), R.style.PopurMenuDark);
        recyclerView = view.findViewById(R.id.recycler);
        toolbar = view.findViewById(R.id.toolbar);
        initRecyclerView();
     // FireBaseConnect fireBaseConnect = new FireBaseConnect();
     // fireBaseConnect.setNewUser(new User("icon2", "email@gmail.com", "123456789", false, 1, "name"));
        button_date = view.findViewById(R.id.button_date);
        button_tag = view.findViewById(R.id.button_tag);
        button_stars = view.findViewById(R.id.button_stars);
        button_stars.setOnClickListener(this);
        button_tag.setOnClickListener(this);
        button_date.setOnClickListener(this);
        return view;
    }

    private void initRecyclerView() {
        MainFilmAdapter mainFilmAdapter = new MainFilmAdapter(list, YouTube_Api);
        recyclerView.setAdapter(mainFilmAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(toolbar.getContext()));
        recyclerView.setOnScrollListener(new ScrollRec() {
            @Override
            public void onHide() {
                hideViews();
            }

            @Override
            public void onShow() {
                showViews();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int currentItem = manager.getChildCount();
                int totalItem = manager.getItemCount();
                int scrollOutItem = manager.findFirstVisibleItemPosition();
                if (scroll && (currentItem + scrollOutItem - 1 == totalItem)) {
                    getJson();
                    scroll = false;
                }
            }
        });
        getJson();
    }

    private void hideViews() {
        toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }

    private void showViews() {
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

    @Override
    public void onClick(View view) {
        PopupMenu popupMenu = new PopupMenu(wrapper, view);
        switch (view.getId()) {
            case R.id.button_tag:
                popupMenu.inflate(R.menu.menu_popur_tag);
                break;
            case R.id.button_date:
                popupMenu.inflate(R.menu.menu_popur_date);
                break;
            case R.id.button_stars:
                popupMenu.inflate(R.menu.menu_popur_stars);
                break;
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                return false;
            }
        });
        popupMenu.show();
    }

    private void getJson() {
        String url = Channel_url;

        if (token != "")
            url = url + nextToken + token;

            Call<ModelMain> date = YouTubeApi.getVideo().getMainVideo(url);
            date.enqueue(new Callback<ModelMain>() {
                @Override
                public void onResponse(Call<ModelMain> call, Response<ModelMain> response) {
                    if (response.errorBody() != null) {
                        Toast.makeText(wrapper, "Error Call<ModelMain>: " + response.errorBody().toString() + " is in onResponse", Toast.LENGTH_SHORT).show();
                    } else {
                        ModelMain modelMain = response.body();
                        for (int i = 0; i < modelMain.getItems().size(); i++) {
                            if (modelMain.getItems().get(i).getId().getVideoId() != null)
                                list.add(modelMain.getItems().get(i));
                        }
                        recyclerView.getAdapter().notifyDataSetChanged();
                        if (modelMain.getNextToken() != null)
                            token = modelMain.getNextToken();
                        scroll = true;
                    }
                }

                @Override
                public void onFailure(Call<ModelMain> call, Throwable t) {
                    Toast.makeText(wrapper, "Error Call<ModelMain>: " + t.getMessage() + " is in onFailure", Toast.LENGTH_SHORT).show();
                }
            });
        }
}
