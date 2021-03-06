package com.example.farhan.weather_app;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.annotation.StringDef;
import android.support.annotation.StyleRes;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Display;
import android.widget.RemoteViews;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.farhan.weather_app.MainActivity.Jdata;
import static com.example.farhan.weather_app.MainActivity.Wurl;
import static com.example.farhan.weather_app.MainActivity.iconURL;
import static com.example.farhan.weather_app.MainActivity.imageView;


/**
 * Created by Farhan on 10/28/2017.
 */

public class GetInformation extends AsyncTask<Void, Void, Void> {

    String data="";
    private OkHttpClient okHttpClient;
    private Request request;
    Bitmap bitmap= null;

    Activity activity = null;
    private RemoteViews views = null;
    private int WidgetID;
    private AppWidgetManager WidgetManager = null;


    public GetInformation(Activity activity){
        this.activity = activity;
    }

    public GetInformation(RemoteViews views, int appWidgetID, AppWidgetManager appWidgetManager) {
        Log.d("TAAGGGG", "constructor go");
        this.views = views;
        this.WidgetID = appWidgetID;
        this.WidgetManager = appWidgetManager;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        Log.d("TAAGGGG", "AsyncStart");
        try{

            if(Wurl!="") {
                okHttpClient = new OkHttpClient();
                request = new Request.Builder().url(Wurl).build();
                Response response = okHttpClient.newCall(request).execute();
                data = response.body().string();

                JSONObject json = new JSONObject(data);
                URL urll = new URL(json.getJSONObject("current_observation").getString("icon_url"));
                HttpURLConnection connection = (HttpURLConnection) urll.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream is = connection.getInputStream();
                bitmap=BitmapFactory.decodeStream(is);



            }


            Log.d("TAAGGGG", "AsyncFinish");
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        try {
            Log.d("TAAGGGG", "PostExecuteStart");
            Log.d("TAAGGGG", data);
            Jdata= data;
            JSONObject json = null;
            json = new JSONObject(Jdata);

            Log.d("TAAGGGG", "PostExecuteStart");
            if(activity!=null)
                imageView.setImageBitmap(bitmap.createScaledBitmap(bitmap,1000,1000,true));
            Log.d("TAAGGGG", "PostExecuteStart");
            String loca= json.getJSONObject("current_observation").getJSONObject("display_location").getString("full");
            String wa= json.getJSONObject("current_observation").getString("weather");
            String tfa=json.getJSONObject("current_observation").getString("temp_f");
            String tca=json.getJSONObject("current_observation").getString("temp_c");
            String ha=json.getJSONObject("current_observation").getString("relative_humidity");


            if(WidgetManager!=null && views!=null) {
                Log.d("TAAGGGG", "Set Widget");
                views.setImageViewBitmap(R.id.imageView3, bitmap.createScaledBitmap(bitmap, 500, 500, true));
                views.setTextViewText(R.id.textView2, tfa + (char) 0x00B0 + "F");
                views.setTextViewText(R.id.textView3, wa);
                WidgetManager.updateAppWidget(WidgetID, views);
                /*Intent i = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
                i.putExtra("image", bitmap.createScaledBitmap(bitmap,500,500,true));
                i.putExtra("textView2", tfa+  (char)0x00B0+"F");
                i.putExtra("textView3", wa);*/
            }


            if(activity!=null) {
                Intent i = new Intent("specialAction");

                i.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                i.putExtra("location", loca);
                i.putExtra("weather", wa);
                i.putExtra("tempf", tfa);
                i.putExtra("tempc", tca);
                i.putExtra("humid", ha);

                activity.getApplicationContext().sendBroadcast(i);
            }
        } catch (JSONException e) {
            Log.d("TAAGGGG", e+"");
            e.printStackTrace();
        }

    }
}
