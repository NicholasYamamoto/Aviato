package com.example.aviato;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "AVIATO.db";
    private static final int DB_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static void insertClient(SQLiteDatabase db, String firstName, String lastName, String phone, String creditCard) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("FIRST_NAME", capitalizeString(firstName.toLowerCase()));
        clientValues.put("LAST_NAME", capitalizeString(lastName.toLowerCase()));
        clientValues.put("PHONE", phone);
        clientValues.put("CREDIT_CARD_NUMBER", creditCard);
        db.insert("CLIENT", null, clientValues);
    }

    public static void insertAccount(SQLiteDatabase db, String email, String password, int clientID) {
        ContentValues accountValues = new ContentValues();
        accountValues.put("EMAIL", email);
        accountValues.put("PASSWORD", password);
        accountValues.put("ACCOUNT_CLIENT", clientID);
        db.insert("ACCOUNT", null, accountValues);
    }

    public static void insertOrder(SQLiteDatabase db, int flightID, int clientID, int passenger) {
        ContentValues orderValues = new ContentValues();
        orderValues.put("PAST_ORDERS_FLIGHT", flightID);
        orderValues.put("PAST_ORDERS_CLIENT", clientID);
        orderValues.put("PASSENGER", passenger);
        db.insert("PAST_ORDERS", null, orderValues);
    }

    public static Cursor selectFlight(SQLiteDatabase db, int flightID) {
        return db.rawQuery("SELECT FLIGHT._id, FLIGHT_NUMBER, DEPARTING_CITY, DESTINATION_CITY, DEPARTING_DATE, DESTINATION_DATE, DEPARTING_TIME, " +
                " DESTINATION_TIME, FLIGHT_DURATION, PRICE, AIRLINE_CARRIER, SEAT_NUMBER, FLIGHT_TYPE_NAME " +
                "FROM FLIGHT " +
                "INNER JOIN AIRLINE " +
                "ON FLIGHT.FLIGHT_AIRLINE = AIRLINE._id " +
                "INNER JOIN " +
                "SEAT " +
                "ON SEAT.SEAT_FLIGHT = FLIGHT._id " +
                "INNER JOIN " +
                "FLIGHT_TYPE " +
                "ON SEAT.SEAT_FLIGHT_TYPE = FLIGHT_TYPE._id " +
                "WHERE FLIGHT._id = '" + flightID + "'", null);
    }

    public static Cursor getOrderDetails(SQLiteDatabase db, int flightID) {
        return db.rawQuery("SELECT FLIGHT._id, FLIGHT_NUMBER, DEPARTING_CITY, DESTINATION_CITY, DEPARTING_DATE, DESTINATION_DATE, DEPARTING_TIME, " +
                " DESTINATION_TIME, FLIGHT_DURATION, PRICE, AIRLINE_CARRIER, SEAT_NUMBER, FLIGHT_TYPE_NAME, PASSENGER, TIMESTAMP " +
                "FROM FLIGHT " +
                "INNER JOIN AIRLINE " +
                "ON FLIGHT.FLIGHT_AIRLINE = AIRLINE._id " +
                "INNER JOIN " +
                "SEAT " +
                "ON SEAT.SEAT_FLIGHT = FLIGHT._id " +
                "INNER JOIN " +
                "FLIGHT_TYPE " +
                "ON SEAT.SEAT_FLIGHT_TYPE = FLIGHT_TYPE._id " +
                "JOIN PAST_ORDERS " +
                "ON PAST_ORDERS.PAST_ORDERS_FLIGHT = FLIGHT._id " +
                "WHERE FLIGHT._id = '" + flightID + "'", null);
    }

    public static Cursor selectFlight(SQLiteDatabase db, String origin, String destination,
                                      String departureDate, String flightClass) {
        return db.rawQuery("SELECT FLIGHT._id, FLIGHT_NUMBER, DEPARTING_CITY, DESTINATION_CITY, DEPARTING_DATE, DESTINATION_DATE, DEPARTING_TIME, " +
                " DESTINATION_TIME, FLIGHT_DURATION, PRICE, AIRLINE_CARRIER, SEAT_NUMBER, FLIGHT_TYPE_NAME " +
                "FROM FLIGHT " +
                "JOIN AIRLINE " +
                "ON AIRLINE._id = FLIGHT.FLIGHT_AIRLINE " +
                "JOIN " +
                "SEAT " +
                "ON FLIGHT._id = SEAT.SEAT_FLIGHT " +
                "INNER JOIN " +
                "FLIGHT_TYPE " +
                "ON SEAT.SEAT_FLIGHT_TYPE = FLIGHT_TYPE._id " +
                "WHERE DEPARTING_CITY = '" + origin +
                "' AND DESTINATION_CITY = '" + destination +
                "' AND DEPARTING_DATE = '" + departureDate +
                "' AND FLIGHT_TYPE_NAME = '" + flightClass +
                "' AND SEAT.STATUS = 0 ", null);
    }

    public static Cursor selectFlight(SQLiteDatabase db, String origin, String destination,
                                      String departureDate, String flightClass, String orderBy) {
        return db.rawQuery("SELECT FLIGHT._id, FLIGHT_NUMBER, DEPARTING_CITY, DESTINATION_CITY, DEPARTING_DATE, DESTINATION_DATE, DEPARTING_TIME, " +
                " DESTINATION_TIME, FLIGHT_DURATION, PRICE, AIRLINE_CARRIER, SEAT_NUMBER, FLIGHT_TYPE_NAME " +
                "FROM FLIGHT " +
                "INNER JOIN AIRLINE " +
                "ON FLIGHT.FLIGHT_AIRLINE = AIRLINE._id " +
                "INNER JOIN " +
                "SEAT " +
                "ON SEAT.SEAT_FLIGHT = FLIGHT._id " +
                "INNER JOIN " +
                "FLIGHT_TYPE " +
                "ON SEAT.SEAT_FLIGHT_TYPE = FLIGHT_TYPE._id " +
                "WHERE DEPARTING_CITY = '" + origin +
                "' AND DESTINATION_CITY = '" + destination +
                "' AND DEPARTING_DATE = '" + departureDate +
                "' AND FLIGHT_TYPE_NAME = '" + flightClass +
                "' AND SEAT.STATUS = 0 " +
                "ORDER BY " + orderBy + " ASC", null);
    }

    public static Cursor selectOrder(SQLiteDatabase db, int clientID) {
        return db.rawQuery("SELECT FLIGHT._id, FLIGHT_NUMBER, DEPARTING_CITY, DESTINATION_CITY, DEPARTING_DATE, DESTINATION_DATE, DEPARTING_TIME, " +
                " DESTINATION_TIME, FLIGHT_DURATION, PRICE, AIRLINE_CARRIER, SEAT_NUMBER, FLIGHT_TYPE_NAME " +
                "FROM FLIGHT " +
                "INNER JOIN AIRLINE " +
                "ON FLIGHT.FLIGHT_AIRLINE = AIRLINE._id " +
                "INNER JOIN PAST_ORDERS " +
                "ON  FLIGHT._id = PAST_ORDERS.PAST_ORDERS_FLIGHT " +
                "INNER JOIN " +
                "SEAT " +
                "ON SEAT.SEAT_FLIGHT = FLIGHT._id " +
                "INNER JOIN " +
                "FLIGHT_TYPE " +
                "ON SEAT.SEAT_FLIGHT_TYPE = FLIGHT_TYPE._id " +
                "WHERE PAST_ORDERS.PAST_ORDERS_CLIENT = " + clientID, null);
    }

    public static Cursor selectOrder(SQLiteDatabase db, int flightID, int clientID) {
        return db.query("PAST_ORDERS", null, " PAST_ORDERS_FLIGHT = ? AND PAST_ORDERS_CLIENT = ?",
                new String[]{String.valueOf(flightID), String.valueOf(clientID)}, null, null, null, null);
    }

    public static void deleteOrder(SQLiteDatabase db, int itineraryID) {
        db.delete("PAST_ORDERS", " _id = ? ", new String[]{String.valueOf(itineraryID)});
    }

    public static Cursor login(SQLiteDatabase db, String email, String password) {
        return db.query("ACCOUNT", new String[]{"_id", "EMAIL", "PASSWORD", "ACCOUNT_CLIENT"},
                "EMAIL = ? AND PASSWORD = ? ", new String[]{email, password},
                null, null, null, null);
    }

    public static void deleteAccount(SQLiteDatabase db, String clientID) {
        db.delete("CLIENT", "_id = ? ", new String[]{clientID});
        db.delete("ACCOUNT", "_id = ? ", new String[]{clientID});
        db.delete("PAST_ORDERS", "_id = ? ", new String[]{clientID});
    }

    public static void updateClientImage(SQLiteDatabase db, byte[] image, String id) {
        ContentValues employeeValues = new ContentValues();
        employeeValues.put("IMAGE", image);
        db.update("CLIENT", employeeValues, " _id = ? ", new String[]{id});
    }

    public static void updatePassword(SQLiteDatabase db, String password, String id) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("PASSWORD", password);
        db.update("ACCOUNT", clientValues, " _id = ? ", new String[]{id});
    }

    public static Cursor selectImage(SQLiteDatabase db, int clientID) {
        return db.query("CLIENT", new String[]{"IMAGE"}, "_id = ? ",
                new String[]{Integer.toString(clientID)}, null, null,
                null, null);
    }

    public static Cursor selectClientPassword(SQLiteDatabase db, int clientID) {
        return db.query("ACCOUNT", new String[]{"PASSWORD"}, "_id = ? ",
                new String[]{Integer.toString(clientID)}, null, null,
                null, null);
    }

    public static void updateClient(SQLiteDatabase db, String firstName, String lastName,
                                    String phone, String creditCard, int clientID) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("FIRST_NAME", capitalizeString(firstName.toLowerCase()));
        clientValues.put("LAST_NAME", capitalizeString(lastName.toLowerCase()));
        clientValues.put("PHONE", phone);
        clientValues.put("CREDIT_CARD_NUMBER", creditCard);
        db.update("CLIENT", clientValues, "_id = ?", new String[]{String.valueOf(clientID)});
    }

    public static void updateAccount(SQLiteDatabase db, String email, int clientID) {
        ContentValues accountValues = new ContentValues();
        accountValues.put("EMAIL", email);
        db.update("ACCOUNT", accountValues, " ACCOUNT_CLIENT = ?",
                new String[]{String.valueOf(clientID)});
    }

    public static Cursor selectClientID(SQLiteDatabase db, String firstName, String lastName,
                                        String phone, String creditCard) {
        return db.query("CLIENT", new String[]{"_id"},
                "FIRST_NAME = ? AND LAST_NAME = ? AND PHONE = ? AND CREDIT_CARD_NUMBER = ? ",
                new String[]{firstName, lastName, phone, creditCard},
                null, null, null, null);
    }

    public static Cursor selectClientJoinAccount(SQLiteDatabase db, int clientID) {
        return db.rawQuery("SELECT FIRST_NAME, LAST_NAME, PHONE, CREDIT_CARD_NUMBER, EMAIL FROM CLIENT " +
                "JOIN ACCOUNT " +
                "ON CLIENT._id = ACCOUNT.ACCOUNT_CLIENT " +
                "WHERE " +
                "CLIENT._id = '" + clientID + "'", null);
    }

    public static Cursor selectClient(SQLiteDatabase db, int clientID) {
        return db.query("CLIENT", null, " _id = ? ",
                new String[]{String.valueOf(clientID)}, null, null, null, null);
    }

    public static Cursor selectAccount(SQLiteDatabase db, String email) {
        return db.query("ACCOUNT", null, " EMAIL = ? ",
                new String[]{email}, null, null, null, null);
    }

    public static String capitalizeString(String str) {
        return str.length() == 0 ? str : str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String filterString(String input) {
        if (!containsSpecialChars(input)) {
            return (input);
        }
        StringBuilder sb = new StringBuilder(input.length());
        char c;

        for (int i = 0; i < input.length(); i++) {
            c = input.charAt(i);
            switch (c) {
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '"':
                    sb.append("&quot;");
                    break;
                case '\'':
                    sb.append("&apos;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    private static boolean containsSpecialChars(String input) {

        Pattern regexSpecialChars = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher inputStr = regexSpecialChars.matcher(input);
        boolean hasSpecialChars = inputStr.find();

        return hasSpecialChars;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, 0, DB_VERSION);
    }

    //requires API level 16 and above
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + "AIRLINE");
        db.execSQL("DROP TABLE IF EXISTS " + "FLIGHT");
        db.execSQL("DROP TABLE IF EXISTS " + "SEAT");
        db.execSQL("DROP TABLE IF EXISTS " + "FLIGHT_TYPE");
        db.execSQL("DROP TABLE IF EXISTS " + "CLIENT");
        db.execSQL("DROP TABLE IF EXISTS " + "ACCOUNT");
        db.execSQL("DROP TABLE IF EXISTS " + "PAST_ORDERS");


        return status != -1;
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
    }

    public String updateFlight() {
        return "UPDATE FLIGHT SET FLIGHT_DURATION = ((strftime('%s',DESTINATION_TIME) - strftime('%s', DEPARTING_TIME)) / 60)/60, " +
                "FLIGHT_NUMBER = _id + 10000";
    }

    public String updateSeatNumber() {
        return "UPDATE SEAT SET SEAT_NUMBER = _id + 100";
    }

    public void insertAirline(SQLiteDatabase db, String airlineName) {
        ContentValues airlineValues = new ContentValues();
        airlineValues.put("AIRLINE_CARRIER", airlineName);
        db.insert("AIRLINE", null, airlineValues);
    }

    public void insertFlight(SQLiteDatabase db, String origin, String destination, String departureDate, String arrivalDate, String departureTime, String arrivalTime, Double fare, int airlineID) {
        ContentValues flightValues = new ContentValues();
        flightValues.put("DEPARTING_CITY", origin);
        flightValues.put("DESTINATION_CITY", destination);
        flightValues.put("DEPARTING_DATE", departureDate);
        flightValues.put("DESTINATION_DATE", arrivalDate);
        flightValues.put("DEPARTING_TIME", departureTime);
        flightValues.put("DESTINATION_TIME", arrivalTime);
        flightValues.put("PRICE", fare);
        flightValues.put("FLIGHT_AIRLINE", airlineID);
        db.insert("FLIGHT", null, flightValues);
    }

    public void insertSeat(SQLiteDatabase db, int status, int flightID, int flightClassID) {
        ContentValues seatValues = new ContentValues();
        seatValues.put("STATUS", status);
        seatValues.put("SEAT_FLIGHT", flightID);
        seatValues.put("SEAT_FLIGHT_TYPE", flightClassID);
        db.insert("SEAT", null, seatValues);
    }

    public void insertFlightClass(SQLiteDatabase db, String flightClassName) {
        ContentValues flightClassValues = new ContentValues();
        flightClassValues.put("FLIGHT_TYPE_NAME", flightClassName);
        db.insert("FLIGHT_TYPE", null, flightClassValues);
    }
}
