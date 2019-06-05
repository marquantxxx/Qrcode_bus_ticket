package com.marquant.inc.busticket;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText fullname;
    EditText emailAddress;
    EditText phoneNumber;
    EditText secretText;
    EditText pass;
    EditText confirmPass;

    RadioButton male;
    RadioButton female;
    String gender = null;

    TextView login_question;
    Button registerBtn;

    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Register");

        // Initialize views
        fullname = findViewById(R.id.fullname);
        emailAddress = findViewById(R.id.emailAddress);
        phoneNumber = findViewById(R.id.phoneNumber);
        secretText = findViewById(R.id.secretText);
        pass = findViewById(R.id.pass);
        confirmPass = findViewById(R.id.confirmPass);

        login_question = findViewById(R.id.login_question);
        registerBtn = findViewById(R.id.registerBtn);

        // Initialize Objects
        databaseHelper = new DatabaseHelper(this);
        user = new User();


        // Initialize Listeners
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pass.getText().toString().equals(confirmPass.getText().toString())){
                    Snackbar.make(v, "Password do not match", Snackbar.LENGTH_SHORT).show();
                }else{
                    postDataToSQLite();
                }
            }
        });

        login_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lgnIntent = new Intent(getApplicationContext(), Login.class);
                startActivity(lgnIntent);
            }
        });
    }

    public void gender(View v) {

        switch (v.getId()) {

            case R.id.male:
                gender = male.getText().toString();
                break;

            case R.id.female:
                gender = female.getText().toString();
                break;
        }
    }

    public void postDataToSQLite(){
        if (!databaseHelper.checkUser(emailAddress.getText().toString(), pass.getText().toString())){

            user.setFullname(fullname.getText().toString());
            user.setEmail(emailAddress.getText().toString());
            user.setPhone(phoneNumber.getText().toString());
            user.setSecret_text(secretText.getText().toString());
            user.setPassword(pass.getText().toString());


            databaseHelper.addUser(user);

            Toast.makeText(this, "Registration Successful", Toast.LENGTH_LONG).show();

            Intent regUser = new Intent(this, Login.class);
            startActivity(regUser);

            emptyFields();
        }else {
            Toast.makeText(this, "User exists already", Toast.LENGTH_LONG).show();
            emptyFields();
        }
    }

    public void emptyFields(){
        fullname.setText(null);
        emailAddress.setText(null);
        phoneNumber.setText(null);
        secretText.setText(null);
        pass.setText(null);
        confirmPass.setText(null);
    }
}
