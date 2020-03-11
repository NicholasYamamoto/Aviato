package com.example.aviato.Pages;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.aviato.DatabaseHelper;
import com.example.aviato.R;

public class LoginOptionsPage extends AppCompatActivity {
    Button sign_in, sign_up, details, orderdetails;
    DatabaseHelper myDB;
    Animation uptodown, downtoup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options_page);
        uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);

        sign_in = findViewById(R.id.signin);
        sign_up = findViewById(R.id.signup);
        details = findViewById(R.id.userinfo);
        orderdetails = findViewById(R.id.order_details);

        sign_in.setAnimation(downtoup);
        sign_up.setAnimation(uptodown);


        myDB = new DatabaseHelper(this);
        User_info();
        Order_data();

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signinpage = new Intent(getApplicationContext(), SignInPage.class);
                startActivity(signinpage);
                finish();
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signuppage = new Intent(getApplicationContext(), SignUpPage.class);
                startActivity(signuppage);
                finish();
            }
        });

    }

    public void User_info() {

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDB.getData();
                if (res.getCount() == 0) {
                    showMessage("Error", "nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    buffer.append("Id : " + res.getString(0) + "\n");
                    buffer.append("Username : " + res.getString(1) + "\n");
                    buffer.append("Password : " + res.getString(2) + "\n");
                    buffer.append("Carrier : " + res.getString(3) + "\n");
                    buffer.append("City : " + res.getString(4) + "\n");
                    buffer.append("Order Details : " + res.getString(5) + "\n");

                }
                showMessage("Data", buffer.toString());


            }
        });

    }

    public void Order_data() {
        orderdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDB.getOrderDetails();
                if (res.getCount() == 0) {
                    showMessage("Error", "nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {

                    buffer.append("Id : " + res.getString(0) + "\n");
                    buffer.append("Item Name : " + res.getString(1) + "\n");
                    buffer.append("Quantity : " + res.getString(2) + "\n");
                    buffer.append("Price : " + res.getString(3) + "\n");

                }
                showMessage("Data", buffer.toString());


            }
        });
    }

    public void showMessage(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

}

