package com.umbell.repository;

import com.umbell.models.Account;
import com.umbell.models.Movement;
import java.util.List;

public interface MovementRepository {
    void save(Movement movement);
    void update(Movement movement);
    void delete(Long id);
    Movement findById(Long id);
    List<Movement> findByAccount(Account account);
    List<Movement> findByAccountId(Long accountId);
} 