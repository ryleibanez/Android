package com.example.jaj;

import android.content.Context;
import android.os.AsyncTask;

public class LoginUpdater extends AsyncTask<Void, Void, Void> {
    private Context context;

    public LoginUpdater(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        new TableUpdater().readCourseInstructor(context);
        new TableUpdater().readTrainingInstructor(context);
        new TableUpdater().readInstructor(context);
        new TableUpdater().readUpdateLogin(context);
        new TableUpdater().readCourses(context);
        new TableUpdater().readEnrollment(context);
        new TableUpdater().readUpdateBookTraining(context);
        new TableUpdater().readTraining(context);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        // Handle post-execution tasks if needed
    }
}

