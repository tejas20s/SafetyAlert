package com.safetyalert.app.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.safetyalert.app.R;

public class ContactsActivity extends AppCompatActivity {

    private EditText etName1, etPhone1, etName2, etPhone2;
    private Button btnSave;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        prefs = getSharedPreferences("safealert_prefs", MODE_PRIVATE);

        etName1 = findViewById(R.id.etName1);
        etPhone1 = findViewById(R.id.etPhone1);
        etName2 = findViewById(R.id.etName2);
        etPhone2 = findViewById(R.id.etPhone2);
        btnSave = findViewById(R.id.btnSaveContacts);

        loadSaved();

        btnSave.setOnClickListener(v -> {
            String n1 = etName1.getText().toString().trim();
            String p1 = etPhone1.getText().toString().trim();
            String n2 = etName2.getText().toString().trim();
            String p2 = etPhone2.getText().toString().trim();

            if (TextUtils.isEmpty(p1) && TextUtils.isEmpty(p2)) {
                Toast.makeText(this, "Add at least one phone number", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences.Editor ed = prefs.edit();
            ed.putString("contact_name_1", n1);
            ed.putString("contact_phone_1", p1);
            ed.putString("contact_name_2", n2);
            ed.putString("contact_phone_2", p2);
            ed.apply();

            Toast.makeText(this, "Contacts saved", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void loadSaved() {
        etName1.setText(prefs.getString("contact_name_1", ""));
        etPhone1.setText(prefs.getString("contact_phone_1", ""));
        etName2.setText(prefs.getString("contact_name_2", ""));
        etPhone2.setText(prefs.getString("contact_phone_2", ""));
    }
}
