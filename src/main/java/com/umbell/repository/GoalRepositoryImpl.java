package com.umbell.repository;

import com.umbell.models.Goal;
import com.umbell.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoalRepositoryImpl implements GoalRepository {

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

    @Override
    public List<Goal> findByUserId(Long userId) {
        List<Goal> goals = new ArrayList<>();
        String sql = "SELECT g.* FROM Goal g JOIN Account a ON g.account_id = a.code WHERE a.user_email = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    goals.add(mapResultSetToGoal(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar metas do usuário", e);
        }
        return goals;
    }

    private Goal mapResultSetToGoal(ResultSet rs) throws SQLException {
        Goal goal = new Goal();
        goal.setId(rs.getLong("id"));
        goal.setTitle(rs.getString("title"));
        goal.setTargetAmount(rs.getDouble("target_amount"));
        goal.setCurrentAmount(rs.getDouble("current_amount"));
        goal.setAchieved(rs.getBoolean("achieved"));
        return goal;
    }
} 