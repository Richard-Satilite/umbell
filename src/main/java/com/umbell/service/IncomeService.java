package com.umbell.service;

import com.umbell.models.Income;

public class IncomeService extends CategoryService {

    public IncomeService() {
        super();
    }

    public Income createIncome(Income income) {
        // Add specific validation for Income if needed
        return (Income) createCategory(income);
    }

    public void updateIncome(Income income) {
        // Add specific validation for Income if needed
        updateCategory(income);
    }
} 