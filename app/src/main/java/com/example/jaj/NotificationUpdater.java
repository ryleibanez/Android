package com.example.jaj;

import android.content.Context;
import android.os.AsyncTask;

public class NotificationUpdater extends AsyncTask<Void, Void, Void> {
    private Context context;

    public NotificationUpdater(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        new TableUpdater().updateBookTrainingInfo(context, "");
        new TableUpdater().insertIntoNotificationTable(context, "");
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        // Handle post-execution tasks if needed
    }
}

