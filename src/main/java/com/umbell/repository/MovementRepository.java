package com.umbell.repository;

import com.umbell.models.Movement;
import java.util.List;

public interface MovementRepository {
    Movement save(Movement movement);
    Movement findById(Long id);
    List<Movement> findByAccountId(Long accountId);
    void update(Movement movement);
    void delete(Long id);
} 