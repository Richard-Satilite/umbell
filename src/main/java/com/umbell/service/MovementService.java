package com.umbell.service;

import com.umbell.models.Account;
import com.umbell.models.Movement;
import com.umbell.repository.MovementRepository;
import com.umbell.repository.MovementRepositoryImpl;
import java.util.List;

public class MovementService {
    private final MovementRepository movementRepository;

    public MovementService() {
        this.movementRepository = new MovementRepositoryImpl();
    }

    public List<Movement> findByAccount(Account account) {
        return movementRepository.findByAccount(account);
    }

    public void save(Movement movement) {
        movementRepository.save(movement);
    }

    public void update(Movement movement) {
        movementRepository.update(movement);
    }

    public void delete(Long id) {
        movementRepository.delete(id);
    }

    public Movement findById(Long id) {
        return movementRepository.findById(id);
    }

    public List<Movement> findByAccountId(Long accountId) {
        return movementRepository.findByAccountId(accountId);
    }
} 