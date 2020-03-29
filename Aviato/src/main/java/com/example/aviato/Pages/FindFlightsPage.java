package com.example.aviato.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.aviato.AppDatabaseHelper;
import com.example.aviato.AvailableFlightsDatabaseHelper;
import com.example.aviato.R;

// TODO: Either this has to implement Parcelable or Serializable
public abstract class FindFlightsPage extends AppCompatActivity implements Parcelable {
    AppDatabaseHelper appDatabaseHelper;
    AvailableFlightsDatabaseHelper availableFlightsDatabaseHelper;

    EditText signupFirstName, signupEmailAddress, signupPassword;
    Spinner signupPreferredCarrier, signupDefaultDepart;
    Button addFlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_flights_test);

        availableFlightsDatabaseHelper = new AvailableFlightsDatabaseHelper(this);

        ListView listView = findViewById(R.id.find_flights_page_lv);
        appDatabaseHelper = new AppDatabaseHelper(this);

        Intent i = getIntent();
        CheckoutPage info;

        info = i.getParcelableExtra("find_available_flights_cursor");

        Log.d("HELLO WORLD", "HERE IS THE CURSOR FROM BOOK A FLIGHT SEARCH: " + info);

//
//
//
//        ArrayList<OrderClass> list = new ArrayList<>(); // Use to store the Details of the order, as big as it needs to be
//        Cursor data = appDatabaseHelper.getOrderDetails();  // Get all of the
//
//        if (data.getCount() == 0) {
//            Toast.makeText(FindFlightsPage.this, "No flights were found. Try your search again!", Toast.LENGTH_SHORT).show();
//        }
//
//
//        else {
//            while (data.moveToNext()) {
//
//                list.add(data.getString());
//            }
//        }
//
//        OrderAdapter adapter = new OrderAdapter(getApplicationContext(), list);
//        listView.setAdapter(adapter);

    }
//
//    public void addFlight() {
//        addFlight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FlightInformationClass flight = new FlightInformationClass(
//                        tripID,
//                        customerID,
//                        carrierName,
//                        departingDate,
//                        departingCity,
//                        departingTime,
//                        departingGate,
//                        destinationDate,
//                        destinationTime,
//                        destinationCity,
//                        destinationGate,
//                        returnDate,
//                        returnTime,
//                        returnGate,
//                        generateTicketNumber(),
//                        orderTotal);
//
//                boolean isInserted = appDatabaseHelper.addFlightInformationToTable(flight);
//
//                if (isInserted) {
//                    Toast.makeText(FindFlightsPage.this, "Account Created! Please Log In.", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(), CheckoutPage.class);
//                    startActivity(intent);
//                    finish();
//                } else
//                    Toast.makeText(FindFlightsPage.this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
