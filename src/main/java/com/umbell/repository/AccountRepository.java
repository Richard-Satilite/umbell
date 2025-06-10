package com.umbell.repository;

import com.umbell.models.Account;
import java.util.List;

public interface AccountRepository {
    Account save(Account account);
    void update(Account account);
    void delete(Long id);
    Account findById(Long id);
    List<Account> findByUserEmail(String userEmail);
} 