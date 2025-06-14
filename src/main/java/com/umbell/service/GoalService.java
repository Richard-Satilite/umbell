package com.umbell.service;

import com.umbell.models.Goal;
import com.umbell.models.Account;
import com.umbell.repository.GoalRepository;
import com.umbell.repository.GoalRepositoryImpl;

import java.util.List;

/**
 * Serviço responsável por gerenciar operações relacionadas a metas financeiras (Goals).
 * Permite criar, buscar, atualizar, deletar e verificar o progresso de metas vinculadas a contas de usuário.
 * 
 * @author Richard Satilite
 */
public class GoalService {

    private final GoalRepository goalRepository;

    /**
     * Construtor padrão que inicializa o repositório de metas com a implementação padrão.
     */
    public GoalService() {
        this.goalRepository = new GoalRepositoryImpl();
    }

    /**
     * Cria uma nova meta financeira associada a uma conta.
     *
     * @param title o título da meta
     * @param targetAmount o valor que se deseja atingir
     * @param account a conta associada à meta
     * @return a meta criada e persistida
     */
    public Goal createGoal(String title, double targetAmount, Account account) {
        Goal goal = new Goal(title, targetAmount, account);
        return goalRepository.save(goal);
    }

    /**
     * Busca uma meta pelo seu identificador.
     *
     * @param id o ID da meta
     * @return a meta correspondente, ou {@code null} se não for encontrada
     */
    public Goal findById(Long id) {
        return goalRepository.findById(id);
    }

    /**
     * Busca todas as metas associadas a uma conta específica.
     *
     * @param accountId o ID da conta
     * @return uma lista de metas associadas à conta
     */
    public List<Goal> findByAccountId(Long accountId) {
        return goalRepository.findByAccountId(accountId);
    }

    /**
     * Busca todas as metas pertencentes a um usuário específico.
     *
     * @param userId o ID do usuário
     * @return uma lista de metas do usuário
     */
    public List<Goal> findByUserId(Long userId) {
        return goalRepository.findByUserId(userId);
    }

    /**
     * Atualiza os dados de uma meta existente.
     *
     * @param goal a meta com os dados atualizados
     */
    public void updateGoal(Goal goal) {
        goalRepository.update(goal);
    }

    /**
     * Remove uma meta com base no seu identificador.
     *
     * @param id o ID da meta a ser removida
     */
    public void deleteGoal(Long id) {
        goalRepository.delete(id);
    }

    /**
     * Verifica se a meta foi atingida com base no valor atual da conta.
     * Atualiza o progresso da meta e a salva no banco de dados.
     *
     * @param goal a meta a ser verificada
     * @param currentAccountValue o valor atual da conta vinculada
     * @return {@code true} se a meta foi atingida, {@code false} caso contrário
     * @throws IllegalArgumentException se a meta for {@code null}
     */
    public boolean checkIfAchieved(Goal goal, double currentAccountValue) {
        if (goal == null) {
            throw new IllegalArgumentException("Meta não pode ser nula.");
        }

        goal.setCurrentAmount(currentAccountValue);
        goalRepository.update(goal);

        return goal.getCurrentAmount() >= goal.getTargetAmount();
    }

    /**
     * Atualiza o progresso de uma meta somando um valor ao total atual.
     *
     * @param goal a meta cujo progresso será atualizado
     * @param amount o valor a ser adicionado ao progresso atual
     */
    public void updateProgress(Goal goal, double amount) {
        goal.setCurrentAmount(goal.getCurrentAmount() + amount);
        goalRepository.update(goal);
    }
}