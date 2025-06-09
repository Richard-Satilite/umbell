package com.umbell.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Account {
    private Long code;
    private BigDecimal totalBalance;
    private String userEmail;
    private LocalDateTime createdAt;

    public Account() {  
        this.totalBalance = BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
    }

    public Account(String userEmail) {
        this();
        this.userEmail = userEmail;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
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
} 