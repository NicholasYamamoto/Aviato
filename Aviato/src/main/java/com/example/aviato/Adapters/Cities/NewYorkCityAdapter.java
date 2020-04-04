package com.example.aviato.Adapters.Cities;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aviato.DatabaseHelper;
import com.example.aviato.Classes.Cities.NewYorkCityClass;
import com.example.aviato.R;

import java.util.ArrayList;


public class NewYorkCityAdapter extends ArrayAdapter<NewYorkCityClass> {
    int quantity = 0;
    int i = 0;
    int value = 0;
    String hold = "";
    int pos = 0, counter = 1;
    String[] order_details = new String[1000];
    DatabaseHelper databaseHelper;
    String Number, Name, Quantity, Price = "";


    public NewYorkCityAdapter(Activity context, ArrayList<NewYorkCityClass> nycItem) {
        super(context, 0, nycItem);
        this.databaseHelper = new DatabaseHelper(context.getApplicationContext());
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.menu_design, parent, false);
        }

        NewYorkCityClass currentNYCItem = getItem(position);

        ImageView imageView = listItemView.findViewById(R.id.item_image);
        imageView.setImageResource(currentNYCItem.getImageResourceId());

        TextView nameTextView = listItemView.findViewById(R.id.item_name);
        nameTextView.setText(currentNYCItem.getItemName());

        TextView priceTextView = listItemView.findViewById(R.id.item_price);
        priceTextView.setText("Price " + currentNYCItem.getItemPrice());


        Button plus = listItemView.findViewById(R.id.btn_plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                quantity = quantity + 1;
                notifyDataSetChanged();

            }
        });

        Button minus = listItemView.findViewById(R.id.btn_minus);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (quantity > 0) quantity = quantity - 1;
                else quantity = quantity;
                notifyDataSetChanged();

            }
        });


        hold = currentNYCItem.getItemquantity();
        value = Integer.parseInt(hold);
        quantity = value + quantity;

        TextView quantityTextView = listItemView.findViewById(R.id.quantity);
        quantityTextView.setText(String.valueOf(quantity));

        Button cart_btn = listItemView.findViewById(R.id.btn_cart);
        cart_btn.setTag(position);

        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {        //Database Items not changed from original items


                //btn_cart.setEnabled(false);
                pos = (Integer) view.getTag();
                if (quantity != 0) { //if quan < 0 or equals to 0
                    if (pos == 0) {
                        boolean isinserted = databaseHelper.addTripToTable("Statue of Liberty Tour", String.valueOf(quantity), String.valueOf(550 * quantity));
                        if (isinserted) {
                            int price = 1; //price * quantity = total price
                            order_details[i] = "Id " + counter + " Tour price " + 150 * quantity + " ";
                            counter++;  //var use for no of items order
                            i++;  //var uses to store data in array */
                            quantity = 0;  //holds the value for each item quantity*s/
                            Toast.makeText(getContext(), "Order Added Successfully !", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        } else
                            Toast.makeText(getContext(), "Please, Try again", Toast.LENGTH_SHORT).show();


                    }
                    if (pos == 1) {
                        boolean isinserted = databaseHelper.addTripToTable("Grand Central Terminal Tour", String.valueOf(quantity), String.valueOf(550 * quantity));
                        if (isinserted) {
                            int price = 1;
                            order_details[i] = "Id " + counter + " Tour price " + 550 * quantity + " ";
                            counter++;
                            i++;
                            quantity = 0;
                            Toast.makeText(getContext(), "Order Added Successfully !", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        } else
                            Toast.makeText(getContext(), "Please, Try again", Toast.LENGTH_SHORT).show();


                    }
                    if (pos == 2) {
                        boolean isinserted = databaseHelper.addTripToTable("St. Patrick's Cathedral Tour", String.valueOf(quantity), String.valueOf(250 * quantity));
                        if (isinserted) {
                            int price = 1;
                            order_details[i] = "Id : " + counter + " Tour price " + 250 * quantity + " ";
                            counter++;
                            i++;
                            quantity = 0;
                            Toast.makeText(getContext(), "Order Added Successfully !", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        } else
                            Toast.makeText(getContext(), "Please, Try again", Toast.LENGTH_SHORT).show();


                    }
                    if (pos == 3) {

                        boolean isinserted = databaseHelper.addTripToTable("Yankee Stadium Tour", String.valueOf(quantity), String.valueOf(100 * quantity));
                        if (isinserted) {
                            int price = 1;
                            order_details[i] = "Id " + counter + " Tour price " + 100 * quantity + " ";
                            counter++;
                            i++;
                            quantity = 0;
                            Toast.makeText(getContext(), "Order Added Successfully !", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();

                        } else
                            Toast.makeText(getContext(), "Please, Try again", Toast.LENGTH_SHORT).show();


                    }
                    if (pos == 4) {//duplicated to match the 5 items

                        boolean isinserted = databaseHelper.addTripToTable("One World Trade Center Tour", String.valueOf(quantity), String.valueOf(100 * quantity));
                        if (isinserted) {
                            int price = 1;
                            order_details[i] = "Id " + counter + " Tour price " + 100 * quantity + " ";
                            counter++;
                            i++;
                            quantity = 0;
                            Toast.makeText(getContext(), "Order Added Successfully !", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();

                        } else
                            Toast.makeText(getContext(), "Please, Try again", Toast.LENGTH_SHORT).show();


                    }


                } else {
                    Toast.makeText(getContext(), "Quantity value can't be zero or lesser!!!", Toast.LENGTH_SHORT).show();

                    int j = 0;
                    Toast.makeText(getContext(), "" + order_details[j] + "\n" + order_details[j + 1] + "\n" + order_details[j + 2] + "\n" + order_details[j + 3], Toast.LENGTH_LONG).show();

                }


            }
        });


        return listItemView;
    }


}
                  /*         Cursor data =  databaseHelper.Get_OrderDetails();
                            if(data != null) {
                                Number = data.getString(0);
                                Name = data.getString(1);
                                Quantity = data.getString(2);
                                Price = data.getString(3);
                                data.moveToNext();
                            }
                            order_details[i] = "Id : " + Number + " Name : " + Name +  "Quantity "  + Quantity + "Price Rs " + Price ;

*/