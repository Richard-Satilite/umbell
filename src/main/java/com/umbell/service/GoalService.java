package com.umbell.service;

import com.umbell.models.Goal;
import com.umbell.models.Account;
import com.umbell.repository.GoalRepository;
import com.umbell.repository.GoalRepositoryImpl;

import java.util.List;

public class GoalService {
    private final GoalRepository goalRepository;

    public GoalService() {
        this.goalRepository = new GoalRepositoryImpl();
    }

    public Goal createGoal(String title, double targetAmount, Account account) {
        Goal goal = new Goal(title, targetAmount, account);
        return goalRepository.save(goal);
    }

    public Goal findById(Long id) {
        return goalRepository.findById(id);
    }

    public List<Goal> findByAccountId(Long accountId) {
        return goalRepository.findByAccountId(accountId);
    }

    public List<Goal> findByUserId(Long userId) {
        return goalRepository.findByUserId(userId);
    }

    public void updateGoal(Goal goal) {
        goalRepository.update(goal);
    }

    public void deleteGoal(Long id) {
        goalRepository.delete(id);
    }

    /**
     * Verifica se a meta foi atingida com base no valor atual da conta.
     * Atualiza o progresso da meta e a salva no banco.
     * @param goal A meta a ser verificada.
     * @param currentAccountValue O valor atual da conta associada à meta.
     * @return true se a meta foi atingida, false caso contrário.
     */
    public boolean checkIfAchieved(Goal goal, double currentAccountValue) {
        if (goal == null) {
            throw new IllegalArgumentException("Meta não pode ser nula.");
        }

        // Atualiza o progresso da meta com o valor atual da conta
        goal.setCurrentAmount(currentAccountValue);
        goalRepository.update(goal); // Salva o progresso atualizado no banco

        // Verifica se o progresso atingiu ou superou o valor alvo
        return goal.getCurrentAmount() >= goal.getTargetAmount();
    }

    public void updateProgress(Goal goal, double amount) {
        goal.setCurrentAmount(goal.getCurrentAmount() + amount);
        goalRepository.update(goal);
    }
} 