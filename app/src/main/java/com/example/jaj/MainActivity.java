package com.example.jaj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import com.rudderstack.android.sdk.core.RudderClient;
import com.rudderstack.android.sdk.core.RudderConfig;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    private RecyclerView recyclerView;
    private RecyclerView recyclerNotification;
    private RecyclerViewAdapter adapter;
    private RecyclerViewNotificationAdapter notificationAdapter;
    private UserController userController;

    private ImageButton btnHome,btnRecord,btnBook,btnMyProfile, btnLogout;
    private int interval = 5000; // Time interval in milliseconds (e.g., 10 seconds)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        UserController user = new UserController();
        List<TrainingModel>getUpcoming = new trainingController().getUpcoming(new SessionController(getApplicationContext()).getEmpId(), getApplicationContext());

        List<NotificationModel>getNotification = new NotificationController().getAll(new SessionController(getApplicationContext()).getUserEmail(), getApplicationContext());
        btnHome = findViewById(R.id.btnHome);
        btnRecord = findViewById(R.id.btnRecord);
        btnBook = findViewById(R.id.btnBook);
        btnMyProfile = findViewById(R.id.btnMyProfile);
        btnLogout = findViewById(R.id.btnLogout);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, myprofile.class);
                startActivity(intent);
                finish();
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectCourse.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SessionController(getApplicationContext()).logoutUser();
                stopUserReaderService();



            }
        });

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TrainingRecord.class);
                startActivity(intent);
                finish();



            }
        });



        recyclerNotification = findViewById(R.id.recyclerNotification);
        recyclerView = findViewById(R.id.recyclerUpcoming);

        adapter = new RecyclerViewAdapter(this, getUpcoming);



        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notificationAdapter = new RecyclerViewNotificationAdapter(this, getNotification);
        recyclerNotification.setAdapter(notificationAdapter);
        recyclerNotification.setLayoutManager(new LinearLayoutManager(this));

        userController = new UserController();



        startPeriodicTask();


    }

    public void stopUserReaderService() {
        Intent serviceIntent = new Intent(MainActivity.this, UserReader.class);
        stopService(serviceIntent);

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void startPeriodicTask() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                new BookTrainingUpdater(MainActivity.this).execute();
                // Call your getUsers method here
                List<TrainingModel> getAllDetails = new trainingController().getUpcoming(new SessionController(getApplicationContext()).getEmpId(),getApplicationContext());

                // Update the existing adapter's data
                adapter.updateData(getAllDetails);

                // Schedule the task to run again after the specified interval
                handler.postDelayed(this, interval);
            }
        }, interval); // Initial delay before the first run
    }
    protected void onDestroy() {
        super.onDestroy();
        // Remove any remaining callbacks when the activity is destroyed

        handler.removeCallbacksAndMessages(null);
    }
}