package com.example.aviato.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aviato.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFlightsFragment extends Fragment {

    public FindFlightsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_find_flights, container, false);

        return view;
    }
}


