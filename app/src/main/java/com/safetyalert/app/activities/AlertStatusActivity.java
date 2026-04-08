package com.safetyalert.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.safetyalert.app.R;

public class AlertStatusActivity extends AppCompatActivity {

    private TextView tvStatus;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_status);

        tvStatus = findViewById(R.id.tvStatus);
        btnCancel = findViewById(R.id.btnCancelSOS);

        tvStatus.setText("SOS active — sharing location with your emergency contacts");

        btnCancel.setOnClickListener(v -> {
            Intent svc = new Intent(this, com.safetyalert.app.services.LocationService.class);
            svc.setAction("STOP_SOS");
            startService(svc);
            finish();
        });
    }
}
