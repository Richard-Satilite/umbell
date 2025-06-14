package com.umbell.repository;

import com.umbell.models.Notification;
import java.util.List;
import java.util.Optional;

/**
 * Interface responsável pelas operações de persistência e recuperação
 * de notificações associadas aos usuários do sistema.
 * 
 * @author Richard Satilite
 */
public interface NotificationRepository {

    /**
     * Salva uma nova notificação.
     * 
     * @param notification a notificação a ser salva
     * @return a notificação salva, possivelmente com ID gerado
     */
    Notification save(Notification notification);

    /**
     * Busca uma notificação pelo seu ID.
     * 
     * @param id o identificador da notificação
     * @return um {@link Optional} contendo a notificação, se encontrada
     */
    Optional<Notification> findById(Long id);

    /**
     * Retorna todas as notificações associadas a um usuário pelo seu ID.
     * 
     * @param userId o identificador do usuário
     * @return uma lista de notificações do usuário
     */
    List<Notification> findByUserId(Long userId);

    /**
     * Retorna todas as notificações não lidas associadas a um usuário pelo seu ID.
     * 
     * @param userId o identificador do usuário
     * @return uma lista de notificações não lidas
     */
    List<Notification> findUnreadByUserId(Long userId);

    /**
     * Atualiza uma notificação existente.
     * 
     * @param notification a notificação com os dados atualizados
     */
    void update(Notification notification);

    /**
     * Remove uma notificação com base no seu ID.
     * 
     * @param id o identificador da notificação a ser removida
     */
    void delete(Long id);

    /**
     * Marca uma notificação como lida.
     * 
     * @param id o identificador da notificação a ser marcada como lida
     */
    void markAsRead(Long id);

    /**
     * Retorna todas as notificações associadas ao e-mail de um usuário.
     * 
     * @param userEmail o e-mail do usuário
     * @return uma lista de notificações associadas ao e-mail
     */
    List<Notification> findByUserEmail(String userEmail);

    /**
     * Retorna todas as notificações não lidas associadas ao e-mail de um usuário.
     * 
     * @param userEmail o e-mail do usuário
     * @return uma lista de notificações não lidas associadas ao e-mail
     */
    List<Notification> findUnreadByUserEmail(String userEmail);
}