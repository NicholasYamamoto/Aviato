package com.example.aviato.Pages;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.aviato.DatabaseHelper;
import com.example.aviato.R;

public class PastOrdersPage extends AppCompatActivity {

    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase databaseInstance;
    private Cursor cursor;
    private int flightID;
    private int clientID;
    private Intent intent;
    private ListView pastOrdersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_orders_page);

        pastOrdersList = findViewById(R.id.lv_past_orders);

        clientID = clientID();


        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            databaseInstance = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.selectOrder(databaseInstance, clientID);

            if (cursor != null && cursor.getCount() > 0) {

                CursorAdapter listAdapter = new SimpleCursorAdapter(getApplicationContext(),
                        R.layout.custom_past_orders_list_view,
                        cursor,
                        new String[]{"DEPARTING_CITY", "DESTINATION_CITY", "AIRLINE_CARRIER",
                                "FLIGHT_DURATION", "SEAT_FLIGHT_TYPE", "DEPARTING_DATE"},
                        new int[]{R.id.txt_order_departure_city_list, R.id.txt_order_destination_city_list,
                                R.id.txt_order_airline_carrier, R.id.txt_order_flight_duration,
                                R.id.txt_order_flight_type, R.id.txt_departing_date_list},
                        0);

                pastOrdersList.setAdapter(listAdapter);
            } else
                Toast.makeText(PastOrdersPage.this, "Error: No Past Orders Found!", Toast.LENGTH_SHORT).show();

            pastOrdersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    flightID = (int) id;

                    intent = new Intent(getApplicationContext(), CheckoutPage.class);
                    intent.putExtra("FLIGHT_ID", flightID);

                    startActivity(intent);
                }
            });


        } catch (SQLiteException e) {
            System.out.println("PAST ORDERS PAGE ERROR");
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
