package com.example.jaj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText loginEmail, loginPassword;
    private Button signinButton;

    private TextView backButton;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new LoginUpdater(this).execute();

        UserController user = new UserController();
        SessionController sessionManager = new SessionController(getApplicationContext());



            loginEmail = findViewById(R.id.loginEmail);
            loginPassword = findViewById(R.id.loginPassword);
            signinButton = findViewById(R.id.signinButton);
            boolean checkUser = checkUserSession();
            if (checkUser) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {

            }

            signinButton.setOnClickListener(new View.OnClickListener() {



                @Override
                public void onClick(View view) {

                    if (isNetworkAvailable()) {

                        String email = loginEmail.getText().toString().trim();
                        String pass = loginPassword.getText().toString();
                        boolean checker = user.Login(email, pass, getApplicationContext());
                        String empID = "";


                        if (TextUtils.isEmpty(email)) {
                            loginEmail.setError("Email is required!");
                            loginEmail.requestFocus();
                            return; // Return early if email is empty
                        }

                        if (TextUtils.isEmpty(pass)) {
                            loginPassword.setError("Password is required!");
                            loginPassword.requestFocus();
                            return; // Return early if password is empty
                        }

                        if (checker) {
                            empID = user.getEmpId(email);
                            // Request the FOREGROUND_SERVICE permission at runtime
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.FOREGROUND_SERVICE) != PackageManager.PERMISSION_GRANTED) {
                                    // Permission is not granted, request it
                                    ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.FOREGROUND_SERVICE}, 0);
                                } else {
                                    sessionManager.createLoginSession(email, empID);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                    Toast.makeText(LoginActivity.this, "Great! Welcome " + sessionManager.getUserEmail(), Toast.LENGTH_SHORT).show();
                                    // Permission is granted, start the UserReader service
                                    startUserReaderService();
                                }

                            } else {
                                // For Android versions before P, you can start the service directly
                                startUserReaderService();
                            }
                        }else{
                            Toast.makeText(LoginActivity.this, "Invalid Email or Password!", Toast.LENGTH_SHORT).show();
                        }
                    }else{

                        Toast.makeText(LoginActivity.this, "Internet is Required to Access This App.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }









    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
    public void stopUserReaderService() {
        Intent serviceIntent = new Intent(LoginActivity.this, UserReader.class);
        stopService(serviceIntent);


    }
    // Start the UserReader service (called if the permission is already granted)
    private void startUserReaderService() {
        Intent serviceIntent = new Intent(LoginActivity.this, UserReader.class);
        startService(serviceIntent);
        Toast.makeText(this, "Service Started.", Toast.LENGTH_SHORT).show();
    }

    private boolean checkUserSession(){
        boolean check = false;
        SessionController session = new SessionController(getApplicationContext());
        if(session.getUserEmail()!=null && session.getUserEmail().length()>1){
            check = true;
        }
        return check;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can now start the foreground service
            } else {
                // Permission denied, handle the case where the user denied the permission
            }
        }
    }



}