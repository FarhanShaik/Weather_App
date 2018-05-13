package com.example.farhan.weather_app;

        import android.app.Service;
        import android.content.Intent;
        import android.os.IBinder;
        import android.support.annotation.IntDef;
        import android.support.annotation.Nullable;
        import android.widget.Toast;



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


        } catch (Exception e) {
            Toast.makeText(MyService.this, "ERROR", Toast.LENGTH_SHORT).show();
        }



        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}