package com.example.jaj;

public class User {

    private int EmployeeId;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Password;
    private String JobTitle;
    private String Address;
    private String ContactNumber;
    private String Gender;
    private String DateOfBirth;
    private String Archived;
    private String code;
    private String age;

    public User(int employeeId, String firstName, String lastName, String email, String password, String jobTitle, String address, String contactNumber, String gender, String dateOfBirth, String archived, String code, String age) {
        EmployeeId = employeeId;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Password = password;
        JobTitle = jobTitle;
        Address = address;
        ContactNumber = contactNumber;
        Gender = gender;
        DateOfBirth = dateOfBirth;
        Archived = archived;
        this.age = age;
        this.code = code;
    }

    public String getAge() {
        return age;
    }

    public int getEmployeeId() {
        return EmployeeId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public String getAddress() {
        return Address;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public String getGender() {
        return Gender;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public String getArchived() {
        return Archived;
    }

    public String getCode() {
        return code;
    }
}
