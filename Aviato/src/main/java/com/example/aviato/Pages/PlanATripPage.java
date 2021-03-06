//package com.example.aviato.Pages;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Toast;
//
//import com.example.aviato.Classes.PlanATripClass;
//import com.example.aviato.DatabaseHelper;
//import com.example.aviato.MainActivity;
//import com.example.aviato.R;
//
//public class PlanATripPage extends AppCompatActivity {
//
//    DatabaseHelper databaseHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_plan_a_trip_page);
//
//        databaseHelper = new DatabaseHelper(this);
//
//        PlanATripClass trip = new PlanATripClass(
//                city_ID,
//                cityName,
//                cityPicture);
//        boolean isInserted = databaseHelper.addFlightInformation(trip);
//
//        btn_forgot_password_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isExist = databaseHelper.verifyEmailForgotPassword(forgotPasswordEmail.getText().toString());
//                //TODO: Edit this so it will do some logic like have users answer security questions before it gives the Password.
//                if (isExist) {
//                    Intent login = new Intent(getApplicationContext(), MainActivity.class);
//                    login.putExtra("email", forgotPasswordEmail.getText().toString());
//                    startActivity(login);
//                    finish();
//                }
//
//                else {
//                    forgotPasswordEmail.setText(null);
//                    Toast.makeText(PlanATripPage.this, "No User Found. Please Create an AccountClass or Try Again.", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(), SignInPageOld.class);
//                    startActivity(intent);
//                }
//            }
//        });
//    }
//}
