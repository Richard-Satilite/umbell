package com.umbell.models;

public class Income extends Category {
    private String source;
    private String frequency;
    private double tax;

    public Income() {
        super();
        setCategoryType("Income");
    }

    public Income(String name, String source, String frequency, double tax) {
        super(name, null, "Income"); // monthLimit is null for Income
        this.source = source;
        this.frequency = frequency;
        this.tax = tax;
    }

    // Getters and Setters

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }
} 