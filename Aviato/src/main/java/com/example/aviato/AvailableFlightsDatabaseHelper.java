package com.example.aviato;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AvailableFlightsDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "available_flights.db";
    private static final String TABLE_AVAILABLE_FLIGHTS = "available_flights";

    /* Available Flights table columns */
    private static final String COL_FLIGHT_NO = "flight_no";
    private static final String COL_CARRIER_NAME = "carrier";

    private static final String COL_DEPART_DATE = "depart_date";
    private static final String COL_DEPART_CITY = "depart_city";
    private static final String COL_DEPART_TIME = "depart_time";
    private static final String COL_DEPART_GATE = "depart_gate";

    private static final String COL_DEST_DATE = "destination_date";
    private static final String COL_DEST_CITY = "destination_city";
    private static final String COL_DEST_TIME = "destination_time";
    private static final String COL_DEST_GATE = "destination_gate";

    private static final String COL_RETURN_DATE = "return_date";
    private static final String COL_RETURN_TIME = "return_time";
    private static final String COL_RETURN_GATE = "return_gate";

    //Constructor
    public AvailableFlightsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Available Flights Table
        String CREATE_TABLE_AVAILABLE_FLIGHTS = "CREATE TABLE " + TABLE_AVAILABLE_FLIGHTS + " (" +
                COL_FLIGHT_NO + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                COL_CARRIER_NAME + " TEXT," +
                COL_DEPART_DATE + " INTEGER, " +
                COL_DEPART_CITY + " TEXT," +
                COL_DEPART_TIME + " INTEGER," +
                COL_DEPART_GATE + " INTEGER, " +
                COL_DEST_DATE + " INTEGER, " +
                COL_DEST_CITY + " TEXT, " +
                COL_DEST_TIME + " INTEGER, " +
                COL_DEST_GATE + " INTEGER, " +
                COL_RETURN_DATE + " INTEGER, " +
                COL_RETURN_TIME + " INTEGER, " +
                COL_RETURN_GATE + " INTEGER)";

        sqLiteDatabase.execSQL(CREATE_TABLE_AVAILABLE_FLIGHTS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int currentVersion) {
        //If database version changes drop all current tables and recreate.
        if (oldVersion != currentVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_AVAILABLE_FLIGHTS);

            onCreate(sqLiteDatabase);
        }
    }

    /*  TODO: Have this query the Available Flights table, creating the query based on the
          values pulled from the Buttons/Spinners/DatePicker, and when a Flight is
          selected, call the addFlight function to put it into the Order Details table.

 */
    public Cursor getAvailableFlights(String departing_date, String departing_city, String destination_city, String return_date) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String[] searchParameters = new String[] { departing_date, departing_city, destination_city, return_date};
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM available_flights " +
                                                  "WHERE depart_date = ? " +
                                                  "AND depart_city = ?" +
                                                  "AND destination_city = ?" +
                                                  "AND return_date = ?",
                                                  searchParameters);
        return data;
    }

//    //    // Add a Trip to the Order, which will populate some things from the Flight Information
////    // table and get displayed in the Checkout Page ONLY if this functionality was used
//    public boolean addToTrip(String Name, String Quantity, String Price) {
//        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
////        contentValues.put(COL_NAME, Name);
////        contentValues.put(COL_QUANTITY, Quantity);
////        contentValues.put(COL_PRICE, Price);
//
//        double check = sqLiteDatabase.insert(TABLE_FLIGHT_INFO, null, contentValues);
//        return check != -1;
//    }



    //Adds city to the database and returns boolean true if insert was successful or false if insert failed
    /*
            TODO: The functionality of this is to choose the Destination city at a more personalized level,
                  allowing you to skip some parts of the regular Book a Flight functionality and choose a city
                  using a UI versus just choosing the name from the Spinner, and add some destinations.
                  So, this can be refactored to use the FlightInformationClass class and you can get rid of the
                  City class and related stuff altogether.

     */
//    public boolean addCityToPlan(FlightInformationClass flight) {
//        long status;
//
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put("COL_FLIGHT_INFO_DEST_CITY", flight.getDestinationCity());
//
//        status = sqLiteDatabase.insert(TABLE_FLIGHT_INFO, null, contentValues);
//
//        return status != -1;
//    }

    /*
        TODO: Build this out to insert the values from the order into the Past Orders table
    */
    // Adds completed order to the database and returns boolean true if insert was successful or false if insert failed
//    public boolean addOrder(PastOrdersClass order) {
//        long status;
//
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put("accountID", order.getAccountID());
//        contentValues.put("first_name", order.getFirstName());
//        contentValues.put("email", order.getEmail());
//        contentValues.put("password", order.getPassword());
//
//        System.out.println(contentValues.toString());
//
//        status = sqLiteDatabase.insert(DatabaseHelper.TABLE_ORDERS, null, contentValues);
//
//        return status != -1;
//    }

}
