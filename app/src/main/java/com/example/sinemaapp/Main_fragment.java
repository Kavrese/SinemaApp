package com.example.sinemaapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;

import java.util.ArrayList;

public class Main_fragment extends Fragment {
    Toolbar toolbar;
    ArrayList<Film> list = new ArrayList<Film>();
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_fragment,container,false);
        recyclerView = view.findViewById(R.id.recycler);
        toolbar = view.findViewById(R.id.toolbar);
        list.add(new Film("Урок по чему-то","12.04.20","null","img","25:00","Видео",5.6f));
        list.add(new Film("Как и зачем","12.04.20","null","img","10:00","Видео",7.4f));
        list.add(new Film("Существует ли ?","12.04.20","null","img","8:50","Видео",4.6f));
        list.add(new Film("Как быть с этим","12.04.20","null","img","14:03","Видео",9.6f));
        list.add(new Film("Подкаст","12.04.20","null","img","45:56","Видео",8.8f));
        list.add(new Film("Фильм","12.04.20","null","img","1:05:46","Фильм",8.4f));
        list.add(new Film("Строим дом","12.04.20","null","img","4:13","Видео",1.6f));
        initRecyclerView();
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
}
