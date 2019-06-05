package com.marquant.inc.busticket;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class BookingStatus extends AppCompatActivity {

    EditText ticket_no_prompt;
    Button view_details;
    String TAG = "GenerateQRCode";
    TextView booking_status_tv;
    String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    ImageView qrImage;
    Button saver;
    Button gen;


    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_status);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Booking Status");

        // Initialize views
        ticket_no_prompt = findViewById(R.id.ticket_no_prompt);
        view_details = findViewById(R.id.view_details);
        qrImage =findViewById(R.id.QR_Image);
        booking_status_tv = findViewById(R.id.booking_status_tv);
        saver = findViewById(R.id.saver);
        gen = findViewById(R.id.generate);


        // Initialize objects
        databaseHelper = new DatabaseHelper(this);

        // Initialize Listeners

        view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (databaseHelper.checkTicketNo(ticket_no_prompt.getText().toString())){

                    Snackbar.make(v, "Ticket number exists", Snackbar.LENGTH_SHORT).show();

                    String[] profileDetails = databaseHelper.bookingProfile(ticket_no_prompt.getText().toString());
                    booking_status_tv.setText(profileDetails[0]);

                }else{
                    Snackbar.make(v, "Ticket number does not exists", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        //button for qrcode generation
        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //retrieving information from database to string
                String[] profileDetails = databaseHelper.bookingProfile(ticket_no_prompt.getText().toString());
                String message = ticket_no_prompt.getText().toString();
                //saving each values obtained to be used on the qrcode as text
            String status ="booking status: "+  profileDetails [0] + "\n" +"Travelling from: " + profileDetails[1] + "\n" +"Travelling to: "+ profileDetails[2]
                    + "\n" + "Date: "+profileDetails[3] + "\n" +"Number of travellers: "+ profileDetails[4] + "\n" + "Price: "+profileDetails[5] + "\n" + "Ticket no: " + message;

            //getting window size
                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int smallerDimension = width < height ? width : height;
                smallerDimension = smallerDimension * 3 / 4;


                //encoding values to the qrimage
                qrgEncoder = new QRGEncoder(
                        status, null,
                        QRGContents.Type.TEXT,
                        smallerDimension);
                try {
                    //writing text generated to image
                    bitmap = qrgEncoder.encodeAsBitmap();

                    //locating imageview and setting text
                    qrImage.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    Log.v(TAG, e.toString());
                }
            }
        });

        //button to save qrcode to gallery
        saver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //used to save image to gallery
                boolean save;
                String result;
                if
                //checking for permissions to write to storage for android 6.0>
                (ContextCompat.checkSelfPermission(BookingStatus.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(BookingStatus.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},23
                    );

                }else {
                    try {
                        //saves text to image
                        save = QRGSaver.save(savePath, ticket_no_prompt.getText().toString().trim(), bitmap, QRGContents.ImageType.IMAGE_JPEG);
                        result = save ? "Image Saved" : "Image Not Saved";
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

        });



    }
}