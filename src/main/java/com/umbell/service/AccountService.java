package com.umbell.service;

import com.umbell.models.Account;
import com.umbell.models.Movement;
import com.umbell.repository.AccountRepository;
import com.umbell.repository.AccountRepositoryImpl;
import com.umbell.repository.MovementRepository;
import com.umbell.repository.MovementRepositoryImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * Serviço responsável por gerenciar operações relacionadas a contas e movimentações financeiras.
 * Atua como camada intermediária entre os controladores e os repositórios de dados.
 * 
 * @author Richard Satilite
 */
public class AccountService {

    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;

    /**
     * Construtor padrão que inicializa os repositórios com suas implementações padrão.
     */
    public AccountService() {
        this.accountRepository = new AccountRepositoryImpl();
        this.movementRepository = new MovementRepositoryImpl();
    }

    /**
     * Registra uma nova conta no sistema.
     * 
     * @param account a conta a ser registrada
     * @return a conta registrada com ID atribuído
     */
    public Account registerAccount(Account account) {
        return accountRepository.save(account);
    }

    /**
     * Adiciona uma movimentação à conta informada, atualizando o saldo total da conta.
     * 
     * @param account a conta à qual a movimentação será associada
     * @param movement a movimentação a ser adicionada
     * @return a conta atualizada
     * @throws IllegalArgumentException se a conta ou a movimentação forem nulas
     */
    public Account addMovement(Account account, Movement movement) {
        if (account == null || movement == null) {
            throw new IllegalArgumentException("Account and Movement cannot be null");
        }

        movement.setAccount(account);
        movementRepository.save(movement);
        account.getMovements().add(movement);

        BigDecimal newBalance = account.getTotalBalance().add(movement.getAmount());
        account.setTotalBalance(newBalance);
        accountRepository.update(account);

        return account;
    }

    /**
     * Busca uma conta pelo seu ID.
     * 
     * @param id o identificador da conta
     * @return a conta correspondente, ou {@code null} se não encontrada
     */
    public Account findById(Long id) {
        return accountRepository.findById(id);
    }

    /**
     * Busca todas as contas associadas a um e-mail de usuário.
     * 
     * @param userEmail o e-mail do usuário
     * @return uma lista de contas associadas ao e-mail informado
     */
    public List<Account> findByUserEmail(String userEmail) {
        return accountRepository.findByUserEmail(userEmail);
    }

    /**
     * Atualiza as informações de uma conta existente.
     * 
     * @param account a conta a ser atualizada
     */
    public void updateAccount(Account account) {
        accountRepository.update(account);
    }

    /**
     * Remove uma conta com base em seu identificador.
     * 
     * @param id o identificador da conta a ser removida
     */
    public void deleteAccount(Long id) {
        accountRepository.delete(id);
    }

    /**
     * Obtém o valor total de todas as movimentações da conta informada.
     * 
     * @param accountId o identificador da conta
     * @return o valor total das movimentações
     */
    public BigDecimal getTotalMovementsByAccountId(Long accountId) {
        return movementRepository.getTotalMovementsByAccountId(accountId);
    }

    /**
     * Obtém o valor total de despesas da conta informada.
     * 
     * @param accountId o identificador da conta
     * @return o valor total de despesas
     */
    public BigDecimal getTotalExpensesByAccountId(Long accountId) {
        return movementRepository.getTotalExpensesByAccountId(accountId);
    }

    /**
     * Obtém o valor total de receitas da conta informada.
     * 
     * @param accountId o identificador da conta
     * @return o valor total de receitas
     */
    public BigDecimal getTotalIncomesByAccountId(Long accountId) {
        return movementRepository.getTotalIncomesByAccountId(accountId);
    }

    /**
     * Obtém o valor total de investimentos da conta informada.
     * 
     * @param accountId o identificador da conta
     * @return o valor total de investimentos
     */
    public BigDecimal getTotalInvestmentsByAccountId(Long accountId) {
        return movementRepository.getTotalInvestmentsByAccountId(accountId);
    }
}