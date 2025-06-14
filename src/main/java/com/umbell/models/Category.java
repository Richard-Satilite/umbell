package com.umbell.models;

import java.math.BigDecimal;

/**
 * Representa uma categoria de transação no sistema.
 * Esta classe armazena informações sobre uma categoria, incluindo seu código, nome e descrição.
 *
 * @author Richard Satilite
 */
public abstract class Category {
    private Long id;
    private String name;
    private BigDecimal monthLimit;
    private String categoryType;

    public Category() {}

    public Category(String name, BigDecimal monthLimit, String categoryType) {
        this.name = name;
        this.monthLimit = monthLimit;
        this.categoryType = categoryType;
    }

    // Getters and Setters

    /**
     * Obtém o código da categoria.
     *
     * @return O código da categoria
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o código da categoria.
     *
     * @param id O código da categoria
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém o nome da categoria.
     *
     * @return O nome da categoria
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome da categoria.
     *
     * @param name O nome da categoria
     */
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMonthLimit() {
        return monthLimit;
    }

    public void setMonthLimit(BigDecimal monthLimit) {
        this.monthLimit = monthLimit;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
} 