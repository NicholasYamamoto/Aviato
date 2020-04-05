//package com.example.aviato.Fragments.Cities;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//
//import com.example.aviato.Adapters.Cities.NewYorkCityAdapter;
//import com.example.aviato.Classes.Cities.NewYorkCityClass;
//import com.example.aviato.DatabaseHelper;
//import com.example.aviato.R;
//
//import java.util.ArrayList;
//
//public class NewYorkCityFragment extends Fragment {
//    DatabaseHelper databaseHelper;
//
//    public NewYorkCityFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        View view = inflater.inflate(R.layout.fragment_new_york_city, container, false);              //need to change fragment
//        this.databaseHelper = new DatabaseHelper(getContext());
//        ArrayList<NewYorkCityClass> nycItem = new ArrayList<NewYorkCityClass>();
//        nycItem.add(new NewYorkCityClass("Statue of\nLiberty", "$150", R.drawable.nyc_statue_of_liberty, "0"));
//        nycItem.add(new NewYorkCityClass("Grand Central\nTerminal", "$550", R.drawable.nyc_grand_central_terminal, "0"));
//        nycItem.add(new NewYorkCityClass("St. Patrick's\nCathedral", "$250", R.drawable.nyc_st_patricks_cathedral, "0"));
//        nycItem.add(new NewYorkCityClass("Yankee Stadium", "$100", R.drawable.nyc_yankee_stadium, "0"));
//        nycItem.add(new NewYorkCityClass("One World\nTrade Center", "$100", R.drawable.nyc_trade_center, "0"));
//        NewYorkCityAdapter newYorkCityAdapter = new NewYorkCityAdapter(getActivity(), nycItem);
//
//        ListView listView = view.findViewById(R.id.listview_new_york_city);
//        listView.setAdapter(newYorkCityAdapter);
//
//        return view;
//    }
//}
