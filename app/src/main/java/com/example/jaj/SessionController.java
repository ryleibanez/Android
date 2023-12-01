package com.example.jaj;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionController {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    private static final String PREF_NAME = "YourAppSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_EMP_ID = "empId";

    public SessionController(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String userEmail, String empId) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USER_EMAIL, userEmail);
        editor.putString(KEY_EMP_ID, empId);
        editor.commit();

        MyAlarmReceiver.scheduleAlarm(context.getApplicationContext());
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getUserEmail() {
        return pref.getString(KEY_USER_EMAIL, null);
    }

    public String getEmpId(){
        return pref.getString(KEY_EMP_ID,  null);
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
    }
}
