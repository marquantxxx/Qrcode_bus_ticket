package com.marquant.inc.busticket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button about;
    Button bookTicket;
    Button bookStatus;
    Button reschedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home");

        about = findViewById(R.id.about);
        bookTicket = findViewById(R.id.bookTicket);
        bookStatus = findViewById(R.id.bookStatus);
        reschedule = findViewById(R.id.reschedule);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutIntent = new Intent(Dashboard.this, AboutUs.class);
                startActivity(aboutIntent);
            }
        });

        bookTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btIntent = new Intent(Dashboard.this, BookTicket.class);
                startActivity(btIntent);
            }
        });

        bookStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bsIntent = new Intent(Dashboard.this, BookingStatus.class);
                startActivity(bsIntent);
            }
        });

        reschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rescheduleIntent = new Intent(Dashboard.this, Reschedule.class);
                startActivity(rescheduleIntent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.book_ticket) {

            Intent bookIntent = new Intent(Dashboard.this, BookTicket.class);
            startActivity(bookIntent);

        } else if (id == R.id.booking_status) {

            Intent statusIntent = new Intent(Dashboard.this, BookingStatus.class);
            startActivity(statusIntent);

        } else if (id == R.id.about_us) {

            Intent aboutIntent = new Intent(Dashboard.this, AboutUs.class);
            startActivity(aboutIntent);

        }else if (id == R.id.cancelBooking) {

                Intent cancelIntent = new Intent(Dashboard.this, CancelBooking.class);
                startActivity(cancelIntent);

        } else if (id == R.id.rescheduleBooking) {

            Intent reschedIntent = new Intent(Dashboard.this, Reschedule.class);
            startActivity(reschedIntent);


        } else if (id == R.id.userProfile) {

            Intent profileIntent = new Intent(Dashboard.this, UserProfile.class);
            startActivity(profileIntent);

        }

        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
