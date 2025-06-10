package com.umbell.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Investment extends Category {
    private BigDecimal currentValue;
    private BigDecimal expectReturn;
    private LocalDate maturityDate;
    private String institution;
    private String investmentType;

    public Investment() {
        super();
        setCategoryType("Investment");
    }

    public Investment(String name, BigDecimal currentValue, BigDecimal expectReturn, LocalDate maturityDate, String institution, String investmentType) {
        super(name, null, "Investment"); // monthLimit is null for Investment
        this.currentValue = currentValue;
        this.expectReturn = expectReturn;
        this.maturityDate = maturityDate;
        this.institution = institution;
        this.investmentType = investmentType;
    }

    // Getters and Setters

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public BigDecimal getExpectReturn() {
        return expectReturn;
    }

    public void setExpectReturn(BigDecimal expectReturn) {
        this.expectReturn = expectReturn;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getInvestmentType() {
        return investmentType;
    }

    public void setInvestmentType(String investmentType) {
        this.investmentType = investmentType;
    }
} 