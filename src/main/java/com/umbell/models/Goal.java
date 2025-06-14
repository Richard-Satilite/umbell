package com.umbell.models;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa uma meta financeira no sistema.
 * Esta classe armazena informações sobre uma meta, incluindo seu código, nome, valor alvo, valor atual, data de início, data de término e status de conclusão.
 *
 * @author Richard Satilite
 */
public class Goal {
    private Long id;
    private String title;
    private double targetAmount;
    private double currentAmount;
    private boolean achieved;
    private LocalDateTime createdAt;
    private Account account;

    public Goal() {
    }

    public Goal(String title, double targetAmount, Account account) {
        this.title = title;
        this.targetAmount = targetAmount;
        this.currentAmount = 0.0;
        this.achieved = false;
        this.createdAt = LocalDateTime.now();
        this.account = account;
    }

    /**
     * Obtém o código da meta.
     *
     * @return O código da meta
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o código da meta.
     *
     * @param id O código da meta
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém o nome da meta.
     *
     * @return O nome da meta
     */
    public String getTitle() {
        return title;
    }

    /**
     * Define o nome da meta.
     *
     * @param title O nome da meta
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Obtém o valor alvo da meta.
     *
     * @return O valor alvo da meta
     */
    public double getTargetAmount() {
        return targetAmount;
    }

    /**
     * Define o valor alvo da meta.
     *
     * @param targetAmount O valor alvo da meta
     */
    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    /**
     * Obtém o valor atual da meta.
     *
     * @return O valor atual da meta
     */
    public double getCurrentAmount() {
        return currentAmount;
    }

    /**
     * Define o valor atual da meta.
     *
     * @param currentAmount O valor atual da meta
     */
    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
        if (this.currentAmount >= this.targetAmount) {
            this.achieved = true;
        }
    }

    /**
     * Verifica se a meta foi concluída.
     *
     * @return true se a meta foi concluída, false caso contrário
     */
    public boolean isAchieved() {
        return achieved;
    }

    /**
     * Define se a meta foi concluída.
     *
     * @param achieved true se a meta foi concluída, false caso contrário
     */
    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }

    /**
     * Obtém a data de início da meta.
     *
     * @return A data de início da meta
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Define a data de início da meta.
     *
     * @param createdAt A data de início da meta
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
} 