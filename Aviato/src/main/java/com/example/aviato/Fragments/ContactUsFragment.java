package com.example.aviato.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.aviato.Adapters.ContactUsAdapter;
import com.example.aviato.Classes.ContactUsClass;
import com.example.aviato.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment {


    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        ArrayList<ContactUsClass> conti_food = new ArrayList<ContactUsClass>();

        ContactUsAdapter contactUsAdapter = new ContactUsAdapter(getActivity(), conti_food);

        ListView listView = view.findViewById(R.id.listview_contact_us);
        listView.setAdapter(contactUsAdapter);

        return view;
    }

}
