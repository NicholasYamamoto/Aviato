package com.example.aviato.Pages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
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

    public static final String MY_PREFERENCES = "MY_PREFS";
    public static final String EMAIL = "EMAIL";
    public static final String CLIENT_ID = "CLIENT_ID";
    public static final String LOGIN_STATUS = "LOGGED_IN";
    public static SharedPreferences sharedPreferences, loginPreferences;

    EditText signinEmailAddress, signinPassword;
    Button loginBtn;
    TextView forgotPassword;
    CheckBox rememberMe;
    SharedPreferences.Editor loginPrefsEditor;
    SQLiteOpenHelper databaseHelper;
    SQLiteDatabase databaseInstance;
    Cursor cursor;
    int clientID;
    Boolean saveLogin, isLoggedIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        loginBtn = findViewById(R.id.loginBtn);

        signinEmailAddress = findViewById(R.id.edt_forgot_email_address);
        signinPassword = findViewById(R.id.edt_signin_password);

        forgotPassword = findViewById(R.id.btn_forgot_password_tv);
        rememberMe = findViewById(R.id.cbx_remember_me);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        sharedPreferences = getSharedPreferences(MY_PREFERENCES, 0);
        isLoggedIn = sharedPreferences.getBoolean(LOGIN_STATUS, false);

        // Verifies if the User is Logged In
        if (isLoggedIn) {
            Intent login = new Intent(getApplicationContext(), MainActivity.class);
            login.putExtra("email", signinEmailAddress.getText().toString());
            startActivity(login);
            finish();
            return;
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyAccountLogin();
            }
        });

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin) {
            signinEmailAddress.setText(loginPreferences.getString("email", ""));
            signinPassword.setText(loginPreferences.getString("password", ""));
            rememberMe.setChecked(true);
        }

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotPassword = new Intent(getApplicationContext(), ForgotPasswordPage.class);
                startActivity(forgotPassword);
            }
        });
    }

    public void verifyAccountLogin() {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            databaseInstance = databaseHelper.getReadableDatabase();

            // Filter the Login information
            String filteredEmail = DatabaseHelper.filterString(signinEmailAddress.getText().toString());
            String filteredPassword = DatabaseHelper.filterString(signinPassword.getText().toString());

            if (signinEmailAddress.getText().toString().equals(""))
                Toast.makeText(SignInPage.this, "Email Address cannot be left blank", Toast.LENGTH_SHORT).show();

            else if (signinPassword.getText().toString().equals(""))
                Toast.makeText(SignInPage.this, "Password cannot be left blank", Toast.LENGTH_SHORT).show();

            else {
                cursor = DatabaseHelper.login(databaseInstance, filteredEmail, filteredPassword);

                if (cursor != null && cursor.getCount() == 1) {
                    cursor.moveToFirst();

                    String email = cursor.getString(1);
                    clientID = cursor.getInt(3);

                    sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putInt(CLIENT_ID, clientID);
                    editor.putString(EMAIL, email);
                    editor.putBoolean(LOGIN_STATUS, true);

                    editor.apply();

                    // Open the Home Page if Login is Successful!
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("email", signinEmailAddress.getText().toString());
                    startActivity(intent);
                    finish();
                } else
                    Toast.makeText(SignInPage.this, "Account Not Found. Please Try Again.", Toast.LENGTH_SHORT).show();
            }

        } catch (SQLiteException e) {
            System.out.println("SIGN IN PAGE - Signing In ERROR");
            System.out.println(e.toString());
            Toast.makeText(getApplicationContext(), "Error: Database Unavailable.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            if (cursor != null)
                cursor.close();

            if (databaseInstance != null)
                databaseInstance.close();

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "There was an error closing the database.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
    }
}
