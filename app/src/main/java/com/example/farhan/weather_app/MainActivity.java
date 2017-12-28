package com.example.farhan.weather_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    Button btn;
    LocationManager locationManager;
    LocationListener locationListener;
    public static String Wurl="";
    public static String Jdata = "";
    public static String w="";
    public static String tf="";
    public static String tc="";
    public static String h="";
    public static String loc="";

    public TextView weather, tempF, tempC, humid, city;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempF = (TextView) findViewById(R.id.tempF);
        tempC = (TextView) findViewById(R.id.tempC);
        weather = (TextView) findViewById(R.id.weather);
        humid = (TextView) findViewById(R.id.humid);
        city = (TextView) findViewById(R.id.location);
        btn = (Button) findViewById(R.id.Btn);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Wurl = "http://api.wunderground.com/api/7a71322e01bde96a/conditions/q/"+location.getLatitude()+","+location.getLongitude()+".json";
//               txt1.setText("Latitude: "+ location.getLatitude()+", Longitude: "+ location.getLongitude());
                new GetInformation().execute();

                city.setText("Location: "+loc);
                weather.setText("Weather: "+w);
                tempF.setText("Temperature(F): "+tf);
                tempC.setText("Temperature(C): "+tc);
                humid.setText("Relative % Humidity: "+h);

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
                   Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET
           }, 10);
            return;
        } else{
            //locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
            configureButton();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
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
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);



            }
        });


    }
}
