package com.umbell.repository;

import com.umbell.models.Account;
import com.umbell.models.Movement;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface responsável pela definição das operações de persistência
 * relacionadas às movimentações financeiras.
 * 
 * @author Richard Satilite
 */
public interface MovementRepository {

    /**
     * Salva uma nova movimentação.
     * 
     * @param movement a movimentação a ser salva
     */
    void save(Movement movement);

    /**
     * Atualiza uma movimentação existente.
     * 
     * @param movement a movimentação com os dados atualizados
     */
    void update(Movement movement);

    /**
     * Remove uma movimentação com base no ID.
     * 
     * @param id o identificador da movimentação a ser removida
     */
    void delete(Long id);

    /**
     * Busca uma movimentação pelo seu ID.
     * 
     * @param id o identificador da movimentação
     * @return a movimentação correspondente, ou {@code null} se não encontrada
     */
    Movement findById(Long id);

    /**
     * Retorna todas as movimentações associadas a uma conta.
     * 
     * @param account a conta a ser consultada
     * @return uma lista de movimentações da conta
     */
    List<Movement> findByAccount(Account account);

    /**
     * Retorna todas as movimentações associadas ao ID de uma conta.
     * 
     * @param accountId o identificador da conta
     * @return uma lista de movimentações da conta
     */
    List<Movement> findByAccountId(Long accountId);

    /**
     * Retorna o valor total de todas as movimentações de uma conta.
     * 
     * @param accountId o identificador da conta
     * @return o valor total das movimentações
     */
    BigDecimal getTotalMovementsByAccountId(Long accountId);

    /**
     * Retorna o valor total de investimentos de uma conta.
     * 
     * @param accountId o identificador da conta
     * @return o valor total de investimentos
     */
    BigDecimal getTotalInvestmentsByAccountId(Long accountId);

    /**
     * Retorna o valor total de despesas de uma conta.
     * 
     * @param accountId o identificador da conta
     * @return o valor total de despesas
     */
    BigDecimal getTotalExpensesByAccountId(Long accountId);

    /**
     * Retorna o valor total de receitas de uma conta.
     * 
     * @param accountId o identificador da conta
     * @return o valor total de receitas
     */
    BigDecimal getTotalIncomesByAccountId(Long accountId);
}