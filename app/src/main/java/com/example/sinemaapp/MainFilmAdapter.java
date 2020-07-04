package com.example.sinemaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainFilmAdapter extends RecyclerView.Adapter<MainFilmAdapter.FilmViewHolder> {
    ArrayList<Film> list;
    public MainFilmAdapter(ArrayList<Film> list){
        this.list = list;
    }
    public class FilmViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tag;
        TextView name;
        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_film);
            tag = itemView.findViewById(R.id.tag);
            img = itemView.findViewById(R.id.img);
        }
    }
    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.maket_recycler_view_film,parent,false);
        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
