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

    private static final String DB_NAME = "aviato.db";
    private static final int DB_VERSION = 4;
    private static final String TABLE_ACCOUNT = "ACCOUNT";
    private static final String TABLE_FLIGHT_INFO = "FLIGHT_INFO";
    private static final String TABLE_TRIP = "TRIP";
    private static final String TABLE_PAST_ORDERS = "PAST_ORDERS";
    private static final String TABLE_AIRLINES = "AIRLINE";
    private static final String TABLE_FLIGHT_TYPE = "FLIGHT_TYPE";
    private static final String TABLE_SEATS = "SEAT";
    private static final String TABLE_CLIENTS = "CLIENT";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static void insertClient(SQLiteDatabase databaseInstance, String firstName, String lastName, String phone, String creditCard) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("FIRST_NAME", capitalizeString(firstName.toLowerCase()));
        clientValues.put("LAST_NAME", capitalizeString(lastName.toLowerCase()));
        clientValues.put("PHONE", phone);
        clientValues.put("CREDIT_CARD_NUMBER", creditCard);
        databaseInstance.insert(TABLE_CLIENTS, null, clientValues);
    }

    public static void insertAccount(SQLiteDatabase databaseInstance, String email, String password, int clientID) {
        ContentValues accountValues = new ContentValues();
        accountValues.put("EMAIL", email);
        accountValues.put("PASSWORD", password);
        accountValues.put("ACCOUNT_CLIENT", clientID);
        databaseInstance.insert(TABLE_ACCOUNT, null, accountValues);
    }

    public static void insertOrder(SQLiteDatabase databaseInstance, int flightID, int clientID, int traveller) {
        ContentValues orderValues = new ContentValues();
        orderValues.put("ORDER_FLIGHT", flightID);
        orderValues.put("ORDER_CLIENT", clientID);
        orderValues.put("PASSENGER", traveller);
        databaseInstance.insert(TABLE_PAST_ORDERS, null, orderValues);
    }

    /* TODO: This needs to be tested to see if I can change the order of the
            elements in the Cursor WITHOUT having to change the order of the
            INNER JOINS.
            This pattern will need to be applied EVERYWHERE
    */
    public static Cursor selectFlight(SQLiteDatabase databaseInstance, String departingCity, String destinationCity,
                                      String departingDate, String flightType) {
        return databaseInstance.rawQuery("SELECT " + TABLE_FLIGHT_INFO + "._id, " + "FLIGHT_NUMBER, DEPARTING_CITY, DEPARTING_DATE, DEPARTING_TIME, DESTINATION_CITY, " +
                "DESTINATION_DATE, DESTINATION_TIME, AIRLINE_CARRIER, SEAT_NUMBER, FLIGHT_DURATION, FLIGHT_TYPE, PRICE " +
                "FROM " + TABLE_FLIGHT_INFO + " " +
                "INNER JOIN " + TABLE_AIRLINES + " " +
                "ON " + TABLE_FLIGHT_INFO + ".FLIGHT_AIRLINE = " + TABLE_AIRLINES + "._id " +
                "INNER JOIN " + TABLE_SEATS + " " +
                "ON " + TABLE_SEATS + ".SEAT_FLIGHT = " + TABLE_FLIGHT_INFO + "._id " +
                "INNER JOIN " + TABLE_FLIGHT_TYPE + " " +
                "ON " + TABLE_SEATS + ".SEAT_FLIGHTCLASS = " + TABLE_FLIGHT_TYPE + "._id " +
                "WHERE DEPARTING_CITY = '" + departingCity +
                "' AND DESTINATION_CITY = '" + destinationCity +
                "' AND DEPARTING_DATE = '" + departingDate +
                "' AND FLIGHT_TYPE = '" + flightType +
                "' AND " + TABLE_SEATS + ".STATUS = 0 ", null);
    }

