package com.example.tikee.Net;

import com.example.tikee.Utils.Constants;
import com.example.tikee.Utils.PostResultMessage;
import com.example.tikee.Utils.PostResultMessageResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ResultMessageGetHttp {
    public static List<PostResultMessage> getData(String studentId){
        String urlStr;
        if(studentId!=null){
            urlStr =String.format("%svideo?student_id=%s", Constants.base_url,studentId);
        } else{
            urlStr =String.format("%svideo", Constants.base_url);
        }
        PostResultMessageResponse result=null;
        try {
            URL url=new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("token", Constants.token);  //设置header中的token

            if (conn.getResponseCode() == 200){
                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                result = new Gson().fromJson(reader, new TypeToken<PostResultMessageResponse>() {
                }.getType());
                reader.close();
                in.close();

            } else{
                throw new IOException("Error");
            }

        } catch (Exception e){
            e.printStackTrace();

        }
        if(result!=null&&result.mItems!=null){
            return result.mItems;  //拿到了值
        }
        return  null;
    }

}
