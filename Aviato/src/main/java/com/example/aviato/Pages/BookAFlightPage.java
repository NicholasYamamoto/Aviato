package com.example.aviato.Pages;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.aviato.DatabaseHelper;
import com.example.aviato.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookAFlightPage extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;
    Cursor cursor;
    Intent intent;

    String departureDate, returnDate;

    int passengerCount = 1;
    int year, month, day;

    //date picker dialog
    DatePickerDialog departingDateDialog;
    DatePickerDialog returnDateDialog;

    Spinner flight_departing_city_spinner, flight_destination_city_spinner, flight_type_spinner;
    TextView flight_passenger_count_tv, flight_departing_date_tv, flight_return_date_tv;
    Button flight_add_passenger_btn, flight_remove_passenger_btn, find_available_flights_btn;
    final Calendar departDateCalendar = Calendar.getInstance();
    final Calendar returnDateCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_a_flight_page);

        flight_departing_city_spinner = findViewById(R.id.departingCitySpnr);
        flight_destination_city_spinner = findViewById(R.id.destinationCitySpnr);

        ArrayAdapter departingCityAdapter = ArrayAdapter.createFromResource(this, R.array.airports_array, R.layout.spinner_item);
        flight_departing_city_spinner.setAdapter(departingCityAdapter);

        ArrayAdapter destinationCityAdapter = ArrayAdapter.createFromResource(this, R.array.airports_array, R.layout.spinner_item);
        flight_destination_city_spinner.setAdapter(destinationCityAdapter);

        flight_departing_date_tv = findViewById(R.id.departDateTV);
        flight_return_date_tv = findViewById(R.id.returnDateTV);

        flight_add_passenger_btn = findViewById(R.id.flightAddPassengerBtn);
        flight_passenger_count_tv = findViewById(R.id.flightPassengerCountTV);
        flight_remove_passenger_btn = findViewById(R.id.flightRemovePassengerBtn);

        find_available_flights_btn = findViewById(R.id.findAvailableFlightsBtn);

        databaseHelper = new DatabaseHelper(this);

        flight_departing_date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog(1).show();
            }});

        flight_return_date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog(2).show();
            }});

        flight_add_passenger_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passengerCount = Integer.parseInt(flight_passenger_count_tv.getText().toString());
                flight_passenger_count_tv.setText(String.valueOf(passengerCount + 1));
            }});

        flight_remove_passenger_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passengerCount = Integer.parseInt(flight_passenger_count_tv.getText().toString());
                if(passengerCount == 0);
                else
                    flight_passenger_count_tv.setText(String.valueOf(passengerCount - 1));
            }});

        /*
            Sends the Search Parameters to the Find Flights Page to perform the search and
            query the Available Flights table.
         */
        find_available_flights_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchAvailableFlights();
            }});
    }

    /*
        Returns a list of Available Flights based on search parameters and displays to ListView
    */
    public void searchAvailableFlights() {
        intent = new Intent(getApplicationContext(), DepartingFlightsPage.class);

        sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        getApplicationContext().getSharedPreferences("PREFS", 0).edit().clear().commit();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("departing_city", flight_departing_city_spinner.getSelectedItem().toString());
        editor.putString("destination_city", flight_destination_city_spinner.getSelectedItem().toString());
        editor.putString("departure_date", departureDate);
        editor.putString("return_date", returnDate);
        editor.putString("flight_type", flight_type_spinner.getSelectedItem().toString());

        editor.putString("flight_type", btnOneWayClass.getText().toString());
        editor.putInt("passenger_count", passengerCount);

        editor.commit();

        startActivity(intent);
    }

    public DatePickerDialog datePickerDialog(int datePickerID) {
        switch (datePickerID) {
            case 1:
                if (departingDateDialog == null) {
                    departingDateDialog = new DatePickerDialog(this, getDepartureDatePickerListener(), year, month, day);
                }
                departingDateDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                return departingDateDialog;

            case 2:
                if (returnDateDialog == null) {
                    returnDateDialog = new DatePickerDialog(this, getReturnDatePickerListener(), year, month, day);
                }
                returnDateDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                return returnDateDialog;
        }
        return null;
    }

    public DatePickerDialog.OnDateSetListener getDepartureDatePickerListener() {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int startYear, int startMonth, int startDay) {
                departDateCalendar.set(Calendar.YEAR, startYear);
                departDateCalendar.set(Calendar.MONTH, startMonth);
                departDateCalendar.set(Calendar.DAY_OF_MONTH, startDay);
                departureDate = startYear + "-" + (startMonth + 1) + "-" + startDay;

                updateDepartDateLabel();
            }
        };
    }

    public DatePickerDialog.OnDateSetListener getReturnDatePickerListener() {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int startYear, int startMonth, int startDay) {
                returnDateCalendar.set(Calendar.YEAR, startYear);
                returnDateCalendar.set(Calendar.MONTH, startMonth);
                returnDateCalendar.set(Calendar.DAY_OF_MONTH, startDay);
                returnDate = startYear + "-" + (startMonth + 1) + "-" + startDay;

                updateReturnDateLabel();
            }
        };
    }

    private void updateDepartDateLabel() {
        String myFormat = "MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        flight_departing_date_tv.setText(sdf.format(departDateCalendar.getTime()));
    }

    private void updateReturnDateLabel() {
        String myFormat = "MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        flight_return_date_tv.setText(sdf.format(returnDateCalendar.getTime()));
    }

}
