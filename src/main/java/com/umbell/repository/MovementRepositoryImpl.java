package com.umbell.repository;

import com.umbell.models.Account;
import com.umbell.models.Movement;
import com.umbell.utils.DatabaseUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação da interface MovementRepository.
 * Esta classe fornece a implementação concreta dos métodos definidos na interface MovementRepository,
 * realizando operações de persistência para movimentações no banco de dados.
 *
 * @author Richard Satilite
 */
public class MovementRepositoryImpl implements MovementRepository {

    /**
     * Salva uma nova movimentação.
     *
     * @param movement a movimentação a ser salva
     */
    @Override
    public void save(Movement movement) {
        String sql = "INSERT INTO Movement (category, value, date, description, account_code) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, movement.getType());
            stmt.setBigDecimal(2, movement.getAmount());
            stmt.setString(3, movement.getDate().toString());
            stmt.setString(4, movement.getDescription());
            stmt.setLong(5, movement.getAccount().getCode());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    movement.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar movimento", e);
        }
    }

    /**
     * Atualiza uma movimentação existente.
     *
     * @param movement a movimentação com os dados atualizados
     */
    @Override
    public void update(Movement movement) {
        String sql = "UPDATE Movement SET category = ?, value = ?, date = ?, description = ? WHERE code = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, movement.getType());
            stmt.setBigDecimal(2, movement.getAmount());
            stmt.setString(3, movement.getDate().toString());
            stmt.setString(4, movement.getDescription());
            stmt.setLong(5, movement.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar movimento", e);
        }
    }

    /**
     * Remove uma movimentação com base no ID.
     *
     * @param id o identificador da movimentação a ser removida
     */
    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM Movement WHERE code = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar movimento", e);
        }
    }

    /**
     * Busca uma movimentação pelo seu ID.
     *
     * @param id o identificador da movimentação
     * @return a movimentação correspondente, ou {@code null} se não encontrada
     */
    @Override
    public Movement findById(Long id) {
        String sql = "SELECT * FROM Movement WHERE code = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMovement(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar movimento", e);
        }
        return null;
    }

    /**
     * Retorna todas as movimentações associadas a uma conta.
     *
     * @param account a conta a ser consultada
     * @return uma lista de movimentações da conta
     */
    @Override
    public List<Movement> findByAccount(Account account) {
        List<Movement> movements = new ArrayList<>();
        String sql = "SELECT * FROM Movement WHERE account_code = ? ORDER BY date DESC";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, account.getCode());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Movement movement = mapResultSetToMovement(rs);
                    movement.setAccount(account);
                    movements.add(movement);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar movimentos da conta", e);
        }
        return movements;
    }

    /**
     * Retorna todas as movimentações associadas ao ID de uma conta.
     *
     * @param accountId o identificador da conta
     * @return uma lista de movimentações da conta
     */
    @Override
    public List<Movement> findByAccountId(Long accountId) {
        List<Movement> movements = new ArrayList<>();
        String sql = "SELECT * FROM Movement WHERE account_code = ? ORDER BY date DESC";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    movements.add(mapResultSetToMovement(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar movimentos da conta", e);
        }
        return movements;
    }

    /**
     * Retorna o valor total de todas as movimentações de uma conta.
     *
     * @param accountId o identificador da conta
     * @return o valor total das movimentações
     */
    @Override
    public BigDecimal getTotalMovementsByAccountId(Long accountId) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        String sql = "SELECT SUM(value) FROM Movement WHERE account_code = ?";
        try(Connection conn = DatabaseUtil.connect(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, accountId);
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    totalAmount = rs.getBigDecimal(1);
                }
            }
        } catch(SQLException e){
            throw new RuntimeException("Erro ao buscar a soma das movimentacoes da conta", e);
        }

        return totalAmount;
    }

    /**
     * Retorna o valor total de despesas de uma conta.
     *
     * @param accountId o identificador da conta
     * @return o valor total de despesas
     */
    @Override
    public BigDecimal getTotalExpensesByAccountId(Long accountId) {
        BigDecimal totalExpenses = BigDecimal.ZERO;
        String sql = "SELECT SUM(value) FROM Movement WHERE account_code = ? AND category = 'Expense'";
        try(Connection conn = DatabaseUtil.connect(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, accountId);
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    totalExpenses = rs.getBigDecimal(1);
                }
            }
        } catch(SQLException e){
            throw new RuntimeException("Erro ao buscar a soma dos gastos da conta", e);
        }

        return totalExpenses;
    }

    /**
     * Retorna o valor total de receitas de uma conta.
     *
     * @param accountId o identificador da conta
     * @return o valor total de receitas
     */
    @Override
    public BigDecimal getTotalIncomesByAccountId(Long accountId) {
        BigDecimal totalIncomes = BigDecimal.ZERO;
        String sql = "SELECT SUM(value) FROM Movement WHERE account_code = ? AND category = 'Income'";
        try(Connection conn = DatabaseUtil.connect(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, accountId);
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    totalIncomes = rs.getBigDecimal(1);
                }
            }
        } catch(SQLException e){
            throw new RuntimeException("Erro ao buscar a soma dos ganhos da conta", e);
        }

        return totalIncomes;
    }

    /**
     * Retorna o valor total de investimentos de uma conta.
     *
     * @param accountId o identificador da conta
     * @return o valor total de investimentos
     */
    @Override
    public BigDecimal getTotalInvestmentsByAccountId(Long accountId) {
        BigDecimal totalInvestments = BigDecimal.ZERO;
        String sql = "SELECT SUM(value) FROM Movement WHERE account_code = ? AND category = 'Investment'";
        try(Connection conn = DatabaseUtil.connect(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, accountId);
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    totalInvestments = rs.getBigDecimal(1);
                }
            }
        } catch(SQLException e){
            throw new RuntimeException("Erro ao buscar a soma dos investimentos da conta", e);
        }

        return totalInvestments;
    }

    /**
     * Converte um ResultSet em um objeto Movement.
     *
     * @param rs O ResultSet contendo os dados da movimentação
     * @return Um objeto Movement preenchido com os dados do ResultSet
     * @throws SQLException Se ocorrer um erro ao acessar os dados do ResultSet
     */
    private Movement mapResultSetToMovement(ResultSet rs) throws SQLException {
        Movement movement = new Movement();
        movement.setId(rs.getLong("code"));
        movement.setAmount(rs.getBigDecimal("value"));
        movement.setDate(LocalDate.parse(rs.getString("date")));
        movement.setDescription(rs.getString("description"));
        movement.setType(rs.getString("category"));
        return movement;
    }
} 