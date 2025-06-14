package com.umbell.models;

import java.math.BigDecimal;

/**
 * Representa uma categoria de receitas no sistema.
 * Esta classe estende a classe Category e é específica para categorias de receitas.
 *
 * @author Richard Satilite
 */
public class Income extends Category {
    private String source;
    private String frequency;
    private double tax;

    /**
     * Construtor padrão para a categoria de receitas.
     */
    public Income() {
        super();
        setCategoryType("Income");
    }

    /**
     * Construtor para criar uma categoria de receitas com nome, fonte e frequência.
     *
     * @param name O nome da categoria de receitas
     * @param source A fonte da receita
     * @param frequency A frequência da receita
     * @param tax O imposto sobre a receita
     */
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