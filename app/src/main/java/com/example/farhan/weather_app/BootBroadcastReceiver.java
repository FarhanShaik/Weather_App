package com.example.farhan.weather_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import layout.Weather_App_Widget;

/**
 * Created by Farhan on 5/17/2018.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TAAGGGG", "Booted");
//        Intent startServiceIntent = new Intent(, MyService.class);
        //context.startService(startServiceIntent);
    }
}
