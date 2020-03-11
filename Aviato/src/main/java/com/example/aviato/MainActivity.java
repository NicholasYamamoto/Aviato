package com.example.aviato;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aviato.Fragments.AboutUsFragment;
import com.example.aviato.Fragments.BookFlightFragment;
import com.example.aviato.Fragments.ContactUsFragment;
import com.example.aviato.Fragments.MainFragment;
import com.example.aviato.Fragments.PlanATripFragment;
import com.example.aviato.Fragments.ViewProfileFragment;
import com.example.aviato.Pages.LoginOptionsPage;
import com.example.aviato.Pages.OrderPage;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;
    Toolbar toolbar = null;
    Button logout, cancel;
    DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new DatabaseHelper(this);
        Intent i = getIntent();
        //TODO: Fix this to get it to return the currently logged in Account's name
        String user_name = i.getStringExtra("Name_marker");

        Toast welcomeMessage = Toast.makeText(getApplicationContext(), "Hello " + user_name + " , Welcome to Aviato.", Toast.LENGTH_LONG);
        welcomeMessage.setGravity(Gravity.CENTER_VERTICAL, Gravity.CENTER_HORIZONTAL, 350);
        welcomeMessage.show();

        //DEFAULT FRAGMENT
        MainFragment fragment = new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Fragment_container, fragment);
        fragmentTransaction.commit();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.view_profile) {
            Intent intent = new Intent(getApplicationContext(), OrderPage.class);
            startActivity(intent);
        }
        //TODO: Implement this Cart to view Current Cart
//        else if(id == R.id.view_cart){
//
//            Cursor check ;
//            check = database.getOrderDetails() ;
//
//            if(check!=null && check.getCount()>0) {
//                SubmitOrder fragment = new SubmitOrder();
//                android.support.v4.app.FragmentTransaction fragmentTransaction =
//                        getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.Fragment_container, fragment);
//                fragmentTransaction.commit();
//
//            }
//            else  {Toast.makeText(getApplicationContext(),"Sorry, You don't order anything...",Toast.LENGTH_SHORT).show(); }
//        }
        else if (id == R.id.past_orders) {

            Cursor check;
            check = database.getOrderDetails();

            if (check != null && check.getCount() > 0) {
                SubmitOrder fragment = new SubmitOrder();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.Fragment_container, fragment);
                fragmentTransaction.commit();

            } else {
                Toast noOrdersMessage = Toast.makeText(getApplicationContext(), "No past orders found for this user.", Toast.LENGTH_LONG);
                noOrdersMessage.setGravity(Gravity.CENTER_VERTICAL, Gravity.CENTER_HORIZONTAL, 350);
                noOrdersMessage.show();
            }
        } else if (id == R.id.book_a_flight) {
            BookFlightFragment fragment = new BookFlightFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.Fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.view_profile) {
            ViewProfileFragment fragment = new ViewProfileFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.Fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.plan_a_trip) {

            PlanATripFragment fragment = new PlanATripFragment();
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.Fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.about_us) {

            AboutUsFragment fragment = new AboutUsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.Fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.contact_us) {
            ContactUsFragment fragment = new ContactUsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.Fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.log_out) {
            openLogoutDialog();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openLogoutDialog() {

        final Dialog builder = new Dialog(this);
        builder.setContentView(R.layout.dialog_logout);
        builder.setTitle(R.string.dialog_popup);
        builder.show();
        logout = builder.findViewById(R.id.logout_dialog_ok);
        cancel = builder.findViewById(R.id.logout_dialog_cancel);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database.deleteAll();

                Toast logoutMessage = Toast.makeText(getApplicationContext(), "Successfully Logged Out!.", Toast.LENGTH_LONG);
                logoutMessage.setGravity(Gravity.CENTER_VERTICAL, Gravity.CENTER_HORIZONTAL, 350);
                logoutMessage.show();

                Intent intent = new Intent(getApplicationContext(), LoginOptionsPage.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });

    }
}
