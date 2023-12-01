package com.example.jaj;

public class TrainingModel {

    private int trainingID;
    private int CourseID;
    private String TrainingName;
    private String TrainingDate;
    private String TrainingStart;
    private String TrainingEnd;
    private String TrainingLocation;
    private String TrainingArchived;
    private String TrainingDescription;

    public TrainingModel(int trainingID, int courseID, String trainingName, String trainingDate, String trainingStart, String trainingEnd, String trainingLocation, String trainingArchived, String trainingDescription) {
        this.trainingID = trainingID;
        CourseID = courseID;
        TrainingName = trainingName;
        TrainingDate = trainingDate;
        TrainingStart = trainingStart;
        TrainingEnd = trainingEnd;
        TrainingLocation = trainingLocation;
        TrainingArchived = trainingArchived;
        TrainingDescription = trainingDescription;
    }


    public int getTrainingID() {
        return trainingID;
    }

    public int getCourseID() {
        return CourseID;
    }

    public String getTrainingName() {
        return TrainingName;
    }

    public String getTrainingDate() {
        return TrainingDate;
    }

    public String getTrainingStart() {
        return TrainingStart;
    }

    public String getTrainingEnd() {
        return TrainingEnd;
    }

    public String getTrainingLocation() {
        return TrainingLocation;
    }

    public String getTrainingArchived() {
        return TrainingArchived;
    }

    public String getTrainingDescription() {
        return TrainingDescription;
    }
}
