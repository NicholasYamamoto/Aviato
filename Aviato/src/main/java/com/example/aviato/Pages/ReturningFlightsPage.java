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
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.aviato.DatabaseHelper;
import com.example.aviato.R;

public class ReturningFlightsPage extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SQLiteOpenHelper databaseHelper;
    SQLiteDatabase databaseInstance;
    Cursor cursor;
    Intent intent;

    String departing_city, destination_city, departure_date, return_date,
            flight_type;

    ListView availableReturningFlightsList;

    int returnFlightID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_return_flights);

        sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);

        departing_city = sharedPreferences.getString("departing_city", "");
        destination_city = sharedPreferences.getString("destination_city", "");
        departure_date = sharedPreferences.getString("departure_date", "");
        return_date = sharedPreferences.getString("return_date", "");
        flight_type = sharedPreferences.getString("flight_type", "");

        availableReturningFlightsList = findViewById(R.id.lv_available_return_flights);

        searchAvailableReturningFlights();
    }

    public void searchAvailableReturningFlights() {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            databaseInstance = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.selectFlight(databaseInstance, destination_city, departing_city,
                    return_date, flight_type);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                CursorAdapter listAdapter = new SimpleCursorAdapter(getApplicationContext(),
                        R.layout.custom_search_list_view,
                        cursor,
                        new String[]{"DEPARTING_TIME", "DESTINATION_TIME", "PRICE", "AIRLINE_CARRIER", "FLIGHT_DURATION", "FLIGHT_TYPE_NAME"},
                        new int[]{R.id.txt_departing_time_list_layout, R.id.txt_arrival_time_list_layout, R.id.txt_price_list_layout, R.id.txt_airline_carrier_list_layout, R.id.txt_travel_time_list_layout, R.id.txt_flight_type_list_layout},//map the contents of NAME col to text in ListView
                        0);

                availableReturningFlightsList.setAdapter(listAdapter);
            } else
                Toast.makeText(getApplicationContext(), "The selected Return flight is not available.", Toast.LENGTH_SHORT).show();

            availableReturningFlightsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    returnFlightID = (int) id;

                    intent = new Intent(getApplicationContext(), CheckoutPage.class);

                    sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("return_flight_ID");
                    editor.putInt("return_flight_ID", returnFlightID);

                    editor.apply();

                    startActivity(intent);
                    finish();
                }
            });

        } catch (SQLiteException e) {
            System.out.println("RETURNING FLIGHTS PAGE - Displaying Available Return Flights ERROR");
            System.out.println(e.toString());
            Toast.makeText(getApplicationContext(), "Error: Database is Unavailable.", Toast.LENGTH_SHORT).show();
        }
    }

}
