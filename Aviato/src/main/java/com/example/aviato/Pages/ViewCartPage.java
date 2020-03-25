package com.example.aviato.Pages;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.aviato.Adapters.OrderAdapter;
import com.example.aviato.Classes.PastOrdersClass;
import com.example.aviato.DatabaseHelper;
import com.example.aviato.R;

import java.util.ArrayList;

public class ViewCartPage extends AppCompatActivity {

    //TODO: This can probably be a Fragment to show the cart, rather than it's own page.
    // The Checkout page can handle the functionality that this View Cart page would've
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);

        ListView listView = findViewById(R.id.view_cart_page);
        mydb = new DatabaseHelper(this);


        ArrayList<PastOrdersClass> list = new ArrayList<PastOrdersClass>(); //use to store data from cursor
        Cursor data = mydb.getOrderDetails();  //contain all data

        if (data.getCount() == 0) {


        }

        else {
            while (data.moveToNext()) {

                list.add(new PastOrdersClass(data.getString(0), data.getString(1), data.getString(2), data.getString(3)));

            }
        }

        OrderAdapter adapter = new OrderAdapter(getApplicationContext(), list);
        listView.setAdapter(adapter);
    }
}




    /*public void Order_data(){
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res =   mydb.Get_OrderDetails();
                if(res.getCount() == 0) {
                    showmessage("Error", "nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){

                    buffer.append("Id : " + res.getString(0) + "\n");
                    buffer.append("Item Name : " + res.getString(1) + "\n");
                    buffer.append("Quantity : " + res.getString(2)+ "\n");
                    buffer.append("Price : " + res.getString(3)+ "\n");

                }
                showmessage("Data",buffer.toString());


            }
        });
    }
    public void DeleteAll() {
        delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mydb.delete_all();
        }
    });


    }


        public void showmessage(String title,String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
*/

