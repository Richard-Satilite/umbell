package com.umbell.models;

public class Expenses extends Category {
    private boolean fixed;
    private String payMethod;
    private String vendor;

    public Expenses() {
        super();
        setCategoryType("Expenses");
    }

    public Expenses(String name, boolean fixed, String payMethod, String vendor) {
        super(name, null, "Expenses"); // monthLimit is null for Expenses
        this.fixed = fixed;
        this.payMethod = payMethod;
        this.vendor = vendor;
    }

    // Getters and Setters

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
} 