package com.example.sinemaapp;

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
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Main_fragment extends Fragment implements View.OnClickListener {
    private static String YouTube_Api = "AIzaSyB2P4Q7d234l-EI_oO6dAi-BlbMpuOg0CE";
    private String Channel_id = "UCOD2veMoMj5jy6K0pGt55Bw";
    private String Channel_url = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&channelId="+Channel_id+"&maxResults=20&key="+YouTube_Api+"";
    Toolbar toolbar;
    ArrayList<Film> list = new ArrayList<Film>();
    RecyclerView recyclerView;
    Context wrapper;
    Button button_tag,button_stars,button_date;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_fragment,container,false);
        wrapper = new ContextThemeWrapper(inflater.getContext(),R.style.PopurMenuDark);
        recyclerView = view.findViewById(R.id.recycler);
        toolbar = view.findViewById(R.id.toolbar);
        initRecyclerView();
        listInit();
        button_date = view.findViewById(R.id.button_date);
        button_tag = view.findViewById(R.id.button_tag);
        button_stars = view.findViewById(R.id.button_stars);
        button_stars.setOnClickListener(this);
        button_tag.setOnClickListener(this);
        button_date.setOnClickListener(this);
        return view;
    }
    private void initRecyclerView (){
        MainFilmAdapter mainFilmAdapter = new MainFilmAdapter(list);
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
    }
    private void hideViews() {
        toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }

    private void showViews() {
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }

    @Override
    public void onClick(View view) {
        PopupMenu popupMenu = new PopupMenu(wrapper,view);
        switch (view.getId()){
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
                switch (menuItem.getItemId()){
                    case R.id.good:
                        button_stars.setText(getString(R.string.str_stars_good));
                        activButton(button_stars);
                        break;
                    case R.id.bad:
                        button_stars.setText(getString(R.string.str_stars_bad));
                        activButton(button_stars);
                        break;

                    case R.id.date_old:
                        button_date.setText(getString(R.string.str_date_old));
                        activButton(button_date);
                        break;
                    case R.id.date_new:
                        button_date.setText(getString(R.string.str_date_new));
                        activButton(button_date);
                        break;

                    case R.id.video_tag:
                        button_tag.setText(getString(R.string.str_tag_video));
                        activButton(button_tag);
                        break;
                    case R.id.film_tag:
                        button_tag.setText(getString(R.string.str_tag_film));
                        activButton(button_tag);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }
    private void listInit (){
        YouTubeApiConnect youTubeApiConnect = new YouTubeApiConnect(Channel_url,list,recyclerView);
        youTubeApiConnect.execute();
    }
    private void activButton (Button activ){
        switch (activ.getId()){
            case R.id.button_date:
                button_tag.setText(getResources().getString(R.string.bt_tag));
                button_stars.setText(getResources().getString(R.string.bt_stars));
                break;
            case R.id.button_tag:
                button_date.setText(getResources().getString(R.string.bt_date));
                button_stars.setText(getResources().getString(R.string.bt_stars));
                break;
            case R.id.button_stars:
                button_tag.setText(getResources().getString(R.string.bt_tag));
                button_date.setText(getResources().getString(R.string.bt_date));
                break;
        }
    }
}
