package com.umbell.repository;

import com.umbell.models.Category;
import com.umbell.models.Movement;
import com.umbell.utilities.DatabaseUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MovementRepositoryImpl implements MovementRepository {

    private final Connection connection;
    private final CategoryRepository categoryRepository;

    public MovementRepositoryImpl() {
        this.connection = DatabaseUtil.getConnection();
        this.categoryRepository = new CategoryRepositoryImpl();
    }

    @Override
    public Movement save(Movement movement) {
        String sql = "INSERT INTO Movement (category_id, value, date, description, account_code) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, movement.getCategory().getId());
            stmt.setBigDecimal(2, movement.getValue());
            stmt.setString(3, movement.getDate().toString());
            stmt.setString(4, movement.getDescription());
            stmt.setLong(5, movement.getAccountCode());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating movement failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    movement.setCode(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating movement failed, no ID obtained.");
                }
            }
            return movement;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Movement findById(Long id) {
        String sql = "SELECT * FROM Movement WHERE code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMovement(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Movement> findByAccountId(Long accountId) {
        List<Movement> movements = new ArrayList<>();
        String sql = "SELECT * FROM Movement WHERE account_code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    movements.add(mapResultSetToMovement(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movements;
    }

    @Override
    public void update(Movement movement) {
        String sql = "UPDATE Movement SET category_id = ?, value = ?, date = ?, description = ?, account_code = ? WHERE code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, movement.getCategory().getId());
            stmt.setBigDecimal(2, movement.getValue());
            stmt.setString(3, movement.getDate().toString());
            stmt.setString(4, movement.getDescription());
            stmt.setLong(5, movement.getAccountCode());
            stmt.setLong(6, movement.getCode());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM Movement WHERE code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Movement mapResultSetToMovement(ResultSet rs) throws SQLException {
        Movement movement = new Movement();
        movement.setCode(rs.getLong("code"));
        movement.setValue(rs.getBigDecimal("value"));
        movement.setDate(LocalDate.parse(rs.getString("date")));
        movement.setDescription(rs.getString("description"));
        movement.setAccountCode(rs.getLong("account_code"));

        Long categoryId = rs.getLong("category_id");
        Category category = categoryRepository.findById(categoryId);
        movement.setCategory(category);
        return movement;
    }
} 