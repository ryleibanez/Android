package com.example.jaj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ViewTraining extends AppCompatActivity {

    private TextView txtSelectedTraining, txtDescription, txtDate, txtTime, txtLocation, txtInstructor;
    private Handler handler = new Handler();
    private ImageButton btnHome,btnRecord,btnBook,btnMyProfile;

    String tid,location,description,date,title,startTime, endTime, name;

    private Button btnSave, btnCancel;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_training);

        intent = getIntent();
        tid = intent.getStringExtra("tid");
        location = intent.getStringExtra("location");
        description = intent.getStringExtra("description");
        date = intent.getStringExtra("date");
        title = intent.getStringExtra("title");
        startTime = intent.getStringExtra("startTime");
        endTime = intent.getStringExtra("endTime");
        name = intent.getStringExtra("name");
        DateConverter convert = new DateConverter();

        txtDate = findViewById(R.id.txtDate);
        txtDescription = findViewById(R.id.txtDescription);
        txtLocation = findViewById(R.id.txtLocation);
        txtTime = findViewById(R.id.txtTime);
        txtSelectedTraining = findViewById(R.id.txtSelectedTraining);
        txtInstructor = findViewById(R.id.txtInstructor);
        //inserting the values

        txtDate.setText("Date: "+convert.date(date));
        txtDescription.setText("Description:\n"+description);
        txtLocation.setText("Location: "+location);
        txtTime.setText("Time: "+convert.time(startTime) + " - " + convert.time(endTime));
        txtSelectedTraining.setText(title);
        txtInstructor.setText("Training Instructor: " + name);

        btnHome = findViewById(R.id.btnHome);
        btnRecord = findViewById(R.id.btnRecord);
        btnBook = findViewById(R.id.btnBook);
        btnMyProfile = findViewById(R.id.btnMyProfile);
        View btnLogout = findViewById(R.id.btnLogout);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewTraining.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewTraining.this, myprofile.class);
                startActivity(intent);
                finish();
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewTraining.this, SelectCourse.class);
                startActivity(intent);
                finish();
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SessionController(getApplicationContext()).logoutUser();
                new MainActivity().stopUserReaderService();



            }
        });


        //booking the training
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String empId = new SessionController(getApplicationContext()).getEmpId();
                String book =  new trainingController().book(empId, tid, date, startTime, "");

              if(book.equals("Success")){
                  Toast.makeText(ViewTraining.this, "Book Successfully!", Toast.LENGTH_SHORT).show();
                  Intent intent = new Intent(ViewTraining.this, MainActivity.class);
                  startActivity(intent);
                  finish();
              }else if (book.contains("Duplicate")){
                  Toast.makeText(ViewTraining.this, "An Error Occured! You are enrolled for this training.", Toast.LENGTH_SHORT).show();
              }else{
                  Toast.makeText(ViewTraining.this, "An error occured!", Toast.LENGTH_SHORT).show();
              }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewTraining.this, SelectCourse.class);
                startActivity(intent);
                finish();
            }
        });

        //menu actions
        btnHome = findViewById(R.id.btnHome);
        btnRecord = findViewById(R.id.btnRecord);
        btnBook = findViewById(R.id.btnBook);
        btnMyProfile = findViewById(R.id.btnMyProfile);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewTraining.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewTraining.this, myprofile.class);
                startActivity(intent);
                finish();
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewTraining.this, SelectCourse.class);
                startActivity(intent);
                finish();
            }
        });







    }
    protected void onDestroy() {
        super.onDestroy();
        // Remove any remaining callbacks when the activity is destroyed

        

        handler.removeCallbacksAndMessages(null);
    }
}