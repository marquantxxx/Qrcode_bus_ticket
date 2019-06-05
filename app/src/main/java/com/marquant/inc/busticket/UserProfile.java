package com.marquant.inc.busticket;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {

    EditText email;

    Button Okay;
    Button view_my_details;
    TextView secret_text;
    TextView phone_display;
    TextView email_display;
    TextView full_name;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("User Profile");

        email = findViewById(R.id.email);
        Okay = findViewById(R.id.Okay);
        view_my_details = findViewById(R.id.view_my_details);
        secret_text = findViewById(R.id.secret_text);
        phone_display = findViewById(R.id.phone_display);
        email_display = findViewById(R.id.email_display);
        full_name = findViewById(R.id.full_name);

        databaseHelper = new DatabaseHelper(this);

        view_my_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (databaseHelper.checkEmail(email.getText().toString())){

                    Snackbar.make(v, "Email exists", Snackbar.LENGTH_SHORT).show();

                    String[] profileDetails = databaseHelper.userProfile(email.getText().toString());
                    email_display.setText(profileDetails[0]);
                    full_name.setText(profileDetails[1]);
                    phone_display.setText(profileDetails[2]);
                    secret_text.setText(profileDetails[3]);
                }else{
                    Snackbar.make(v, "Email does not exists", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}