//    public static Cursor selectFlight(SQLiteDatabase databaseInstance, String departingCity, String destinationCity,
//                                      String departingDate, String flightType) {
//        return databaseInstance.rawQuery("SELECT " + TABLE_FLIGHT_INFO + "._id, FLIGHT_NUMBER, DEPARTING_CITY, DESTINATION_CITY, DEPARTING_DATE, DESTINATION_DATE, DEPARTING_TIME, " +
//                " DESTINATION_TIME, FLIGHT_DURATION, PRICE, AIRLINE_CARRIER, SEAT_NUMBER, FLIGHT_TYPE " +
//                "FROM " + TABLE_FLIGHT_INFO + " " +
//                "JOIN " + TABLE_AIRLINES + " " +
//                "ON " + TABLE_FLIGHT_INFO + ".FLIGHT_AIRLINE " +
//                "JOIN " + TABLE_SEATS + " " +
//                "ON " + TABLE_FLIGHT_INFO + "._id = " + TABLE_SEATS + ".SEAT_FLIGHT " +
//                "INNER JOIN " + TABLE_FLIGHT_TYPE + " " +
//                "ON " + TABLE_SEATS + ".SEAT_FLIGHTCLASS = " + TABLE_FLIGHT_TYPE + "._id " +
//                "WHERE DEPARTING_CITY = '" + departingCity +
//                "' AND DESTINATION_CITY = '" + destinationCity +
//                "' AND DEPARTING_DATE = '" + departingDate +
//                "' AND FLIGHT_TYPE = '" + flightType +
//                "' AND " + TABLE_SEATS + ".STATUS = 0 ", null);
//    }


  //THIS IS THE ORIGINAL IMPLEMENTATION
    public static Cursor getFlightDetails(SQLiteDatabase databaseInstance, int flightID) {
        return databaseInstance.rawQuery("SELECT " + TABLE_FLIGHT_INFO + "._id, " + "FLIGHT_NUMBER, DEPARTING_CITY, DESTINATION_CITY, DEPARTING_DATE, DESTINATION_DATE, DEPARTING_TIME, " +
                " DESTINATION_TIME, FLIGHT_DURATION, PRICE, AIRLINE_CARRIER, SEAT_NUMBER, FLIGHT_TYPE " +
                "FROM " + TABLE_FLIGHT_INFO + " " +
                "INNER JOIN " + TABLE_AIRLINES + " " +
                "ON " + TABLE_FLIGHT_INFO + ".FLIGHT_AIRLINE = " + TABLE_AIRLINES + "._id " +
                "INNER JOIN " + TABLE_SEATS + " " +
                "ON " + TABLE_SEATS + ".SEAT_FLIGHT = " + TABLE_FLIGHT_INFO + "._id " +
                "INNER JOIN " + TABLE_FLIGHT_TYPE + " " +
                "ON " + TABLE_SEATS + ".SEAT_FLIGHTCLASS = " + TABLE_FLIGHT_TYPE + "._id " +
                "WHERE " + TABLE_FLIGHT_INFO + "._id = '" + flightID + "'", null);
    }

    public static Cursor getPastOrderDetails(SQLiteDatabase databaseInstance, int flightID) {
        return databaseInstance.rawQuery("SELECT " + TABLE_FLIGHT_INFO + "._id, FLIGHT_NUMBER, DEPARTING_CITY, DESTINATION_CITY, DEPARTING_DATE, DESTINATION_DATE, DEPARTING_TIME, " +
                " DESTINATION_TIME, FLIGHT_DURATION, PRICE, AIRLINE_CARRIER, SEAT_NUMBER, FLIGHT_TYPE, PASSENGER, TIMESTAMP " +
                "FROM " + TABLE_FLIGHT_INFO + " " +
                "INNER JOIN " + TABLE_AIRLINES + " " +
                "ON " + TABLE_FLIGHT_INFO + ".FLIGHT_AIRLINE = " + TABLE_AIRLINES + "._id " +
                "INNER JOIN " + TABLE_SEATS + " " +
                "ON " + TABLE_SEATS + ".SEAT_FLIGHT = " + TABLE_FLIGHT_INFO + "._id " +
                "INNER JOIN " + TABLE_FLIGHT_TYPE + " " +
                "ON " + TABLE_SEATS + ".SEAT_FLIGHTCLASS = " + TABLE_FLIGHT_TYPE + "._id " +
                "JOIN " + TABLE_PAST_ORDERS + " " +
                "ON " + TABLE_PAST_ORDERS + ".ORDER_FLIGHT = " + TABLE_FLIGHT_INFO + "._id " +
                "WHERE " + TABLE_FLIGHT_INFO + "._id = '" + flightID + "'", null);
    }

    public static Cursor selectOrder(SQLiteDatabase databaseInstance, int clientID) {
        return databaseInstance.rawQuery("SELECT " + TABLE_FLIGHT_INFO + "._id, FLIGHT_NUMBER, DEPARTING_CITY, DESTINATION_CITY, DEPARTING_DATE, DESTINATION_DATE, DEPARTING_TIME, " +
                " DESTINATION_TIME, FLIGHT_DURATION, PRICE, AIRLINE_CARRIER, SEAT_NUMBER, FLIGHT_TYPE " +
                "FROM " + TABLE_FLIGHT_INFO + " " +
                "INNER JOIN " + TABLE_AIRLINES + " " +
                "ON " + TABLE_FLIGHT_INFO + ".FLIGHT_AIRLINE = " + TABLE_AIRLINES + "._id " +
                "INNER JOIN " + TABLE_PAST_ORDERS + " " +
                "ON " + TABLE_FLIGHT_INFO + "._id " + TABLE_PAST_ORDERS + ".ORDER_FLIGHT " +
                "INNER JOIN " + TABLE_SEATS + " " +
                "ON " + TABLE_SEATS + ".SEAT_FLIGHT = " + TABLE_FLIGHT_INFO + "._id " +
                "INNER JOIN " + TABLE_FLIGHT_TYPE + " " +
                "ON " + TABLE_SEATS + ".SEAT_FLIGHTCLASS = " + TABLE_FLIGHT_TYPE + "._id " +
                "WHERE " + TABLE_PAST_ORDERS + ".ORDER_CLIENT = " + clientID, null);
    }

    public static void deleteOrder(SQLiteDatabase databaseInstance, int itineraryID) {
        databaseInstance.delete(TABLE_PAST_ORDERS, " _id = ? ", new String[]{String.valueOf(itineraryID)});
    }

    public static Cursor selectOrder(SQLiteDatabase databaseInstance, int flightID, int clientID) {
        return databaseInstance.query(TABLE_PAST_ORDERS, null, " ORDER_FLIGHT = ? AND ORDER_CLIENT = ?",
                new String[]{String.valueOf(flightID), String.valueOf(clientID)}, null, null, null, null);
    }

    public static Cursor login(SQLiteDatabase databaseInstance, String email, String password) {
        return databaseInstance.query(TABLE_ACCOUNT, new String[]{"_id", "EMAIL", "PASSWORD", "ACCOUNT_CLIENT"},
                "EMAIL = ? AND PASSWORD = ? ", new String[]{email, password},
                null, null, null, null);
    }

    public static void deleteAccount(SQLiteDatabase databaseInstance, String clientID) {
        databaseInstance.delete(TABLE_CLIENTS, "_id = ? ", new String[]{clientID});
        databaseInstance.delete(TABLE_ACCOUNT, "_id = ? ", new String[]{clientID});
        databaseInstance.delete(TABLE_PAST_ORDERS, "_id = ? ", new String[]{clientID});
    }

    public static void updateClientImage(SQLiteDatabase databaseInstance, byte[] image, String id) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("IMAGE", image);
        databaseInstance.update(TABLE_CLIENTS, clientValues, " _id = ? ", new String[]{id});
    }

    public static void updatePassword(SQLiteDatabase databaseInstance, String password, String id) {
        ContentValues accountValues = new ContentValues();
        accountValues.put("PASSWORD", password);
        databaseInstance.update(TABLE_ACCOUNT, accountValues, " _id = ? ", new String[]{id});
    }

    public static Cursor selectImage(SQLiteDatabase databaseInstance, int clientID) {
        return databaseInstance.query(TABLE_CLIENTS, new String[]{"IMAGE"}, "_id = ? ",
                new String[]{Integer.toString(clientID)}, null, null,
                null, null);
    }

    public static Cursor selectClientPassword(SQLiteDatabase databaseInstance, int clientID) {
        return databaseInstance.query(TABLE_ACCOUNT, new String[]{"PASSWORD"}, "_id = ? ",
                new String[]{Integer.toString(clientID)}, null, null,
                null, null);
    }

    public static void updateClient(SQLiteDatabase databaseInstance, String firstName, String lastName,
                                    String phone, String creditCard, int clientID) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("FIRST_NAME", capitalizeString(firstName.toLowerCase()));
        clientValues.put("LAST_NAME", capitalizeString(lastName.toLowerCase()));
        clientValues.put("PHONE", phone);
        clientValues.put("CREDIT_CARD_NUMBER", creditCard);
        databaseInstance.update(TABLE_CLIENTS, clientValues, "_id = ?", new String[]{String.valueOf(clientID)});
    }

    public static void updateAccount(SQLiteDatabase databaseInstance, String email, int clientID) {
        ContentValues accountValues = new ContentValues();
        accountValues.put("EMAIL", email);
        databaseInstance.update(TABLE_ACCOUNT, accountValues, " ACCOUNT_CLIENT = ?",
                new String[]{String.valueOf(clientID)});
    }

    public static Cursor selectClientID(SQLiteDatabase databaseInstance, String firstName, String lastName,
                                        String phone, String creditCard) {
        return databaseInstance.query(TABLE_CLIENTS, new String[]{"_id"},
                "FIRST_NAME = ? AND LAST_NAME = ? AND PHONE = ? AND CREDIT_CARD_NUMBER = ? ",
                new String[]{firstName, lastName, phone, creditCard},
                null, null, null, null);
    }

    public static Cursor selectClientJoinAccount(SQLiteDatabase databaseInstance, int clientID) {
        return databaseInstance.rawQuery("SELECT FIRST_NAME, LAST_NAME, PHONE, CREDIT_CARD_NUMBER, EMAIL FROM " + TABLE_CLIENTS + " " +
                "JOIN " + TABLE_ACCOUNT + " " +
                "ON " + TABLE_CLIENTS + "._id = " + TABLE_ACCOUNT + ".ACCOUNT_CLIENT " +
                "WHERE " + TABLE_CLIENTS + "._id = '" + clientID + "'", null);
    }

    public static Cursor selectClient(SQLiteDatabase databaseInstance, int clientID) {
        return databaseInstance.query(TABLE_CLIENTS, null, " _id = ? ",
                new String[]{String.valueOf(clientID)}, null, null, null, null);
    }

    public static Cursor selectAccount(SQLiteDatabase databaseInstance, String email) {
        return databaseInstance.query(TABLE_ACCOUNT, null, " EMAIL = ? ",
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
    public void onCreate(SQLiteDatabase databaseInstance) {
//        // Create Database tables
//        System.out.println("HELLO BITCH");
//        databaseInstance.execSQL(createAirlinesTable());
//        databaseInstance.execSQL(createFlightInfoTable());
//        databaseInstance.execSQL(createSeatTable());
//        databaseInstance.execSQL(createFlightTypeTable());
//        databaseInstance.execSQL(createClientTable());
//        databaseInstance.execSQL(createAccountTable());
//        databaseInstance.execSQL(createPastOrdersTable());
        updateDatabase(databaseInstance, 0, DB_VERSION);
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
        databaseInstance.execSQL("DROP TABLE IF EXISTS " + TABLE_PAST_ORDERS);

        updateDatabase(databaseInstance, oldVersion, newVersion);
    }

    private void updateDatabase(SQLiteDatabase databaseInstance, int oldVersion, int newVersion) {
            // Create Database tables
            databaseInstance.execSQL(createAirlinesTable());
            databaseInstance.execSQL(createFlightInfoTable());
            databaseInstance.execSQL(createSeatTable());
            databaseInstance.execSQL(createFlightTypeTable());
            databaseInstance.execSQL(createClientTable());
            databaseInstance.execSQL(createAccountTable());
            databaseInstance.execSQL(createPastOrdersTable());

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

    public String createAirlinesTable() {
        return "CREATE TABLE " + TABLE_AIRLINES + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "AIRLINE_CARRIER TEXT COLLATE NOCASE);";
    }

    public String createFlightInfoTable() {
        return "CREATE TABLE " + TABLE_FLIGHT_INFO + " (" +
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
                "FOREIGN KEY(FLIGHT_AIRLINE) REFERENCES " + TABLE_AIRLINES + "(_id));";
    }

    public String createSeatTable() {
        return "CREATE TABLE " + TABLE_SEATS + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "SEAT_NUMBER INTEGER, " +
                "SEAT_FLIGHT INTEGER, " +
                "STATUS INTEGER, " +
                "SEAT_FLIGHT_TYPE INTEGER, " +
                "FOREIGN KEY(SEAT_FLIGHT) REFERENCES " + TABLE_FLIGHT_INFO + "(_id)," +
                "FOREIGN KEY(SEAT_FLIGHT_TYPE) REFERENCES " + TABLE_FLIGHT_TYPE + "(_id));";
    }

    public String createFlightTypeTable() {
        return "CREATE TABLE " + TABLE_FLIGHT_TYPE + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FLIGHT_TYPE TEXT);";
    }

    public String createClientTable() {
        return "CREATE TABLE " + TABLE_CLIENTS + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FIRST_NAME TEXT COLLATE NOCASE, " +
                "LAST_NAME TEXT COLLATE NOCASE, " +
                "PHONE TEXT, " +
                "CREDIT_CARD_NUMBER TEXT, " +
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

    public String createPastOrdersTable() {
        return "CREATE TABLE " +  TABLE_PAST_ORDERS + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TIMESTAMP DATETIME DEFAULT (STRFTIME('%Y-%m-%d  %H:%M', 'NOW','localtime')), " +
                "ORDER_CLIENT INTEGER, " +
                "ORDER_FLIGHT INTEGER, " +
                "PASSENGER INTEGER, " +
                "FOREIGN KEY(ORDER_CLIENT) REFERENCES " + TABLE_CLIENTS + "(_id), " +
                "FOREIGN KEY(ORDER_FLIGHT) REFERENCES " + TABLE_FLIGHT_INFO + "(_id));";
    }

    public String updateFlight() {
        return "UPDATE " + TABLE_FLIGHT_INFO + " SET FLIGHT_DURATION = ((strftime('%s',DESTINATION_TIME) - strftime('%s', DEPARTING_TIME)) / 60)/60, " +
                "FLIGHT_NUMBER = _id + 10000";
    }

    public String updateSeatNumber() {
        return "UPDATE " + TABLE_SEATS + " SET SEAT_NUMBER = _id + 100";
    }

    public void insertAirline(SQLiteDatabase databaseInstance, String airlineName) {
        ContentValues airlineValues = new ContentValues();
        airlineValues.put("AIRLINE_CARRIER", airlineName);
        databaseInstance.insert(TABLE_AIRLINES, null, airlineValues);
    }

    public void insertFlight(SQLiteDatabase databaseInstance, String departing_city, String destination, String departingDate, String destinationDate, String departingTime, String destinationTime, Double price, int airlineID) {
        ContentValues flightValues = new ContentValues();
        flightValues.put("DEPARTING_CITY", departing_city);
        flightValues.put("DESTINATION_CITY", destination);
        flightValues.put("DEPARTING_DATE", departingDate);
        flightValues.put("DESTINATION_DATE", destinationDate);
        flightValues.put("DEPARTING_TIME", departingTime);
        flightValues.put("DESTINATION_TIME", destinationTime);
        flightValues.put("PRICE", price);
        flightValues.put("FLIGHT_AIRLINE", airlineID);
        databaseInstance.insert(TABLE_FLIGHT_INFO, null, flightValues);
    }

    public void insertSeat(SQLiteDatabase databaseInstance, int status, int flightID, int flightTypeID) {
        ContentValues seatValues = new ContentValues();
        seatValues.put("STATUS", status);
        seatValues.put("SEAT_FLIGHT", flightID);
        seatValues.put("SEAT_FLIGHTCLASS", flightTypeID);
        databaseInstance.insert(TABLE_SEATS, null, seatValues);
    }

    public void insertFlightType(SQLiteDatabase databaseInstance, String flightType) {
        ContentValues flightClassValues = new ContentValues();
        flightClassValues.put("FLIGHT_TYPE", flightType);
        databaseInstance.insert(TABLE_FLIGHT_TYPE, null, flightClassValues);
    }
}
