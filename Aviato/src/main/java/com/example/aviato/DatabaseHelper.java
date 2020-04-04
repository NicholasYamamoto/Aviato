package com.example.aviato;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "aviato.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ACCOUNT = "ACCOUNT";
    private static final String TABLE_FLIGHT_INFO = "FLIGHT_INFO";
    private static final String TABLE_TRIP = "TRIP";
    private static final String TABLE_ORDERS = "PAST_ORDERS";
    private static final String TABLE_AIRLINES = "AIRLINE";
    private static final String TABLE_FLIGHT_TYPE = "FLIGHT_TYPE";
    private static final String TABLE_SEATS = "SEAT";
    private static final String TABLE_CLIENTS = "CLIENT";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase databaseInstance) {
        updateDatabase(databaseInstance, 0, DATABASE_VERSION);
    }

    //requires API level 16 and above
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onOpen(SQLiteDatabase databaseInstance) {
        super.onOpen(databaseInstance);
        if (!databaseInstance.isReadOnly()) {
            databaseInstance.setForeignKeyConstraintsEnabled(true);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase databaseInstance, int oldVersion, int newVersion) {

        databaseInstance.execSQL("DROP TABLE IF EXISTS " + TABLE_AIRLINES);
        databaseInstance.execSQL("DROP TABLE IF EXISTS " + TABLE_FLIGHT_INFO);
        databaseInstance.execSQL("DROP TABLE IF EXISTS " + TABLE_SEATS);
        databaseInstance.execSQL("DROP TABLE IF EXISTS " + TABLE_FLIGHT_TYPE);
        databaseInstance.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTS);
        databaseInstance.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        databaseInstance.execSQL("DROP TABLE IF EXISTS " + "ITINERARY");

        updateDatabase(databaseInstance, oldVersion, newVersion);
    }

    private void updateDatabase(SQLiteDatabase databaseInstance, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            // Create Database tables
            databaseInstance.execSQL(createAirlinesTable());
            databaseInstance.execSQL(createFlightInfoTable());
            databaseInstance.execSQL(createSeatTable());
            databaseInstance.execSQL(createFlightTypeTable());
            databaseInstance.execSQL(createClientTable());
            databaseInstance.execSQL(createAccountTable());
            databaseInstance.execSQL(createItinerary());

            // Populate test data
            // TODO: Replace all of these inserts with data from an external file,
            //       not hard-coded values
            insertAirline(databaseInstance, "Air Canada");
            insertAirline(databaseInstance, "Air France");
            insertAirline(databaseInstance, "Air Transat");
            insertAirline(databaseInstance, "Alitalia");
            insertAirline(databaseInstance, "Austrian");
            insertAirline(databaseInstance, "Delta");
            insertAirline(databaseInstance, "Emirates");
            insertAirline(databaseInstance, "InterJet");
            insertAirline(databaseInstance, "Lufthansa");
            insertAirline(databaseInstance, "United");
            insertAirline(databaseInstance, "WestJet");


            //toronto to ottawa
            insertFlight(databaseInstance, "Toronto", "Ottawa", "2018-8-10", "2018-8-10", "10:10", "12:10", 200.00, 1);
            insertFlight(databaseInstance, "Toronto", "Ottawa", "2018-8-10", "2018-8-10", "10:10", "12:10", 150.00, 2);
            insertFlight(databaseInstance, "Toronto", "Ottawa", "2018-8-10", "2018-8-10", "11:10", "12:10", 350.00, 3);
            insertFlight(databaseInstance, "Toronto", "Ottawa", "2018-8-10", "2018-8-10", "09:10", "12:10", 250.00, 4);
            insertFlight(databaseInstance, "Toronto", "Ottawa", "2018-8-10", "2018-8-10", "10:10", "12:10", 100.00, 5);

            //ottawa to toronto
            insertFlight(databaseInstance, "Ottawa", "Toronto", "2018-8-12", "2018-8-12", "10:10", "12:10", 120.00, 6);
            insertFlight(databaseInstance, "Ottawa", "Toronto", "2018-8-12", "2018-8-12", "09:00", "12:10", 150.00, 7);
            insertFlight(databaseInstance, "Ottawa", "Toronto", "2018-8-12", "2018-8-12", "10:10", "12:10", 170.00, 8);
            insertFlight(databaseInstance, "Ottawa", "Toronto", "2018-8-12", "2018-8-12", "09:00", "12:10", 140.00, 9);
            insertFlight(databaseInstance, "Ottawa", "Toronto", "2018-8-12", "2018-8-12", "09:00", "12:10", 100.00, 10);
            insertFlight(databaseInstance, "Ottawa", "Toronto", "2018-8-12", "2018-8-12", "10:10", "12:10", 350.00, 11);

            //Edmonton to winnipeg
            insertFlight(databaseInstance, "Edmonton", "Winnipeg", "2018-8-25", "2018-8-25", "02:00", "04:45", 300.00, 2);
            insertFlight(databaseInstance, "Edmonton", "Winnipeg", "2018-8-25", "2018-8-25", "01:00", "03:15", 205.00, 1);
            insertFlight(databaseInstance, "Edmonton", "Winnipeg", "2018-8-25", "2018-8-25", "09:00", "12:20", 350.00, 10);
            insertFlight(databaseInstance, "Edmonton", "Winnipeg", "2018-8-25", "2018-8-25", "08:00", "10:15", 400.00, 11);
            insertFlight(databaseInstance, "Edmonton", "Winnipeg", "2018-8-25", "2018-8-25", "11:00", "13:11", 250.00, 1);

            //winnipeg to edmonton
            insertFlight(databaseInstance, "Winnipeg", "Edmonton", "2018-9-15", "2018-9-15", "11:00", "13:11", 300.00, 9);
            insertFlight(databaseInstance, "Winnipeg", "Edmonton", "2018-9-15", "2018-9-15", "09:00", "12:00", 250.00, 1);
            insertFlight(databaseInstance, "Winnipeg", "Edmonton", "2018-9-15", "2018-9-15", "08:00", "11:00", 400.00, 3);
            insertFlight(databaseInstance, "Winnipeg", "Edmonton", "2018-9-15", "2018-9-15", "01:00", "04:00", 150.00, 7);
            insertFlight(databaseInstance, "Winnipeg", "Edmonton", "2018-9-15", "2018-9-15", "12:00", "14:00", 350.00, 10);


            insertFlight(databaseInstance, "Montreal", "Edmonton", "2018-7-28", "2018-7-28", "10:10", "12:10", 350.00, 4);
            insertFlight(databaseInstance, "New York", "Edmonton", "2018-8-15", "2018-8-15", "09:10", "12:10", 185.00, 5);
            insertFlight(databaseInstance, "Quebec City", "NewYork", "2018-7-28", "2018-7-28", "11:10", "12:10", 250.00, 6);
            insertFlight(databaseInstance, "Charlottetown", "Victoria", "2018-8-25", "2018-8-25", "10:10", "12:10", 360.00, 7);
            insertFlight(databaseInstance, "Los Angeles", "Ottawa", "2018-8-26", "2018-8-26", "10:10", "12:10", 350.00, 8);
            insertFlight(databaseInstance, "Winnipeg", "Toronto", "2018-8-27", "2018-8-27", "09:10", "12:10", 350.00, 9);
            insertFlight(databaseInstance, "Victoria", "New York", "2018-8-28", "2018-8-28", "10:10", "12:10", 350.00, 10);

            insertSeat(databaseInstance, 0, 1, 1);
            insertSeat(databaseInstance, 0, 2, 1);
            insertSeat(databaseInstance, 0, 3, 1);
            insertSeat(databaseInstance, 0, 4, 1);
            insertSeat(databaseInstance, 0, 5, 1);
            insertSeat(databaseInstance, 0, 6, 1);
            insertSeat(databaseInstance, 0, 7, 1);
            insertSeat(databaseInstance, 0, 8, 1);
            insertSeat(databaseInstance, 0, 9, 1);
            insertSeat(databaseInstance, 0, 10, 1);
            insertSeat(databaseInstance, 0, 11, 1);
            insertSeat(databaseInstance, 0, 12, 1);
            insertSeat(databaseInstance, 0, 13, 1);
            insertSeat(databaseInstance, 0, 14, 1);
            insertSeat(databaseInstance, 0, 15, 1);
            insertSeat(databaseInstance, 0, 16, 1);
            insertSeat(databaseInstance, 0, 17, 1);
            insertSeat(databaseInstance, 0, 18, 1);
            insertSeat(databaseInstance, 0, 19, 1);
            insertSeat(databaseInstance, 0, 20, 1);
            insertSeat(databaseInstance, 0, 21, 1);
            insertSeat(databaseInstance, 0, 22, 1);
            insertSeat(databaseInstance, 0, 23, 1);
            insertSeat(databaseInstance, 0, 24, 1);
            insertSeat(databaseInstance, 0, 25, 1);
            insertSeat(databaseInstance, 0, 26, 2);
            insertSeat(databaseInstance, 0, 27, 2);
            insertSeat(databaseInstance, 0, 28, 2);

            insertFlightType(databaseInstance, "Economy");
            insertFlightType(databaseInstance, "Business");

            insertClient(databaseInstance, "John", "Doe", "4164121000", "5412547854125963");

            insertAccount(databaseInstance, "john@gmail.com", "password", 1);

            databaseInstance.execSQL(updateFlight());
            databaseInstance.execSQL(updateSeatNumber());
        }
    }

    public String createAirlinesTable() {
        return "CREATE TABLE " + TABLE_AIRLINES + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "AIRLINE_CARRIER TEXT COLLATE NOCASE);";
    }

    public String createFlightInfoTable() {
        return "CREATE TABLE " + TABLE_FLIGHT_INFO + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FLIGHTNUMBER INTEGER, " +
                "DEPARTING_CITY TEXT COLLATE NOCASE, " +
                "DESTINATION_CITY TEXT COLLATE NOCASE, " +
                "DEPARTURE_DATE DATE, " +
                "DESTINATION_DATE DATE, " +
                "DEPARTURE_TIME TIME, " +
                "ARRIVAL_TIME TIME, " +
                "FLIGHT_DURATION TIME, " +
                "PRICE REAL, " +
                "FLIGHT_AIRLINE INTEGER, " +
                "FOREIGN KEY(FLIGHT_AIRLINE) REFERENCES " + TABLE_AIRLINES + "(_id));";
    }

    public String createSeatTable() {
        return "CREATE TABLE " + TABLE_SEATS + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "SEATNUMBER INTEGER, " +
                "SEAT_FLIGHT INTEGER, " +
                "STATUS INTEGER, " +
                "SEAT_FLIGHTTYPE INTEGER, " +
                "FOREIGN KEY(SEAT_FLIGHT) REFERENCES " + TABLE_FLIGHT_INFO + "(_id)," +
                "FOREIGN KEY(SEAT_FLIGHTTYPE) REFERENCES " + TABLE_FLIGHT_TYPE + "(_id));";
    }

    public String createFlightTypeTable() {
        return "CREATE TABLE " + TABLE_FLIGHT_TYPE + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FLIGHT_TYPE TEXT);";
    }

    public String createClientTable() {
        return "CREATE TABLE " + TABLE_CLIENTS + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FIRSTNAME TEXT COLLATE NOCASE, " +
                "LASTNAME TEXT COLLATE NOCASE, " +
                "PHONE TEXT, " +
                "CREDITCARD TEXT, " +
                "IMAGE BLOB);";
    }

    public String createAccountTable() {
        return "CREATE TABLE " + TABLE_ACCOUNT + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "EMAIL TEXT, " +
                "PASSWORD TEXT, " +
                "ACCOUNT_CLIENT INTEGER, " +
                "FOREIGN KEY (ACCOUNT_CLIENT) REFERENCES " + TABLE_CLIENTS + "(_id));";
    }

    public String createItinerary() {
        return "CREATE TABLE ITINERARY (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TIMESTAMP DATETIME DEFAULT (STRFTIME('%Y-%m-%d  %H:%M', 'NOW','localtime')), " +
                "ITINERARY_CLIENT INTEGER, " +
                "ITINERARY_FLIGHT INTEGER, " +
                "PASSENGER INTEGER, " +
                "FOREIGN KEY(ITINERARY_CLIENT) REFERENCES " + TABLE_CLIENTS + "(_id), " +
                "FOREIGN KEY(ITINERARY_FLIGHT) REFERENCES " + TABLE_FLIGHT_INFO + "(_id));";
    }

    public String updateFlight() {
        return "UPDATE "+ TABLE_FLIGHT_INFO + " SET FLIGHT_DURATION = ((strftime('%s',ARRIVAL_TIME) - strftime('%s', DEPARTURE_TIME)) / 60)/60, " +
                "FLIGHTNUMBER = _id + 10000";
    }

    public String updateSeatNumber(){
        return "UPDATE "+ TABLE_SEATS + " SET SEATNUMBER = _id + 100";
    }

    public void insertAirline(SQLiteDatabase databaseInstance, String airlineName) {
        ContentValues airlineValues = new ContentValues();
        airlineValues.put("AIRLINE_CARRIER", airlineName);
        databaseInstance.insert(TABLE_AIRLINES, null, airlineValues);
    }

    public void insertFlight(SQLiteDatabase db, String departing_city, String destination, String departureDate, String arrivalDate, String departureTime, String arrivalTime, Double price, int airlineID) {
        ContentValues flightValues = new ContentValues();
        flightValues.put("DEPARTING_CITY", departing_city);
        flightValues.put("DESTINATION_CITY", destination);
        flightValues.put("DEPARTURE_DATE", departureDate);
        flightValues.put("ARRIVAL_DATE", arrivalDate);
        flightValues.put("DEPARTURE_TIME", departureTime);
        flightValues.put("ARRIVAL_TIME", arrivalTime);
        flightValues.put("PRICE", price);
        flightValues.put("FLIGHT_AIRLINE", airlineID);
        db.insert(TABLE_FLIGHT_INFO, null, flightValues);
    }

    public void insertSeat(SQLiteDatabase db, int status, int flightID, int flightTypeID) {
        ContentValues seatValues = new ContentValues();
        seatValues.put("STATUS", status);
        seatValues.put("SEAT_FLIGHT", flightID);
        seatValues.put("SEAT_FLIGHTCLASS", flightTypeID);
        db.insert(TABLE_SEATS, null, seatValues);
    }

    public void insertFlightType(SQLiteDatabase db, String flightType) {
        ContentValues flightClassValues = new ContentValues();
        flightClassValues.put("FLIGHT_TYPE", flightType);
        db.insert(TABLE_FLIGHT_TYPE, null, flightClassValues);
    }

    public static void insertClient(SQLiteDatabase db, String firstName, String lastName, String phone, String creditCard) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("FIRSTNAME", capitalize(firstName.toLowerCase()));
        clientValues.put("LASTNAME", capitalize(lastName.toLowerCase()));
        clientValues.put("PHONE", phone);
        clientValues.put("CREDITCARD", creditCard);
        db.insert(TABLE_CLIENTS, null, clientValues);
    }

    public static void insertAccount(SQLiteDatabase db, String email, String password, int clientID) {
        ContentValues accountValues = new ContentValues();
        accountValues.put("EMAIL", email);
        accountValues.put("PASSWORD", password);
        accountValues.put("ACCOUNT_CLIENT", clientID);
        db.insert(TABLE_ACCOUNT, null, accountValues);
    }

    public static void insertItinerary(SQLiteDatabase db, int flightID, int clientID, int traveller) {
        ContentValues itineraryValues = new ContentValues();
        itineraryValues.put("ITINERARY_FLIGHT", flightID);
        itineraryValues.put("ITINERARY_CLIENT", clientID);
        itineraryValues.put("PASSENGER", traveller);
        db.insert("ITINERARY", null, itineraryValues);
    }

    public static Cursor selectFlight(SQLiteDatabase db, int flightID) {
        return db.rawQuery("SELECT " + TABLE_FLIGHT_INFO + "._id, " + "FLIGHTNUMBER, DEPARTING_CITY, DESTINATION_CITY, DEPARTURE_DATE, ARRIVAL_DATE, DEPARTURE_TIME, " +
                " ARRIVAL_TIME, FLIGHT_DURATION, PRICE, AIRLINE_CARRIER, SEATNUMBER, FLIGHT_TYPE " +
                "FROM " + TABLE_FLIGHT_INFO + " " +
                "INNER JOIN " + TABLE_AIRLINES + " " +
                "ON " + TABLE_FLIGHT_INFO + ".FLIGHT_AIRLINE = " + TABLE_AIRLINES + "._id "+
                "INNER JOIN " + TABLE_SEATS + " " +
                "ON " + TABLE_SEATS + ".SEAT_FLIGHT = " + TABLE_FLIGHT_INFO + "._id " +
                "INNER JOIN " + TABLE_FLIGHT_TYPE + " " +
                "ON " + TABLE_SEATS + ".SEAT_FLIGHTCLASS = " + TABLE_FLIGHT_TYPE + "._id " +
                "WHERE " + TABLE_FLIGHT_INFO + "._id = '" + flightID + "'", null);
    }

    public static Cursor getPastOrderDetails(SQLiteDatabase db, int flightID) {
        return db.rawQuery("SELECT FLIGHT._id, FLIGHTNUMBER, DEPARTING_CITY, DESTINATION_CITY, DEPARTURE_DATE, ARRIVAL_DATE, DEPARTURE_TIME, " +
                " ARRIVAL_TIME, FLIGHT_DURATION, PRICE, AIRLINE_CARRIER, SEATNUMBER, FLIGHT_TYPE, PASSENGER, TIMESTAMP " +
                "FROM " + TABLE_FLIGHT_INFO + " " +
                "INNER JOIN " + TABLE_AIRLINES + " " +
                "ON " + TABLE_FLIGHT_INFO + ".FLIGHT_AIRLINE = " + TABLE_AIRLINES + "._id "+
                "INNER JOIN " + TABLE_SEATS + " " +
                "ON " + TABLE_SEATS + ".SEAT_FLIGHT = " + TABLE_FLIGHT_INFO + "._id " +
                "INNER JOIN " + TABLE_FLIGHT_TYPE + " " +
                "ON " + TABLE_SEATS + ".SEAT_FLIGHTCLASS = " + TABLE_FLIGHT_TYPE + "._id " +
                "JOIN ITINERARY " +
                "ON ITINERARY.ITINERARY_FLIGHT = " + TABLE_FLIGHT_INFO + "._id " +
                "WHERE " + TABLE_FLIGHT_INFO + "._id = '" + flightID + "'", null);
    }

    public static Cursor selectFlight(SQLiteDatabase databaseInstance, String departingCity, String destinationCity,
                                      String departureDate, String flightType) {
        return databaseInstance.rawQuery("SELECT FLIGHT._id, FLIGHTNUMBER, DEPARTING_CITY, DESTINATION_CITY, DEPARTURE_DATE, ARRIVAL_DATE, DEPARTURE_TIME, " +
                " ARRIVAL_TIME, FLIGHT_DURATION, PRICE, AIRLINE_CARRIER, SEATNUMBER, FLIGHT_TYPE " +
                "FROM " + TABLE_FLIGHT_INFO + " " +
                "JOIN " + TABLE_AIRLINES + " " +
                "ON " + TABLE_FLIGHT_INFO + ".FLIGHT_AIRLINE " +
                "JOIN " + TABLE_SEATS + " " +
                "ON " + TABLE_FLIGHT_INFO + "._id = " + TABLE_SEATS + ".SEAT_FLIGHT " +
                "INNER JOIN " + TABLE_FLIGHT_TYPE + " " +
                "ON " + TABLE_SEATS + ".SEAT_FLIGHTCLASS = " + TABLE_FLIGHT_TYPE + "._id " +
                "WHERE DEPARTING_CITY = '" + departingCity +
                "' AND DESTINATION_CITY = '" + destinationCity +
                "' AND DEPARTURE_DATE = '" + departureDate +
                "' AND FLIGHT_TYPE = '" + flightType +
                "' AND SEAT.STATUS = 0 ", null);
    }

    public static Cursor selectItinerary(SQLiteDatabase databaseInstance, int clientID) {
        return databaseInstance.rawQuery("SELECT " + TABLE_FLIGHT_INFO + "._id, FLIGHTNUMBER, DEPARTING_CITY, DESTINATION_CITY, DEPARTURE_DATE, ARRIVAL_DATE, DEPARTURE_TIME, " +
                " ARRIVAL_TIME, FLIGHT_DURATION, PRICE, AIRLINE_CARRIER, SEATNUMBER, FLIGHT_TYPE " +
                "FROM " + TABLE_FLIGHT_INFO + " " +
                "INNER JOIN " + TABLE_AIRLINES + " " +
                "ON " + TABLE_FLIGHT_INFO + ".FLIGHT_AIRLINE = " + TABLE_AIRLINES + "._id "+
                "INNER JOIN ITINERARY " +
                "ON " + TABLE_FLIGHT_INFO + "._id = ITINERARY.ITINERARY_FLIGHT " +
                "INNER JOIN " + TABLE_SEATS + " " +
                "ON " + TABLE_SEATS + ".SEAT_FLIGHT = " + TABLE_FLIGHT_INFO + "._id " +
                "INNER JOIN " + TABLE_FLIGHT_TYPE + " " +
                "ON " + TABLE_SEATS + ".SEAT_FLIGHTCLASS = " + TABLE_FLIGHT_TYPE + "._id " +
                "WHERE ITINERARY.ITINERARY_CLIENT = " + clientID, null);
    }

    public static void deleteItinerary(SQLiteDatabase db, int itineraryID) {
        db.delete("ITINERARY", " _id = ? ", new String[]{String.valueOf(itineraryID)});
    }

    public static Cursor selectItinerary(SQLiteDatabase db, int flightID, int clientID) {
        return db.query("ITINERARY", null, " ITINERARY_FLIGHT = ? AND ITINERARY_CLIENT = ?",
                new String[]{String.valueOf(flightID), String.valueOf(clientID)}, null, null, null, null);
    }

    public static Cursor login(SQLiteDatabase db, String email, String password) {
        return db.query(TABLE_ACCOUNT, new String[]{"_id", "EMAIL", "PASSWORD", "ACCOUNT_CLIENT"},
                "EMAIL = ? AND PASSWORD = ? ", new String[]{email, password},
                null, null, null, null);
    }

    public static void deleteAccount(SQLiteDatabase db, String clientID) {
        db.delete(TABLE_CLIENTS, "_id = ? ", new String[]{clientID});
        db.delete(TABLE_ACCOUNT, "_id = ? ", new String[]{clientID});
        db.delete("ITINERARY", "_id = ? ", new String[]{clientID});
    }

    public static void updateClientImage(SQLiteDatabase db, byte[] image, String id) {
        ContentValues employeeValues = new ContentValues();
        employeeValues.put("IMAGE", image);
        db.update(TABLE_CLIENTS, employeeValues, " _id = ? ", new String[]{id});
    }

    public static void updatePassword(SQLiteDatabase db, String password, String id) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("PASSWORD", password);
        db.update(TABLE_ACCOUNT, clientValues, " _id = ? ", new String[]{id});
    }

    public static Cursor selectImage(SQLiteDatabase db, int clientID) {
        return db.query(TABLE_CLIENTS, new String[]{"IMAGE"}, "_id = ? ",
                new String[]{Integer.toString(clientID)}, null, null,
                null, null);
    }


    public static Cursor selectClientPassword(SQLiteDatabase db, int clientID) {
        return db.query(TABLE_ACCOUNT, new String[]{"PASSWORD"}, "_id = ? ",
                new String[]{Integer.toString(clientID)}, null, null,
                null, null);
    }

    public static void updateClient(SQLiteDatabase db, String firstName, String lastName,
                                    String phone, String creditCard, int clientID) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("FIRSTNAME", capitalize(firstName.toLowerCase()));
        clientValues.put("LASTNAME", capitalize(lastName.toLowerCase()));
        clientValues.put("PHONE", phone);
        clientValues.put("CREDITCARD", creditCard);
        db.update(TABLE_CLIENTS, clientValues, "_id = ?", new String[]{String.valueOf(clientID)});
    }

    public static void updateAccount(SQLiteDatabase db, String email, int clientID) {
        ContentValues accountValues = new ContentValues();
        accountValues.put("EMAIL", email);
        db.update(TABLE_ACCOUNT, accountValues, " ACCOUNT_CLIENT = ?",
                new String[]{String.valueOf(clientID)});
    }

    public static Cursor selectClientID(SQLiteDatabase db, String firstName, String lastName,
                                        String phone, String creditCard) {
        return db.query(TABLE_CLIENTS, new String[]{"_id"},
                "FIRSTNAME = ? AND LASTNAME = ? AND PHONE = ? AND CREDITCARD = ? ",
                new String[]{firstName, lastName, phone, creditCard},
                null, null, null, null);
    }

    public static Cursor selectClientJoinAccount(SQLiteDatabase db, int clientID) {
        return db.rawQuery("SELECT FIRSTNAME, LASTNAME, PHONE, CREDITCARD, EMAIL FROM " + TABLE_CLIENTS + " " +
                "JOIN " + TABLE_ACCOUNT + " " +
                "ON " + TABLE_CLIENTS + "._id = " + TABLE_ACCOUNT + ".ACCOUNT_CLIENT " +
                "WHERE " + TABLE_CLIENTS + "._id = '" + clientID + "'", null);
    }

    public static Cursor selectClient(SQLiteDatabase db, int clientID) {
        return db.query(TABLE_CLIENTS, null, " _id = ? ",
                new String[]{String.valueOf(clientID)}, null, null, null, null);
    }

    public static Cursor selectAccount(SQLiteDatabase db, String email) {
        return db.query(TABLE_ACCOUNT, null, " EMAIL = ? ",
                new String[]{email}, null, null, null, null);
    }

    public static final String[] CITIES = new String[] {
            "Toronto", "Ottawa", "Edmonton", "Winnipeg", "Victoria",
            "Fredericton", "St. John's", "Halifax", "Charlottetown",
            "Quebec City", "Regina", "Yellowknife", "Iqaluit", "Whitehorse", "New York", "Boston", "Los Angeles",
            "Montreal"
    };

    public static String capitalize(String str) {
        return str.length() == 0 ? str : str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
