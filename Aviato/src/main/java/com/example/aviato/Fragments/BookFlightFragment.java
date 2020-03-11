package com.example.aviato.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.aviato.Adapters.BookFlightAdapter;
import com.example.aviato.Classes.BookFlightClass;
import com.example.aviato.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookFlightFragment extends Fragment {

    public BookFlightFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_flight, container, false);

        ArrayList<BookFlightClass> current_flights_list = new ArrayList<BookFlightClass>();
//        current_flights_list.add(new BookFlightClass("Finger Fish", "600 Rs", R.drawable.fingerfish, "0"));
//        current_flights_list.add(new BookFlightClass("Shrimp", "900 Rs", R.drawable.shrimp, "0"));
//        current_flights_list.add(new BookFlightClass("Fried Fish", "650 Rs", R.drawable.fishone, "0"));
//        current_flights_list.add(new BookFlightClass("Prawn Soup", "450 Rs", R.drawable.prawnsoup, "0"));

        BookFlightAdapter bookFlightAdapter = new BookFlightAdapter(getActivity(), current_flights_list);

        ListView listView = view.findViewById(R.id.listview_book_flight);
        listView.setAdapter(bookFlightAdapter);

        return view;
    }
}
