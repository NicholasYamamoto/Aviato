package com.example.aviato.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.aviato.Adapters.AboutUsAdapter;
import com.example.aviato.Classes.AboutUsClass;
import com.example.aviato.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {

    public AboutUsFragment() {
        // Required empty public constructor
    }

    //TODO: Refactor this to display About Us section, probably similar to Contact Us Fragment layout

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);

        ArrayList<AboutUsClass> conti_food = new ArrayList<>();

        AboutUsAdapter aboutUsAdapter = new AboutUsAdapter(getActivity(), conti_food);

        ListView listView = view.findViewById(R.id.listview_about_us);
        listView.setAdapter(aboutUsAdapter);

        return view;
    }

}
