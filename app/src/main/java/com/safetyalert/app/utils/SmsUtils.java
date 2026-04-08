package com.safetyalert.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class SmsUtils {

    public static void sendSosSmsToSavedContacts(Context ctx, boolean isHarassment) {
        SharedPreferences prefs = ctx.getSharedPreferences("safealert_prefs", Context.MODE_PRIVATE);
        String p1 = prefs.getString("contact_phone_1", "");
        String p2 = prefs.getString("contact_phone_2", "");
        String name = prefs.getString("user_name", "Someone");

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(ctx);
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            String locLink = "Location not available";
            if (location != null) {
                locLink = "https://maps.google.com/?q=" + location.getLatitude() + "," + location.getLongitude();
            }

            String msg;
            if (isHarassment) {
                msg = "HARASSMENT ALERT: " + name + " might be in danger. Location: " + locLink;
            } else {
                msg = "SOS ALERT: " + name + " needs help. Location: " + locLink;
            }

            SmsManager sms = SmsManager.getDefault();
            try {
                if (p1 != null && !p1.isEmpty()) sms.sendTextMessage(p1, null, msg, null, null);
                if (p2 != null && !p2.isEmpty()) sms.sendTextMessage(p2, null, msg, null, null);
                Toast.makeText(ctx, "SOS message sent", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(ctx, "Failed to send SMS: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }).addOnFailureListener(e -> Toast.makeText(ctx, "Location fetch failed", Toast.LENGTH_SHORT).show());
    }
}
