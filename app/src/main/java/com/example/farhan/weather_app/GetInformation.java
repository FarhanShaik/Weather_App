package com.example.farhan.weather_app;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static com.example.farhan.weather_app.MainActivity.Jdata;
import static com.example.farhan.weather_app.MainActivity.Wurl;
import static com.example.farhan.weather_app.MainActivity.h;
import static com.example.farhan.weather_app.MainActivity.loc;
import static com.example.farhan.weather_app.MainActivity.tc;
import static com.example.farhan.weather_app.MainActivity.tf;
import static com.example.farhan.weather_app.MainActivity.w;



/**
 * Created by Farhan on 10/28/2017.
 */

public class GetInformation extends AsyncTask<Void, Void, Void> {

    String data="";
    @Override
    protected Void doInBackground(Void... voids) {
        try{
            URL url = new URL(Wurl);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();


            con.setRequestMethod("GET");
            con.connect();
            BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = "";
            String value = bf.readLine();
            System.out.println("result: " + value);
            while(line!=null){
                line = bf.readLine();
                data = data+line;
            }
            Jdata= data;
            JSONObject json = new JSONObject(Jdata);
            loc= json.getJSONObject("current_observation").getJSONObject("display_location").getString("full");
            w= json.getJSONObject("current_observation").getString("weather");
            tf=json.getJSONObject("current_observation").getString("temp_f");
            tc=json.getJSONObject("current_observation").getString("temp_c");
            h=json.getJSONObject("current_observation").getString("relative_humidity");



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
}
