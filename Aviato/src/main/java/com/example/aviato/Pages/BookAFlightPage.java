package com.example.aviato.Pages;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    final Calendar departDateCalendar = Calendar.getInstance();
    final Calendar returnDateCalendar = Calendar.getInstance();
    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;
    Intent intent;
    String departingDate, returnDate;
    int passengerCount = 1;
    int year, month, day;

    DatePickerDialog departingDateDialog;
    DatePickerDialog returnDateDialog;
    Spinner flight_departing_city_spinner, flight_destination_city_spinner, flight_type_spinner;
    TextView flight_passenger_count_tv, flight_departing_date_tv, flight_return_date_tv;
    Button flight_add_passenger_btn, flight_remove_passenger_btn, searchFlightsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_a_flight_page);

        flight_departing_city_spinner = findViewById(R.id.spnr_departing_city);
        flight_destination_city_spinner = findViewById(R.id.spnr_destination_city);

        ArrayAdapter departingCityAdapter = ArrayAdapter.createFromResource(this, R.array.airports_array, R.layout.spinner_item);
        flight_departing_city_spinner.setAdapter(departingCityAdapter);

        ArrayAdapter destinationCityAdapter = ArrayAdapter.createFromResource(this, R.array.airports_array, R.layout.spinner_item);
        flight_destination_city_spinner.setAdapter(destinationCityAdapter);

        flight_departing_date_tv = findViewById(R.id.txt_book_departing_date);
        flight_return_date_tv = findViewById(R.id.txt_book_return_date);

        flight_add_passenger_btn = findViewById(R.id.btn_book_add_passenger);
        flight_passenger_count_tv = findViewById(R.id.txt_book_passenger_count);
        flight_remove_passenger_btn = findViewById(R.id.btn_book_remove_passenger);

        searchFlightsBtn = findViewById(R.id.btn_book_search_flights);

        databaseHelper = new DatabaseHelper(this);

        flight_departing_date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog(1).show();
            }
        });

        flight_return_date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog(2).show();
            }
        });

        flight_add_passenger_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passengerCount = Integer.parseInt(flight_passenger_count_tv.getText().toString());
                flight_passenger_count_tv.setText(String.valueOf(passengerCount + 1));
            }
        });

        flight_remove_passenger_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passengerCount = Integer.parseInt(flight_passenger_count_tv.getText().toString());
                if (passengerCount == 0) ;
                else
                    flight_passenger_count_tv.setText(String.valueOf(passengerCount - 1));
            }
        });

        /*
            Sends the Search Parameters to the Find Flights Page to perform the search and
            query the Available Flights table.
         */
        searchFlightsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchAvailableFlights();
            }
        });
    }

    /*
        Returns a list of Available Flights based on search parameters and displays to ListView
    */
    public void searchAvailableFlights() {
        intent = new Intent(getApplicationContext(), DepartingFlightsPage.class);

        sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        getApplicationContext().getSharedPreferences("PREFS", 0).edit().clear().apply();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("departing_city", flight_departing_city_spinner.getSelectedItem().toString());
        editor.putString("destination_city", flight_destination_city_spinner.getSelectedItem().toString());
        editor.putString("departure_date", departingDate);
        editor.putString("return_date", returnDate);
        editor.putString("flight_type", flight_type_spinner.getSelectedItem().toString());
        editor.putInt("passenger_count", passengerCount);

        editor.apply();

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
                departingDate = startYear + "-" + (startMonth + 1) + "-" + startDay;

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
