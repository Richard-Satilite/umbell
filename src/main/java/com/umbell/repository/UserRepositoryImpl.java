package com.umbell.repository;

import com.umbell.models.User;
import com.umbell.models.Account;
import com.umbell.utils.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public User save(User user) {
        String sql = "INSERT INTO User (name, email, password_hash) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                }
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar usuário", e);
        }
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE User SET name = ?, email = ?, password_hash = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setLong(4, user.getId());
            
            stmt.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM User WHERE id = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário", e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM User WHERE id = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM User WHERE email = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por email", e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos os usuários", e);
        }
        return users;
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM User WHERE email = ?";
        
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            
            return false;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência de email", e);
        }
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password_hash"));
        return user;
    }

    private List<Account> loadUserAccounts(String userEmail) {
        String sql = "SELECT * FROM Account WHERE user_email = ?";
        List<Account> accounts = new ArrayList<>();
        
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userEmail);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Account account = new Account();
                    account.setCode(rs.getLong("code"));
                    account.setTotalBalance(rs.getBigDecimal("totalBalance"));
                    account.setUserEmail(rs.getString("user_email"));
                    account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    accounts.add(account);
                }
            }
            
            return accounts;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao carregar contas do usuário", e);
        }
    }
} 