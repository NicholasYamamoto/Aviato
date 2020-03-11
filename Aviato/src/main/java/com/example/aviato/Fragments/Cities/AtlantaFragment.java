package com.example.aviato.Fragments.Cities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.aviato.Adapters.Cities.AtlantaAdapter;
import com.example.aviato.Classes.Cities.AtlantaClass;
import com.example.aviato.DatabaseHelper;
import com.example.aviato.R;

import java.util.ArrayList;

public class AtlantaFragment extends Fragment {
    DatabaseHelper mydb;

    public AtlantaFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_atlanta, container, false);
        this.mydb = new DatabaseHelper(getContext());
        ArrayList<AtlantaClass> atlantaItem = new ArrayList<AtlantaClass>();
        atlantaItem.add(new AtlantaClass("Piedmont Park", "$150", R.drawable.atlanta_piedmont_park, "0"));
        atlantaItem.add(new AtlantaClass("Martin Luther King\nJr. National Museum", "$550", R.drawable.atlanta_mlk_national_museum, "0"));
        atlantaItem.add(new AtlantaClass("Skyview Atlanta", "$250", R.drawable.atlanta_skyview_atlanta, "0"));
        atlantaItem.add(new AtlantaClass("High Museum\nof Art", "$100", R.drawable.atlanta_high_museum_of_art, "0"));
        atlantaItem.add(new AtlantaClass("Sweetwater Creek\nState Park", "$100", R.drawable.atlanta_sweetwater_creek_state_park, "0"));
        AtlantaAdapter atlantaAdapter = new AtlantaAdapter(getActivity(), atlantaItem);

        ListView listView = view.findViewById(R.id.listview_atlanta);
        listView.setAdapter(atlantaAdapter);

        return view;
    }
}
