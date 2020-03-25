package com.example.aviato.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aviato.DatabaseHelper;
import com.example.aviato.MainActivity;
import com.example.aviato.R;

public class ForgotPasswordPage extends AppCompatActivity {

    //TODO: Here will need to implement logic similar to the SignInPage, where
    //      a check will need to be made to verify that the User can be found in the
    //      Database.
    //      All of this code will need to be replaced.
    Button forgotPasswordSubmitBtn;
    EditText forgotPasswordEmail;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_page);

        myDB = new DatabaseHelper(this);

        forgotPasswordEmail = findViewById(R.id.signin_email_address);
        forgotPasswordSubmitBtn = findViewById(R.id.forgotPasswordSubmitBtn);

        myDB = new DatabaseHelper(this);

        forgotPasswordSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isExist = myDB.verifyEmailForgotPassword(forgotPasswordEmail.getText().toString());
                //TODO: Edit this so it will do some logic like have users answer security questions before it gives the Password.
                if (isExist) {
                    Intent login = new Intent(getApplicationContext(), MainActivity.class);
                    login.putExtra("user_name", forgotPasswordEmail.getText().toString());
                    startActivity(login);
                    finish();
                }

                else {
                    forgotPasswordEmail.setText(null);
                    Toast.makeText(ForgotPasswordPage.this, "No User Found. Please Create an Account or Try Again.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), SignInPage.class);
                    startActivity(intent);
                }
            }
        });
    }
}
