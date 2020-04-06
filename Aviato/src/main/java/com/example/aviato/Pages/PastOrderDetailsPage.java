package com.example.aviato.Pages;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aviato.DatabaseHelper;
import com.example.aviato.R;

public class PastOrderDetailsPage extends AppCompatActivity {

    SQLiteOpenHelper databaseHelper;
    SQLiteDatabase databaseInstance;
    Cursor cursor;

    TextView flightNo, departing_city, destination_city, departing_date, destination_date,
            departing_time, destination_time, flight_duration, flight_type, airline_carrier,
            subtotal, grand_total, first_name, credit_card_number, time_stamp;
    Intent intent;
    TextView passenger;
    int flightID, passengerCount, clientID;
    double singleTicketCost, totalTicketCost;

    /*
Calculate the Grand Total of the Order
*/
    public static Double calculateGrandTotal(double singleTicketCost, int numTraveller) {
        return singleTicketCost * numTraveller;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_order_details_page);

        intent = getIntent();
        flightID = intent.getIntExtra("FLIGHT_ID", 0);
        clientID = clientID();

        first_name = findViewById(R.id.txtFName);
        credit_card_number = findViewById(R.id.txt_past_order_credit_card_number);
        time_stamp = findViewById(R.id.txt_past_order_timestamp);

        airline_carrier = findViewById(R.id.txt_past_order_airline_carrier);
        flightNo = findViewById(R.id.txt_past_order_flight_number);
        departing_city = findViewById(R.id.txt_past_order_departing_city);
        destination_city = findViewById(R.id.txt_past_order_destination_city);
        departing_date = findViewById(R.id.txt_past_order_departing_date);
        destination_date = findViewById(R.id.txt_past_order_arrival_date);
        departing_time = findViewById(R.id.txt_past_order_departure_time);
        destination_time = findViewById(R.id.txt_past_order_arrival_time);
        flight_duration = findViewById(R.id.txt_past_order_flight_duration);
        flight_type = findViewById(R.id.txt_past_order_flight_type);
        passenger = findViewById(R.id.txt_past_order_passenger_count);
        subtotal = findViewById(R.id.txt_past_order_subtotal);
        grand_total = findViewById(R.id.txt_past_order_grand_total);

        displaySelectedFlightInfo(flightID);


        totalTicketCost = calculateGrandTotal(singleTicketCost, passengerCount);
        passenger.setText(String.valueOf(passengerCount));
        subtotal.setText("$" + singleTicketCost);
        grand_total.setText("$" + totalTicketCost);

    }

    public void displaySelectedFlightInfo(int id) {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            databaseInstance = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.selectFlight(databaseInstance, id);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                flightNo.setText(String.valueOf(cursor.getInt(1)));
                departing_city.setText(cursor.getString(2));
                destination_city.setText(cursor.getString(3));
                departing_date.setText(cursor.getString(4));
                destination_date.setText(cursor.getString(5));
                departing_time.setText(cursor.getString(6));
                destination_time.setText(cursor.getString(7));
                flight_duration.setText(cursor.getString(8) + "h");
                singleTicketCost = cursor.getDouble(9);
                airline_carrier.setText(cursor.getString(10));
                flight_type.setText(cursor.getString(12));
                passengerCount = cursor.getInt(13);
                time_stamp.setText(cursor.getString(14));
            }

            cursor = DatabaseHelper.selectClientJoinAccount(databaseInstance, clientID);
            if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();

                first_name.setText(DatabaseHelper.capitalizeString(cursor.getString(0)) + " " + DatabaseHelper.capitalizeString(cursor.getString(1)));
                credit_card_number.setText(cursor.getString(3));
            }

        } catch (SQLiteException e) {
            System.out.println("PAST ORDERS DETAILS PAGE - Display Selected Flights ERROR");
            System.out.println(e.toString());
            Toast.makeText(getApplicationContext(), "Error: Database is Unavailable.", Toast.LENGTH_SHORT).show();
        }
    }


    public int clientID() {
        SignInPage.sharedPreferences = getSharedPreferences(SignInPage.MY_PREFERENCES, Context.MODE_PRIVATE);
        clientID = SignInPage.sharedPreferences.getInt(SignInPage.CLIENT_ID, 0);
        return clientID;
    }
}
