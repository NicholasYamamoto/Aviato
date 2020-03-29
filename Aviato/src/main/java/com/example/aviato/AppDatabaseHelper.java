package com.example.aviato;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.aviato.Classes.AccountClass;
import com.example.aviato.Classes.FlightInformationClass;

public class AppDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "AppDatabaseHelper";
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "aviato.db";
    private static final String TABLE_ACCOUNT = "account";
    private static final String TABLE_FLIGHT_INFO = "flight_info";
    private static final String TABLE_TRIP = "trip";
    private static final String TABLE_ORDERS = "past_orders";

    /* Account table columns */
    private static final String COL_ACCOUNT_ID = "account_id";

    private static final String COL_ACCOUNT_FIRST_NAME = "first_name";
    private static final String COL_ACCOUNT_USERNAME = "email";
    private static final String COL_ACCOUNT_PASSWORD = "password";
    private static final String COL_ACCOUNT_DEPART = "default_departure";
    private static final String COL_ACCOUNT_CARRIER = "preferred_carrier";

    /* Trip table columns */
    private static final String COL_TRIP_ID = "trip_id";

    private static final String COL_TRIP_DEST_CITY = "trip_dest_city";
    private static final String COL_TRIP_ATTRACTION_1 = "trip_attraction_1";
    private static final String COL_TRIP_ATTRACTION_2 = "trip_attraction_2";
    private static final String COL_TRIP_ATTRACTION_3 = "trip_attraction_3";
    private static final String COL_TRIP_ATTRACTION_4 = "trip_attraction_4";
    private static final String COL_TRIP_ATTRACTION_5 = "trip_attraction_5";

    //Flight Information table columns
    /*
    TODO: Adapt this so it will encompass the functionality of the
          Trip table and the Order Details table.
          FLIGHT ID is the Unique Key that is used to find the "Order" after
          it is placed. This can be referenced when using the Past Orders functionality
    */
    private static final String COL_FLIGHT_INFO_ID = "flight_id";

    private static final String COL_FLIGHT_INFO_ACCOUNT_ID_FK = "account_id";
    private static final String COL_FLIGHT_INFO_CARRIER_NAME = "carrier_name";
    private static final String COL_FLIGHT_INFO_DEPART_DATE = "depart_date";
    private static final String COL_FLIGHT_INFO_DEPART_CITY = "depart_city";
    private static final String COL_FLIGHT_INFO_DEPART_TIME = "depart_time";
    private static final String COL_FLIGHT_INFO_DEPART_GATE = "depart_gate";
    private static final String COL_FLIGHT_INFO_DEST_DATE = "dest_date";
    private static final String COL_FLIGHT_INFO_DEST_CITY = "dest_city";
    private static final String COL_FLIGHT_INFO_DEST_TIME = "dest_time";
    private static final String COL_FLIGHT_INFO_DEST_GATE = "dest_gate";
    private static final String COL_FLIGHT_INFO_RETURN_DATE = "return_date";
    private static final String COL_FLIGHT_INFO_RETURN_TIME = "return_time";
    private static final String COL_FLIGHT_INFO_RETURN_GATE = "return_gate";
    private static final String COL_FLIGHT_INFO_ORDER_TOTAL = "order_total";
    private static final String COL_FLIGHT_INFO_TICKET_NUMBER = "ticket_number";

    /* Past Orders table columns
            TODO: This will consist of a Unique Order number, which will be auto incremented
                  and used as a Foreign Key to get the actual details of the order from the
                  Flight Information table.
                  This table will be used to query the Flight Information table and return the
                  details of the order like the Departing and Destination city based on the
                  Account ID of the User, so only THAT user's order details are displayed.
    */
    private static final String COL_ORDER_ACCOUNT_ID = "account_ID";
    private static final String COL_ORDER_NUMBER = "order_number";


    //Constructor
    public AppDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Create Account Table
        String CREATE_TABLE_ACCOUNT = "CREATE TABLE " + TABLE_ACCOUNT + " (" +
                COL_ACCOUNT_PASSWORD + " TEXT, " +
                COL_ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_ACCOUNT_FIRST_NAME + " TEXT, " +
                COL_ACCOUNT_CARRIER + " TEXT," +
                COL_ACCOUNT_USERNAME + " TEXT," +
                COL_ACCOUNT_DEPART + " TEXT)";

        sqLiteDatabase.execSQL(CREATE_TABLE_ACCOUNT);

        //Create Trip Table
        String CREATE_TABLE_TRIP = "CREATE TABLE " + TABLE_TRIP + " (" +
                COL_TRIP_DEST_CITY + " TEXT, " +
                COL_TRIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_TRIP_ATTRACTION_1 + " TEXT, " +
                COL_TRIP_ATTRACTION_2 + " TEXT, " +
                COL_TRIP_ATTRACTION_3 + " TEXT, " +
                COL_TRIP_ATTRACTION_4 + " TEXT, " +
                COL_TRIP_ATTRACTION_5 + " TEXT)";

        sqLiteDatabase.execSQL(CREATE_TABLE_TRIP);

        //Create Flight Information Table
        String CREATE_TABLE_FLIGHT_INFO = "CREATE TABLE " + TABLE_FLIGHT_INFO + " (" +
                COL_FLIGHT_INFO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_FLIGHT_INFO_ACCOUNT_ID_FK + " INTEGER REFERENCES " +
                TABLE_ACCOUNT + ", " +
                COL_FLIGHT_INFO_CARRIER_NAME + "TEXT, " +
                COL_FLIGHT_INFO_DEPART_DATE + "INTEGER, " +
                COL_FLIGHT_INFO_DEPART_CITY + "TEXT, " +
                COL_FLIGHT_INFO_DEPART_TIME + "INTEGER, " +
                COL_FLIGHT_INFO_DEPART_GATE + "INTEGER, " +
                COL_FLIGHT_INFO_DEST_DATE + "INTEGER, " +
                COL_FLIGHT_INFO_DEST_CITY + "TEXT," +
                COL_FLIGHT_INFO_DEST_TIME + "INTEGER, " +
                COL_FLIGHT_INFO_DEST_GATE + "INTEGER, " +
                COL_FLIGHT_INFO_RETURN_DATE + "INTEGER, " +
                COL_FLIGHT_INFO_RETURN_TIME + "INTEGER, " +
                COL_FLIGHT_INFO_RETURN_GATE + "INTEGER, " +
                COL_FLIGHT_INFO_ORDER_TOTAL + "INTEGER, " +
                COL_FLIGHT_INFO_TICKET_NUMBER + "INTEGER)";

        sqLiteDatabase.execSQL(CREATE_TABLE_FLIGHT_INFO);

        //Create Orders Table
        String CREATE_TABLE_ORDERS = "CREATE TABLE " + TABLE_ORDERS + " (" +
                COL_ORDER_ACCOUNT_ID + " TEXT, " +
                COL_ORDER_NUMBER + " INTEGER PRIMARY KEY AUTOINCREMENT)";

        sqLiteDatabase.execSQL(CREATE_TABLE_ORDERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int currentVersion) {
        //If database version changes drop all current tables and recreate.
        if (oldVersion != currentVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FLIGHT_INFO);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);

            onCreate(sqLiteDatabase);
        }
    }

    /*
     * Check the Account table for a given emailAddress when password has been forgotten
     */
    public boolean verifyEmailForgotPassword(String emailAddress) {
        String[] columns = {"email"};
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String selection = "email = ?";
        String[] selectionArgs = {emailAddress};

        Cursor cursor = sqLiteDatabase.query(TABLE_ACCOUNT, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();

        cursor.close();

        return count > 0;
    }

    /*
     * Check the Account table for a given emailAddress and password when logging in
     */
    public boolean verifyAccount(String emailAddress, String password) {
        String[] columns = {"email"};
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String selection = "email = ? and password = ?";
        String[] selectionArgs = {emailAddress, password};

        Cursor cursor = sqLiteDatabase.query(TABLE_ACCOUNT, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();

        cursor.close();

        return count > 0;
    }

//    // Add a Trip to the Order, which will populate some things from the Flight Information
//    // table and get displayed in the Checkout Page ONLY if this functionality was used
    public boolean addTripToTable(String Name, String Quantity, String Price) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_NAME, Name);
//        contentValues.put(COL_QUANTITY, Quantity);
//        contentValues.put(COL_PRICE, Price);

        double check = sqLiteDatabase.insert(TABLE_FLIGHT_INFO, null, contentValues);
        return check != -1;
    }

    //Adds Flight Information to the database and returns a Boolean true if insert was successful or false if insert failed
    public boolean addFlightInformationToTable(FlightInformationClass flight) {
        long status;

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("COL_FLIGHT_INFO_CUSTOMER_ID_FK", flight.getCustomerID());
        contentValues.put("COL_FLIGHT_INFO_CARRIER_NAME", flight.getCarrier_id());
        contentValues.put("COL_FLIGHT_INFO_DEPART_DATE", flight.getDepartDate());
        contentValues.put("COL_FLIGHT_INFO_DEPART_CITY", flight.getDepartingCity());
        contentValues.put("COL_FLIGHT_INFO_DEPART_TIME", flight.getDepartingTime());
        contentValues.put("COL_FLIGHT_INFO_DEPART_GATE", flight.getDepartingGate());
        contentValues.put("COL_FLIGHT_INFO_DEST_DATE", flight.getDestinationDate());
        contentValues.put("COL_FLIGHT_INFO_DEST_CITY", flight.getDestinationCity());
        contentValues.put("COL_FLIGHT_INFO_DEST_TIME", flight.getDestinationTime());
        contentValues.put("COL_FLIGHT_INFO_DEST_GATE", flight.getDestinationGate());
        contentValues.put("COL_FLIGHT_INFO_RETURN_TIME", flight.getReturnTime());
        contentValues.put("COL_FLIGHT_INFO_RETURN_DATE", flight.getReturnDate());
        contentValues.put("COL_FLIGHT_INFO_RETURN_GATE", flight.getReturnGate());
        contentValues.put("COL_FLIGHT_INFO_TICKET_NUMBER", flight.getTicketNumber());
        contentValues.put("COL_FLIGHT_INFO_ORDER_TOTAL", flight.getOrderTotal());

        status = sqLiteDatabase.insert(TABLE_FLIGHT_INFO, null, contentValues);

        return status != -1;
    }

    // Adds account to the database and returns boolean true if insert was successful or false if insert failed
    public boolean addAccountToTable(AccountClass account) {
        long status;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("account_ID", account.getAccount_id());
        contentValues.put("first_name", account.getFirstName());
        contentValues.put("email", account.getEmail());
        contentValues.put("password", account.getPassword());
        contentValues.put("preferred_carrier", account.getDefaultCarrier());
        contentValues.put("default_departure", account.getDefaultDepart());

        System.out.println(contentValues.toString());

        status = sqLiteDatabase.insert(AppDatabaseHelper.TABLE_ACCOUNT, null, contentValues);

        return status != -1;
    }

    //Adds city to the database and returns boolean true if insert was successful or false if insert failed
    /*
            TODO: The functionality of this is to choose the Destination city at a more personalized level,
                  allowing you to skip some parts of the regular Book a Flight functionality and choose a city
                  using a UI versus just choosing the name from the Spinner, and add some destinations.
                  So, this can be refactored to use the FlightInformationClass class and you can get rid of the
                  City class and related stuff altogether.

     */
    public boolean addCityToPlan(FlightInformationClass flight) {
        long status;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("COL_FLIGHT_INFO_DEST_CITY", flight.getDestinationCity());

        status = sqLiteDatabase.insert(TABLE_FLIGHT_INFO, null, contentValues);

        return status != -1;
    }

    /*
        TODO: Build this out to insert the values from the order into the Past Orders table
    */
//    Adds completed order to the database and returns boolean true if insert was successful or false if insert failed
//    public boolean addOrderToTable(PastOrdersClass order) {
//        long status;
//
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put("account_ID", order.getAccount_id());
//        contentValues.put("first_name", order.getFirstName());
//        contentValues.put("email", order.getEmail());
//        contentValues.put("password", order.getPassword());
//
//        System.out.println(contentValues.toString());
//
//        status = sqLiteDatabase.insert(AppDatabaseHelper.TABLE_ORDERS, null, contentValues);
//
//        return status != -1;
//    }


    // Query the Order Details table for Checkout
    //TODO: Replace this/fold this into the Trip table stuff
    //      Basically take everything from the final Flight Information table AFTER the order has been placed
    //      and add it to a new Table for Past Orders, referenced by the Order Number primary key!
    public Cursor getOrderDetails() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_FLIGHT_INFO, null);
        return data;
    }

    // Flush the Order Details table
    //TODO: Replace this/fold this into the Trip table stuff
    //      I don't know if this is needed. Why get rid of all the Order Details?
    public void deleteAll() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from " + TABLE_FLIGHT_INFO);
    }
}
