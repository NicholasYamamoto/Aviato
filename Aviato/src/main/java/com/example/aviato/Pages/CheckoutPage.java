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
import android.widget.TextView;
import android.widget.Toast;

import com.example.aviato.DatabaseHelper;
import com.example.aviato.MainActivity;
import com.example.aviato.R;

    /*
        TODO:   These values used for Checkout will be populated by the FlightInformationClass Information table,
                after which it will call OrderConfirmationPage, which will handle inserting everything
                into the Orders table.
                Populate these values from a Cursor object created from the FlightInformationClass Information table
    */

public class CheckoutPage extends AppCompatActivity {


    TextView txt_departing_city, txt_departing_date, txt_departing_time, txt_departing_gate,
            txt_destination_city, txt_destination_date, txt_destination_time, txt_destination_gate,
            txt_return_city, txt_return_date, txt_return_time, txt_return_gate, txt_selected_payment_method,
            txt_passenger_count, txt_total;
    Button btn_submit_order, btn_cancel_order;
    SharedPreferences sharedPreferences;
    SQLiteOpenHelper databaseHelper;
    SQLiteDatabase databaseInstance;
    Cursor cursor;
    int departingFlightID, returnFlightID, clientID, passengerCount;
    double departingTicketPrice, returnTicketPrice, grandTotal;
    Intent intent;
    boolean flightExists = false;
    private TextView flightNo;
    private TextView origin;
    private TextView destination;
    private TextView departingDate;
    private TextView arrivalDate;
    private TextView departingTime;
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

    /*
            Calculate the Grand Total of the Order
     */
    public static Double calculateGrandTotal(double departingTicketPrice, double returnTicketPrice, int passengerCount) {
        return (departingTicketPrice + returnTicketPrice) * passengerCount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_page);

        sharedPreferences = getApplicationContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        departingFlightID = sharedPreferences.getInt("departing_flight_ID", 0);
        returnFlightID = sharedPreferences.getInt("return_flight_ID", 0);
        passengerCount = sharedPreferences.getInt("passenger_count", 0);

        txt_departing_city = findViewById(R.id.txt_departing_city);
        txt_departing_date = findViewById(R.id.txt_book_departing_date);
        txt_departing_time = findViewById(R.id.txt_departing_time_list_layout);
        txt_departing_gate = findViewById(R.id.txt_departing_gate);

        txt_destination_city = findViewById(R.id.txt_destination_city);
        txt_destination_date = findViewById(R.id.txt_destination_date);
        txt_destination_time = findViewById(R.id.txt_destination_time);
        txt_destination_gate = findViewById(R.id.txt_destination_gate);

        txt_return_city = findViewById(R.id.txt_return_city);
        txt_return_date = findViewById(R.id.txt_book_return_date);
        txt_return_time = findViewById(R.id.txt_return_time);
        txt_return_gate = findViewById(R.id.txt_return_gate);

        txt_selected_payment_method = findViewById(R.id.txt_selected_payment_method);
        txt_passenger_count = findViewById(R.id.txt_passenger_count);

        clientID = getClientID();

        displaySelectedDepartingFlightInfo(departingFlightID);
        displaySelectedReturnFlightInfo(returnFlightID);

        txt_passenger_count.setText(String.valueOf(passengerCount));

        grandTotal = calculateGrandTotal(departingTicketPrice, returnTicketPrice, passengerCount);

        txt_total.setText("$" + grandTotal);

