package com.safetyalert.app.activities;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.safetyalert.app.R;

public class MapActivity extends AppCompatActivity {

    private Button btnHospitals, btnPolice;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        btnHospitals = findViewById(R.id.btnHospitals);
        btnPolice = findViewById(R.id.btnPolice);

        btnHospitals.setOnClickListener(v -> openNearby("hospital"));
        btnPolice.setOnClickListener(v -> openNearby("police"));
    }

    private void openNearby(String placeType) {
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            String uri;
            if (location != null) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                uri = String.format("geo:%f,%f?q=%s", lat, lng, placeType);
            } else {
                uri = "geo:0,0?q=" + placeType + " near me";
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
            }
        }).addOnFailureListener(e -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + placeType + " near me")));
        });
    }
}
