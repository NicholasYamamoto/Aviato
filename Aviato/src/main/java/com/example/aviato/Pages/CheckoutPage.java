package com.example.aviato.Pages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aviato.DatabaseHelper;
import com.example.aviato.MainActivity;
import com.example.aviato.R;

    /*
        TODO:   These values used for Checkout will be populated by the Flight Information table,
                after which it will call OrderConfirmationPage, which will handle inserting everything
                into the Orders table.
                Populate these values from a Cursor object created from the Flight Information table
    */

public class CheckoutPage extends AppCompatActivity {


    private TextView flightNo;
    private TextView origin;
    private TextView destination;
    private TextView departureDate;
    private TextView arrivalDate;
    private TextView departureTime;
    private TextView arrivalTime;
    private TextView flightDuration;
    private TextView flightClass;
    private TextView airline;
    private TextView fare;

    private TextView flightNoReturn;
    private TextView originReturn;
    private TextView destinationReturn;
    private TextView departureDateReturn;
    private TextView arrivalDateReturn;
    private TextView departureTimeReturn;
    private TextView arrivalTimeReturn;
    private TextView flightDurationReturn;
    private TextView flightClassReturn;
    private TextView airlineReturn;

    TextView tv_departing_city, tv_departing_date, tv_departing_time, tv_departing_gate,
             tv_destination_city, tv_destination_date, tv_destination_time, tv_destination_gate,
             tv_return_city, tv_return_date, tv_return_time, tv_return_gate,
             tv_passenger_count, tv_total;

    EditText edt_payment_method_card_number, edt_payment_method_security_code;

    Button btn_submit_order, btn_cancel_order;
    Spinner spinner_payment_method_card_type, spinner_credit_card_month, spinner_credit_card_year;

    String testString = "HELLO WORLD";

