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
import android.widget.TextView;
import android.widget.Toast;

import com.example.aviato.DatabaseHelper;
import com.example.aviato.R;

public class DepartingFlightsPage extends AppCompatActivity {

    Intent intent;
    boolean flightUnavailable = false;
    String departing_city, destination_city, departure_date, return_date,
            flight_type;
    ListView flightList;
    int departingFlightID;
    TextView flightUnavailableMessage;
    SharedPreferences sharedPreferences;
    SQLiteOpenHelper databaseHelper;
    SQLiteDatabase databaseInstance;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_departing_flights);

        sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);

        departing_city = sharedPreferences.getString("departing_city", "");
        destination_city = sharedPreferences.getString("destination_city", "");
        departure_date = sharedPreferences.getString("departure_date", "");
        return_date = sharedPreferences.getString("return_date", "");
        flight_type = sharedPreferences.getString("flight_type", "");

        flightList = findViewById(R.id.lv_available_departing_flights);

        flightUnavailableMessage = findViewById(R.id.txt_no_available_departing_flights);

        flightUnavailableMessage.setVisibility(View.INVISIBLE);

        searchDepartingFlights();

    }

    public void searchDepartingFlights() {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            databaseInstance = databaseHelper.getReadableDatabase();

            // Configure Cursor for Departing Flight search workflow
            cursor = DatabaseHelper.selectFlight(databaseInstance, departing_city, destination_city,
                    departure_date, flight_type);

            if (cursor != null && cursor.getCount() > 0) {
                CursorAdapter listAdapter = new SimpleCursorAdapter(getApplicationContext(),
                        R.layout.custom_search_list_view,
                        cursor,
                        new String[]{"DEPARTING_TIME", "DESTINATION_TIME", "PRICE", "AIRLINE_CARRIER", "FLIGHT_DURATION", "FLIGHT_TYPE_NAME"},
                        new int[]{R.id.txt_departing_time_list_layout, R.id.txt_arrival_time_list_layout, R.id.txt_price_list_layout,
                                R.id.txt_airline_carrier_list_layout, R.id.txt_travel_time_list_layout, R.id.txt_flight_type_list_layout},//map the contents of NAME col to text in ListView
                        0);

                flightList.setAdapter(listAdapter);
            } else
                flightUnavailable = true;

            // After Departing Flight is selected, reconfigure Cursor for the Return Flight search workflow
            cursor = DatabaseHelper.selectFlight(databaseInstance, destination_city, departing_city,
                    return_date, flight_type);

            if (cursor != null && cursor.getCount() > 0) {
                //do nothing here
            } else
                flightUnavailable = true;

            if (flightUnavailable == true) {
                flightUnavailableMessage.setVisibility(View.VISIBLE);
            }

            flightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    departingFlightID = (int) id;

                    intent = new Intent(getApplicationContext(), ReturningFlightsPage.class);
                    sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("departing_flight_ID");
                    editor.putInt("departing_flight_ID", departingFlightID);

                    editor.commit();

                    startActivity(intent);
                    finish();
                }
            });


        } catch (SQLiteException e) {
            System.out.println("DEPARTING FLIGHTS PAGE ERROR");
            System.out.println(e.toString());
            Toast.makeText(getApplicationContext(), "Error: Database is Unavailable.", Toast.LENGTH_SHORT).show();
        }
    }

}
