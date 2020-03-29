package com.example.aviato.Pages;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.aviato.AppDatabaseHelper;
import com.example.aviato.R;

//import com.example.aviato.Classes.OrderClass;

public class CheckoutPage extends AppCompatActivity {

    AppDatabaseHelper appDatabaseHelper;
    /*
        TODO:   These values used for Checkout will be populated by the Flight Information table,
                after which it will call OrderConfirmationPage, which will handle inserting everythign
                into the Orders table.
    */

    // TODO: Maybe make this into one Widget like a ListView or something instead of individual TextViews?
    //       Populate these values from a Cursor object created from the Flight Information table
    TextView payment_method_tv, first_name_tv, last_name_tv,
             street_address_tv, city_tv, phone_number_tv,
             subtotal_tv, tax_tv, grand_total_tv;

    RadioGroup ticket_delivery_method_rg;
    DatePicker credit_card_month, credit_card_year;
    Button submit_order_btn, cancel_order_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_page);

        ListView listView = findViewById(R.id.checkout_page_lv);
        appDatabaseHelper = new AppDatabaseHelper(this);

//        ArrayList<OrderClass> list = new ArrayList<>(); // Use to store the Details of the order, as big as it needs to be
//        Cursor data = appDatabaseHelper.getOrderDetails();  // Get all of the
//
//        if (data.getCount() == 0) {
//            Toast.makeText(CheckoutPage.this, "No flights were found. Try your search again!", Toast.LENGTH_SHORT).show();
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
//
//
//
//
//
//
//
//
//        ListView listView = findViewById(R.id.view_cart_page);
//        appDatabaseHelper = new AppDatabaseHelper(this);
//
//
//
//
//        if (data.getCount() == 0) {
//            Toast.makeText(CheckoutPage.this, "No flights were found. Try your search again!", Toast.LENGTH_SHORT).show();
//        }
//
//        else {
//            while (data.moveToNext()) {
//
//                list.add(new PastOrdersClass(data.getString(0),
//                        data.getString(1), data.getString(2),
//                        data.getString(3)));
//            }
//        }
//
//        OrderAdapter adapter = new OrderAdapter(getApplicationContext(), list);
//        listView.setAdapter(adapter);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

//        payment_method_tv = findViewById(R.id.payment_method_);
//
//
//        appDatabaseHelper = new AppDatabaseHelper(this);
//
//        flight_departing_date_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CalendarPickerViewFragment fragment = new CalendarPickerViewFragment();
//                FragmentTransaction fragmentTransaction =
//                        getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.commit();
//            }});
//
//        flight_return_date_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CalendarPickerViewFragment fragment = new CalendarPickerViewFragment();
//                FragmentTransaction fragmentTransaction =
//                        getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.commit();
//            }});
//
//        find_available_flights_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FindFlightsFragment fragment = new FindFlightsFragment();
//                FragmentTransaction fragmentTransaction =
//                        getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.commit();
//            }});
//
//        flight_continue_to_checkout_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), CheckoutPage.class);
//                startActivity(intent);
//                finish();
//            }});
//
//        //searchFlights();
//
//    }
//
//
//
//
//
//
//
//    public void searchFlights() {
//        find_available_flights_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FindFlightsFragment fragment = new FindFlightsFragment();
//                FragmentTransaction fragmentTransaction =
//                        getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.commit();
//            }
//        });
//    }

    /*
            TODO:
     */
//    public void submitOrder() {
//                submit_order_btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        Class order = new PastOrdersClass(
//                                flight_departing_date_tv.getText().toString(),
//                                flight_departing_time_tv.getText().toString(),
//                                flight_departing_city_spinner.getSelectedItem().toString(),
//                                flight_departing_gate_tv.getText().toString(),
//
//                                flight_destination_date_tv.getText().toString(),
//                                flight_destination_time_tv.getText().toString(),
//                                flight_destination_city_spinner.getSelectedItem().toString(),
//                                flight_destination_gate_tv.getText().toString(),
//
//                                flight_return_date_tv.getText().toString(),
//                                flight_return_time_tv.getText().toString(),
//                                flight_return_gate_tv.getText().toString(),
//
//                                flight_passenger_count_tv.getText().toString()
//                        );
//
//
//                        boolean isInserted = appDatabaseHelper.addOrderToTable(order);
//
//                        if (isInserted) {
//                            Toast.makeText(CheckoutPage.this, "Please Review your Order!.", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getApplicationContext(), OrderConfirmationPage.class);
//                            startActivity(intent);
//                            finish();
//                        } else
//                            Toast.makeText(CheckoutPage.this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
//                    }
//                });
    }
}