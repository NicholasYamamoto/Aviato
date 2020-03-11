package com.example.aviato.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.aviato.Adapters.ViewProfileAdapter;
import com.example.aviato.Classes.ViewProfileClass;
import com.example.aviato.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewProfileFragment extends Fragment {

    public ViewProfileFragment() {
        // Required empty public constructor
    }

    //  TODO: This is in the wrong format, and is formatted like a Restaurant Page where you place orders.
    //   Instead, this needs to be something where you can query the Account table to see things like
    //   Username, Email, and Past Orders, as well as edit things like your Account details


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_profile, container, false);

        ArrayList<ViewProfileClass> fastfood = new ArrayList<>();

        ViewProfileAdapter viewProfileAdapter = new ViewProfileAdapter(getActivity(), fastfood);

        ListView listView = view.findViewById(R.id.listview_view_profile);
        listView.setAdapter(viewProfileAdapter);
        return view;
    }
}
