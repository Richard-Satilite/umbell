package com.umbell.repository;

import com.umbell.models.Account;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    Account save(Account account);
    
    Optional<Account> findByCode(Long code);
    
    List<Account> findAllByUserEmail(String userEmail);
    
    Account update(Account account);
    
    boolean delete(Long code);
} 