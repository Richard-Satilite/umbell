package com.umbell.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um usuário no sistema.
 * Esta classe armazena informações sobre um usuário, incluindo seu código, nome, e-mail, senha e data de criação.
 *
 * @author Richard Satilite
 */
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private boolean notify;
    private LocalDateTime createdAt;
    private List<Account> accounts;

    public User() {
        this.accounts = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }

    public User(String name, String email, String password) {
        this();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * Obtém o código do usuário.
     *
     * @return O código do usuário
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o código do usuário.
     *
     * @param id O código do usuário
     */
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
} 