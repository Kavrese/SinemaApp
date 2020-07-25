package com.example.sinemaapp.classes;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.sinemaapp.model.FullinfoVideo;
import com.example.sinemaapp.model.ModelMain;

import org.json.JSONObject;

import retrofit2.Response;

public class Errors {
    public Errors (Response<ModelMain> responseM, Response<FullinfoVideo> responseF, String mode, Context context,String from) {
        Response response = null;
        if(mode.equals("ModelMain"))
            response = responseM;
        else if(mode.equals("FullVideoApi"))
            response = responseF;
        else if(mode.equals("No full info")){
            Toast.makeText(context, "Error info: No info", Toast.LENGTH_SHORT).show();
            return;
        }

        if (response.errorBody() != null) {
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                Log.e("error", jObjError.getJSONObject("error").getString("message"));
                if (jObjError.getJSONObject("error").getString("message").equals("The request cannot be completed because you have exceeded your <a href=\"/youtube/v3/getting-started#quota\">quota</a>.")) {
                    Toast.makeText(context, "Error "+mode+" : Закончились квоты на api. Подождите до полуночи, пока они обновляются, и попробуйте еще раз. Is in " + from, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
    public Errors(Throwable t,Context context,String from){
        Toast.makeText(context, t.getMessage() + " - is in "+from, Toast.LENGTH_LONG).show();
    }
}
