package com.umbell.repository;

import com.umbell.models.Account;
import java.util.List;

/**
 * Interface responsável por definir operações de persistência para contas bancárias.
 * Esta interface define métodos para salvar, atualizar, excluir e buscar contas no banco de dados.
 *
 * @author Richard Satilite
 */
public interface AccountRepository {

    /**
     * Salva uma nova conta no banco de dados.
     *
     * @param account A conta a ser salva
     * @return A conta salva com seu código gerado
     */
    Account save(Account account);

    /**
     * Atualiza uma conta existente no banco de dados.
     *
     * @param account A conta a ser atualizada
     * @return A conta atualizada
     */
    void update(Account account);

    /**
     * Exclui uma conta do banco de dados.
     *
     * @param id O código da conta
     */
    void delete(Long id);

    /**
     * Busca uma conta pelo seu código.
     *
     * @param id O código da conta
     * @return A conta encontrada, ou null se não existir
     */
    Account findById(Long id);

    /**
     * Busca todas as contas associadas a um usuário pelo seu e-mail.
     *
     * @param userEmail O e-mail do usuário
     * @return Uma lista de contas associadas ao usuário
     */
    List<Account> findByUserEmail(String userEmail);
} 