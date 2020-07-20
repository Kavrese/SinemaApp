package com.example.sinemaapp.classes;

import com.example.sinemaapp.model.FullinfoVideo;
import com.example.sinemaapp.model.ModelMain;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;
public class YouTubeApi {
    public static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";
    public interface Video {
        @GET
        Call<ModelMain> getMainVideo(@Url String url);

        @GET
        Call<FullinfoVideo> getInfoVideo(@Url String url);
    }

    private static Video video = null;

    public static Video getVideo(){
        if(video == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            video = retrofit.create(Video.class);
        }
        return video;
    }
}
