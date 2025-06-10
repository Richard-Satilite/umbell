package com.umbell.models;

import java.time.LocalDateTime;

public class Goal {
    private Long id;
    private String title;
    private double targetAmount;
    private double currentAmount;
    private boolean achieved;
    private LocalDateTime createdAt;
    private Account account;

    public Goal() {
    }

    public Goal(String title, double targetAmount, Account account) {
        this.title = title;
        this.targetAmount = targetAmount;
        this.currentAmount = 0.0;
        this.achieved = false;
        this.createdAt = LocalDateTime.now();
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
        if (this.currentAmount >= this.targetAmount) {
            this.achieved = true;
        }
    }

    public boolean isAchieved() {
        return achieved;
    }

    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
} 