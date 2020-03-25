package com.example.aviato.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aviato.DatabaseHelper;
import com.example.aviato.MainActivity;
import com.example.aviato.R;

public class SignInPage extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText signinEmail, signinPassword;
    TextView forgotPassword;
    Button loginBtn;
    CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        myDB = new DatabaseHelper(this);

        signinEmail = findViewById(R.id.signin_email_address);
        signinPassword = findViewById(R.id.signin_password);
        loginBtn = findViewById(R.id.loginBtn);
        forgotPassword = findViewById(R.id.forgotPasswordBtn);
        rememberMe = findViewById(R.id.rememberMeChkbx);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPassword = new Intent(getApplicationContext(), ForgotPasswordPage.class);
                startActivity(forgotPassword);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isExist = myDB.verifyAccount(signinEmail.getText().toString(),
                        signinPassword.getText().toString());
                if (isExist) {
                    Intent login = new Intent(getApplicationContext(), MainActivity.class);
                    login.putExtra("user_name", signinEmail.getText().toString());
                    startActivity(login);
                    finish();
                }
                
                else {
                    signinPassword.setText(null);
                    Toast.makeText(SignInPage.this, "Log In Failed. Please Try Again.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), SignInPage.class);
                    startActivity(intent);
                }
            }
        });
    }
}
