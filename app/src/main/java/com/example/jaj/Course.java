package com.example.jaj;

public class Course {

    private int CourseId;
    private String CourseName;
    private String CourseDescription;
    private String CourseStatus;
    private String CourseArchived;

    public Course(int courseId, String courseName, String courseDescription, String courseStatus, String courseArchived) {
        CourseId = courseId;
        CourseName = courseName;
        CourseDescription = courseDescription;
        CourseStatus = courseStatus;
        CourseArchived = courseArchived;
    }

    public int getCourseId() {
        return CourseId;
    }

    public String getCourseName() {
        return CourseName;
    }

    public String getCourseDescription() {
        return CourseDescription;
    }

    public String getCourseStatus() {
        return CourseStatus;
    }

    public String getCourseArchived() {
        return CourseArchived;
    }
}
