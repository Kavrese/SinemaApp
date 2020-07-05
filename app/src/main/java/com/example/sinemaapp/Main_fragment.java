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
        list.add(new Film("Урок по чему-то","12.04.20","null","img","25:00","Видео",5.6f));
        list.add(new Film("Как и зачем","12.04.20","null","img","10:00","Видео",7.4f));
        list.add(new Film("Существует ли ?","12.04.20","null","img","8:50","Видео",4.6f));
        list.add(new Film("Как быть с этим","12.04.20","null","img","14:03","Видео",9.6f));
        list.add(new Film("Подкаст","12.04.20","null","img","45:56","Видео",8.8f));
        list.add(new Film("Фильм","12.04.20","null","img","1:05:46","Фильм",8.4f));
        list.add(new Film("Строим дом","12.04.20","null","img","4:13","Видео",1.6f));
        initRecyclerView();
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
