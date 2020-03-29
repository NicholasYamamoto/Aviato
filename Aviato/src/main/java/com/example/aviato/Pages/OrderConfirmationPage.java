package com.example.aviato.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.aviato.R;

public class OrderConfirmationPage extends AppCompatActivity {
    String email = "aviato-orders@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation_page);


        // TODO: Find an alternative to sending a generated Receipt email
//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_EMAIL, email);
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Your Aviato Order!");

        Intent intent = new Intent(getApplicationContext(), OrderConfirmationPage.class);
        startActivity(intent);
        finish();
    }
}
