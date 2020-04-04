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
import android.widget.TextView;
import android.widget.Toast;

import com.example.aviato.DatabaseHelper;
import com.example.aviato.R;


public class PastOrdersPage extends AppCompatActivity {

    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private int flightID;
    private int clientID;
    private Intent intent;
    private ListView pastOrdersList;
    private TextView pastOrdersAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_orders_page);

        pastOrdersList = findViewById(R.id.lv_past_orders);

        pastOrdersAlert = findViewById(R.id.tv_past_orders_message);
        clientID = clientID();

        pastOrdersAlert.setVisibility(View.INVISIBLE);

        try{
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.selectItinerary(db, clientID);

            if (cursor != null && cursor.getCount() > 0) {

                //Toast.makeText(getApplicationContext(), String.valueOf(cursor.getCount()), Toast.LENGTH_SHORT).show();

                CursorAdapter listAdapter = new SimpleCursorAdapter(getApplicationContext(),
                        R.layout.custom_past_orders_view,
                        cursor,
                        new String[]{"ORIGIN", "DESTINATION", "AIRLINENAME",
                                "FLIGHTDURATION", "FLIGHTCLASSNAME", "DEPARTUREDATE"},
                        new int[]{R.id.tv_departing_city_list, R.id.tv_destination_city_list,
                                R.id.tv_airline_carrier_list_layout, R.id.tv_travel_time_list_layout, R.id.tv_flight_type_list_layout, R.id.tv_departing_date_list},
                        0);

                pastOrdersList.setAdapter(listAdapter);
            }else{

                pastOrdersAlert.setVisibility(View.VISIBLE);
            }

            pastOrdersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    flightID = (int) id;
                    //Toast.makeText(getApplicationContext(), String.valueOf(flightID), Toast.LENGTH_SHORT).show();

                    intent = new Intent(getApplicationContext(), FlightDetailActivity.class);
                    intent.putExtra("FLIGHT_ID", flightID);

                    startActivity(intent);
                }
            });


        }catch (SQLiteException e){
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }

    }

    public int clientID() {
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        clientID = LoginActivity.sharedPreferences.getInt(LoginActivity.CLIENT_ID, 0);
        return clientID;
    }

}
