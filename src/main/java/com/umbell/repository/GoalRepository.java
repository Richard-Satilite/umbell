package com.umbell.repository;

import com.umbell.models.Goal;
import java.util.List;

public interface GoalRepository {
    Goal save(Goal goal);
    Goal findById(Long id);
    List<Goal> findByAccountId(Long accountId);
    List<Goal> findByUserId(Long userId);
    void update(Goal goal);
    void delete(Long id);
} 