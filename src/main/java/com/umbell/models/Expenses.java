package com.umbell.models;

import java.math.BigDecimal;

/**
 * Representa uma categoria de despesas no sistema.
 * Esta classe estende a classe Category e é específica para categorias de despesas.
 *
 * @author Richard Satilite
 */
public class Expenses extends Category {
    private boolean fixed;
    private String payMethod;
    private String vendor;

    /**
     * Construtor padrão para a categoria de despesas.
     */
    public Expenses() {
        super();
        setCategoryType("Expenses");
    }

    /**
     * Construtor para criar uma categoria de despesas com nome e limite mensal.
     *
     * @param name O nome da categoria de despesas
     * @param monthLimit O limite mensal para a categoria de despesas
     */
    public Expenses(String name, BigDecimal monthLimit) {
        super(name, monthLimit, "Expenses");
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