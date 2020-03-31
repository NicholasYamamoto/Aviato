package com.example.aviato.Pages;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aviato.AppDatabaseHelper;
import com.example.aviato.AvailableFlightsDatabaseHelper;
import com.example.aviato.Classes.FlightInformationClass;
import com.example.aviato.R;

import java.util.Random;

// TODO: Either this has to implement Parcelable or Serializable
public class FindFlightsPage extends AppCompatActivity {
    AppDatabaseHelper appDatabaseHelper;
    AvailableFlightsDatabaseHelper availableFlightsDatabaseHelper;

    Button btn_add_flight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_flights_test);

        availableFlightsDatabaseHelper = new AvailableFlightsDatabaseHelper(this);
        appDatabaseHelper = new AppDatabaseHelper(this);

        btn_add_flight = findViewById(R.id.btn_add_flight);

//        Intent i = getIntent();
//        CheckoutPage info;
//
//        info = i.getParcelableExtra("find_available_flights_cursor");
//
//        Log.d("HELLO WORLD", "HERE IS THE CURSOR FROM BOOK A FLIGHT SEARCH: " + info);


        btn_add_flight.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                FlightInformationClass flight = new FlightInformationClass(
                        1,
                        1,
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        "test",
                        generateTicketNumber(),
                        100);

                boolean isInserted = appDatabaseHelper.addFlightInformationToTable(flight);

                if (isInserted) {
                    Toast.makeText(FindFlightsPage.this, "IT WORKED HOLY SHIT.", Toast.LENGTH_SHORT).show();

//                    Intent intent = new Intent(getApplicationContext(), CheckoutPage.class);
//                    startActivity(intent);
//                    finish();
                } else
                    Toast.makeText(FindFlightsPage.this, "IT BROKE. FUCK.", Toast.LENGTH_SHORT).show();
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
