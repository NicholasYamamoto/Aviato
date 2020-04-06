package com.example.aviato.Pages;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aviato.DatabaseHelper;
import com.example.aviato.R;


public class SignUpPage extends AppCompatActivity {

    EditText signupFirstName, signupEmailAddress, signupCreditCardNumber,
            signupPhoneNumber, signupPassword;
    Button addAccount;
    private int clientID;
    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase databaseInstance;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        addAccount = findViewById(R.id.btn_add_account);

        signupFirstName = findViewById(R.id.edt_signup_first_name);
        signupEmailAddress = findViewById(R.id.edt_signup_email_address);
        signupPassword = findViewById(R.id.edt_signup_password);
        signupPhoneNumber = findViewById(R.id.edt_signup_phone_number);
        signupCreditCardNumber = findViewById(R.id.edt_signup_phone_number);

        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerAccount();
            }
        });

    }

    public void registerAccount() {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            databaseInstance = databaseHelper.getWritableDatabase();

            cursor = DatabaseHelper.selectAccount(databaseInstance, DatabaseHelper.filterString(signupEmailAddress.getText().toString()));

            if (cursor != null && cursor.getCount() > 0)
                Toast.makeText(SignUpPage.this, "Error: AccountClass Already Exists.", Toast.LENGTH_SHORT).show();

            else {
                DatabaseHelper.insertClient(databaseInstance,
                        signupFirstName.getText().toString(),
                        signupEmailAddress.getText().toString(),
                        signupPhoneNumber.getText().toString(),
                        signupCreditCardNumber.getText().toString());

                cursor = DatabaseHelper.selectClientID(databaseInstance,
                        signupFirstName.getText().toString(),
                        signupEmailAddress.getText().toString(),
                        signupPhoneNumber.getText().toString(),
                        signupCreditCardNumber.getText().toString());

                if (cursor != null && cursor.getCount() == 1) {
                    cursor.moveToFirst();

                    DatabaseHelper.insertAccount(databaseInstance,
                            signupEmailAddress.getText().toString(),
                            signupPassword.getText().toString(),
                            cursor.getInt(0));

                    // Display the Sign In Page if account creation is successful
                    Intent intent = new Intent(getApplicationContext(), SignInPage.class);
                    Toast.makeText(SignUpPage.this, "Account Created! Please Log In.", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
            }

        } catch (SQLiteException e) {
            System.out.println("SIGN UP PAGE - Account Creation ERROR");
            System.out.println(e.toString());
            Toast.makeText(getApplicationContext(), "Error: Database is Unavailable.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            cursor.close();
            databaseInstance.close();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error: Failed to Close Database/Cursor.", Toast.LENGTH_SHORT).show();
        }
    }
}
