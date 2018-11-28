package com.zzg.zcib.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zzg.zcib.activity.LoginActivity;
import com.zzg.zcib.activity.MainActivity;
import com.zzg.zcib.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MyVolley {
    //10.0.2.2  安卓访问localhost
    //39.108.220.36
    public static final String IP_="39.108.220.36";

    public interface VolleyCallback {
        void onSuccess(String s);
    }

    public interface ImgCallBack{
        void onSuccess(Bitmap bitmap);
    }

    public static void goVolley(String url, Context context1,final VolleyCallback callback){
        final Context context=context1;
        RequestQueue mQueue= Volley.newRequestQueue(context);
        StringRequest stringRequest=new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        callback.onSuccess(s);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("TAG",volleyError.getMessage(),volleyError);
                    }
                }
        )
        {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString=new String(response.data,"utf-8");
                    return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }catch (Exception je){
                    return Response.error(new ParseError(je));
                }
            }
        };
        mQueue.add(stringRequest);
    }




    public static void imageVolley(String url, final Context context1, final ImgCallBack callback){
        RequestQueue requestQueue = Volley.newRequestQueue(context1);
        //实例ImageRequest，并设置参数，分别为地址，响应成功监听，最大宽、高，图片质量，网络异常监听
        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        callback.onSuccess(bitmap);
                    }
                }, 500, 500, Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
//                        Toast.makeText(context1,"网络异常",Toast.LENGTH_SHORT).show();
                    }
                });
        //添加请求
        requestQueue.add(imageRequest);
    }

}
