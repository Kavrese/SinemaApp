package com.example.sinemaapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class ScrollRec extends RecyclerView.OnScrollListener {
    private int scrollDis = 0;
    private int HIDE = 20;
    private boolean control = true;
    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if(scrollDis > HIDE && control){
            onHide();
            control = false;
            scrollDis = 0;
        }else if(scrollDis < -HIDE && !control){
            onShow();
            control = true;
            scrollDis = 0;
        }
        if((control && dy>0) || (!control && dy<0)) {
            scrollDis += dy;
        }
    }
    public abstract void onHide();
    public abstract void onShow();
}
