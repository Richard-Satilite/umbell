package com.umbell.models;

import java.time.LocalDateTime;

public class Notification {
    private Long id;
    private String name;
    private String message;
    private boolean read;
    private String userEmail;
    private LocalDateTime createdAt;

    public Notification(Long id, String name, String message, boolean read, String userEmail) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.read = read;
        this.userEmail = userEmail;
        this.createdAt = LocalDateTime.now();
    }

    public Notification(String name, String message, String userEmail) {
        this.name = name;
        this.message = message;
        this.read = false;
        this.userEmail = userEmail;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Notification{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", message='" + message + '\'' +
               ", read=" + read +
               ", userEmail='" + userEmail + '\'' +
               ", createdAt=" + createdAt +
               '}';
    }
} 