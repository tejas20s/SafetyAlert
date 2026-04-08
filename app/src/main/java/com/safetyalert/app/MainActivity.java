package com.safetyalert.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.safetyalert.app.activities.AlertStatusActivity;
import com.safetyalert.app.activities.ContactsActivity;
import com.safetyalert.app.activities.LoginActivity;
import com.safetyalert.app.activities.MapActivity;
import com.safetyalert.app.utils.PermissionUtils;
import com.safetyalert.app.utils.SmsUtils;

public class MainActivity extends AppCompatActivity {

    private MaterialButton btnSOS, btnHarass, btnContacts, btnNearby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPrefManager sp = new SharedPrefManager(this);
        if (!sp.isLoggedIn()) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        btnSOS = findViewById(R.id.btnSOS);
        btnHarass = findViewById(R.id.btnHarass);
        btnContacts = findViewById(R.id.btnContacts);
        btnNearby = findViewById(R.id.btnNearby);

        btnContacts.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ContactsActivity.class)));

        btnNearby.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, MapActivity.class)));


        btnSOS.setOnClickListener(v -> {
            if (!PermissionUtils.hasSmsPermission(this) ||
                    !PermissionUtils.hasLocationPermission(this)) {

                PermissionUtils.requestNecessaryPermissions(this);
                Toast.makeText(this, "Grant SMS & Location permissions then press SOS again.",
                        Toast.LENGTH_LONG).show();
                return;
            }

            Intent svc = new Intent(this, com.safetyalert.app.services.LocationService.class);
            svc.setAction("START_SOS");
            startForegroundService(svc);

            startActivity(new Intent(MainActivity.this, AlertStatusActivity.class));
        });

        btnHarass.setOnClickListener(v -> {
            if (!PermissionUtils.hasSmsPermission(this) ||
                    !PermissionUtils.hasLocationPermission(this)) {

                PermissionUtils.requestNecessaryPermissions(this);
                Toast.makeText(this,
                        "Grant SMS & Location permissions then press Harassment Alert again.",
                        Toast.LENGTH_LONG).show();
                return;
            }

            SmsUtils.sendSosSmsToSavedContacts(this, true);
            Toast.makeText(this,
                    "Harassment alert sent to emergency contacts.",
                    Toast.LENGTH_SHORT).show();
        });
    }
}
