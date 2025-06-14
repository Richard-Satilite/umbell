package com.umbell.models;

import java.time.LocalDateTime;

/**
 * Representa uma notificação no sistema.
 * Esta classe armazena informações sobre uma notificação, incluindo seu código, título, mensagem, data de criação, status de leitura e e-mail do usuário associado.
 *
 * @author Richard Satilite
 */
public class Notification {
    private Long id;
    private String name;
    private String message;
    private boolean read;
    private String userEmail;
    private LocalDateTime createdAt;

    public Notification(Long id, String name, String message, boolean read, String userEmail) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.read = read;
        this.userEmail = userEmail;
        this.createdAt = LocalDateTime.now();
    }

    public Notification(String name, String message, String userEmail) {
        this.name = name;
        this.message = message;
        this.read = false;
        this.userEmail = userEmail;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters

    /**
     * Obtém o código da notificação.
     *
     * @return O código da notificação
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o código da notificação.
     *
     * @param id O código da notificação
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém o título da notificação.
     *
     * @return O título da notificação
     */
    public String getName() {
        return name;
    }

    /**
     * Define o título da notificação.
     *
     * @param name O título da notificação
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtém a mensagem da notificação.
     *
     * @return A mensagem da notificação
     */
    public String getMessage() {
        return message;
    }

    /**
     * Define a mensagem da notificação.
     *
     * @param message A mensagem da notificação
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Verifica se a notificação foi lida.
     *
     * @return true se a notificação foi lida, false caso contrário
     */
    public boolean isRead() {
        return read;
    }

    /**
     * Define se a notificação foi lida.
     *
     * @param read true se a notificação foi lida, false caso contrário
     */
    public void setRead(boolean read) {
        this.read = read;
    }

    /**
     * Obtém o e-mail do usuário associado à notificação.
     *
     * @return O e-mail do usuário
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Define o e-mail do usuário associado à notificação.
     *
     * @param userEmail O e-mail do usuário
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * Obtém a data de criação da notificação.
     *
     * @return A data de criação da notificação
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Define a data de criação da notificação.
     *
     * @param createdAt A data de criação da notificação
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Notification{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", message='" + message + '\'' +
               ", read=" + read +
               ", userEmail='" + userEmail + '\'' +
               ", createdAt=" + createdAt +
               '}';
    }
} 