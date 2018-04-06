package com.example.farhan.weather_app;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.farhan.weather_app.R.color.background;

public class MainActivity extends AppCompatActivity {


    Activity activity = this;
    Button btn;
    LocationManager locationManager;
    LocationListener locationListener;
    public static String Wurl = "";
    public static String Jdata = "";
    public static String iconURL= "";
    String w = "";
    String tf = "";
    String tc = "";
    String h = "";
    String loc = "";
    IntentFilter intentFilter = new IntentFilter();
    public TextView weather, tempF,  humid, city;
    public static ImageView imageView;
    ConstraintLayout constraintLayout;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constraintLayout = (ConstraintLayout) findViewById(R.id.conLay);
        constraintLayout.setBackgroundColor(Color.CYAN);
        tempF = (TextView) findViewById(R.id.tempF);
        imageView = (ImageView) findViewById(R.id.imageView);
        weather = (TextView) findViewById(R.id.weather);
        humid = (TextView) findViewById(R.id.humid);
        city = (TextView) findViewById(R.id.location);
        btn = (Button) findViewById(R.id.Btn);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {

                Wurl = "http://api.wunderground.com/api/7a71322e01bde96a/conditions/q/" + location.getLatitude() + "," + location.getLongitude() + ".json";
//               txt1.setText("Latitude: "+ location.getLatitude()+", Longitude: "+ location.getLongitude());
                new GetInformation(activity).execute();
                intentFilter.addAction("specialAction");

                BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        city.setText(intent.getStringExtra("location"));
                        weather.setText(intent.getStringExtra("weather"));
                        tempF.setText(intent.getStringExtra("tempf")+(char) 0x00B0+"F");

                        humid.setText("Relative % Humidity: " + intent.getStringExtra("humid"));
                    }
                };
                registerReceiver(broadcastReceiver, intentFilter);


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
            }, 10);
            return;
        } else {
            //locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
            configureButton();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;

        }
    }

    private void configureButton() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);



            }
        });


    }
}
