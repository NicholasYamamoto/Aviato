///*************************************************************************************************
// * JANUARY 8, 2018
// *  Mentesnot Aboset
// * ************************************************************************************************/
//package com.example.aviato.Pages;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.AppCompatActivity;
//
//import com.example.aviato.R;
//
//public class CheckOutPage2 extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_checkout_page2);
//
//        if (currentTab == 0) {
//            // Create new fragment and transaction
//            CheckoutOnewayFragment newFragment = new CheckoutOnewayFragment();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//            // Replace whatever is in the fragment_container view with this fragment,
//            // and add the transaction to the back stack
//            transaction.replace(R.id.fragment_container, newFragment);
//            transaction.addToBackStack(null);
//
//            // Commit the transaction
//            transaction.commit();
//        } else if (currentTab == 1) {
//            // Create new fragment and transaction
//            CheckoutRoundFragment newFragment = new CheckoutRoundFragment();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//            // Replace whatever is in the fragment_container view with this fragment,
//            // and add the transaction to the back stack
//            transaction.replace(R.id.fragment_container, newFragment);
//            transaction.addToBackStack(null);
//
//            // Commit the transaction
//            transaction.commit();
//        }
//    }
//}
