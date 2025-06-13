package com.umbell.repository;

import com.umbell.models.Account;
import com.umbell.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {

    @Override
    public Account save(Account account) {
        String sql = "INSERT INTO Account (name, totalBalance, user_email) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, account.getName());
            stmt.setBigDecimal(2, account.getTotalBalance());
            stmt.setString(3, account.getUserEmail());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    account.setCode(rs.getLong(1));
                }
            }
            return account;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar conta", e);
        }
    }

    @Override
    public void update(Account account) {
        String sql = "UPDATE Account SET totalBalance = ? WHERE code = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, account.getTotalBalance());
            stmt.setLong(2, account.getCode());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar conta", e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM Account WHERE code = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar conta", e);
        }
    }

    @Override
    public Account findById(Long id) {
        String sql = "SELECT * FROM Account WHERE code = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAccount(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar conta", e);
        }
        return null;
    }

    @Override
    public List<Account> findByUserEmail(String userEmail) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM Account WHERE user_email = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userEmail);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    accounts.add(mapResultSetToAccount(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar contas do usuário", e);
        }
        return accounts;
    }

    private Account mapResultSetToAccount(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setCode(rs.getLong("code"));
        account.setUserEmail(rs.getString("user_email"));
        account.setTotalBalance(rs.getBigDecimal("totalBalance"));
        return account;
    }
} 