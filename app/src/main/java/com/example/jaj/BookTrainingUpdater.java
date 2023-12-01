package com.example.jaj;

import android.content.Context;
import android.os.AsyncTask;

public class BookTrainingUpdater extends AsyncTask<Void, Void, Void> {
    private Context context;

    public BookTrainingUpdater(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        new TableUpdater().readTrainingInstructor(context);
        new TableUpdater().readCourseInstructor(context);
        new TableUpdater().readTraining(context);
        new TableUpdater().readCourses(context);
        new TableUpdater().readEnrollment(context);
        new TableUpdater().readUpdateBookTraining(context);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        // Handle post-execution tasks if needed
    }
}

