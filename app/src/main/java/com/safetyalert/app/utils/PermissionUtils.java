package com.safetyalert.app.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtils {

    public static final int REQ_PERMS = 101;

    public static boolean hasLocationPermission(Activity act) {
        return ContextCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasSmsPermission(Activity act) {
        return ContextCompat.checkSelfPermission(act, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestNecessaryPermissions(Activity act) {
        ActivityCompat.requestPermissions(act, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.SEND_SMS
        }, REQ_PERMS);
    }
}
