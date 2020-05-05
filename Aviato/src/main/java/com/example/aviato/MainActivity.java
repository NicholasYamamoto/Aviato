package com.example.aviato;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aviato.Fragments.MainFragment;
import com.example.aviato.Fragments.ViewProfileFragment;
import com.example.aviato.Pages.AboutUsPage;
import com.example.aviato.Pages.BookAFlightPage;
import com.example.aviato.Pages.ContactUsPage;
import com.example.aviato.Pages.PastOrdersPage;
import com.example.aviato.Pages.SignInPage;
import com.example.aviato.Pages.SplashPage;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;
    ImageView imageView;
    Toolbar toolbar = null;
    Button logout, cancel;
    DatabaseHelper databaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseInstance = new DatabaseHelper(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Home Screen Fragment
        MainFragment fragment = new MainFragment();
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
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

        if (id == R.id.view_cart) {
            Cursor check;
            check = database.getOrderDetails() ;

            Intent intent = new Intent(getApplicationContext(), ViewCartPage.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.view_profile) {
            ViewProfileFragment fragment = new ViewProfileFragment();
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }

        else if (id == R.id.past_orders) {
            Cursor check;
            check = database.getOrderDetails();

            if (check != null && check.getCount() > 0) {
                PastOrdersFragment fragment = new PastOrdersFragment();
                FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.Fragment_container, fragment);
                fragmentTransaction.commit();

            }
 else {
                Toast noOrdersMessage = Toast.makeText(getApplicationContext(), "No past orders found for this user.", Toast.LENGTH_LONG);
                noOrdersMessage.setGravity(Gravity.CENTER_VERTICAL, Gravity.CENTER_HORIZONTAL, 350);
                noOrdersMessage.show();
            }
            Intent intent = new Intent(getApplicationContext(), PastOrdersPage.class);
            startActivity(intent);
        }

        else if (id == R.id.book_a_flight) {
            Intent intent = new Intent(getApplicationContext(), BookAFlightPage.class);
            startActivity(intent);


        }

        else if (id == R.id.about_us) {
            AboutUsFragment fragment = new AboutUsFragment();
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.Fragment_container, fragment);
            fragmentTransaction.commit();

        }

        else if (id == R.id.about_us) {
            Intent intent = new Intent(getApplicationContext(), AboutUsPage.class);
            startActivity(intent);

        }

        else if (id == R.id.contact_us) {
            Intent intent = new Intent(getApplicationContext(), ContactUsPage.class);
            startActivity(intent);
        } else if (id == R.id.log_out) {
            openLogoutDialog();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
     *  Open the Logout dialog box for Users to be able to log out of their accounts.
     */
    public void openLogoutDialog() {
        final Dialog builder = new Dialog(this);
        builder.setContentView(R.layout.dialog_logout);
        builder.show();
        logout = builder.findViewById(R.id.logout_dialog_ok);
        cancel = builder.findViewById(R.id.logout_dialog_cancel);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getApplicationContext().getSharedPreferences(SignInPage.MY_PREFERENCES, 0).edit().clear().apply();
                Intent intent = new Intent(getApplicationContext(), SplashPage.class);
                Toast.makeText(getApplicationContext(), "Successfully Logged Out!", Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
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
