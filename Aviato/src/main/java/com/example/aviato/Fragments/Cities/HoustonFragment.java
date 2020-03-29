package com.example.aviato.Fragments.Cities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.aviato.Adapters.Cities.HoustonAdapter;
import com.example.aviato.Classes.Cities.HoustonClass;
import com.example.aviato.AppDatabaseHelper;
import com.example.aviato.R;

import java.util.ArrayList;

public class HoustonFragment extends Fragment {
    AppDatabaseHelper appDatabaseHelper;

    public HoustonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_houston, container, false);
        this.appDatabaseHelper = new AppDatabaseHelper(getContext());
        ArrayList<HoustonClass> houstonItem = new ArrayList<HoustonClass>();
        houstonItem.add(new HoustonClass("Houston Space\nCenter", "$150", R.drawable.houston_space_center, "0"));
        houstonItem.add(new HoustonClass("Hermann Square", "$550", R.drawable.houston_hermann_square, "0"));
        houstonItem.add(new HoustonClass("Hotel Zaza", "$250", R.drawable.houston_hotel_zaza, "0"));
        houstonItem.add(new HoustonClass("The Houston\nZoo", "$100", R.drawable.houston_houston_zoo, "0"));
        houstonItem.add(new HoustonClass("San Jacinto\nMonument", "$100", R.drawable.houston_san_jacinto, "0"));
        HoustonAdapter houstonAdapter = new HoustonAdapter(getActivity(), houstonItem);

        ListView listView = view.findViewById(R.id.listview_houston);
        listView.setAdapter(houstonAdapter);

        return view;
    }
}
