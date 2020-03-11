package com.example.aviato.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.aviato.Adapters.PlanATripAdapter;
import com.example.aviato.Classes.PlanATripClass;
import com.example.aviato.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlanATripFragment extends Fragment {

    public PlanATripFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_a_city, container, false);


        ArrayList<PlanATripClass> i_food = new ArrayList<PlanATripClass>();
//        i_food.add(new PlanATripClass("Pasta", "450 Rs", R.drawable.pastaone,"0"));
//        i_food.add(new PlanATripClass("Lasagna", "650 Rs", R.drawable.lasagna,"0"));
//        i_food.add(new PlanATripClass("Italian Pizza", "1250 Rs", R.drawable.pizza,"0"));
//        i_food.add(new PlanATripClass("Focaccia Bread", "450 Rs", R.drawable.italiaone,"0"));

        PlanATripAdapter planTripAdapter = new PlanATripAdapter(getActivity(), i_food);

        ListView listView = view.findViewById(R.id.listview_choose_city);
        listView.setAdapter(planTripAdapter);

        return view;
    }

}
