package com.umbell.service;

import com.umbell.models.Account;
import com.umbell.repository.AccountRepository;
import com.umbell.repository.AccountRepositoryImpl;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService() {
        this.accountRepository = new AccountRepositoryImpl();
    }

    public Account registerAccount(Account account) {
        // Aqui você pode adicionar validações se necessário
        return accountRepository.save(account);
    }
} 