package com.example.sinemaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainFilmAdapter extends RecyclerView.Adapter<MainFilmAdapter.FilmViewHolder> {
    ArrayList<Film> list;
    public MainFilmAdapter(ArrayList<Film> list){
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
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.time_long.setText(editString(list.get(position).getTime_long()));
        holder.tag.setText(list.get(position).getTag());
        Picasso.get()
                .load(list.get(position).getImg())
                .error(android.R.drawable.ic_menu_close_clear_cancel)
                .into(holder.img);
        float star = list.get(position).getStar();
        if(star <= 3.9f)
            holder.star.setBackgroundColor(holder.img.getContext().getColor(R.color.red));
        else if(star <= 6.9)
            holder.star.setBackgroundColor(holder.img.getContext().getColor(R.color.yellow));
        else if(star <= 10)
            holder.star.setBackgroundColor(holder.img.getContext().getColor(R.color.green));
        holder.star.setText(String.valueOf(list.get(position).getStar()));
    }
    private String editString(String time_long) {
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
