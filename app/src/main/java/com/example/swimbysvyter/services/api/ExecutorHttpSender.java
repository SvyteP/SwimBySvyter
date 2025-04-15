package com.example.swimbysvyter.services.api;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorHttpSender {
    private final String TAG = "ExecutorHttpSender";
    private final int  TIME_OUT = 5000;


    private ExecutorService executor;
    private Handler mainHandler;

    private String url;
    private String method;
    private String token;
    private RequestResult result;
    private Context ctx;


    public ExecutorHttpSender(String url, String method, String token, RequestResult result, Context ctx) {
        this.executor = Executors.newSingleThreadExecutor();
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.url = url;
        this.method = method;
        this.token = token;
        this.result = result;
        this.ctx = ctx;
    }

    public JSONObject sendRequest(){
        JSONObject resultJSON = new JSONObject();
        try{
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(TIME_OUT);
            connection.setRequestMethod(method);
            if (token != null){
                connection.setRequestProperty("Authorization","Bearer " + token);
            }

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                resultJSON = convertInputStreamToJSON(connection);
            }

        } catch (Exception e){
            Log.e(TAG, "sendRequest failed: " + e.getMessage());
        }
        return resultJSON;
    }

    private JSONObject convertInputStreamToJSON(HttpURLConnection connection){
        JSONObject jsonObject = new JSONObject();
        StringBuffer sBuffer = new StringBuffer();

        try (InputStream inStream = new BufferedInputStream(connection.getInputStream())){
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(inStream));
            String inLine;

            while((inLine = buffReader.readLine()) != null){
                sBuffer.append(inLine);
            }
            jsonObject = new JSONObject(sBuffer.toString());

        } catch (JSONException | IOException e) {
            Log.e(TAG, "convertInputStreamToJSON failed: " + e.getMessage());
        }
        return jsonObject;
    }


}
