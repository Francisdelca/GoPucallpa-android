package com.example.gopucallpa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    private static final int REQUEST_LOCATION = 1;

    TextView showLocationText;

    LocationManager locationManager;
    String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        showLocationText = findViewById(R.id.show_location);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            OnGPS();
        }
        else
        {
            getLocation();
        }

        WebView webView = findViewById(R.id.MainView);
        webView.loadUrl("http://app.gopucallpa.pe/");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    private void getLocation() {
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION ) !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            Location LocationNetwork = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
            Location LocationPassive = locationManager.getLastKnownLocation(locationManager.PASSIVE_PROVIDER);

            if(LocationGps != null)
            {
                double lat = LocationGps.getLatitude();
                double lon = LocationGps.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(lon);

                showLocationText.setText(latitude+"\n"+longitude);
            }
            else if(LocationNetwork != null)
            {
                double lat = LocationNetwork.getLatitude();
                double lon = LocationNetwork.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(lon);

                showLocationText.setText(latitude+"\n"+longitude);
            }
            else if(LocationPassive != null)
            {
                double lat = LocationPassive.getLatitude();
                double lon = LocationPassive.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(lon);

                showLocationText.setText(latitude+"\n"+longitude);
            }
            else
            {
                Toast.makeText(this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }

    private void sendLocation()
    {
    }
}
