package com.umbell.repository;

import com.umbell.models.Goal;
import com.umbell.models.Account;
import com.umbell.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação da interface GoalRepository.
 * Esta classe fornece a implementação concreta dos métodos definidos na interface GoalRepository,
 * realizando operações de persistência para metas no banco de dados.
 *
 * @author Richard Satilite
 */
public class GoalRepositoryImpl implements GoalRepository {

    /**
     * Salva uma nova meta no banco de dados.
     *
     * @param goal A meta a ser salva
     * @return A meta salva com seu código gerado
     */
    @Override
    public Goal save(Goal goal) {
        String sql = "INSERT INTO Goal (title, target_amount, current_amount, achieved, account_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, goal.getTitle());
            stmt.setDouble(2, goal.getTargetAmount());
            stmt.setDouble(3, goal.getCurrentAmount());
            stmt.setBoolean(4, goal.isAchieved());
            stmt.setLong(5, goal.getAccount().getCode());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    goal.setId(rs.getLong(1));
                }
            }
            return goal;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar meta", e);
        }
    }

    /**
     * Busca uma meta pelo seu código.
     *
     * @param id O código da meta
     * @return A meta encontrada, ou null se não existir
     */
    @Override
    public Goal findById(Long id) {
        String sql = "SELECT * FROM Goal WHERE id = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToGoal(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar meta", e);
        }
        return null;
    }

    /**
     * Busca todas as metas associadas a uma conta pelo seu código.
     *
     * @param accountId O código da conta
     * @return Uma lista de metas associadas à conta
     */
    @Override
    public List<Goal> findByAccountId(Long accountId) {
        List<Goal> goals = new ArrayList<>();
        String sql = "SELECT * FROM Goal WHERE account_id = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    goals.add(mapResultSetToGoal(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar metas da conta", e);
        }
        return goals;
    }

    /**
     * Busca todas as metas associadas a um usuário pelo seu código.
     *
     * @param userId O código do usuário
     * @return Uma lista de metas associadas ao usuário
     */
    @Override
    public List<Goal> findByUserId(Long userId) {
        List<Goal> goals = new ArrayList<>();
        String sql = "SELECT g.*, a.totalBalance FROM Goal g " +
                    "JOIN Account a ON g.account_id = a.code " +
                    "WHERE a.user_email = (SELECT email FROM User WHERE id = ?)";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Goal goal = mapResultSetToGoal(rs);
                    // Atualiza o saldo total da conta
                    goal.getAccount().setTotalBalance(rs.getBigDecimal("totalBalance"));
                    goals.add(goal);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar metas do usuário", e);
        }
        return goals;
    }

    /**
     * Atualiza uma meta existente no banco de dados.
     *
     * @param goal A meta a ser atualizada
     */
    @Override
    public void update(Goal goal) {
        String sql = "UPDATE Goal SET title = ?, target_amount = ?, current_amount = ?, achieved = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, goal.getTitle());
            stmt.setDouble(2, goal.getTargetAmount());
            stmt.setDouble(3, goal.getCurrentAmount());
            stmt.setBoolean(4, goal.isAchieved());
            stmt.setLong(5, goal.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar meta", e);
        }
    }

    /**
     * Exclui uma meta do banco de dados.
     *
     * @param id O código da meta
     */
    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM Goal WHERE id = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar meta", e);
        }
    }

    /**
     * Converte um ResultSet em um objeto Goal.
     *
     * @param rs O ResultSet contendo os dados da meta
     * @return Um objeto Goal preenchido com os dados do ResultSet
     * @throws SQLException Se ocorrer um erro ao acessar os dados do ResultSet
     */
    private Goal mapResultSetToGoal(ResultSet rs) throws SQLException {
        Goal goal = new Goal();
        goal.setId(rs.getLong("id"));
        goal.setTitle(rs.getString("title"));
        goal.setTargetAmount(rs.getDouble("target_amount"));
        goal.setCurrentAmount(rs.getDouble("current_amount"));
        goal.setAchieved(rs.getBoolean("achieved"));

        // Carrega a conta associada à meta
        Account account = new Account();
        account.setCode(rs.getLong("account_id"));
        goal.setAccount(account);

        return goal;
    }
} 