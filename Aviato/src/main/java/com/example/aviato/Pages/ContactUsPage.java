package com.example.aviato.Pages;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.aviato.DatabaseHelper;
import com.example.aviato.R;

public class ContactUsPage extends AppCompatActivity {
    Button call_us, email_us;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us_page);

        call_us = findViewById(R.id.callUsBtn);
        email_us = findViewById(R.id.emailUsBtn);

        myDB = new DatabaseHelper(this);

        call_us.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                // Never gonna give you up, never gonna let you down...
                callIntent.setData(Uri.parse("tel:17607067425"));
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
