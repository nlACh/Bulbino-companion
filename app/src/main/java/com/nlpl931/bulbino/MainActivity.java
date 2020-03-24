package com.nlpl931.bulbino;


import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String BASE_URL = "http://";
    private TextView tv;
    private Button connect;
    private Button rainbow;
    private Button off;
    private EditText et;
    private SeekBar seekBar;
    private boolean once = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.et);
        tv = findViewById(R.id.tv);
        connect = findViewById(R.id.connect);
        off = findViewById(R.id.off);
        rainbow = findViewById(R.id.rainbow);
        seekBar = findViewById(R.id.seekBar);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(once){
                    BASE_URL += et.getText();
                }
                tv.setText(BASE_URL);
                requestData(BASE_URL+"/");
                once = false;
            }
        });
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData(BASE_URL+"/off");
            }
        });
        rainbow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData(BASE_URL+"/rainbow");
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //tv.setText(String.valueOf(progress));
                postRequest(BASE_URL+"/post", "b", progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    // TODO: try to use asynchttpClient for post requests!
    private void requestData(String url){
        AsyncHttpClient c = new AsyncHttpClient();
        c.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res){
                Log.d("Bitcoin", "JSON: " + res.toString());
                try {
                    String price = res.getString("status");
                    tv.setText(price);
                } catch (Exception e){
                    Log.e("Bitcoin", e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject res){
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_LONG).show();
                Log.e("ERROR", e.toString());
                Log.d("Bitcoin", "Failed:"+res);
                tv.setText(res.toString());
            }
        });
    }
    private void requestData(String url, String key, String val){
        String uri = url + "?" + "mode=" + key + "&" + "value=" + val;
        tv.setText(uri);
        AsyncHttpClient c = new AsyncHttpClient();
        c.get(uri, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res){
                Log.d("Bitcoin", "JSON: " + res.toString());
                try {
                    String price = res.getString("status");
                    tv.setText(price);
                } catch (Exception e){
                    Log.e("Bitcoin", e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject res){
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_LONG).show();
                Log.e("ERROR", e.toString());
                Log.d("Bitcoin", "Failed:"+res);
                tv.setText(res.toString());
            }
        });
    }

    private void postRequest(String url, String key, int val){
        RequestParams rp = new RequestParams();
        rp.put("mode", key);
        rp.put("value", String.valueOf(val));
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(url, rp, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject res){
                try {
                    tv.setText(res.getString("status"));
                }catch (Exception e){
                    Log.e("Request", e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject res){
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_LONG).show();
                Log.e("Response", e.toString());
            }
        });
    }
}
