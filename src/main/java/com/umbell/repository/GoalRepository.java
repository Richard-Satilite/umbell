package com.umbell.repository;

import com.umbell.models.Goal;
import java.util.List;

/**
 * Interface responsável por definir operações de persistência para metas financeiras.
 * Esta interface define métodos para salvar, atualizar, excluir e buscar metas no banco de dados.
 *
 * @author Richard Satilite
 */
public interface GoalRepository {

    /**
     * Salva uma nova meta no banco de dados.
     *
     * @param goal A meta a ser salva
     * @return A meta salva com seu código gerado
     */
    Goal save(Goal goal);

    /**
     * Atualiza uma meta existente no banco de dados.
     *
     * @param goal A meta a ser atualizada
     */
    void update(Goal goal);

    /**
     * Exclui uma meta do banco de dados.
     *
     * @param id O código da meta
     */
    void delete(Long id);

    /**
     * Busca uma meta pelo seu código.
     *
     * @param id O código da meta
     * @return A meta encontrada, ou null se não existir
     */
    Goal findById(Long id);

    /**
     * Busca todas as metas associadas a uma conta pelo código da conta.
     *
     * @param accountId O e-mail do usuário
     * @return Uma lista de metas associadas a conta
     */
    List<Goal> findByAccountId(Long accountId);
    
    /**
     * Busca todas as metas associadas a um usuário pelo seu id.
     *
     * @param userId O e-mail do usuário
     * @return Uma lista de metas associadas ao usuário
     */
    List<Goal> findByUserId(Long userId);
} 