package com.example.jaj;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class myprofile extends AppCompatActivity {
    private TextView empNum, empName, email, address, phoneNumber, dob, age, gender, btnEdit;
    private ProgressBar progressBar;
    private ImageButton btnHome, btnRecord, btnBook, btnMyProfile;
    private UserController users;
    private DateConverter convert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);


        // Assuming you have a byte array 'blobData' representing your Blob data
        byte[] blobData = new UserController().profileImage(new SessionController(getApplicationContext()).getUserEmail(),getApplicationContext());

        // Convert the byte array to a Bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(blobData, 0, blobData.length);
        ImageView profileImageView = findViewById(R.id.profileImage);


        Glide.with(this).load(bitmap).into(profileImageView);

        convert = new DateConverter();
        initializeViews();
        setupButtonClickListeners();

        new FetchUserInfoTask().execute();
    }

    private void initializeViews() {
        empNum = findViewById(R.id.empNum);
        empName = findViewById(R.id.empName);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        phoneNumber = findViewById(R.id.phoneNumber);
        dob = findViewById(R.id.dob);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        btnEdit = findViewById(R.id.btnEdit);
        btnHome = findViewById(R.id.btnHome);
        btnBook = findViewById(R.id.btnBook);
        btnRecord = findViewById(R.id.btnRecord);
        View btnLogout = findViewById(R.id.btnLogout);
        btnMyProfile = findViewById(R.id.btnMyProfile);
        progressBar = findViewById(R.id.progressbar);
        users = new UserController();
    }

    private void setupButtonClickListeners() {
        btnEdit.setOnClickListener(view -> {
            Intent intent = new Intent(myprofile.this, EditProfile.class);
            startActivity(intent);
            finish();
        });

        btnHome = findViewById(R.id.btnHome);
        btnRecord = findViewById(R.id.btnRecord);
        btnBook = findViewById(R.id.btnBook);
        btnMyProfile = findViewById(R.id.btnMyProfile);
        View btnLogout = findViewById(R.id.btnLogout);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myprofile.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myprofile.this, myprofile.class);
                startActivity(intent);
                finish();
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myprofile.this, SelectCourse.class);
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
        SessionController sessionManager = new SessionController(getApplicationContext());

        List<User> userInfo = users.getInfo(sessionManager.getUserEmail(), getApplicationContext());
        for (User item : userInfo) {
            empNum.setText("Employee Number: "+item.getEmployeeId()+"");
            empName.setText("Name: "+item.getFirstName() + " " + item.getLastName());
            email.setText("Email: " + item.getEmail());
            address.setText("Address: " + item.getAddress());
            phoneNumber.setText("Mobile Number: "+item.getContactNumber());
            dob.setText("Birthday: "+new DateConverter().date(item.getDateOfBirth()));
            age.setText("Age: "+item.getAge());
            gender.setText("Gender: "+item.getGender());
        }
    }

    private class FetchUserInfoTask extends AsyncTask<Void, Void, List<User>> {
        @Override
        protected List<User> doInBackground(Void... voids) {
            SessionController sessionManager = new SessionController(getApplicationContext());
            return users.getInfo(sessionManager.getUserEmail(), getApplicationContext());
        }

        @Override
        protected void onPostExecute(List<User> userInfo) {

            if (!userInfo.isEmpty()) {
                progressBar.setVisibility(View.GONE);
            }

            for (User item : userInfo) {
                empNum.setText("Employee Number: "+item.getEmployeeId()+"");
                empName.setText("Name: "+item.getFirstName() + " " + item.getLastName());
                email.setText("Email: " + item.getEmail());
                address.setText("Address: " + item.getAddress());
                phoneNumber.setText("Mobile Number: "+item.getContactNumber());
                dob.setText("Birthday: "+new DateConverter().date(item.getDateOfBirth()));
                age.setText("Age: "+item.getAge());
                gender.setText("Gender: "+item.getGender());
            }
        }
    }
}
