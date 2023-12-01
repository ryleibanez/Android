package com.example.jaj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;

public class TrainingRecord extends AppCompatActivity {
    private Handler handler = new Handler();
    private RecyclerView recyclerView;
    private RecyclerView recyclerOngoing;
    private RecyclerViewOngoingTrainingAdapter adapter;
    private RecyclerViewCompletedTrainingAdapter completedTrainingAdapter;
    private UserController userController;

    private ImageButton btnHome,btnRecord,btnBook,btnMyProfile, btnLogout;
    private int interval = 5000; // Time interval in milliseconds (e.g., 10 seconds)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_record);

        List<TrainingModel> getCompletedTraining = new trainingController().getCompletedTraining(new SessionController(getApplicationContext()).getEmpId(), getApplicationContext());
        List<TrainingModel> getOngoingTraining = new trainingController().getOngoingTraining(new SessionController(getApplicationContext()).getEmpId(),getApplicationContext());

        btnHome = findViewById(R.id.btnHome);
        btnRecord = findViewById(R.id.btnRecord);
        btnBook = findViewById(R.id.btnBook);
        btnMyProfile = findViewById(R.id.btnMyProfile);
        btnLogout = findViewById(R.id.btnLogout);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrainingRecord.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrainingRecord.this, myprofile.class);
                startActivity(intent);
                finish();
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrainingRecord.this, SelectCourse.class);
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
                Intent intent = new Intent(TrainingRecord.this, TrainingRecord.class);
                startActivity(intent);
                finish();



            }
        });

        recyclerView = findViewById(R.id.recyclerCompleted);
        recyclerOngoing = findViewById(R.id.recyclerOngoing);

        completedTrainingAdapter = new RecyclerViewCompletedTrainingAdapter(this, getCompletedTraining);
        recyclerView.setAdapter(completedTrainingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerViewOngoingTrainingAdapter(this, getOngoingTraining );
        recyclerOngoing.setAdapter(adapter);
        recyclerOngoing.setLayoutManager(new LinearLayoutManager(this));

        startPeriodicTask();

    }
    public void stopUserReaderService() {
        Intent serviceIntent = new Intent(TrainingRecord.this, UserReader.class);
        stopService(serviceIntent);

        Intent intent = new Intent(TrainingRecord.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private void startPeriodicTask() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                new BookTrainingUpdater(TrainingRecord.this).execute();
                // Call your getUsers method here
                List<TrainingModel> getAllDetails = new trainingController().getCompletedTraining(new SessionController(getApplicationContext()).getEmpId(), getApplicationContext());
                List<TrainingModel> getOngoing = new trainingController().getOngoingTraining(new SessionController(getApplicationContext()).getEmpId(), getApplicationContext());

                // Update the existing adapter's data
                completedTrainingAdapter.updateData(getAllDetails);
                adapter.updateData(getOngoing);

                // Schedule the task to run again after the specified interval
                handler.postDelayed(this, interval);
            }
        }, interval); // Initial delay before the first run
    }
}