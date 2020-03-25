package com.example.aviato.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aviato.Classes.AccountClass;
import com.example.aviato.DatabaseHelper;
import com.example.aviato.R;

public class SignUpPage extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText signupFirstName, signupEmailAddress, signupPassword;
    Spinner signupPreferredCarrier, signupDefaultDepart;
    Button addAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        myDB = new DatabaseHelper(this);

        signupFirstName = findViewById(R.id.signup_name);
        signupEmailAddress = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupPreferredCarrier = findViewById(R.id.signup_preferred_carrier_spinner);
        signupDefaultDepart = findViewById(R.id.signup_default_depart_spinner);
        addAccount = findViewById(R.id.btn_add_account);

        addUser();

    }

    public void addUser() {
        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((signupFirstName.getText().toString().equals("")) && (signupPassword.getText().toString().equals(""))) {
                    Toast.makeText(SignUpPage.this, "Username and Password fields can't left blank", Toast.LENGTH_SHORT).show();
                }

                else if ((signupFirstName.getText().toString().equals(""))) {
                    Toast.makeText(SignUpPage.this, "Username cannot be left blank", Toast.LENGTH_SHORT).show();
                }

                else if (signupEmailAddress.getText().toString().equals("")) {
                    Toast.makeText(SignUpPage.this, "Email Address cannot be left blank", Toast.LENGTH_SHORT).show();
                }

                else if (signupPassword.getText().toString().equals("")) {
                    Toast.makeText(SignUpPage.this, "Password cannot be left blank", Toast.LENGTH_SHORT).show();
                }

                else {
                    AccountClass account = new AccountClass(
                            signupFirstName.getText().toString(),
                            signupEmailAddress.getText().toString(),
                            signupPassword.getText().toString(),
                            signupPreferredCarrier.getSelectedItem().toString(),
                            signupDefaultDepart.getSelectedItem().toString());

                    boolean isInserted = myDB.addAccount(account);

                    if (isInserted) {
                        Toast.makeText(SignUpPage.this, "Account Created! Please Log In.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), SignInPage.class);
                        startActivity(intent);
                        finish();
                    } else
                        Toast.makeText(SignUpPage.this, "Please, Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
