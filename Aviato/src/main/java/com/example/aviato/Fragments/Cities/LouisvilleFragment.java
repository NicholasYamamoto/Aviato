//package com.example.aviato.Fragments.Cities;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//
//import com.example.aviato.Adapters.Cities.LouisvilleAdapter;
//import com.example.aviato.Classes.Cities.LouisvilleClass;
//import com.example.aviato.DatabaseHelper;
//import com.example.aviato.R;
//
//import java.util.ArrayList;
//
//public class LouisvilleFragment extends Fragment {
//    DatabaseHelper databaseHelper;
//
//    public LouisvilleFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        View view = inflater.inflate(R.layout.fragment_louisville, container, false);
//        this.databaseHelper = new DatabaseHelper(getContext());
//        ArrayList<LouisvilleClass> louisvilleItem = new ArrayList<LouisvilleClass>();                                     //remember to change drawables
//        louisvilleItem.add(new LouisvilleClass("Franfort Trolley", "$150", R.drawable.louisville_frankfort_trolley, "0"));
//        louisvilleItem.add(new LouisvilleClass("The Muhammad\nAli Center", "$550", R.drawable.louisville_muhammad_ali_center, "0"));
//        louisvilleItem.add(new LouisvilleClass("The Old 502\nWinery", "$250", R.drawable.louisville_old_502_winery, "0"));
//        louisvilleItem.add(new LouisvilleClass("Fairmount Falls", "$100", R.drawable.louisville_fairmount_falls, "0"));
//        louisvilleItem.add(new LouisvilleClass("The Howard\nSteamboat Museum", "$100", R.drawable.louisville_howard_steamboat_museum, "0"));
//        LouisvilleAdapter louisvilleAdapter = new LouisvilleAdapter(getActivity(), louisvilleItem);
//
//        ListView listView = view.findViewById(R.id.listview_louisville);
//        listView.setAdapter(louisvilleAdapter);
//
//        return view;
//    }
//}