    SharedPreferences sharedPreferences;
    SQLiteOpenHelper databaseHelper;
    SQLiteDatabase databaseInstance;
    Cursor cursor;
    int departingFlightID, returnFlightID, clientID, passengerCount;
    double departingTicketPrice, returnTicketPrice, grandTotal;
    Intent intent;
    boolean flightExists = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_page);

        sharedPreferences = getApplicationContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        departingFlightID = sharedPreferences.getInt("departing_flight_ID", 0);
        returnFlightID = sharedPreferences.getInt("return_flight_ID", 0);
        passengerCount = sharedPreferences.getInt("passenger_count", 0);

        tv_departing_city = findViewById(R.id.tv_departing_city);
        tv_departing_date = findViewById(R.id.tv_departing_date);
        tv_departing_time = findViewById(R.id.tv_departing_time_list_layout);
        tv_departing_gate = findViewById(R.id.tv_departing_gate);

        tv_destination_city = findViewById(R.id.tv_destination_city);
        tv_destination_date = findViewById(R.id.tv_destination_date);
        tv_destination_time = findViewById(R.id.tv_destination_time);
        tv_destination_gate = findViewById(R.id.tv_destination_gate);

        tv_return_city = findViewById(R.id.tv_return_city);
        tv_return_date = findViewById(R.id.tv_return_date);
        tv_return_time = findViewById(R.id.tv_return_time);
        tv_return_gate = findViewById(R.id.tv_return_gate);

        spinner_credit_card_month = findViewById(R.id.spinner_credit_card_month);
        spinner_credit_card_year = findViewById(R.id.spinner_credit_card_year);
        edt_payment_method_card_number = findViewById(R.id.edt_payment_method_card_number);
        tv_passenger_count = findViewById(R.id.tv_passenger_count);
        edt_payment_method_security_code = findViewById(R.id.edt_payment_method_security_code);

        tv_passenger_count = findViewById(R.id.tv_passenger_count);
        tv_passenger_count = findViewById(R.id.tv_passenger_count);

        clientID = getClientID();

        displaySelectedDepartingFlightInfo(departingFlightID);
        displaySelectedReturnFlightInfo(returnFlightID);

        tv_passenger_count.setText(String.valueOf(passengerCount));

        grandTotal = calculateGrandTotal(departingTicketPrice, returnTicketPrice, passengerCount);

        tv_total.setText("$" + grandTotal);


         /*
            Submits the Order and opens the OrderConfirmation page, where it will Insert all values
            from the Flight Information table in to the Past Orders table using the unique
            Order ID foreign key.
         */
        btn_submit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookFlight(clientID);
            }
    });

         /*
                Cancels the entire workflow and sends User back to the Main Activity page
         */
        btn_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }});

    }

    public void displaySelectedDepartingFlightInfo(int departingFlightID) {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            databaseInstance = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.selectFlight(databaseInstance, departingFlightID);

            if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();

                //TODO: Either sort these correctly or modify their order in the Cursor
                tv_departing_city.setText(cursor.getString(2));
                tv_departing_date.setText(cursor.getString(4));
                tv_departing_time.setText(cursor.getString(6));
                tv_departing_gate.setText(testString);

                tv_destination_city.setText(cursor.getString(3));
                tv_destination_date.setText(cursor.getString(5));
                tv_destination_time.setText(cursor.getString(7));
                tv_destination_gate.setText(testString);

                flightNo. setText(String.valueOf(cursor.getInt(1)));
                destination.setText(cursor.getString(3));
                arrivalDate.setText(cursor.getString(5));
                departureTime.setText(cursor.getString(6));
                arrivalTime.setText(cursor.getString(7));
                flightDuration.setText(cursor.getString(8));
                departingTicketPrice = cursor.getDouble(9);
                airline.setText(cursor.getString(10));
                flightClass.setText(cursor.getString(12));
            }

        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database error occurred 1", Toast.LENGTH_SHORT).show();
        }
    }

    public void displaySelectedReturnFlightInfo(int returnFlightID) {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            databaseInstance = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.selectFlight(databaseInstance, returnFlightID);

            if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();

                tv_return_city.setText(cursor.getString(3));
                tv_return_date.setText(cursor.getString(5));
                tv_return_time.setText(cursor.getString(7));
                // TODO: Add the Return Gate to the cursor
                tv_return_gate.setText(testString);

                flightNoReturn.setText(cursor.getString(1));
                //originReturn.setText(cursor.getString(2));
                destinationReturn.setText(cursor.getString(3));
                //departureDateReturn.setText(cursor.getString(4));
                arrivalDateReturn.setText(cursor.getString(5));
                //departureTimeReturn.setText(cursor.getString(6));
                arrivalTimeReturn.setText(cursor.getString(7));
                flightDurationReturn.setText(cursor.getString(8));
                returnTicketPrice = cursor.getDouble(9);
                airlineReturn.setText(cursor.getString(10));
                flightClassReturn.setText(cursor.getString(12));
            }

        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database error occurred 2", Toast.LENGTH_SHORT).show();
        }
    }

    /*
        Book the Flight for the given Client ID
     */
    public void bookFlight(int clientID) {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            databaseInstance = databaseHelper.getWritableDatabase();

            cursor = DatabaseHelper.selectItinerary(databaseInstance, departingFlightID, clientID);

            if (cursor != null && cursor.getCount() > 0) {
                flightExists = true;
            }

            cursor = DatabaseHelper.selectItinerary(databaseInstance, returnFlightID, clientID);

            if (cursor != null && cursor.getCount() > 0) {
                flightExists = true;

            }
            if (flightExists) {
                selectedFlightAlreadyBookedAlert().show();
            }

            if (flightExists == false) {

                DatabaseHelper.insertItinerary(databaseInstance, departingFlightID, clientID, passengerCount);
                DatabaseHelper.insertItinerary(databaseInstance, returnFlightID, clientID, passengerCount);

                bookFlightDialog().show();
            }

        } catch (SQLiteException e) {

        }
    }

    public Dialog selectedFlightAlreadyBookedAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("You already booked either the Departing or the Returning flight. Please select another flight and try again.")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

        return builder.create();
    }

    /*
            Return the Client ID so the order can be saved to the Past Orders table
     */
    public int getClientID() {
        LoginActivity.sharedPreferences = getApplicationContext().getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        clientID = LoginActivity.sharedPreferences.getInt(LoginActivity.CLIENT_ID, 0);
        return clientID;
    }

    /*
            Calculate the Grand Total of the Order
     */
    public static Double calculateGrandTotal(double departingTicketPrice, double returnTicketPrice, int passengerCount){
        return (departingTicketPrice + returnTicketPrice) * passengerCount;
    }

}