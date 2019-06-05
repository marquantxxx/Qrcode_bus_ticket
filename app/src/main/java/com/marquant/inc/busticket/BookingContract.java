package com.marquant.inc.busticket;

import android.provider.BaseColumns;

public class BookingContract {

    public static final class BookingEntry implements BaseColumns {

        public static final String BOOKING_TABLE = "bookings";
        public static final String BOOKING_ID = "book_id";
        public static final String TRAV_FROM = "trav_from";
        public static final String TRAV_TO = "trav_to";
        public static final String DEP_DATE = "dep_date";
        public static final String ARR_DATE = "arr_date";
        public static final String NO_OF_TRAV = "no_of_trav";
        public static final String TICKET_NO = "ticket_no";
        public static final String BOOKING_STATUS = "status";
        public static final String BOOKING_PRICE = "price";
    }
}
