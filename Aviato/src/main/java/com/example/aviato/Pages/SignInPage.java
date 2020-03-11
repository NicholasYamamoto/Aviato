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

public class SignInPage extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText edt_signinName, edt_signinPassword;
    Button btn_signinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);
        myDB = new DatabaseHelper(this);
        edt_signinName = findViewById(R.id.signin_id);
        edt_signinPassword = findViewById(R.id.signin_password);
        btn_signinBtn = findViewById(R.id.signinBtn);
        btn_signinBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean isExist = myDB.verifyAccount(edt_signinName.getText().toString(),
                        edt_signinPassword.getText().toString());
                if (isExist) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("user_name", edt_signinName.getText().toString());
                    startActivity(intent);
                    finish();
                } else {
                    edt_signinPassword.setText(null);
                    Toast.makeText(SignInPage.this, "Log In Failed. Please Try Again.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), SignInPage.class);
                    startActivity(intent);
                }
            }
        });
    }
}
