package com.umbell.service;

import com.umbell.models.Account;
import com.umbell.models.Movement;
import com.umbell.repository.MovementRepository;
import com.umbell.repository.MovementRepositoryImpl;
import java.util.List;

/**
 * Serviço responsável pelas operações relacionadas às movimentações financeiras (movements).
 * Realiza ações como salvar, atualizar, remover e buscar movimentações por conta ou ID.
 * 
 * @author Richard Satilite
 */
public class MovementService {

    private final MovementRepository movementRepository;

    /**
     * Construtor padrão que inicializa o repositório de movimentações com a implementação padrão.
     */
    public MovementService() {
        this.movementRepository = new MovementRepositoryImpl();
    }

    /**
     * Busca todas as movimentações associadas a uma conta.
     * 
     * @param account a conta cujas movimentações devem ser buscadas
     * @return uma lista de movimentações da conta
     */
    public List<Movement> findByAccount(Account account) {
        return movementRepository.findByAccount(account);
    }

    /**
     * Salva uma nova movimentação.
     * 
     * @param movement a movimentação a ser salva
     */
    public void save(Movement movement) {
        movementRepository.save(movement);
    }

    /**
     * Atualiza uma movimentação existente.
     * 
     * @param movement a movimentação com os dados atualizados
     */
    public void update(Movement movement) {
        movementRepository.update(movement);
    }

    /**
     * Remove uma movimentação com base no seu identificador.
     * 
     * @param id o ID da movimentação a ser removida
     */
    public void delete(Long id) {
        movementRepository.delete(id);
    }

    /**
     * Busca uma movimentação pelo seu identificador.
     * 
     * @param id o ID da movimentação
     * @return a movimentação encontrada, ou {@code null} se não existir
     */
    public Movement findById(Long id) {
        return movementRepository.findById(id);
    }

    /**
     * Busca todas as movimentações associadas a um determinado ID de conta.
     * 
     * @param accountId o ID da conta
     * @return uma lista de movimentações da conta
     */
    public List<Movement> findByAccountId(Long accountId) {
        return movementRepository.findByAccountId(accountId);
    }
}