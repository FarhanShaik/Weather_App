package com.example.farhan.weather_app;

        import android.app.Service;
        import android.appwidget.AppWidgetManager;
        import android.content.ComponentName;
        import android.content.Context;
        import android.content.Intent;
        import android.location.Location;
        import android.location.LocationManager;
        import android.os.IBinder;
        import android.support.annotation.IntDef;
        import android.support.annotation.Nullable;
        import android.util.Log;
        import android.widget.RemoteViews;
        import android.widget.Toast;

        import layout.Weather_App_Widget;

        import static com.example.farhan.weather_app.MainActivity.Wurl;


public class MyService extends Service{
    boolean isPaused = true;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            Log.d("TAAGGGG", "Service Start");

            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);


            Wurl = "http://api.wunderground.com/api/7a71322e01bde96a/conditions/q/" + location.getLatitude() + "," + location.getLongitude() + ".json";


            /*AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,getClass()));
            RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.weather__app__widget);

            for(int appWidgetId : appWidgetIds) {
                new GetInformation(views, appWidgetId, appWidgetManager);
            }*/
        } catch (Exception e) {
            Log.d("TAA", "There be an error catch in yo dayum MyService");
            Toast.makeText(MyService.this, "ERROR", Toast.LENGTH_SHORT).show();
        }



        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}