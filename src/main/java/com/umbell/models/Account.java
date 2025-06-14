package com.umbell.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma conta bancária no sistema.
 * Esta classe armazena informações sobre uma conta, incluindo seu código, nome, saldo total e e-mail do usuário associado.
 *
 * @author Richard Satilite
 */
public class Account {
    private Long code;
    private BigDecimal totalBalance;
    private String userEmail;
    private String name;
    private LocalDateTime createdAt;
    private List<Movement> movements;
    private List<Goal> goals;

    public Account() {  
        this.totalBalance = BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
        this.movements = new ArrayList<>();
        this.goals = new ArrayList<>();
    }

    public Account(String userEmail, String name) {
        this();
        this.userEmail = userEmail;
        this.name = name;
    }

    /**
     * Define o nome da conta.
     *
     * @param name O nome da conta
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Obtém o nome da conta.
     *
     * @return O nome da conta
     */
    public String getName(){
        return name;
    }

    /**
     * Obtém o código da conta.
     *
     * @return O código da conta
     */
    public Long getCode() {
        return code;
    }

    /**
     * Define o código da conta.
     *
     * @param code O código da conta
     */
    public void setCode(Long code) {
        this.code = code;
    }

    /**
     * Obtém o saldo total da conta.
     *
     * @return O saldo total da conta
     */
    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    /**
     * Define o saldo total da conta.
     *
     * @param totalBalance O saldo total da conta
     */
    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    /**
     * Obtém o e-mail do usuário associado à conta.
     *
     * @return O e-mail do usuário
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Define o e-mail do usuário associado à conta.
     *
     * @param userEmail O e-mail do usuário
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public void setMovements(List<Movement> movements) {
        this.movements = movements;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }
} 