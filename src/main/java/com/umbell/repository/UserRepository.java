package com.umbell.repository;

import com.umbell.models.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    
    /**
     * Salva um novo usuário no banco de dados
     * @param user O usuário a ser salvo
     * @return O usuário salvo com ID gerado
     */
    User save(User user);
    
    /**
     * Busca um usuário pelo ID
     * @param id O ID do usuário
     * @return Optional contendo o usuário se encontrado
     */
    Optional<User> findById(Long id);
    
    /**
     * Busca um usuário pelo email
     * @param email O email do usuário
     * @return Optional contendo o usuário se encontrado
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Lista todos os usuários
     * @return Lista de todos os usuários
     */
    List<User> findAll();
    
    /**
     * Atualiza um usuário existente
     * @param user O usuário com os dados atualizados
     * @return O usuário atualizado
     */
    User update(User user);
    
    /**
     * Remove um usuário pelo ID
     * @param id O ID do usuário a ser removido
     * @return true se o usuário foi removido, false caso contrário
     */
    boolean delete(Long id);
    
    /**
     * Verifica se existe um usuário com o email informado
     * @param email O email a ser verificado
     * @return true se o email já existe, false caso contrário
     */
    boolean existsByEmail(String email);
} 