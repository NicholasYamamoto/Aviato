package com.example.aviato.Pages;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.aviato.AppDatabaseHelper;
import com.example.aviato.AvailableFlightsDatabaseHelper;
import com.example.aviato.Fragments.CalendarPickerViewFragment;
import com.example.aviato.R;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class BookAFlightPage extends AppCompatActivity {
    AppDatabaseHelper appDatabaseHelper;
    AvailableFlightsDatabaseHelper availableFlightsDatabaseHelper;

    Spinner flight_departing_city_spinner, flight_destination_city_spinner;
    TextView flight_passenger_count_tv, flight_departing_date_tv, flight_return_date_tv;
    CalendarPickerView datePicker;
    Button flight_add_passenger_btn, flight_remove_passenger_btn, find_available_flights_btn, flight_continue_to_checkout_btn;

    String tripDepartDate, tripReturnDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_a_flight_page);

        initializeCalendarPickerView();

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

        flight_departing_date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarPickerViewFragment fragment = new CalendarPickerViewFragment();
                FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }});

        flight_return_date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarPickerViewFragment fragment = new CalendarPickerViewFragment();
                FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }});

        //TODO: Might not need to have any functionality for the second TextView for the date
//        flight_departing_date_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CalendarPickerViewFragment fragment = new CalendarPickerViewFragment();
//                FragmentTransaction fragmentTransaction =
//                        getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.commit();
//            }});

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
                Cursor searchParameters = availableFlightsDatabaseHelper.getAvailableFlights(
                                        flight_departing_date_tv.getText().toString(),
                                        flight_departing_city_spinner.getSelectedItem().toString(),
                                        flight_destination_city_spinner.getSelectedItem().toString(),
                                        flight_return_date_tv.getText().toString()
                );

                Intent intent = new Intent(getApplicationContext(), FindFlightsPage.class);
                intent.putExtra("find_available_flights_cursor", (Parcelable) searchParameters);
                startActivity(intent);
            }});
    }

    private void initializeCalendarPickerView() {
        // TODO: Watch those Youtube tutorials and figure out how to use to
        //       extract the date from the Date Picker

        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        datePicker = findViewById(R.id.calendar_grid);

        datePicker.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(today);

        List<Date> tripDuration = datePicker.getSelectedDates();

        flight_departing_date_tv.setText(tripDuration.get(0).toString());
        flight_return_date_tv.setText(tripDuration.get(tripDuration.size() - 1).toString());


        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(date);

//                String selectedDate = "" + calSelected.get(Calendar.DAY_OF_MONTH)
//                        + " " + (calSelected.get(Calendar.MONTH) + 1)
//                        + " " + calSelected.get(Calendar.YEAR);
            }

            @Override
            public void onDateUnselected(Date date) {
            }
        });
    }


    /*
     *      Generate a random Ticket Number using a Lambda function
     */
    @RequiresApi(api = Build.VERSION_CODES.N)

    public String generateTicketNumber() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
