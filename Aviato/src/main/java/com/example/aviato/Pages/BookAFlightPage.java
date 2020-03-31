package com.example.aviato.Pages;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aviato.AppDatabaseHelper;
import com.example.aviato.AvailableFlightsDatabaseHelper;
import com.example.aviato.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookAFlightPage extends AppCompatActivity {
    AppDatabaseHelper appDatabaseHelper;
    AvailableFlightsDatabaseHelper availableFlightsDatabaseHelper;

    Spinner flight_departing_city_spinner, flight_destination_city_spinner;
    TextView flight_passenger_count_tv, flight_departing_date_tv, flight_return_date_tv;
    Button flight_add_passenger_btn, flight_remove_passenger_btn, find_available_flights_btn, flight_continue_to_checkout_btn;

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

        flight_continue_to_checkout_btn = findViewById(R.id.flightContinueToCheckoutBtn);

        appDatabaseHelper = new AppDatabaseHelper(this);

        DatePickerDialog.OnDateSetListener departDate = (view, year, monthOfYear, dayOfMonth) -> {
            departDateCalendar.set(Calendar.YEAR, year);
            departDateCalendar.set(Calendar.MONTH, monthOfYear);
            departDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDepartDateLabel();
        };

        DatePickerDialog.OnDateSetListener returnDate= (view, year, monthOfYear, dayOfMonth) -> {
            returnDateCalendar.set(Calendar.YEAR, year);
            returnDateCalendar.set(Calendar.MONTH, monthOfYear);
            returnDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateReturnDateLabel();
        };

        //TODO: Change this so the DatePicker can be used to select a date and return it as a String
        flight_departing_date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(BookAFlightPage.this, departDate, departDateCalendar
                        .get(Calendar.YEAR), departDateCalendar.get(Calendar.MONTH),
                        departDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }});
        // TODO: Same as above
        flight_return_date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(BookAFlightPage.this, returnDate, returnDateCalendar
                        .get(Calendar.YEAR), returnDateCalendar.get(Calendar.MONTH),
                        returnDateCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }});

        flight_add_passenger_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = Integer.parseInt(flight_passenger_count_tv.getText().toString());
                flight_passenger_count_tv.setText(String.valueOf(number + 1));
            }});

        flight_remove_passenger_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPassengers = Integer.parseInt(flight_passenger_count_tv.getText().toString());
                if(currentPassengers == 0);
                else
                    flight_passenger_count_tv.setText(String.valueOf(currentPassengers - 1));
            }});

        /*
            Sends the Search Parameters to the Find Flights Page to perform the search and
            query the Available Flights table.
         */
        find_available_flights_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Cursor searchParameters = availableFlightsDatabaseHelper.getAvailableFlights(
//                                        flight_departing_date_tv.getText().toString(),
//                                        flight_departing_city_spinner.getSelectedItem().toString(),
//                                        flight_destination_city_spinner.getSelectedItem().toString(),
//                                        flight_return_date_tv.getText().toString()
//                );

                Toast.makeText(BookAFlightPage.this, "YOU SHOULD SEE THIS", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), FindFlightsPage.class);
                //intent.putExtra("find_available_flights_cursor", (Parcelable) searchParameters);
                startActivity(intent);
            }});
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
