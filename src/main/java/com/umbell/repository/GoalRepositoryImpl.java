package com.umbell.repository;

import com.umbell.models.Goal;
import com.umbell.utilities.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GoalRepositoryImpl implements GoalRepository {
    private final Connection connection;

    public GoalRepositoryImpl() {
        this.connection = DatabaseUtil.getConnection();
    }

    @Override
    public Goal save(Goal goal) {
        String sql = "INSERT INTO Goal (title, target_amount, current_amount, achieved, created_at, account_id) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, goal.getTitle());
            stmt.setDouble(2, goal.getTargetAmount());
            stmt.setDouble(3, goal.getCurrentAmount());
            stmt.setBoolean(4, goal.isAchieved());
            stmt.setTimestamp(5, Timestamp.valueOf(goal.getCreatedAt()));
            stmt.setLong(6, goal.getAccount().getId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating goal failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    goal.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating goal failed, no ID obtained.");
                }
            }
            
            return goal;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Goal findById(Long id) {
        String sql = "SELECT * FROM Goal WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToGoal(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    @Override
    public List<Goal> findByAccountId(Long accountId) {
        String sql = "SELECT * FROM Goal WHERE account_id = ?";
        List<Goal> goals = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, accountId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    goals.add(mapResultSetToGoal(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return goals;
    }

    @Override
    public List<Goal> findByUserId(Long userId) {
        String sql = "SELECT g.* FROM Goal g " +
                    "JOIN Account a ON g.account_id = a.code " +
                    "WHERE a.user_email = (SELECT email FROM User WHERE id = ?)";
        List<Goal> goals = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    goals.add(mapResultSetToGoal(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return goals;
    }

    @Override
    public void update(Goal goal) {
        String sql = "UPDATE Goal SET title = ?, target_amount = ?, current_amount = ?, achieved = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, goal.getTitle());
            stmt.setDouble(2, goal.getTargetAmount());
            stmt.setDouble(3, goal.getCurrentAmount());
            stmt.setBoolean(4, goal.isAchieved());
            stmt.setLong(5, goal.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM Goal WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Goal mapResultSetToGoal(ResultSet rs) throws SQLException {
        Goal goal = new Goal();
        goal.setId(rs.getLong("id"));
        goal.setTitle(rs.getString("title"));
        goal.setTargetAmount(rs.getDouble("target_amount"));
        goal.setCurrentAmount(rs.getDouble("current_amount"));
        goal.setAchieved(rs.getBoolean("achieved"));
        goal.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        // The Account object cannot be fully mapped here without an AccountRepository dependency,
        // so it will remain null or require a separate fetch if needed later.
        return goal;
    }
} 