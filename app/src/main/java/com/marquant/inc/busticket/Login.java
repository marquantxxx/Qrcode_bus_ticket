package com.marquant.inc.busticket;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText email;
    EditText password;

    Button loginBtn;
    TextView reg_question;
    TextView forgot_password;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        // Initialize views
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        reg_question = findViewById(R.id.reg_question);
        forgot_password = findViewById(R.id.forgot_password);

        // Initialize Objects
        databaseHelper = new DatabaseHelper(this);

        // Initialize Listeners
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyFromSQLite();
            }
        });

        reg_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(getApplicationContext(), Register.class);
                startActivity(regIntent);
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgIntent = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(forgIntent);
            }
        });
    }

    private void verifyFromSQLite(){
        if (databaseHelper.checkUser(email.getText().toString(), password.getText().toString())){
            Intent verifiedIntent = new Intent(getApplicationContext(), Dashboard.class);
            Toast.makeText(this, "Accepted", Toast.LENGTH_SHORT).show();
            emptyEditText();
            startActivity(verifiedIntent);
        }else{
            Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_LONG).show();
            emptyEditText();
        }
    }

    public void emptyEditText(){
        email.setText(null);
        password.setText(null);
    }
}
