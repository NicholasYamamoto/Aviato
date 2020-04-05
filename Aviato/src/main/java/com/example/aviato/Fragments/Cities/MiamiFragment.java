//package com.example.aviato.Fragments.Cities;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//
//import com.example.aviato.Adapters.Cities.MiamiAdapter;
//import com.example.aviato.Classes.Cities.MiamiClass;
//import com.example.aviato.DatabaseHelper;
//import com.example.aviato.R;
//
//import java.util.ArrayList;
//
//public class MiamiFragment extends Fragment {
//    DatabaseHelper databaseHelper;
//
//    public MiamiFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        View view = inflater.inflate(R.layout.fragment_miami, container, false);
//        this.databaseHelper = new DatabaseHelper(getContext());
//        ArrayList<MiamiClass> miamiItem = new ArrayList<>();
//        miamiItem.add(new MiamiClass("American Airlines\nArena", "$150", R.drawable.miami_american_airlines_arena, "0"));
//        miamiItem.add(new MiamiClass("Bayfront Park", "$550", R.drawable.miami_bayfront_park, "0"));
//        miamiItem.add(new MiamiClass("Miami Art\nDistrict", "$250", R.drawable.miami_art_deco_district, "0"));
//        miamiItem.add(new MiamiClass("Miami Seaquarium", "$100", R.drawable.miami_miami_seaquarium, "0"));
//        miamiItem.add(new MiamiClass("Miami South\nBeach", "$100", R.drawable.miami_south_beach, "0"));
//        MiamiAdapter miamiAdapter = new MiamiAdapter(getActivity(), miamiItem);
//
//        ListView listView = view.findViewById(R.id.listview_miami);
//        listView.setAdapter(miamiAdapter);
//
//        return view;
//    }
//}