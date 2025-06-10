package com.umbell.repository;

import com.umbell.models.Account;
import com.umbell.models.Movement;
import com.umbell.models.Goal;
import com.umbell.utilities.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {

    private final Connection connection;
    private final MovementRepository movementRepository;
    private final GoalRepository goalRepository;

    public AccountRepositoryImpl() {
        this.connection = DatabaseUtil.getConnection();
        this.movementRepository = new MovementRepositoryImpl();
        this.goalRepository = new GoalRepositoryImpl();
    }

    @Override
    public Account save(Account account) {
        String sql = "INSERT INTO Account (totalBalance, user_email, created_at) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setBigDecimal(1, account.getTotalBalance());
            stmt.setString(2, account.getUserEmail());
            stmt.setTimestamp(3, Timestamp.valueOf(account.getCreatedAt()));
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating account failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    account.setCode(generatedKeys.getLong(1));
                    account.setId(generatedKeys.getLong(1)); // Also set the 'id' field
                } else {
                    throw new SQLException("Creating account failed, no ID obtained.");
                }
            }
            return account;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Account findById(Long id) {
        String sql = "SELECT * FROM Account WHERE code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Account account = mapResultSetToAccount(rs);
                    account.setMovements(movementRepository.findByAccountId(account.getCode()));
                    account.setGoals(goalRepository.findByAccountId(account.getCode()));
                    return account;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> findByUserEmail(String userEmail) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM Account WHERE user_email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userEmail);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Account account = mapResultSetToAccount(rs);
                    account.setMovements(movementRepository.findByAccountId(account.getCode()));
                    account.setGoals(goalRepository.findByAccountId(account.getCode()));
                    accounts.add(account);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public void update(Account account) {
        String sql = "UPDATE Account SET totalBalance = ?, user_email = ? WHERE code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBigDecimal(1, account.getTotalBalance());
            stmt.setString(2, account.getUserEmail());
            stmt.setLong(3, account.getCode());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM Account WHERE code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Account mapResultSetToAccount(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setCode(rs.getLong("code"));
        account.setId(rs.getLong("code")); // Also set the 'id' field
        account.setTotalBalance(rs.getBigDecimal("totalBalance"));
        account.setUserEmail(rs.getString("user_email"));
        account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return account;
    }
} 