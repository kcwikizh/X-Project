/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.management.xtraffic.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 */
public class HttpClientUtils {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(HttpClientUtils.class);
    
    public static String GetBody(String url){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful() && response.body() != null)
                return response.body().string();
        } catch (IOException ex) {
            LOG.warn("access url: {} failed", url ,ex);
        }
        return null;
    }
    
    public static String GetBody(String url, String json){
        OkHttpClient client  = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        RequestBody requestBody = FormBody.create(json, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
            .url(url)
            .post(requestBody)
            .build();
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful() && response.body() != null)
                return response.body().string();
        } catch (IOException ex) {
            LOG.warn("access url: {} failed", url ,ex);
        }
        return null;
    }
    
}
