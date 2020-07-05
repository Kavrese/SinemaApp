package com.example.sinemaapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.google.api.client.json.Json;
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

public class YouTubeApiConnect extends AsyncTask<Void,String,String> {
    private RecyclerView recyclerView;
    private String CHANNEL_URL;
    private ArrayList<Film> list;
    public YouTubeApiConnect (String CHANNEL_URL, ArrayList<Film> list, RecyclerView recyclerView){
        this.CHANNEL_URL = CHANNEL_URL;
        this.list = list;
        this.recyclerView = recyclerView;
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
                list = parseVideo(jsonObject);
            }catch (JSONException e){

            }
        }
    }

    public ArrayList<Film> getList() {
        return list;
    }

    private ArrayList<Film> parseVideo(JSONObject jsonObject) throws JSONException {
        ArrayList<Film> list = new ArrayList<>();
        if(jsonObject.has("items")){
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject js = jsonArray.getJSONObject(i);
                if(js.has("id")){
                    JSONObject jsonID = js.getJSONObject("id");
                    if(jsonID.has("kind")){
                        if(jsonID.getString("kind").equals("youtube#video")){
                            //Дальше из snippet
                            JSONObject jsonSnippet = js.getJSONObject("snippet");
                            String title_video = jsonSnippet.getString("title");
                            String description = jsonSnippet.getString("description");
                            String date_created = jsonSnippet.getString("publishedAt");
                            String img = jsonSnippet.getJSONObject("thumbnails").getJSONObject("high").getString("url");
                            Film film = new Film(title_video,date_created,description,img,"12:30","Фильм",8.5f);
                            list.add(film);
                        }
                    }
                }
            }
            recyclerView.setAdapter(new MainFilmAdapter(list));
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        return list;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(CHANNEL_URL);
            Log.e("URL",CHANNEL_URL);
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
