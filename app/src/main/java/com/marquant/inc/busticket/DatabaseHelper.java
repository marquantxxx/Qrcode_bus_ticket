package com.marquant.inc.busticket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    DatabaseHelper DBhelper;
    SQLiteDatabase db;

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "Confluence.db";

    // Users table
    private static final String USER_TABLE = "user";
    private static final String USER_ID = "user_id";
    private static final String USER_FULL_NAME = "user_fullname";
    private static final String USER_EMAIL = "user_email";
    private static final String USER_PHONE = "user_phone";
    private static final String USER_SECRET_TEXT = "user_secret_text";
    private static final String USER_PASSWORD = "user_password";
    private static final String USER_GENDER = "user_gender";

    // Create user table query
    String queryCreateUser = "CREATE TABLE " + USER_TABLE + "(" + USER_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_FULL_NAME + " TEXT,"
            + USER_EMAIL + " TEXT," + USER_PHONE + " TEXT, " + USER_SECRET_TEXT + " TEXT, "
            + USER_PASSWORD + " TEXT," + USER_GENDER + " TEXT" + ")";

    // Create booking table query
    String queryCreateBooking = "CREATE TABLE " + BookingContract.BookingEntry.BOOKING_TABLE + "(" +
            BookingContract.BookingEntry.BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            BookingContract.BookingEntry.BOOKING_STATUS + " TEXT," +
            BookingContract.BookingEntry.TRAV_FROM + " TEXT," +
            BookingContract.BookingEntry.TRAV_TO + " TEXT," +
            BookingContract.BookingEntry.ARR_DATE + " TEXT," +
            BookingContract.BookingEntry.DEP_DATE + " TEXT," +
            BookingContract.BookingEntry.NO_OF_TRAV + " TEXT," +
            BookingContract.BookingEntry.TICKET_NO + " TEXT," +
            BookingContract.BookingEntry.BOOKING_PRICE + " TEXT" +")";

    // Drop user table query
    String queryDropUser = "DROP TABLE IF EXISTS " + USER_TABLE;

    String queryDropBooking = "DROP TABLE IF EXISTS " + BookingContract.BookingEntry.BOOKING_TABLE;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(queryCreateUser);
        db.execSQL(queryCreateBooking);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(queryDropUser);
        db.execSQL(queryDropBooking);
        onCreate(db);
    }

    //-- Opens the database
    public DatabaseHelper open() throws SQLException {
        db = DBhelper.getWritableDatabase();
        return this;
    }

    //-- Closes the database
    public void close(){
        DBhelper.close();
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_FULL_NAME, user.getFullname());
        values.put(USER_EMAIL, user.getEmail());
        values.put(USER_PHONE, user.getPhone());
        values.put(USER_SECRET_TEXT, user.getSecret_text());
        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_GENDER, user.getGender());

        db.insert(USER_TABLE, null, values);
        db.close();
    }

    public void addBooking(Bookings bookings){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BookingContract.BookingEntry.TRAV_FROM, bookings.getTravelFrom());
        values.put(BookingContract.BookingEntry.TRAV_TO, bookings.getTravelTo());
        values.put(BookingContract.BookingEntry.ARR_DATE, bookings.getArrivalDate());
        values.put(BookingContract.BookingEntry.DEP_DATE, bookings.getDepartureDate());
        values.put(BookingContract.BookingEntry.NO_OF_TRAV, bookings.getNoOfTravelers());
        values.put(BookingContract.BookingEntry.TICKET_NO, bookings.getTicketNo());
        values.put(BookingContract.BookingEntry.BOOKING_STATUS, bookings.getStatus());
        values.put(BookingContract.BookingEntry.BOOKING_PRICE, bookings.getPrice());

        db.insert(BookingContract.BookingEntry.BOOKING_TABLE, null, values);
        db.close();
    }

    public int updateBooking(String ticket_no, String dep_terminal, String arr_terminal,
                             String dep_date, String no_of_travelers, String price){
        //UPDATE USER_TABLE SET
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BookingContract.BookingEntry.TRAV_FROM, dep_terminal);
        values.put(BookingContract.BookingEntry.TRAV_TO, arr_terminal);
        values.put(BookingContract.BookingEntry.DEP_DATE, dep_date);
        values.put(BookingContract.BookingEntry.NO_OF_TRAV, no_of_travelers);
        values.put(BookingContract.BookingEntry.BOOKING_PRICE, price);
        String[] args = {ticket_no};
        int count = db.update(BookingContract.BookingEntry.BOOKING_TABLE, values,
                BookingContract.BookingEntry.TICKET_NO+ "=?", args);

        return count;
    }

    public int deleteBooking(String ticket_no){
        SQLiteDatabase db = getWritableDatabase();
        String[]args = {ticket_no};
        int count = db.delete(BookingContract.BookingEntry.BOOKING_TABLE,
                BookingContract.BookingEntry.TICKET_NO + "=?", args);
        return count;
    }

    public int updatePassword(String email, String secretText, String newPassword){
        //UPDATE USER_TABLE SET
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_PASSWORD, newPassword);
        String[] args = {email, secretText};
        int count = db.update(USER_TABLE, values, USER_EMAIL+ "=?"
                + " AND " + USER_SECRET_TEXT + "=?", args);

        return count;
    }

    public String[] bookingProfile(String ticket_no) {
        String[] columns = {
                BookingContract.BookingEntry.BOOKING_STATUS,
                BookingContract.BookingEntry.TRAV_FROM,
                BookingContract.BookingEntry.TRAV_TO,
                BookingContract.BookingEntry.DEP_DATE,
                BookingContract.BookingEntry.NO_OF_TRAV,
                BookingContract.BookingEntry.TICKET_NO,
                BookingContract.BookingEntry.BOOKING_PRICE

        };
        String[] selectionsArgs = { ticket_no };
        SQLiteDatabase db = getWritableDatabase();
        String query = BookingContract.BookingEntry.TICKET_NO + " = ?" ;

        Cursor c = db.query(BookingContract.BookingEntry.BOOKING_TABLE, columns, query,
                selectionsArgs, null,null,null);
        c.moveToFirst();
        String[] dbString = new String[6];
        if(c.getCount() != 0) {

            int column_booking_status = c.getColumnIndex("status");
            int column_trav_from = c.getColumnIndex("trav_from");
            int column_trav_to = c.getColumnIndex("trav_to");
            int column_dep_date = c.getColumnIndex("dep_date");
            int column_no_of_trav = c.getColumnIndex("no_of_trav");
            int column_price = c.getColumnIndex("price");


            dbString[0] = c.getString(column_booking_status);
            dbString[1] = c.getString(column_trav_from);
            dbString[2] = c.getString(column_trav_to);
            dbString[3] = c.getString(column_dep_date);
            dbString[4] = c.getString(column_no_of_trav);
            dbString[5] = c.getString(column_price);


        }
        c.close();
        db.close();
        return dbString;
    }

    public String[] bookingProfileReschedule(String ticket_no) {
        String[] columns = {
                BookingContract.BookingEntry.BOOKING_STATUS,
                BookingContract.BookingEntry.TRAV_FROM,
                BookingContract.BookingEntry.TRAV_TO,
                BookingContract.BookingEntry.DEP_DATE,
                BookingContract.BookingEntry.NO_OF_TRAV,
                BookingContract.BookingEntry.TICKET_NO,
                BookingContract.BookingEntry.BOOKING_PRICE
        };
        String[] selectionsArgs = { ticket_no };
        SQLiteDatabase db = getWritableDatabase();
        String query = BookingContract.BookingEntry.TICKET_NO + " = ?" ;

        Cursor c = db.query(BookingContract.BookingEntry.BOOKING_TABLE, columns, query,
                selectionsArgs, null,null,null);
        c.moveToFirst();
        String[] dbString = new String[5];
        if(c.getCount() != 0) {
            int column_booking_status = c.getColumnIndex("status");
            int column_trav_from = c.getColumnIndex("trav_from");
            int column_trav_to = c.getColumnIndex("trav_to");
            int column_dep_date = c.getColumnIndex("dep_date");
            int column_no_of_trav = c.getColumnIndex("no_of_trav");
            int column_price = c.getColumnIndex("price");

            dbString[0] = c.getString(column_trav_from);
            dbString[1] = c.getString(column_trav_to);
            dbString[2] = c.getString(column_dep_date);
            dbString[3] = c.getString(column_no_of_trav);
            dbString[4] = c.getString(column_price);

        }
        c.close();
        db.close();
        return dbString;
    }

    public String[] userProfile(String email) {
        String[] columns = {
                USER_EMAIL,
                USER_FULL_NAME,
                USER_PHONE,
                USER_SECRET_TEXT
        };
        String[] selectionsArgs = { email };
        SQLiteDatabase db = getWritableDatabase();
        String query = USER_EMAIL + " = ?" ;

        Cursor c = db.query(USER_TABLE, columns, query,
                selectionsArgs, null,null,null);
        c.moveToFirst();
        String[] dbString = new String[4];
        if(c.getCount() != 0) {

            int column_user_email = c.getColumnIndex("user_email");
            int column_user_fullname = c.getColumnIndex("user_fullname");
            int column_user_phone = c.getColumnIndex("user_phone");
            int column_user_secret_text = c.getColumnIndex("user_secret_text");

            dbString[0] = c.getString(column_user_email);
            dbString[1] = c.getString(column_user_fullname);
            dbString[2] = c.getString(column_user_phone);
            dbString[3] = c.getString(column_user_secret_text);
        }
        c.close();
        db.close();
        return dbString;
    }

    public boolean checkUser(String email, String password){
        String[] columns = {USER_ID};

        SQLiteDatabase db = this.getWritableDatabase();
        String selection = USER_EMAIL + " = ?" + " AND " + USER_PASSWORD + " =?";
        String[] selectionArgs = { email, password };

        Cursor cursor = db.query(USER_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;
    }

    public boolean checkTicketNo(String ticketNo){
        String[] columns = {BookingContract.BookingEntry.BOOKING_ID};

        SQLiteDatabase db = this.getWritableDatabase();
        String selection = BookingContract.BookingEntry.TICKET_NO + " = ?";
        String[] selectionArgs = { ticketNo };

        Cursor cursor = db.query(BookingContract.BookingEntry.BOOKING_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;
    }

    public boolean checkEmail(String email){
        String[] columns = {USER_ID};

        SQLiteDatabase db = this.getWritableDatabase();
        String selection = USER_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(USER_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;
    }

    public boolean checkUser1(String email, String secret_text){
        String[] columns = {USER_ID};

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = USER_EMAIL + " = ?" + " AND " + USER_SECRET_TEXT + " =?";
        String[] selectionArgs = { email, secret_text };

        Cursor cursor = db.query(USER_TABLE,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0){
            return true;
        }
        return false;
    }
}
