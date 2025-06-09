package com.umbell.repository;

import com.umbell.models.Account;

import java.sql.*;
import java.util.*;

public class AccountRepositoryImpl implements AccountRepository {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/database/umbell.db";

    @Override
    public Account save(Account account) {
        String sql = "INSERT INTO Account (totalBalance, user_email, created_at) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setBigDecimal(1, account.getTotalBalance());
            stmt.setString(2, account.getUserEmail());
            stmt.setTimestamp(3, Timestamp.valueOf(account.getCreatedAt()));
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                account.setCode(rs.getLong(1));
            }
            return account;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar conta: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Account> findByCode(Long code) {
        String sql = "SELECT * FROM Account WHERE code = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSetToAccount(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar conta: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Account> findAllByUserEmail(String userEmail) {
        String sql = "SELECT * FROM Account WHERE user_email = ?";
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userEmail);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                accounts.add(mapResultSetToAccount(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar contas do usuário: " + e.getMessage(), e);
        }
        return accounts;
    }

    @Override
    public Account update(Account account) {
        String sql = "UPDATE Account SET totalBalance = ?, user_email = ? WHERE code = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, account.getTotalBalance());
            stmt.setString(2, account.getUserEmail());
            stmt.setLong(3, account.getCode());
            stmt.executeUpdate();
            return account;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar conta: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(Long code) {
        String sql = "DELETE FROM Account WHERE code = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, code);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar conta: " + e.getMessage(), e);
        }
    }

    private Account mapResultSetToAccount(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setCode(rs.getLong("code"));
        account.setTotalBalance(rs.getBigDecimal("totalBalance"));
        account.setUserEmail(rs.getString("user_email"));
        account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return account;
    }
} 