         /*
            Submits the Order and opens the OrderConfirmation page, where it will Insert all values
            from the FlightInformationClass Information table in to the Past Orders table using the unique
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
            }
        });

    }

    public void displaySelectedDepartingFlightInfo(int departingFlightID) {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            databaseInstance = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.getFlightDetails(databaseInstance, departingFlightID);

            if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();

                //TODO: Add a Cursor element for Gate Number
                txt_departing_city.setText(cursor.getString(2));
                txt_departing_date.setText(cursor.getString(3));
                txt_departing_time.setText(cursor.getString(4));
                txt_departing_gate.setText("HELLO WORLD");

                txt_destination_city.setText(cursor.getString(5));
                txt_destination_date.setText(cursor.getString(6));
                txt_destination_time.setText(cursor.getString(7));
                txt_destination_gate.setText("HELLO WORLD");

                txt_total.setText(cursor.getString(12));

//                flightNo.setText(String.valueOf(cursor.getInt(1)));
//                destination.setText(cursor.getString(3));
//                arrivalDate.setText(cursor.getString(5));
//                departingTime.setText(cursor.getString(6));
//                arrivalTime.setText(cursor.getString(7));
//                flightDuration.setText(cursor.getString(8));
//                departingTicketPrice = cursor.getDouble(9);
//                airline.setText(cursor.getString(10));
//                flightClass.setText(cursor.getString(12));

                /*
                SelectFlight Cursor Order:
                    1   -   FLIGHT_NUMBER
                    2   -   DEPARTING_CITY
                    3   -   DEPARTING_DATE
                    4   -   DEPARTING_TIME
                    5   -   DESTINATION_CITY
                    6   -   DESTINATION_DATE
                    7   -   DESTINATION_TIME
                    8   -   AIRLINE_CARRIER
                    9   -   SEAT_NUMBER
                    10  -   FLIGHT_DURATION
                    11  -   FLIGHT_TYPE
                    12  -   PRICE
                 */
            }

        } catch (SQLiteException e) {
            System.out.println("CHECKOUT PAGE - Display Departing Flight ERROR");
            System.out.println(e.toString());
            Toast.makeText(getApplicationContext(), "Error: Departing Flight invalid.", Toast.LENGTH_SHORT).show();
        }
    }

    public void displaySelectedReturnFlightInfo(int returnFlightID) {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            databaseInstance = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.getFlightDetails(databaseInstance, returnFlightID);

            if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();

                txt_return_city.setText(cursor.getString(5));
                txt_return_date.setText(cursor.getString(6));
                txt_return_time.setText(cursor.getString(7));
                txt_return_gate.setText("HELLO WORLD");

//                flightNoReturn.setText(cursor.getString(1));
//                originReturn.setText(cursor.getString(2));
//                destinationReturn.setText(cursor.getString(3));
//                departureDateReturn.setText(cursor.getString(4));
//                arrivalDateReturn.setText(cursor.getString(5));
//                departureTimeReturn.setText(cursor.getString(6));
//                arrivalTimeReturn.setText(cursor.getString(7));
//                flightDurationReturn.setText(cursor.getString(8));
//                returnTicketPrice = cursor.getDouble(9);
//                airlineReturn.setText(cursor.getString(10));
//                flightClassReturn.setText(cursor.getString(12));
            }

        } catch (SQLiteException e) {
            System.out.println("CHECKOUT PAGE - Display Return Flight ERROR");
            System.out.println(e.toString());
            Toast.makeText(getApplicationContext(), "Error: Database is Unavailable.", Toast.LENGTH_SHORT).show();        }
    }

    /*
        Book the FlightInformationClass for the given ClientClass ID
     */
    public void bookFlight(int clientID) {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            databaseInstance = databaseHelper.getWritableDatabase();

            cursor = DatabaseHelper.selectOrder(databaseInstance, departingFlightID, clientID);

            if (cursor != null && cursor.getCount() > 0) {
                flightExists = true;
            }

            cursor = DatabaseHelper.selectOrder(databaseInstance, returnFlightID, clientID);

            if (cursor != null && cursor.getCount() > 0) {
                flightExists = true;

            }
            if (flightExists) {
                selectedFlightAlreadyBookedAlert().show();
            }

            if (flightExists == false) {

                DatabaseHelper.insertOrder(databaseInstance, departingFlightID, clientID, passengerCount);
                DatabaseHelper.insertOrder(databaseInstance, returnFlightID, clientID, passengerCount);

                Toast.makeText(getApplicationContext(), "Error: Inserting Order Failed.", Toast.LENGTH_SHORT).show();

                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);            }

        } catch (SQLiteException e) {
            System.out.println("CHECKOUT PAGE - Unknown ERROR");
            System.out.println(e.toString());
            Toast.makeText(getApplicationContext(), "Error: Database is Unavailable.", Toast.LENGTH_SHORT).show();

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
            Return the ClientClass ID so the order can be saved to the Past Orders table
     */
    public int getClientID() {
        SignInPage.sharedPreferences = getApplicationContext().getSharedPreferences(SignInPage.MY_PREFERENCES, Context.MODE_PRIVATE);
        clientID = SignInPage.sharedPreferences.getInt(SignInPage.CLIENT_ID, 0);
        return clientID;
    }

}