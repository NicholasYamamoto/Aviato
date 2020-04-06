package com.example.aviato.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aviato.DatabaseHelper;
import com.example.aviato.MainActivity;
import com.example.aviato.Pages.SignInPage;
import com.example.aviato.R;

public class CheckoutFragment extends Fragment {
    SharedPreferences sharedPreferences;
    SQLiteOpenHelper databaseHelper;
    SQLiteDatabase databaseInstance;
    Cursor cursor;
    int departingFlightID, returnFlightID, clientID, passengerCount;
    double departingTicketPrice, returnTicketPrice, grandTotal;
    Intent intent;
    boolean flightExists = false;

    View view;

    TextView txt_departing_city, txt_departing_date, txt_departing_time, txt_departing_gate,
            txt_destination_city, txt_destination_date, txt_destination_time, txt_destination_gate,
            txt_return_city, txt_return_date, txt_return_time, txt_return_gate,
            txt_passenger_count, txt_total;

    Button btn_submit_order, btn_cancel_order;

    public CheckoutFragment() {
        // Required empty public constructor
    }

    /*
        Calculate the Grand Total of the Order
    */
    public static Double calculateGrandTotal(double departingTicketPrice, double returnTicketPrice, int passengerCount) {
        return (departingTicketPrice + returnTicketPrice) * passengerCount;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_checkout, container, false);

        sharedPreferences = getActivity().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        departingFlightID = sharedPreferences.getInt("departing_flight_ID", 0);
        returnFlightID = sharedPreferences.getInt("return_flight_ID", 0);
        passengerCount = sharedPreferences.getInt("passenger_count", 0);

        txt_departing_city = view.findViewById(R.id.txt_departing_city);
        txt_departing_date = view.findViewById(R.id.txt_book_departing_date);
        txt_departing_time = view.findViewById(R.id.txt_departing_time_list_layout);
        txt_departing_gate = view.findViewById(R.id.txt_departing_gate);

        txt_destination_city = view.findViewById(R.id.txt_destination_city);
        txt_destination_date = view.findViewById(R.id.txt_destination_date);
        txt_destination_time = view.findViewById(R.id.txt_destination_time);
        txt_destination_gate = view.findViewById(R.id.txt_destination_gate);

        txt_return_city = view.findViewById(R.id.txt_return_city);
        txt_return_date = view.findViewById(R.id.txt_book_return_date);
        txt_return_time = view.findViewById(R.id.txt_return_time);
        txt_return_gate = view.findViewById(R.id.txt_return_gate);

        txt_passenger_count = view.findViewById(R.id.txt_passenger_count);

        txt_total = view.findViewById(R.id.txt_total);

        btn_submit_order = view.findViewById(R.id.btn_submit_order);
        btn_cancel_order = view.findViewById(R.id.btn_cancel_order);

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
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        displaySelectedDepartingFlightInfo(departingFlightID);
        displaySelectedReturnFlightInfo(returnFlightID);

        txt_passenger_count.setText(String.valueOf(passengerCount));

        grandTotal = calculateGrandTotal(departingTicketPrice, returnTicketPrice, passengerCount);
        txt_total.setText("$" + grandTotal);
    }

    public void displaySelectedDepartingFlightInfo(int departingFlightID) {
        try {
            databaseHelper = new DatabaseHelper(getActivity());
            databaseInstance = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.selectFlight(databaseInstance, departingFlightID);

            if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();

                //TODO: Add a Cursor element for Gate Number
                txt_departing_city.setText(cursor.getString(2));
                txt_departing_date.setText(cursor.getString(4));
                txt_departing_time.setText(cursor.getString(6));
                txt_departing_gate.setText("HELLO WORLD");

                txt_destination_city.setText(cursor.getString(3));
                txt_destination_date.setText(cursor.getString(5));
                txt_destination_time.setText(cursor.getString(7));
                txt_destination_gate.setText("HELLO WORLD");

                txt_total.setText(cursor.getString(12));

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
            Toast.makeText(getActivity(), "Error: Departing Flight invalid.", Toast.LENGTH_SHORT).show();
        }
    }

    public void displaySelectedReturnFlightInfo(int returnFlightID) {
        try {
            databaseHelper = new DatabaseHelper(getActivity());
            databaseInstance = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.getOrderDetails(databaseInstance, returnFlightID);

            if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();

                txt_return_city.setText("BUTTHOLES");
                txt_return_date.setText(cursor.getString(6));
                txt_return_time.setText(cursor.getString(7));
                txt_return_gate.setText("HELLO WORLD");
            }

        } catch (SQLiteException e) {
            System.out.println("CHECKOUT PAGE - Display Return Flight ERROR");
            System.out.println(e.toString());
            Toast.makeText(getActivity(), "Error: Database is Unavailable.", Toast.LENGTH_SHORT).show();
        }
    }

    /*
        Book the FlightInformationClass for the given ClientClass ID
     */
    public void bookFlight(int clientID) {
        try {
            databaseHelper = new DatabaseHelper(getActivity());
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
                Toast.makeText(getActivity(), "Error: You Have Already Booked This Flight!", Toast.LENGTH_SHORT).show();
            }

            if (flightExists == false) {

                DatabaseHelper.insertOrder(databaseInstance, departingFlightID, clientID, passengerCount);
                DatabaseHelper.insertOrder(databaseInstance, returnFlightID, clientID, passengerCount);

                Toast.makeText(getActivity(), "Your Flight Has Been Booked!", Toast.LENGTH_SHORT).show();

                //Change this to go to OrderConfirmationPage instead of back to MainActivity
                intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }

        } catch (SQLiteException e) {
            System.out.println("CHECKOUT PAGE - Unknown ERROR");
            System.out.println(e.toString());
            Toast.makeText(getActivity(), "Error: Database is Unavailable.", Toast.LENGTH_SHORT).show();

        }
    }

    /*
            Return the ClientClass ID so the order can be saved to the Past Orders table
     */
    public int getClientID() {
        SignInPage.sharedPreferences = getActivity().getSharedPreferences(SignInPage.MY_PREFERENCES, Context.MODE_PRIVATE);
        clientID = SignInPage.sharedPreferences.getInt(SignInPage.CLIENT_ID, 0);
        return clientID;
    }
}
