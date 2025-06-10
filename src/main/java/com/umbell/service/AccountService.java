package com.umbell.service;

import com.umbell.models.Account;
import com.umbell.models.Movement;
import com.umbell.repository.AccountRepository;
import com.umbell.repository.AccountRepositoryImpl;
import com.umbell.repository.MovementRepository;
import com.umbell.repository.MovementRepositoryImpl;

import java.math.BigDecimal;
import java.util.List;

public class AccountService {
    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;

    public AccountService() {
        this.accountRepository = new AccountRepositoryImpl();
        this.movementRepository = new MovementRepositoryImpl();
    }

    public Account registerAccount(Account account) {
        // Aqui você pode adicionar validações se necessário
        return accountRepository.save(account);
    }

    public Account addMovement(Account account, Movement movement) {
        if (account == null || movement == null) {
            throw new IllegalArgumentException("Account and Movement cannot be null");
        }

        // Set the account code for the movement
        movement.setAccountCode(account.getCode());

        // Save the movement
        Movement savedMovement = movementRepository.save(movement);
        account.getMovements().add(savedMovement);

        // Update account balance based on movement value
        BigDecimal newBalance = account.getTotalBalance().add(movement.getValue());
        account.setTotalBalance(newBalance);
        accountRepository.update(account);

        return account;
    }

    public Account findById(Long id) {
        return accountRepository.findById(id);
    }

    public List<Account> findByUserEmail(String userEmail) {
        return accountRepository.findByUserEmail(userEmail);
    }

    public void updateAccount(Account account) {
        accountRepository.update(account);
    }

    public void deleteAccount(Long id) {
        accountRepository.delete(id);
    }
} 