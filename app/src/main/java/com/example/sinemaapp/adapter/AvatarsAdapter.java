package com.example.sinemaapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sinemaapp.R;
import com.example.sinemaapp.fragments.Profile_fragment;
import com.example.sinemaapp.model.Avatars;

import java.io.File;
import java.util.ArrayList;

public class AvatarsAdapter extends RecyclerView.Adapter<AvatarsAdapter.AvatarsViewHolder> {
    ArrayList<Avatars> list;
    Profile_fragment fragment;
    public AvatarsAdapter(ArrayList<Avatars> list, Profile_fragment fragment){
        this.list = list;
        this.fragment = fragment;
    }
    public static class AvatarsViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public AvatarsViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.ava);
        }
    }
    @NonNull
    @Override
    public AvatarsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.maket_recycler_view_avatars,parent,false);
        return new AvatarsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AvatarsViewHolder holder, final int position) {
        final ImageView avatar = fragment.getView().findViewById(R.id.avatar);
        final ImageView avatar_back = fragment.getView().findViewById(R.id.img_back);
        final Bitmap bitmap = BitmapFactory.decodeFile(list.get(position).getUri());
        holder.img.setImageBitmap(bitmap);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Context context = new ContextThemeWrapper(holder.itemView.getContext(),R.style.PopurMenuDark);
                PopupMenu popupMenu = new PopupMenu(context,holder.itemView);
                popupMenu.inflate(R.menu.menu_avatar_rec);
                Vibrator vibrator = (Vibrator) avatar.getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.load:
                                avatar.setImageBitmap(bitmap);
                                avatar_back.setImageBitmap(bitmap);
                                fragment.saveBdImg(bitmap,new File(list.get(position).getUri()));
                                break;
                            case R.id.add_new:
                                fragment.startChooseAvatar(true);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
