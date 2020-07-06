package com.example.sinemaapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

import com.google.gson.JsonObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main_fragment extends Fragment implements View.OnClickListener {
    private static String YouTube_Api = "AIzaSyB2P4Q7d234l-EI_oO6dAi-BlbMpuOg0CE";
    private String Channel_id = "UCOD2veMoMj5jy6K0pGt55Bw";
    private String Channel_url = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&channelId=" + Channel_id + "&maxResults=2&key=" + YouTube_Api + "";
    Toolbar toolbar;
    ArrayList<Film> list = new ArrayList<Film>();
    ArrayList list_video_id = new ArrayList();
    RecyclerView recyclerView;
    Context wrapper;
    Button button_tag, button_stars, button_date;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_fragment, container, false);
        wrapper = new ContextThemeWrapper(inflater.getContext(), R.style.PopurMenuDark);
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

    private void initRecyclerView() {
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
                switch (menuItem.getItemId()) {
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

    private void listInit() {
        new YouTubeApiConnect(Channel_url, "list videos").execute();
        for(int i = 0;i<list_video_id.size();i++)
            new YouTubeApiConnect("https://www.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics&id="+list_video_id.get(i).toString()+"&key="+YouTube_Api+"","statics_list").execute();
    }
    private void setList (ArrayList<Film> list){
        this.list = list;
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void activButton(Button activ) {
        switch (activ.getId()) {
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
    public class YouTubeApiConnect extends AsyncTask<Void,String,String> {
        private String URL;
        private String type;
        public YouTubeApiConnect (String URL,String type){
            this.URL = URL;
            this.type = type;;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s != null){
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    Log.e("message",jsonObject.toString());
                    if(type.equals("list videos"))
                        parseVideo(jsonObject);
                    else if(type.equals("statics_list")){}
                        parseVideoStatics(jsonObject);
                }catch (JSONException e){

                }
            }
        }
        private void parseVideo(JSONObject jsonObject) throws JSONException {
            if (jsonObject.has("items")) {
                JSONArray jsonArray = jsonObject.getJSONArray("items");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject js = jsonArray.getJSONObject(i);
                    if (js.has("id")) {
                        JSONObject jsonID = js.getJSONObject("id");
                        if(jsonID.has("videoId")){
                            String videoId = jsonID.getString("videoId");
                            list_video_id.add(videoId);
                        }
                    }
                }
            }
            for(int i = 0;i<list_video_id.size();i++)
                new YouTubeApiConnect("https://www.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics&id="+list_video_id.get(i).toString()+"&key="+YouTube_Api+"","statics_list").execute();
        }
        private void parseVideoStatics(JSONObject jsonObject) throws JSONException {
            if(type.equals("statics_list")) {
                String title = "error", des = "error", created_date = "null", img = null, tag = "error",long_time = "null";
                final float stars;
                if (jsonObject.has("items")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    //Берём основные данные
                    if (jsonObject1.has("snippet")) {
                        JSONObject jsonObjectSnippet = jsonObject1.getJSONObject("snippet");
                        title = jsonObjectSnippet.getString("title");
                        des = jsonObjectSnippet.getString("description");
                        created_date = jsonObjectSnippet.getString("publishedAt");
                        //Дальше берём превью
                        if (jsonObjectSnippet.has("thumbnails")) {
                            JSONObject jsonObject_thumbnails = jsonObjectSnippet.getJSONObject("thumbnails");
                            if (jsonObject_thumbnails.has("high")) {
                                JSONObject jsonObjectHigh = jsonObject_thumbnails.getJSONObject("high");
                                img = jsonObjectHigh.getString("url");
                            }
                        }
                        //Берём тэг
                        if (jsonObjectSnippet.has("tags")) {
                            JSONArray JSONArrayTag = jsonObjectSnippet.getJSONArray("tags");
                            tag = JSONArrayTag.getString(0);
                        }
                    }
                    if(jsonObject1.has("contentDetails")){
                        JSONObject jsonObject_Details = jsonObject1.getJSONObject("contentDetails");
                        long_time = jsonObject_Details.getString("duration");
                    }
                    //Берём оценки
                    if (jsonObject1.has("statistics")) {
                        JSONObject jsonObject_statistics = jsonObject1.getJSONObject("statistics");
                        int like = jsonObject_statistics.getInt("likeCount");
                        int dislike = jsonObject_statistics.getInt("dislikeCount");
                        float fl = like * 100 / (like + dislike);     //Соотношений лайков к общей сумме оценок
                        stars = fl * 10 / 100;
                        list.add(new Film(title, created_date, des, img, long_time, tag, stars));
                        setList(list);
                    }
                }
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(URL);
                Log.e("URL",URL);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                String json = EntityUtils.toString(httpEntity);
                return json;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
