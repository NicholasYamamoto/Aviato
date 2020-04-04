package com.example.aviato.Pages;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aviato.DatabaseHelper;
import com.example.aviato.R;

public class PastOrderDetailsPage extends AppCompatActivity {

     SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase databaseInstance;
    private Cursor cursor;
    private int flightID;

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
    private TextView subtotal;
    private TextView grandTotal;
    private Button btnCancelFlight;
    private Intent intent;
    private int itineraryID;
    private TextView traveller;
    private int numTraveller;
    private double singleTicketCost;
    private double totalTicketCost;
    private TextView fName;
    private TextView cCard;
    private TextView timeStamp;
    private int clientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_order_details_page);

        intent = getIntent();
        flightID = intent.getIntExtra("FLIGHT_ID",0);
        clientID =clientID();

        fName = findViewById(R.id.txtFName);
        cCard = findViewById(R.id.tv_past_order_credit_card_number);
        timeStamp = findViewById(R.id.tv_past_order_timestamp);

        airline =  findViewById(R.id.tv_past_order_airline_carrier);
        flightNo =  findViewById(R.id.tv_past_order_flight_number);
        origin =  findViewById(R.id.tv_past_order_departing_city);
        destination =  findViewById(R.id.tv_past_order_destination_city);
        departureDate =  findViewById(R.id.tv_past_order_departing_date);
        arrivalDate =  findViewById(R.id.tv_past_order_arrival_date);
        departureTime =  findViewById(R.id.tv_past_order_departure_time);
        arrivalTime =  findViewById(R.id.tv_past_order_arrival_time);
        flightDuration =  findViewById(R.id.tv_past_order_flight_duration);
        flightClass =  findViewById(R.id.tv_past_order_flight_type);
        traveller = findViewById(R.id.tv_past_order_passenger_count);
        subtotal =  findViewById(R.id.tv_past_order_subtotal);
        grandTotal =  findViewById(R.id.tv_past_order_grand_total);

        displaySelectedFlightInfo(flightID);


        totalTicketCost = calculateGrandTotal(singleTicketCost, numTraveller);
        traveller.setText(String.valueOf(numTraveller));
        subtotal.setText("$" + singleTicketCost);
        grandTotal.setText("$" + totalTicketCost);

        btnCancelFlight = findViewById(R.id.btnCancelFlight) ;

        btnCancelFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cancelFlightAlert().show();

            }
        });
    }

    public void displaySelectedFlightInfo(int id) {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            databaseInstance = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.getPastOrderDetails(databaseInstance, id);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                flightNo.setText(String.valueOf(cursor.getInt(1)));
                origin.setText(cursor.getString(2));
                destination.setText(cursor.getString(3));
                departureDate.setText(cursor.getString(4));
                arrivalDate.setText(cursor.getString(5));
                departureTime.setText(cursor.getString(6));
                arrivalTime.setText(cursor.getString(7));
                flightDuration.setText(cursor.getString(8) + "h");
                singleTicketCost = cursor.getDouble(9);
                airline.setText(cursor.getString(10));
                flightClass.setText(cursor.getString(12));
                numTraveller = cursor.getInt(13);
                timeStamp.setText(cursor.getString(14));
            }

            cursor = DatabaseHelper.selectClientJoinAccount(databaseInstance, clientID);
            if(cursor != null && cursor.getCount() == 1){
                cursor.moveToFirst();

                fName.setText(HelperUtilities.capitalize(cursor.getString(0)) + " " + HelperUtilities.capitalize(cursor.getString(1)));
                cCard.setText(HelperUtilities.maskCardNumber(cursor.getString(3)));
            }

        } catch (SQLiteException ex) {

        }
    }

    public void cancelFlight(){
        try{

            databaseHelper = new DatabaseHelper(getApplicationContext());
            databaseInstance = databaseHelper.getWritableDatabase();

            clientID =clientID();

            cursor = DatabaseHelper.selectItinerary(databaseInstance, flightID, clientID);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                itineraryID = cursor.getInt(0);

                // Toast.makeText(getApplicationContext(), String.valueOf(itineraryID), Toast.LENGTH_SHORT).show();
            }

            DatabaseHelper.deleteItinerary(databaseInstance, itineraryID);

        }catch(SQLiteException e){

        }
    }

    public Dialog cancelFlightAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to cancel your flight? ")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        cancelFlight();
                        Intent intent = new Intent(getApplicationContext(), ItineraryActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                    }
                });

        return builder.create();
    }

    public int clientID() {
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        clientID = LoginActivity.sharedPreferences.getInt(LoginActivity.CLIENT_ID, 0);
        return clientID;
    }

    /*
Calculate the Grand Total of the Order
*/
    public static Double calculateGrandTotal(double singleTicketCost,  int numTraveller){
        return singleTicketCost * numTraveller;
    }




}
