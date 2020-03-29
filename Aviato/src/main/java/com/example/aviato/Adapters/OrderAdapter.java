//package com.example.aviato.Adapters;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import com.example.aviato.AppDatabaseHelper;
//import com.example.aviato.Classes.OrderClass;
//import com.example.aviato.R;
//
//import java.util.ArrayList;
//
//
//public class OrderAdapter extends ArrayAdapter<OrderClass> {
//
//    AppDatabaseHelper appDatabaseHelper;
//
//    public OrderAdapter(Context context, ArrayList<OrderClass> data) {
//        super(context, 0, data);
//        this.appDatabaseHelper = new AppDatabaseHelper(context.getApplicationContext());
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        View listItemView = convertView;
//        if (listItemView == null) {
//            listItemView = LayoutInflater.from(getContext()).inflate(
//                    R.layout.order_layout, parent, false);
//        }
//
//        OrderClass data_item = getItem(position);
//
//        TextView idTextView = listItemView.findViewById(R.id.order_item_id);
//        idTextView.setText("Id : " + data_item.getItemId());
//
//        TextView nameTextView = listItemView.findViewById(R.id.order_item_name);
//        nameTextView.setText("Name : " + data_item.getItemName());
//
//        TextView quantityTextView = listItemView.findViewById(R.id.order_item_quantity);
//        quantityTextView.setText("Quantity : " + data_item.getItemquantity());
//
//        TextView priceTextView = listItemView.findViewById(R.id.order_item_price);
//        priceTextView.setText("Price : " + data_item.getItemPrice() + "Rs");
//
//        return listItemView;
//    }
//}
