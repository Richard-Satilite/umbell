package com.umbell.models;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Representa um movimento financeiro no sistema.
 * Esta classe armazena informações sobre um movimento, incluindo seu código, descrição, valor, data, notas, tipo e conta associada.
 *
 * @author Richard Satilite
 */
public class Movement {
    private Long id;
    private Account account;
    private String type;
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private String notes;

    /**
     * Obtém o código do movimento.
     *
     * @return O código do movimento
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o código do movimento.
     *
     * @param id O código do movimento
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém a conta associada ao movimento.
     *
     * @return A conta associada ao movimento
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Define a conta associada ao movimento.
     *
     * @param account A conta associada ao movimento
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Obtém o tipo do movimento.
     *
     * @return O tipo do movimento
     */
    public String getType() {
        return type;
    }

    /**
     * Define o tipo do movimento.
     *
     * @param type O tipo do movimento
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Obtém a descrição do movimento.
     *
     * @return A descrição do movimento
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define a descrição do movimento.
     *
     * @param description A descrição do movimento
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtém o valor do movimento.
     *
     * @return O valor do movimento
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Define o valor do movimento.
     *
     * @param amount O valor do movimento
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Obtém a data do movimento.
     *
     * @return A data do movimento
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Define a data do movimento.
     *
     * @param date A data do movimento
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Obtém as notas do movimento.
     *
     * @return As notas do movimento
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Define as notas do movimento.
     *
     * @param notes As notas do movimento
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
} 