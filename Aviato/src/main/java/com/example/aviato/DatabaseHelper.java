package com.example.aviato;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.aviato.Classes.AccountClass;
import com.example.aviato.Classes.CarrierClass;
import com.example.aviato.Classes.PlanATripClass;
import com.example.aviato.Classes.TripClass;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "aviato.db";

    //Database Tables
    private static final String TABLE_ACCOUNT = "account";
    private static final String TABLE_TRIP = "trip";
    private static final String TABLE_CARRIER = "carrier";
    private static final String TABLE_CITY = "city";
    // Update this to include the R.string value for Order Details values
    private static final String TABLE_ORDER_DETAILS = "order_details";

    //Order Details table columns
    //TODO: Need to fix these then should be good to go!
    private static final String COL_PRICE = "price";
    private static final String COL_NAME = "name"; // THIS IS A TEMP VARIABLE
    private static final String COL_QUANTITY = "quantity";
    private static final String COL_ORDER_NUMBER_FK = "order_number";

    //Account table columns
    private static final String COL_ACCOUNT_ID = "account_ID";
    private static final String COL_ACCOUNT_EMAIL = "email";
    private static final String COL_ACCOUNT_PASSWORD = "password";
    private static final String COL_ACCOUNT_DEPART = "default_departure";
    private static final String COL_ACCOUNT_CARRIER = "preferred_carrier";
    private static final String COL_ACCOUNT_NAME = "user_name";

    //City table columns
    private static final String COL_CITY_ID = "city_ID";
    private static final String COL_CITY_NAME = "city_name";
    private static final String COL_CITY_PIC = "picture";

    //Carrier table columns
    private static final String COL_CARRIER_ID = "carrier_ID";
    private static final String COL_CARRIER_NAME = "carrier_name";

    //Trip table columns
    private static final String COL_TRIP_ID = "trip_ID";
    private static final String COL_TRIP_CUSTOMER_ID_FK = "customer_id";
    private static final String COL_TRIP_CARRIER_ID_FK = "carrier_id";
    private static final String COL_TRIP_DEPART_DATE = "depart_date";
    private static final String COL_TRIP_DEPART_LOC = "depart_location";
    private static final String COL_TRIP_DEPART_TIME = "depart_time";
    private static final String COL_TRIP_DEPART_GATE = "depart_gate";
    private static final String COL_TRIP_DEST_LOC = "dest_location";
    private static final String COL_TRIP_DEST_TIME = "dest_time";
    private static final String COL_TRIP_DEST_GATE = "dest_gate";
    private static final String COL_TRIP_RETURN_DATE = "return_date";
    private static final String COL_TRIP_RETURN_TIME = "return_time";
    private static final String COL_TRIP_RETURN_GATE = "return_gate";
    private static final String COL_TRIP_TICKET_NUMBER = "ticket_number";

    //Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Create Account Table
        String CREATE_TABLE_ACCOUNT = "CREATE TABLE " + TABLE_ACCOUNT + " (" +
                COL_ACCOUNT_PASSWORD + " TEXT, " +
                COL_ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_ACCOUNT_NAME + " TEXT, " +
                COL_ACCOUNT_CARRIER + " TEXT," +
                COL_ACCOUNT_EMAIL + " TEXT," +
                COL_ACCOUNT_DEPART + " TEXT)";

        //Create City Table
        String CREATE_TABLE_CITY = "CREATE TABLE " + TABLE_CITY + " (" +
                COL_CITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_CITY_NAME + " TEXT," +
                COL_CITY_PIC + " TEXT)";

        //Create Carrier Table
        String CREATE_TABLE_CARRIER = "CREATE TABLE " + TABLE_CARRIER + " (" +
                COL_CARRIER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_CARRIER_NAME + " TEXT)";

        //Create Trip Table
        String CREATE_TABLE_TRIP = "CREATE TABLE " + TABLE_TRIP + " (" +
                COL_TRIP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_TRIP_CUSTOMER_ID_FK + " INTEGER REFERENCES " +
                TABLE_ACCOUNT + ", " +
                COL_TRIP_CARRIER_ID_FK + " INTEGER REFERENCES " +
                TABLE_CARRIER + ", " +
                COL_TRIP_DEPART_DATE + "TEXT, " +
                COL_TRIP_DEPART_LOC + "TEXT, " +
                COL_TRIP_DEPART_TIME + "TEXT, " +
                COL_TRIP_DEPART_GATE + "TEXT, " +
                COL_TRIP_DEST_LOC + "TEXT," +
                COL_TRIP_DEST_TIME + "TEXT, " +
                COL_TRIP_DEST_GATE + "TEXT, " +
                COL_TRIP_RETURN_DATE + "TEXT, " +
                COL_TRIP_RETURN_GATE + "TEXT, " +
                COL_TRIP_RETURN_TIME + "TEXT, " +
                COL_TRIP_TICKET_NUMBER + "TEXT)";

        //Create Order Details Table
        String CREATE_TABLE_ORDER_DETAILS = "CREATE TABLE " + TABLE_ORDER_DETAILS + " (" +
                COL_PRICE + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " TEXT," +
                COL_QUANTITY + " TEXT," +
                COL_ORDER_NUMBER_FK + " INTEGER)"; //TODO: Make this into a Foreign Key instead of just Integer

        //Executes Table creation
        sqLiteDatabase.execSQL(CREATE_TABLE_ACCOUNT);
        sqLiteDatabase.execSQL(CREATE_TABLE_CITY);
        sqLiteDatabase.execSQL(CREATE_TABLE_CARRIER);
        sqLiteDatabase.execSQL(CREATE_TABLE_TRIP);
        sqLiteDatabase.execSQL(CREATE_TABLE_ORDER_DETAILS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //If database version changes drop all current tables and recreate.
        if (i != i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CARRIER);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_DETAILS);
            onCreate(sqLiteDatabase);
        }
    }

    public boolean verifyEmailForgotPassword(String emailAddress) {
        String[] columns = {"user_name"};
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = "user_name=?";
        String[] selectionArgs = {emailAddress};

        Cursor cursor = db.query(TABLE_ACCOUNT, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();

        cursor.close();

        return count > 0;
    }

    public boolean verifyAccount(String username, String password) {
        String[] columns = {"user_name"};
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = "user_name=? and password = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_ACCOUNT, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();

        cursor.close();

        return count > 0;
    }


    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_ACCOUNT, null);
        return res;
    }

    //ORDER DETAILS DATABASE WORK
    public boolean addToCart(String Name, String Quantity, String Price) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(COL_NAME, Name);
        contentValues1.put(COL_QUANTITY, Quantity);
        contentValues1.put(COL_PRICE, Price);
        double check = db.insert(TABLE_ORDER_DETAILS, null, contentValues1);
        return check != -1;
    }

    // Query the Order Details table for Checkout
    public Cursor getOrderDetails() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_ORDER_DETAILS, null);
        return data;
    }

    // Flush the Order Details table
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_ORDER_DETAILS);
    }

    //Adds account to the database and returns boolean true if insert was successful or false if insert failed
    public boolean addAccount(AccountClass account) {
        long status;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //TODO: Fix this
        //contentValues.put("account_ID", account.getAccount_id());
        contentValues.put("user_name", account.getFirstName());
        contentValues.put("email", account.getEmail());
        contentValues.put("password", account.getPassword());
        contentValues.put("preferred_carrier", account.getDefaultCarrier());
        contentValues.put("default_departure", account.getDefaultDepart());

        System.out.println(contentValues.toString());

        status = sqLiteDatabase.insert(DatabaseHelper.TABLE_ACCOUNT, null, contentValues);

        return status != -1;
    }

    //Adds city to the database and returns boolean true if insert was successful or false if insert failed
    public boolean addCity(PlanATripClass city) {
        long status;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("COL_CITY_NAME", city.getName());
        contentValues.put("COL_CITY_PIC", city.getPicFile());

        status = sqLiteDatabase.insert(TABLE_CITY, null, contentValues);

        return status != -1;

    }

    //Adds carrier to the database and returns boolean true if insert was successful or false if insert failed
    public boolean addCarrier(CarrierClass carrier) {
        long status;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("COL_CARRIER_NAME", carrier.getName());

        status = sqLiteDatabase.insert(TABLE_CARRIER, null, contentValues);

<<<<<<< Updated upstream
        return status != -1;
=======
        updateDatabase(db, oldVersion, newVersion);

    }

    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 1) {

            // Create all Database tables
            db.execSQL(createAirlineTable());
            db.execSQL(createFlightTable());
            db.execSQL(createSeatTable());
            db.execSQL(createFlightTypeTable());
            db.execSQL(createClientTable());
            db.execSQL(createAccountTable());
            db.execSQL(createPastOrdersTable());

            // Insert Airlines to table
            insertAirline(db, "Southwest Airlines");
            insertAirline(db, "Delta Airlines");
            insertAirline(db, "American Airlines");
            insertAirline(db, "SkyWest Airlines");
            insertAirline(db, "JetBlue Airlines");
            insertAirline(db, "Alaska Airlines");
            insertAirline(db, "Spirit Airlines");

            // Insert Flights to table
            insertFlight(db, "Los Angeles, CA - LAX", "Kansas City, MO - MCI", "2020-5-4", "2020-5-4", "10:10 AM", "12:10 PM", 200.00, 1);
            insertFlight(db, "Los Angeles, CA - LAX", "Kansas City, MO - MCI", "2020-5-4", "2020-5-4", "10:10 AM", "12:10 PM", 150.00, 2);
            insertFlight(db, "Los Angeles, CA - LAX", "Kansas City, MO - MCI", "2020-5-4", "2020-5-4", "11:10 AM", "12:10 PM", 350.00, 3);

            insertFlight(db, "Kansas City, MO - MCI", "Los Angeles, CA - LAX", "2020-5-6", "2020-5-6", "10:10 AM", "12:10 PM", 120.00, 6);
            insertFlight(db, "Kansas City, MO - MCI", "Los Angeles, CA - LAX", "2020-5-6", "2020-5-6", "09:00 AM", "12:10 PM", 150.00, 7);
            insertFlight(db, "Kansas City, MO - MCI", "Los Angeles, CA - LAX", "2020-5-6", "2020-5-6", "10:10 AM", "12:10 PM", 170.00, 8);

            // Insert Seats to table
            insertSeat(db, 0, 1, 1);
            insertSeat(db, 0, 2, 1);
            insertSeat(db, 0, 3, 1);
            insertSeat(db, 0, 4, 1);
            insertSeat(db, 0, 5, 1);
            insertSeat(db, 0, 6, 1);
            insertSeat(db, 0, 7, 1);
            insertSeat(db, 0, 8, 1);
            insertSeat(db, 0, 9, 1);
            insertSeat(db, 0, 10, 1);
            insertSeat(db, 0, 11, 1);
            insertSeat(db, 0, 12, 1);
            insertSeat(db, 0, 13, 1);
            insertSeat(db, 0, 14, 1);
            insertSeat(db, 0, 15, 1);
            insertSeat(db, 0, 16, 1);
            insertSeat(db, 0, 17, 1);
            insertSeat(db, 0, 18, 1);
            insertSeat(db, 0, 19, 1);
            insertSeat(db, 0, 20, 1);
            insertSeat(db, 0, 21, 1);
            insertSeat(db, 0, 22, 1);
            insertSeat(db, 0, 23, 1);
            insertSeat(db, 0, 24, 1);
            insertSeat(db, 0, 25, 1);
            insertSeat(db, 0, 26, 2);
            insertSeat(db, 0, 27, 2);
            insertSeat(db, 0, 28, 2);

            // Insert Flight Class to table
            insertFlightClass(db, "Economy");
            insertFlightClass(db, "Business");


            // Insert default Client to table
            insertClient(db, "Chuck", "Norris", "8168675309", "1234123412341234");

            // Insert default User Account to table
            insertAccount(db, "chuck@norris.com", "password", 1);

            db.execSQL(updateFlight());
            db.execSQL(updateSeatNumber());

        }
    }

    public String createAirlineTable() {
        return "CREATE TABLE AIRLINE ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "AIRLINE_CARRIER TEXT COLLATE NOCASE);";
    }

    public String createFlightTable() {
        return "CREATE TABLE FLIGHT (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FLIGHT_NUMBER INTEGER, " +
                "DEPARTING_CITY TEXT COLLATE NOCASE, " +
                "DESTINATION_CITY TEXT COLLATE NOCASE, " +
                "DEPARTING_DATE DATE, " +
                "DESTINATION_DATE DATE, " +
                "DEPARTING_TIME TIME, " +
                "DESTINATION_TIME TIME, " +
                "FLIGHT_DURATION TIME, " +
                "PRICE REAL, " +
                "FLIGHT_AIRLINE INTEGER, " +
                "FOREIGN KEY(FLIGHT_AIRLINE) REFERENCES AIRLINE(_id));";
    }

    public String createSeatTable() {
        return "CREATE TABLE SEAT (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "SEAT_NUMBER INTEGER, " +
                "SEAT_FLIGHT INTEGER, " +
                "STATUS INTEGER, " +
                "SEAT_FLIGHT_TYPE INTEGER, " +
                "FOREIGN KEY(SEAT_FLIGHT) REFERENCES FLIGHT(_id)," +
                "FOREIGN KEY(SEAT_FLIGHT_TYPE) REFERENCES FLIGHT_TYPE(_id));";
    }

    public String createFlightTypeTable() {
        return "CREATE TABLE FLIGHT_TYPE (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FLIGHT_TYPE_NAME TEXT);";
    }

    public String createClientTable() {
        return "CREATE TABLE CLIENT (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FIRST_NAME TEXT COLLATE NOCASE, " +
                "LAST_NAME TEXT COLLATE NOCASE, " +
                "PHONE TEXT, " +
                "CREDIT_CARD_NUMBER TEXT, " +
                "IMAGE BLOB);";
    }

    public String createAccountTable() {
        return "CREATE TABLE ACCOUNT (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "EMAIL TEXT, " +
                "PASSWORD TEXT, " +
                "ACCOUNT_CLIENT INTEGER, " +
                "FOREIGN KEY (ACCOUNT_CLIENT) REFERENCES CLIENT(_id));";
    }

    public String createPastOrdersTable() {
        return "CREATE TABLE PAST_ORDERS (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TIMESTAMP DATETIME DEFAULT (STRFTIME('%Y-%m-%d  %H:%M', 'NOW','localtime')), " +
                "PAST_ORDERS_CLIENT INTEGER, " +
                "PAST_ORDERS_FLIGHT INTEGER, " +
                "PASSENGER INTEGER, " +
                "FOREIGN KEY(PAST_ORDERS_CLIENT) REFERENCES CLIENT(_id), " +
                "FOREIGN KEY(PAST_ORDERS_FLIGHT) REFERENCES FLIGHT(_id));";
>>>>>>> Stashed changes
    }

    //Adds trip to the database and returns boolean true if insert was successful or false if insert failed
    public boolean addTrip(TripClass trip) {
        long status;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("COL_TRIP_CUSTOMER_ID_FK", trip.getCustomer_id());
        contentValues.put("COL_TRIP_CARRIER_ID_FK", trip.getCarrier_id());
        contentValues.put("COL_TRIP_DEPART_DATE", trip.getDepartDate());
        contentValues.put("COL_TRIP_DEPART_LOC", trip.getDepartLoc());
        contentValues.put("COL_TRIP_DEPART_TIME", trip.getDepartTime());
        contentValues.put("COL_TRIP_DEPART_GATE", trip.getDepartGate());
        contentValues.put("COL_TRIP_DEST_LOC", trip.getDestinationLoc());
        contentValues.put("COL_TRIP_DEST_TIME", trip.getDestinationTime());
        contentValues.put("COL_TRIP_DEST_GATE", trip.getGetDestinationGate());
        contentValues.put("COL_TRIP_RETURN_TIME", trip.getReturnTime());
        contentValues.put("COL_TRIP_RETURN_DATE", trip.getReturnDate());
        contentValues.put("COL_TRIP_RETURN_GATE", trip.getReturnGate());
        contentValues.put("COL_TRIP_TICKET_NUMBER", trip.getTicketNumber());

        status = sqLiteDatabase.insert(TABLE_TRIP, null, contentValues);

        return status != -1;
    }
}
