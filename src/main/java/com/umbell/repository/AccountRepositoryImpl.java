package com.umbell.repository;

import com.umbell.models.Account;
import com.umbell.utils.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação da interface AccountRepository.
 * Esta classe fornece a implementação concreta dos métodos definidos na interface AccountRepository,
 * realizando operações de persistência para contas bancárias no banco de dados.
 *
 * @author Richard Satilite
 */
public class AccountRepositoryImpl implements AccountRepository {

    /**
     * Salva uma nova conta no banco de dados.
     *
     * @param account A conta a ser salva
     * @return A conta salva com seu código gerado
     */
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

    /**
     * Atualiza uma conta existente no banco de dados.
     *
     * @param account A conta a ser atualizada
     * @return A conta atualizada
     */
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

    /**
     * Exclui uma conta do banco de dados.
     *
     * @param id O código da conta
     */
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

    /**
     * Busca uma conta pelo seu código.
     *
     * @param id O código da conta
     * @return A conta encontrada, ou null se não existir
     */
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

    /**
     * Busca todas as contas associadas a um usuário pelo seu e-mail.
     *
     * @param userEmail O e-mail do usuário
     * @return Uma lista de contas associadas ao usuário
     */
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