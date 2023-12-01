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

public class SelectCourse extends AppCompatActivity {
    private Handler handler = new Handler();
    private RecyclerView recyclerView;
    private RecyclerViewCourseAdapter adapter;
    private ProgressBar progressBar;
    private CourseController courseController;
    private ImageButton btnHome,btnRecord,btnBook,btnMyProfile;
    private  String empId;
    private int interval = 5000; // Time interval in milliseconds (e.g., 10 seconds)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_course);
        SessionController sessionController = new SessionController(getApplicationContext());

        empId = sessionController.getEmpId();
        courseController = new CourseController();
        List<Course> getAll = courseController.getAllCourses(empId, getApplicationContext());

        progressBar = findViewById(R.id.progressbar);


        btnHome = findViewById(R.id.btnHome);
        btnRecord = findViewById(R.id.btnRecord);
        btnBook = findViewById(R.id.btnBook);
        btnMyProfile = findViewById(R.id.btnMyProfile);
        View btnLogout = findViewById(R.id.btnLogout);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectCourse.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectCourse.this, myprofile.class);
                startActivity(intent);
                finish();
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectCourse.this, SelectCourse.class);
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
                Intent intent = new Intent(SelectCourse.this, TrainingRecord.class);
                startActivity(intent);
                finish();



            }
        });

        recyclerView = findViewById(R.id.recyclerCourse);
        adapter = new RecyclerViewCourseAdapter(SelectCourse.this, getAll);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SelectCourse.this));

        new GetDataAsyncTask().execute();



        startPeriodicTask();

    }

    private void startPeriodicTask() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Execute AsyncTask to get data in the background
                new GetDataAsyncTask().execute();
                if(!courseController.getAllCourses(empId ,getApplicationContext()).isEmpty()){
                    progressBar.setVisibility(View.GONE);
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                }
                // Schedule the task to run again after the specified interval
                handler.postDelayed(this, interval);
            }
        }, interval); // Initial delay before the first run
    }

    private class GetDataAsyncTask extends AsyncTask<Void, Void, List<Course>> {
        @Override
        protected List<Course> doInBackground(Void... voids) {
            // Perform time-consuming operation in the background
            return courseController.getAllCourses(empId, getApplicationContext());
        }

        @Override
        protected void onPostExecute(List<Course> result) {
            // Update the existing adapter's data
            if(!result.isEmpty()){
                progressBar.setVisibility(View.GONE);
            }


            adapter.updateData(result);


        }
    }
}