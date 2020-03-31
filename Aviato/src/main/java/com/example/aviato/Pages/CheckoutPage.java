package com.example.aviato.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.aviato.AppDatabaseHelper;
import com.example.aviato.R;

//import com.example.aviato.Classes.OrderClass;

    /*
        TODO:   These values used for Checkout will be populated by the Flight Information table,
                after which it will call OrderConfirmationPage, which will handle inserting everything
                into the Orders table.
                Populate these values from a Cursor object created from the Flight Information table
    */

public class CheckoutPage extends AppCompatActivity {
    AppDatabaseHelper appDatabaseHelper;

    TextView tv_departing_city, tv_departing_date, tv_departing_time, tv_departing_gate,
             tv_destination_city, tv_destination_date, tv_destination_time, tv_destination_gate,
             tv_return_city, tv_return_date, tv_return_time, tv_return_gate,
             tv_payment_method_card_number, tv_total;

    EditText edt_payment_method_card_number, edt_payment_method_security_code;

    Button btn_submit_order, btn_cancel_order;
    Spinner spinner_payment_method_card_type, spinner_credit_card_month, spinner_credit_card_year;

    String testString = "HELLO WORLD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_page);

        appDatabaseHelper = new AppDatabaseHelper(this);

        // TODO: Edit this so it will set the text based on the query from
        //       the Flight Information table
        tv_departing_city = findViewById(R.id.tv_departing_city);
        tv_departing_date = findViewById(R.id.tv_departing_date);
        tv_departing_time = findViewById(R.id.tv_departing_time);
        tv_departing_gate = findViewById(R.id.tv_departing_gate);

        tv_destination_city = findViewById(R.id.tv_destination_city);
        tv_destination_date = findViewById(R.id.tv_destination_date);
        tv_destination_time = findViewById(R.id.tv_destination_time);
        tv_destination_gate = findViewById(R.id.tv_destination_gate);

        tv_return_city = findViewById(R.id.tv_return_city);
        tv_return_date = findViewById(R.id.tv_return_date);
        tv_return_time = findViewById(R.id.tv_return_time);
        tv_return_gate = findViewById(R.id.tv_return_gate);


        tv_departing_city.setText(testString);
        tv_departing_date.setText(testString);
        tv_departing_time.setText(testString);
        tv_departing_gate.setText(testString);

        tv_destination_city.setText(testString);
        tv_destination_date.setText(testString);
        tv_destination_time.setText(testString);
        tv_destination_gate.setText(testString);


        tv_return_city.setText(testString);
        tv_return_date.setText(testString);
        tv_return_time.setText(testString);
        tv_return_gate.setText(testString);

         /*
            Submits the Order and opens the OrderConfirmation page, where it will Insert all values
            from the Flight Information table in to the Past Orders table using the unique
            Order ID foreign key.
         */
        btn_submit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: Fix the Past Orders class to take in these parameters

//                Intent i = getIntent();
//                FlightInformationClass info;
//
//                info = i.getParcelableExtra("find_available_flights_cursor");
//                PastOrdersClass order = new PastOrdersClass(
//                        info.
//            );


//            boolean isInserted = appDatabaseHelper.addOrderToTable(order);
//
//            if (isInserted) {
//                Intent intent = new Intent(getApplicationContext(), OrderConfirmationPage.class);
//                startActivity(intent);
//                finish();
//            } else
//                Toast.makeText(CheckoutPage.this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
        }
    });



         /*
                Cancels the entire workflow and sends User back to the Splash page
         */
        btn_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SplashPage.class);
                startActivity(intent);
            }});

    }



    /*
            TODO:
     */
//    public void submitOrder() {
//                submit_order_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        Class order = new PastOrdersClass(
//                                flight_departing_date_tv.getText().toString(),
//                                flight_departing_time_tv.getText().toString(),
//                                flight_departing_city_spinner.getSelectedItem().toString(),
//                                flight_departing_gate_tv.getText().toString(),
//
//                                flight_destination_date_tv.getText().toString(),
//                                flight_destination_time_tv.getText().toString(),
//                                flight_destination_city_spinner.getSelectedItem().toString(),
//                                flight_destination_gate_tv.getText().toString(),
//
//                                flight_return_date_tv.getText().toString(),
//                                flight_return_time_tv.getText().toString(),
//                                flight_return_gate_tv.getText().toString(),
//
//                                flight_passenger_count_tv.getText().toString()
//                        );
//
//
//                        boolean isInserted = appDatabaseHelper.addOrderToTable(order);
//
//                        if (isInserted) {
//                            Toast.makeText(CheckoutPage.this, "Please Review your Order!.", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getApplicationContext(), OrderConfirmationPage.class);
//                            startActivity(intent);
//                            finish();
//                        } else
//                            Toast.makeText(CheckoutPage.this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
//                    }
//                });


}