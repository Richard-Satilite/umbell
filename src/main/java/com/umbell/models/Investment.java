package com.umbell.models;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa uma categoria de investimentos no sistema.
 * Esta classe estende a classe Category e é específica para categorias de investimentos.
 *
 * @author Richard Satilite
 */
public class Investment extends Category {
    private BigDecimal currentValue;
    private BigDecimal expectReturn;
    private LocalDate maturityDate;
    private String institution;
    private String investmentType;

    /**
     * Construtor padrão para a categoria de investimentos.
     */
    public Investment() {
        super();
        setCategoryType("Investment");
    }

    /**
     * Construtor para criar uma categoria de investimentos com nome e limite mensal.
     *
     * @param name O nome da categoria de investimentos
     * @param monthLimit O limite mensal para a categoria de investimentos
     */
    public Investment(String name, BigDecimal monthLimit) {
        super(name, monthLimit, "Investment");
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