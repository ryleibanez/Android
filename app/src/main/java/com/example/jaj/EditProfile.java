package com.example.jaj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditProfile extends AppCompatActivity {

    TextInputEditText txtFirstName,txtLastName,txtEmail,txtAddress,txtPhoneNumber,txtAge;
    private EditText editTextDateOfBirth;
    private Spinner spinnerGender;
    private ArrayAdapter<CharSequence> genderAdapter;
    private UserController users;
    private TextView btnBack;

    private String email;
private AppCompatButton btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        editTextDateOfBirth = findViewById(R.id.editTextDateOfBirth);
        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);

        txtAddress = findViewById(R.id.txtAddress);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);

        editTextDateOfBirth = findViewById(R.id.editTextDateOfBirth);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);


        spinnerGender = findViewById(R.id.spinnerGender);

        // Create an ArrayAdapter using the string array and a default spinner layout
        genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerGender.setAdapter(genderAdapter);






        //going back

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfile.this, myprofile.class);
                startActivity(intent);
                finish();
            }
        });
        //Updating Info:
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //variables

                String fname = txtFirstName.getText().toString().trim();
                String lname = txtLastName.getText().toString().trim();

                String address = txtAddress.getText().toString().trim();
                String phone = txtPhoneNumber.getText().toString().trim();
                String dob = editTextDateOfBirth.getText().toString();

                String gender = spinnerGender.getSelectedItem().toString();



                if (fname.length()==0){
                    txtFirstName.setError("Full Name is Required");
                    txtFirstName.requestFocus();
                    return;
                }

                if(!fname.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")){
                    txtFirstName.setError("Enter only alphabetical characters!");
                    txtFirstName.requestFocus();
                    return;
                }

                if (lname.length()==0){
                    txtLastName.setError("Full Name is Required");
                    txtLastName.requestFocus();
                    return;
                }

                if(!lname.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")){
                    txtLastName.setError("Enter only alphabetical characters!");
                    txtLastName.requestFocus();
                    return;
                }

                if (address.length()==0){
                    txtAddress.setError("Full Name is Required");
                    txtAddress.requestFocus();
                    return;
                }

                if (phone.length() == 0){
                    txtPhoneNumber.setError("Phone Number is Required");
                    txtPhoneNumber.requestFocus();
                    return;
                }
                if (!phone.matches("^[0-9]{10,11}$")){
                    txtPhoneNumber.setError("Enter a correct phone number!");
                    txtPhoneNumber.requestFocus();
                    return;
                }

                if (gender.equals("Select Your Gender")){
                    Toast.makeText(EditProfile.this, "Select your Gender", Toast.LENGTH_SHORT).show();
                    spinnerGender.requestFocus();

                    return;
                }

                if (isUserTooYoung(dob)){
                    Toast.makeText(EditProfile.this, "Invalid Age.", Toast.LENGTH_SHORT).show();
                    editTextDateOfBirth.requestFocus();

                    return;
                }
                SessionController session = new SessionController(getApplicationContext());

                boolean check = users.updateInfo(fname,lname,session.getUserEmail(),address,phone,dob,gender, getApplicationContext());
                Toast.makeText(EditProfile.this, "Please Wait...", Toast.LENGTH_SHORT).show();
                if(check){
                    Toast.makeText(EditProfile.this, "Profile Information Has Been Changed Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditProfile.this, myprofile.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(EditProfile.this, "An Error Occurred. Please Try Again Later!", Toast.LENGTH_SHORT).show();
                }









            }
        });

        users = new UserController();
        SessionController sessionManager = new SessionController(getApplicationContext());
        List<User> userInfo = users.getInfo(sessionManager.getUserEmail(), getApplicationContext());

        for (User item : userInfo){
            txtFirstName.setText(item.getFirstName());
            txtLastName.setText(item.getLastName());

            txtAddress.setText(item.getAddress());
            txtPhoneNumber.setText(item.getContactNumber());
            editTextDateOfBirth.setText(item.getDateOfBirth());


        }
        new FetchUserInfoTask().execute();

   }

    public static boolean isUserTooYoung(String dob) {
        // Define the minimum age (e.g., 18 years)
        int minimumAge = 18;

        // Parse the entered date of birth
        Date birthDate = parseDateOfBirth(dob);

        if (birthDate != null) {
            // Calculate the age based on the current date
            int age = calculateAge(birthDate);

            // Check if the age is less than the minimum allowed age
            return age < minimumAge;
        } else {
            // Invalid date of birth format
            return false;
        }
    }

    // Method to parse the entered date of birth
    private static Date parseDateOfBirth(String dob) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(dob);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to calculate age based on the current date
    private static int calculateAge(Date birthDate) {
        Calendar dob = Calendar.getInstance();
        dob.setTime(birthDate);

        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        // Adjust age if the birthday hasn't occurred yet this year
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }

    // Method to show DatePickerDialog
    public void showDatePickerDialog(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate =  selectedYear  + "-" +  (selectedMonth + 1)  + "-" + selectedDay  ;
                    editTextDateOfBirth.setText(selectedDate);
                },
                year,
                month,
                day
        );

        datePickerDialog.show();
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

            }






            for (User item : userInfo){
                txtFirstName.setText(item.getFirstName());
                txtLastName.setText(item.getLastName());

                txtAddress.setText(item.getAddress());
                txtPhoneNumber.setText(item.getContactNumber());
                editTextDateOfBirth.setText(item.getDateOfBirth());


            }


        }
    }
}