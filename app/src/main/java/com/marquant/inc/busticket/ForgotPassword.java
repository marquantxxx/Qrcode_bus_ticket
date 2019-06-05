package com.marquant.inc.busticket;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {

    EditText email_forgot;
    EditText secretText_forgot;
    Button verifyEmail;

    EditText new_pass;
    EditText re_enter_new_pass;
    Button confirmChange;

    LinearLayout pass_change_lyt;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Forgot Password");


        email_forgot = findViewById(R.id.email_forgot);
        secretText_forgot = findViewById(R.id.secretText_forgot);
        verifyEmail = findViewById(R.id.verifyEmail);

        new_pass = findViewById(R.id.new_pass);
        re_enter_new_pass = findViewById(R.id.re_enter_new_pass);
        confirmChange = findViewById(R.id.confirmChange);

        pass_change_lyt = findViewById(R.id.pass_change_lyt);

        databaseHelper = new DatabaseHelper(this);

        verifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (databaseHelper.checkUser1(email_forgot.getText().toString(), secretText_forgot.getText().toString())){
                    pass_change_lyt.setVisibility(View.VISIBLE);
                    verifyEmail.setVisibility(View.GONE);
                }else{
                    pass_change_lyt.setVisibility(View.GONE);
                    Snackbar.make(v, "Invalid Response!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        confirmChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!new_pass.getText().toString().equals(re_enter_new_pass.getText().toString())) {
//
                    Toast.makeText(getApplicationContext(), "Unsuccessful. New passwords do not match", Toast.LENGTH_LONG).show();

                } else if (email_forgot.getText().toString().isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Unsuccessful. Enter Email...", Toast.LENGTH_LONG).show();


                } else if (email_forgot.getText().toString().isEmpty() || secretText_forgot.getText().toString().isEmpty()
                        || new_pass.getText().toString().isEmpty() || re_enter_new_pass.getText().toString().isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Fill in empty fields...", Toast.LENGTH_LONG).show();

                } else {
                    databaseHelper.updatePassword(email_forgot.getText().toString(), secretText_forgot.getText().toString(),
                            new_pass.getText().toString());
//
                    Toast.makeText(getApplicationContext(), "Password Reset successfully", Toast.LENGTH_LONG).show();
                    emptyFields();
                }
            }
        });
    }

    public void emptyFields(){
        new_pass.setText(null);
        re_enter_new_pass.setText(null);
        secretText_forgot.setText(null);
        email_forgot.setText(null);
    }
}
