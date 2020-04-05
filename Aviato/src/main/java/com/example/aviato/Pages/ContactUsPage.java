package com.example.aviato.Pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.aviato.R;

import static android.Manifest.permission.CALL_PHONE;
import static java.security.AccessController.getContext;


public class ContactUsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us_page);

        Button call_us, email_us;

        call_us = findViewById(R.id.btn_call_us);
        email_us = findViewById(R.id.btn_email_us);

        call_us.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // Never gonna give you up, never gonna let you down...
                callIntent.setData(Uri.parse("tel:17607067425"));
                startActivity(callIntent);

                if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(callIntent);
                } else {
                    requestPermissions(new String[]{CALL_PHONE}, 1);
                }
                startActivity(callIntent);
            }
        });

        email_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, "aviato_support@gmail.com");
                emailIntent.setType("plain/text");

                startActivity(emailIntent);
            }
        });


    }
}





