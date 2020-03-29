package com.example.aviato.Pages;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.aviato.AppDatabaseHelper;
import com.example.aviato.R;

public class ViewProfilePage extends AppCompatActivity {
    AppDatabaseHelper appDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);

        Intent intent = new Intent(getApplicationContext(), ViewProfilePage.class);
        startActivity(intent);
        finish();
    }

//    public void User_info(){
//
//        details.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Cursor res = appDatabaseHelper.getData();
//                if(res.getCount() == 0) {
//                    showMessage("Error", "nothing found");
//                    return;
//                }
//                StringBuffer buffer = new StringBuffer();
//                while (res.moveToNext()){
//
//                    buffer.append("Id : " + res.getString(0) + "\n");
//                    buffer.append("Username : " + res.getString(1) + "\n");
//                    buffer.append("Password : " + res.getString(2)+ "\n");
//                    buffer.append("Carrier : " + res.getString(3)+ "\n");
//                    buffer.append("City : " + res.getString(4)+ "\n");
//                    buffer.append("Order Details : " + res.getString(5)+ "\n");
//
//                }
//                showMessage("Data",buffer.toString());
//
//
//            }
//        });
//
//    }

    public void showMessage(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
}
