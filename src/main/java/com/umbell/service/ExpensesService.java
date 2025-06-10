package com.umbell.service;

import com.umbell.models.Expenses;

public class ExpensesService extends CategoryService {

    public ExpensesService() {
        super();
    }

    public Expenses createExpenses(Expenses expenses) {
        // Add specific validation for Expenses if needed
        return (Expenses) createCategory(expenses);
    }

    public void updateExpenses(Expenses expenses) {
        // Add specific validation for Expenses if needed
        updateCategory(expenses);
    }
} 