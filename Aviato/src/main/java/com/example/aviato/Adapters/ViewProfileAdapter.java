package com.example.aviato.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aviato.Classes.ViewProfileClass;
import com.example.aviato.DatabaseHelper;
import com.example.aviato.R;

import java.util.ArrayList;

public class ViewProfileAdapter extends ArrayAdapter<ViewProfileClass> {
    int quantity = 0;
    int i = 0;
    int value = 0;
    String hold = "";
    int pos = 0, counter = 1;
    String[] order_details = new String[1000];
    DatabaseHelper mydb;
    String Number, Name, Quantity, Price = "";

    public ViewProfileAdapter(Activity context, ArrayList<ViewProfileClass> c_food) {
        super(context, 0, c_food);
        this.mydb = new DatabaseHelper(context.getApplicationContext());
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.menu_design, parent, false);
        }

        ViewProfileClass currentcfood = getItem(position);

        ImageView imageView = listItemView.findViewById(R.id.item_image);
        imageView.setImageResource(currentcfood.getImageResourceId());

        TextView nameTextView = listItemView.findViewById(R.id.item_name);
        nameTextView.setText(currentcfood.getItemName());

        TextView priceTextView = listItemView.findViewById(R.id.item_price);
        priceTextView.setText("Price " + currentcfood.getItemPrice());


        Button plus = listItemView.findViewById(R.id.btn_plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quantity = quantity + 1;

            }
        });

        Button minus = listItemView.findViewById(R.id.btn_minus);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (quantity > 0) quantity = quantity - 1;
                else quantity = quantity;

            }
        });

        hold = currentcfood.getItemquantity();
        value = Integer.parseInt(hold);
        quantity = value + quantity;

        TextView quantityTextView = listItemView.findViewById(R.id.quantity);
        quantityTextView.setText(String.valueOf(quantity));

        Button cart_btn = listItemView.findViewById(R.id.btn_cart);
        cart_btn.setTag(position);

        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //btn_cart.setEnabled(false);
                pos = (Integer) view.getTag();
                if (quantity != 0) { //if quan < 0 or equals to 0
                    if (pos == 0) {
                        boolean isinserted = mydb.addToCart("Fried Rice", String.valueOf(quantity), String.valueOf(550 * quantity));
                        if (isinserted) {
                            int price = 1; //price * quantity = total price
                            order_details[i] = "Id " + counter + " Fried Rice Price Rs " + 150 * quantity + " ";
                            counter++;  //var use for no of items order
                            i++;  //var uses to store data in array */
                            quantity = 0;  //holds the value for each item quantity*s/
                            Toast.makeText(getContext(), "Order Added Successfully !", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getContext(), "Please, Try again", Toast.LENGTH_SHORT).show();
                    }

                    if (pos == 1) {
                        boolean isinserted = mydb.addToCart("Sushi", String.valueOf(quantity), String.valueOf(550 * quantity));
                        if (isinserted) {
                            int price = 1;
                            order_details[i] = "Id " + counter + " Sushi Price Rs " + 550 * quantity + " ";
                            counter++;
                            i++;
                            quantity = 0;
                            Toast.makeText(getContext(), "Order Added Successfully !", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getContext(), "Please, Try again", Toast.LENGTH_SHORT).show();
                    }
                    if (pos == 2) {
                        boolean isinserted = mydb.addToCart("Haka Noodles", String.valueOf(quantity), String.valueOf(250 * quantity));
                        if (isinserted) {
                            int price = 1;
                            order_details[i] = "Id : " + counter + " Haka Noodles Price Rs " + 250 * quantity + " ";
                            counter++;
                            i++;
                            quantity = 0;
                            Toast.makeText(getContext(), "Order Added Successfully !", Toast.LENGTH_SHORT).show();

                        } else
                            Toast.makeText(getContext(), "Please, Try again", Toast.LENGTH_SHORT).show();
                    }

                    if (pos == 3) {

                        boolean isinserted = mydb.addToCart("Corn Soup", String.valueOf(quantity), String.valueOf(100 * quantity));
                        if (isinserted) {
                            int price = 1;
                            order_details[i] = "Id " + counter + " Corn Soup Price Rs " + 100 * quantity + " ";
                            counter++;
                            i++;
                            quantity = 0;
                            Toast.makeText(getContext(), "Order Added Successfully !", Toast.LENGTH_SHORT).show();

                        } else
                            Toast.makeText(getContext(), "Please, Try again", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(getContext(), "Quantity value can't be zero or lesser!!!", Toast.LENGTH_SHORT).show();
                    int j = 0;
                    Toast.makeText(getContext(), "" + order_details[j] + "\n" + order_details[j + 1] + "\n" + order_details[j + 2]
                            + "\n" + order_details[j + 3], Toast.LENGTH_LONG).show();
                }
            }
        });

        return listItemView;
    }
}
