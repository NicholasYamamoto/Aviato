package com.example.aviato.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aviato.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderConfirmationFragment extends Fragment {

    String email = "aviato-orders@gmail.com";

    public OrderConfirmationFragment() {
        // Required empty public constructor;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Your Aviato Order!");

        if (intent.resolveActivity(getActivity().getPackageManager()) != null)
            startActivity(intent);

        return inflater.inflate(R.layout.fragment_order_confirmation, container, false);

    }

}
