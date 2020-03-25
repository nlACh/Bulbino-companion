package com.nlpl931.bulbino;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class httpReq{

    private AsyncHttpClient c;
    private Context context;

    httpReq(Context s){
        c = new AsyncHttpClient();
        context = s;
    }

    void requestData(String url) {
        c.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res){
                Log.d("Bitcoin", "JSON: " + res.toString());
                try {
                    String price = res.getString("status");
                    //tv.setText(price);
                } catch (Exception e){
                    Log.e("Bitcoin", e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject res){
                Toast.makeText(context, "Request Failed", Toast.LENGTH_LONG).show();
                Log.e("ERROR", e.toString());
                Log.d("Bitcoin", "Failed:"+res);
                //tv.setText(res.toString());
            }
        });
    }

    public void postRequest(String url, String key, int val){
        RequestParams rp = new RequestParams();
        rp.put("mode", key);
        rp.put("value", String.valueOf(val));
        c.get(url, rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res){
                try {
                    //tv.setText(res.getString("status"));
                }catch (Exception e){
                    Log.e("Request", e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject res){
                Toast.makeText(context, "Request Failed", Toast.LENGTH_LONG).show();
                Log.e("Response", e.toString());
            }
        });
    }
}
