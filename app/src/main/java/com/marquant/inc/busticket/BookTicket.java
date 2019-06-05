package com.marquant.inc.busticket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.DatePickerDialog;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class BookTicket extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    ArrayAdapter<CharSequence> adapter;

    Button one_way;
    Button roundtrip;
    Button btnBookTicket;

    LinearLayout arrivalTime_lyt;

    Spinner departureTerminal;
    Spinner arrivalTerminal;
    Spinner noOfTravelers;

    TextView arrivalDate;
    TextView departureDate;

    TextView ticket_no;
    TextView price;
    Button generate_ticketNo;

    String dtSelected = null;
    String atSelected = null;
    String ntSelected = null;

    String status = "Successful";

    private DatabaseHelper databaseHelper;
    private Bookings bookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Book Ticket");


        // Spinner adapterview implementation for departure
        departureTerminal = findViewById(R.id.departureTerminal);
        adapter = ArrayAdapter.createFromResource(this, R.array.terminal_names_from, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departureTerminal.setAdapter(adapter);
        departureTerminal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals("Select Terminal")){

                }else {
//                    Snackbar.make(view, parent.getItemAtPosition(position) + " selected", Snackbar.LENGTH_SHORT).show();
                    dtSelected = parent.getItemAtPosition(position).toString().trim();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Spinner adapterview implementation

        // Spinner adapterview implementation for arrival
        arrivalTerminal = findViewById(R.id.arrivalTerminal);
        adapter = ArrayAdapter.createFromResource(this, R.array.terminal_names_to, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrivalTerminal.setAdapter(adapter);
        arrivalTerminal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals("Select Terminal")){

                }else {
//                    Snackbar.make(view, parent.getItemAtPosition(position) + " selected", Snackbar.LENGTH_SHORT).show();
                    atSelected = parent.getItemAtPosition(position).toString().trim();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Spinner adapterview implementation for arrival


        // Spinner adapterview implementation for no of travelers
        noOfTravelers = findViewById(R.id.noOfTravelers);
        adapter = ArrayAdapter.createFromResource(this, R.array.noOfTravelers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noOfTravelers.setAdapter(adapter);
        noOfTravelers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals("Select Number")){

                }else {
//                    Snackbar.make(view, parent.getItemAtPosition(position) + " selected", Snackbar.LENGTH_SHORT).show();
                    ntSelected = parent.getItemAtPosition(position).toString().trim();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Spinner adapterview implementation for no of travelers


        // Initialize views
        one_way = findViewById(R.id.one_way);
        roundtrip = findViewById(R.id.roundtrip);
        btnBookTicket = findViewById(R.id.btnBookTicket);

        arrivalDate = findViewById(R.id.arrivalDate);
        departureDate = findViewById(R.id.departureDate);

        arrivalTime_lyt = findViewById(R.id.arrivalTime_lyt);

        ticket_no = findViewById(R.id.ticket_no);
        price = findViewById(R.id.price);
        generate_ticketNo = findViewById(R.id.generate_ticketNo);


        // Initialize Objects
        databaseHelper = new DatabaseHelper(this);
        bookings = new Bookings();


        // Initialize Listeners
        one_way.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrivalTime_lyt.setVisibility(View.GONE);
            }
        });

        roundtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrivalTime_lyt.setVisibility(View.VISIBLE);
            }
        });

        generate_ticketNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticket_no.setText(atSelected.substring(0,3).concat(departureDate.getText().toString().substring(4,6)));
                if (atSelected.equals("Aba")){
                    if (ntSelected.equals("1"))
                        price.setText("4,300 Naira");
                    else if(ntSelected.equals("2")){
                        price.setText("8,600 Naira");
                    }
                }else if (atSelected.equals("Calabar")){
                    if (ntSelected.equals("1"))
                        price.setText("4,900 Naira");
                    else if(ntSelected.equals("2")){
                        price.setText("9,800 Naira");
                    }
                }else if (atSelected.equals("Enugu")){
                    if (ntSelected.equals("1"))
                        price.setText("3,500 Naira");
                    else if(ntSelected.equals("2")){
                        price.setText("7,000 Naira");
                    }
                }else if (atSelected.equals("Lagos")){
                    if (ntSelected.equals("1"))
                        price.setText("4,600 Naira");
                    else if(ntSelected.equals("2")){
                        price.setText("9,200 Naira");
                    }
                }else if (atSelected.equals("Onitsha")){
                    if (ntSelected.equals("1"))
                        price.setText("4,100 Naira");
                    else if(ntSelected.equals("2")){
                        price.setText("8,200 Naira");
                    }
                }else if (atSelected.equals("Owerri")){
                    if (ntSelected.equals("1"))
                        price.setText("5,300 Naira");
                    else if(ntSelected.equals("2")){
                        price.setText("10,600 Naira");
                    }
                }else if (atSelected.equals("PortHarcourt")){
                    if (ntSelected.equals("1"))
                        price.setText("4,500 Naira");
                    else if(ntSelected.equals("2")){
                        price.setText("9,000 Naira");
                    }
                }else if (atSelected.equals("Umuahia")){
                    if (ntSelected.equals("1"))
                        price.setText("4,300 Naira");
                    else if(ntSelected.equals("2")){
                        price.setText("8,600 Naira");
                    }
                }else if (atSelected.equals("Uyo")){
                    if (ntSelected.equals("1"))
                        price.setText("4,700 Naira");
                    else if(ntSelected.equals("2")){
                        price.setText("9,400 Naira");
                    }
                }


            }
        });

        btnBookTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!departureDate.getText().toString().isEmpty() && !dtSelected.isEmpty() &&
                        !atSelected.isEmpty()){

                    postBookingToSQLite();
                    Intent newIntent = new Intent(getApplicationContext(), PayPal.class);
                    startActivity(newIntent);
                }

            }
        });
    }

    public void postBookingToSQLite() {
//        if (cat.isEmpty() && subCat.isEmpty() && compType.isEmpty()){
//            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
//            emptyFields();
//        }else{
            bookings.setArrivalDate(arrivalDate.getText().toString());
            bookings.setDepartureDate(departureDate.getText().toString());
            bookings.setTravelFrom(dtSelected);
            bookings.setTravelTo(atSelected);
            bookings.setNoOfTravelers(ntSelected);
            bookings.setTicketNo(ticket_no.getText().toString());
            bookings.setStatus(status);
            bookings.setPrice(price.getText().toString());

            databaseHelper.addBooking(bookings);

            Toast.makeText(this, "Booking made Successfully", Toast.LENGTH_LONG).show();
            emptyFields();

    }


    public void emptyFields(){
        arrivalDate.setText("yyyy-mm-dd");
        departureDate.setText("yyyy-mm-dd");
        noOfTravelers.setPrompt("Select Number");
        departureTerminal.setPrompt("Select Terminal");
        arrivalTerminal.setPrompt("Select Terminal");
        ticket_no.setText(null);
        price.setText(null);
    }

    //Calls DatePicker Fragment class which returns the calender view
    public void datePicker(View view){
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "date");
    }

    private void setDate(final Calendar calendar){
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        departureDate.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
    }

    public static class DatePickerFragment extends DialogFragment{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),
            (DatePickerDialog.OnDateSetListener)getActivity(), year, month, day);

        }
    }
}
