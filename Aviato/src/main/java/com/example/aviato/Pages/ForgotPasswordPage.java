package com.example.aviato.Pages;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.aviato.DatabaseHelper;
import com.example.aviato.R;

public class ForgotPasswordPage extends AppCompatActivity {
    Button forgotPasswordSubmitBtn;
    EditText forgotPasswordEmail;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_page);

        databaseHelper = new DatabaseHelper(this);

        forgotPasswordEmail = findViewById(R.id.edt_forgot_email_address);
        forgotPasswordSubmitBtn = findViewById(R.id.btn_forgot_password_submit);

        forgotPasswordSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*  TODO: Edit this so it will do some logic like have users answer security questions before it gives the Password.
                      Might need to implement a column in the database for Security Questions and their answers in order
                      to successfully recover account and Login, since we don't have an email setup to send a Verification link
                      or anything. That's way too extra for this project.
                */
//                boolean isExist = databaseHelper.verifyEmailForgotPassword(forgotPasswordEmail.getText().toString());
//                if (isExist) {
//                    Intent login = new Intent(getApplicationContext(), MainActivity.class);
//                    login.putExtra("email", forgotPasswordEmail.getText().toString());
//                    startActivity(login);
//                    finish();
//                }
//                else {
//                    forgotPasswordEmail.setText(null);
//                    Toast.makeText(ForgotPasswordPage.this, "Error: No User Found. Please Create an AccountClass or Try Again.", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(), SignInPageOld.class);
//                    startActivity(intent);
//                }
            }
        });
    }
}
