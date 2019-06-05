package com.marquant.inc.busticket;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Reschedule extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    TextView departure_date;
    TextView arrival_terminal;
    TextView no_of_travelers;
    EditText ticket_no_prompt;
    TextView departure_terminal;
    TextView price;

    Button reschedule;
    Button edit_details;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reschedule);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Reschedule");

        departure_date = findViewById(R.id.departure_date);
        no_of_travelers = findViewById(R.id.no_of_travelers);
        ticket_no_prompt = findViewById(R.id.ticket_no_prompt);
        departure_terminal = findViewById(R.id.departure_terminal);
        arrival_terminal = findViewById(R.id.arrival_terminal);
        price = findViewById(R.id.price);

        reschedule = findViewById(R.id.reschedule);
        edit_details = findViewById(R.id.edit_details);

        databaseHelper = new DatabaseHelper(this);

        reschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.updateBooking(ticket_no_prompt.getText().toString(),
                        departure_terminal.getText().toString(), arrival_terminal.getText().toString(),
                        departure_date.getText().toString(),
                        no_of_travelers.getText().toString(),
                        price.getText().toString());
                Snackbar.make(v, "Booking Profile Updated", Snackbar.LENGTH_SHORT).show();
                emptyFields();
            }
        });

        edit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (databaseHelper.checkTicketNo(ticket_no_prompt.getText().toString())){

                    Snackbar.make(v, "Ticket number exists", Snackbar.LENGTH_SHORT).show();

                    String[] profileDetails = databaseHelper.bookingProfileReschedule(ticket_no_prompt.getText().toString());

                    departure_terminal.setText(profileDetails[0]);
                    arrival_terminal.setText(profileDetails[1]);
                    departure_date.setText(profileDetails[2]);
                    no_of_travelers.setText(profileDetails[3]);
                    price.setText(profileDetails[4]);

                }else{
                    Snackbar.make(v, "Ticket number does not exists", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void emptyFields(){
        departure_terminal.setText(null);
        arrival_terminal.setText(null);
        departure_date.setText(null);
        no_of_travelers.setText(null);
        price.setText(null);
    }

    //Calls DatePicker Fragment class which returns the calender view
    public void datePicker(View view){
        Reschedule.DatePickerFragment fragment = new Reschedule.DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "date");
    }

    private void setDate(final Calendar calendar){
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        departure_date.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
    }

    public static class DatePickerFragment extends DialogFragment {

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
