package com.umbell.service;

import com.umbell.models.Investment;

public class InvestmentService extends CategoryService {

    public InvestmentService() {
        super();
    }

    public Investment createInvestment(Investment investment) {
        // Add specific validation for Investment if needed
        return (Investment) createCategory(investment);
    }

    public void updateInvestment(Investment investment) {
        // Add specific validation for Investment if needed
        updateCategory(investment);
    }
} 