package com.example.jaj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import java.util.List;

public class BookTraining extends AppCompatActivity {
    private Handler handler = new Handler();
    private RecyclerView recyclerView;
    private RecyclerViewBookAdapter adapter;
    private ProgressBar progressBar;
    private trainingController trainingController;
    Intent intent;
    String courseId;
    private ImageButton btnHome,btnRecord,btnBook,btnMyProfile;
    private int interval = 10000; // Time interval in milliseconds (e.g., 10 seconds)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_training);

        trainingController = new trainingController();
        intent = getIntent();
         courseId = intent.getStringExtra("courseId");
        List<TrainingModel>getAll = trainingController.getAll(courseId, getApplicationContext());



        progressBar = findViewById(R.id.progressbar);


        btnHome = findViewById(R.id.btnHome);
        btnRecord = findViewById(R.id.btnRecord);
        btnBook = findViewById(R.id.btnBook);
        btnMyProfile = findViewById(R.id.btnMyProfile);
        View btnLogout = findViewById(R.id.btnLogout);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookTraining.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookTraining.this, myprofile.class);
                startActivity(intent);
                finish();
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookTraining.this, SelectCourse.class);
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

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookTraining.this, TrainingRecord.class);
                startActivity(intent);
                finish();



            }
        });

        recyclerView = findViewById(R.id.recyclerBook);
        adapter = new RecyclerViewBookAdapter(BookTraining.this, getAll);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(BookTraining.this));
        new GetDataAsyncTask().execute();



        startPeriodicTask();


    }

    private void startPeriodicTask() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Execute AsyncTask to get data in the background
                new GetDataAsyncTask().execute();
                if(!trainingController.getAll(courseId, getApplicationContext()).isEmpty()){

                    progressBar.setVisibility(View.GONE);
                }else{

                    progressBar.setVisibility(View.VISIBLE);
                }
                // Schedule the task to run again after the specified interval
                handler.postDelayed(this, interval);
            }
        }, interval); // Initial delay before the first run
    }

    private class GetDataAsyncTask extends AsyncTask<Void, Void, List<TrainingModel>> {
        @Override
        protected List<TrainingModel> doInBackground(Void... voids) {
            new TableUpdater().readUpdateBookTraining(getApplicationContext());
            new TableUpdater().readTraining(getApplicationContext());
            new TableUpdater().readInstructor(getApplicationContext());
            // Perform time-consuming operation in the background
            return trainingController.getAll(courseId, getApplicationContext());
        }

        @Override
        protected void onPostExecute(List<TrainingModel> result) {
            // Update the existing adapter's data
            if(!result.isEmpty()){
                progressBar.setVisibility(View.GONE);
            }


            adapter.updateData(result);


        }
    }



}