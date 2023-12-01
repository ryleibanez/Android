package com.example.jaj;

public class CreateTables {

    public String createEmployeeTable() {
        String query = "CREATE TABLE IF NOT EXISTS Employee ("
                + "EmployeeId INTEGER PRIMARY KEY,"
                + "FirstName TEXT NOT NULL,"
                + "LastName TEXT NOT NULL,"
                + "Email TEXT NOT NULL,"
                + "Password TEXT NOT NULL,"
                + "JobTitle TEXT NOT NULL,"
                + "Address TEXT NOT NULL,"
                + "ContactNumber TEXT NOT NULL,"
                + "Gender TEXT NOT NULL,"
                + "DateOfBirth DATE NOT NULL,"
                + "Archived TEXT NOT NULL,"
                + "Image BLOB,"
                + "ResetPasswordToken TEXT"
                + ");";

        return query;
    }

    public String createEnrollment() {
        String query = "CREATE TABLE IF NOT EXISTS Enrollment ("
                + "EnrollmentId INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "EnrollmentArchived TEXT,"
                + "EmployeeId INTEGER NOT NULL,"
                + "CourseId INTEGER NOT NULL,"
                + "EnrollmentDate DATE NOT NULL,"
                + "FOREIGN KEY (EmployeeId) REFERENCES Employee (EmployeeId),"
                + "FOREIGN KEY (CourseId) REFERENCES Course (CourseId)"
                + ");";

        return query;
    }

    public String createSQLiteNotificationTableQuery() {
        String query = "CREATE TABLE IF NOT EXISTS notification ("
                + "NotificationId INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "email TEXT,"
                + "message TEXT"
                + ");";

        return query;
    }


    public String createInstructorTable() {
        String query = "CREATE TABLE IF NOT EXISTS Instructor ("
                + "InstructorId INTEGER PRIMARY KEY,"
                + "InsFirstName TEXT NOT NULL,"
                + "InsLastName TEXT NOT NULL,"
                + "InsEmail TEXT NOT NULL,"
                + "InsPassword TEXT NOT NULL,"
                + "InsAddress TEXT NOT NULL,"
                + "InsContactNumber TEXT NOT NULL,"
                + "InsGender TEXT NOT NULL,"
                + "InsDateOfBirth DATE NOT NULL,"
                + "InsImage BLOB,"
                + "InsJobTitle TEXT NOT NULL,"
                + "InsArchived TEXT,"
                + "IsAdmin TEXT,"
                + "ResetPasswordToken TEXT"
                + ");";

        return query;
    }

    public String createTrainingTable() {
        String query = "CREATE TABLE IF NOT EXISTS Training ("
                + "TrainingId INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "CourseId INTEGER NOT NULL,"
                + "TrainingName TEXT NOT NULL,"
                + "TrainingDate DATE NOT NULL,"
                + "TrainingStart TIME NOT NULL,"
                + "TrainingEnd TIME NOT NULL,"
                + "TrainingLocation TEXT,"
                + "TrainingArchived TEXT,"
                + "TrainingDescription TEXT,"
                + "FOREIGN KEY (CourseId) REFERENCES Course (CourseId)"
                + ");";

        return query;
    }






    public String createTrainingInstructorTable() {
        String query = "CREATE TABLE IF NOT EXISTS TrainingInstructor ("
                + "TrainingInstructorId INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "TrainingId INTEGER NOT NULL,"
                + "InstructorId INTEGER NOT NULL,"
                + "TrainingInstructorArchived TEXT,"
                + "FOREIGN KEY (InstructorId) REFERENCES Instructor (InstructorId),"
                + "FOREIGN KEY (TrainingId) REFERENCES Training (TrainingId)"
                + ");";

        return query;
    }



    public String createCourseTable() {
        String query = "CREATE TABLE IF NOT EXISTS Course ("
                + "CourseId INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "CourseName TEXT NOT NULL,"
                + "CourseDescription TEXT,"
                + "CourseStatus TEXT NOT NULL,"
                + "CourseArchived TEXT,"
                + "CourseImage BLOB,"
                + "PptFileContent BLOB"
                + ");";

        return query;
    }


    public String createCourseInstructor() {
        String query = "CREATE TABLE IF NOT EXISTS CourseInstructor ("
                + "CourseInstructorId INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "CourseId INTEGER NOT NULL,"
                + "InstructorId INTEGER NOT NULL,"
                + "CourseInstructorArchived TEXT,"
                + "FOREIGN KEY (InstructorId) REFERENCES Instructor (InstructorId),"
                + "FOREIGN KEY (CourseId) REFERENCES Course (CourseId)"
                + ");";

        return query;
    }

    public String createBookTrainingTable() {
        String query = "CREATE TABLE IF NOT EXISTS BookTraining ("
                + "BookTrainingId INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "BookTrainingArchived TEXT NOT NULL,"
                + "EmployeeId INTEGER NOT NULL,"
                + "TrainingId INTEGER NOT NULL,"
                + "BookTrainingDate DATE NOT NULL,"
                + "TrainingDate TEXT,"
                + "TrainingTime TEXT,"
                + "Notify TEXT,"
                + "FOREIGN KEY (TrainingId) REFERENCES Training (TrainingId),"
                + "FOREIGN KEY (EmployeeId) REFERENCES Employee (EmployeeId)"
                + ");";

        return query;
    }





}
