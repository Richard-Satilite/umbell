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
        return accountRepository.save(account);
    }

    public Account addMovement(Account account, Movement movement) {
        if (account == null || movement == null) {
            throw new IllegalArgumentException("Account and Movement cannot be null");
        }

        // Set the account for the movement
        movement.setAccount(account);

        // Save the movement
        movementRepository.save(movement);
        account.getMovements().add(movement);

        // Update account balance based on movement amount
        BigDecimal newBalance = account.getTotalBalance().add(movement.getAmount());
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