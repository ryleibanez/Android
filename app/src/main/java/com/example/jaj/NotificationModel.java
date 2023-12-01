package com.example.jaj;

public class NotificationModel {

    private int NotificationId;
    private String email;
    private String message;

    public NotificationModel(int notificationId, String email, String message) {
        NotificationId = notificationId;
        this.email = email;
        this.message = message;
    }

    public int getNotificationId() {
        return NotificationId;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }
}
