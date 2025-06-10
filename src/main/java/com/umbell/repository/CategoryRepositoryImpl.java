package com.umbell.repository;

import com.umbell.models.Category;
import com.umbell.models.Income;
import com.umbell.models.Expenses;
import com.umbell.models.Investment;
import com.umbell.utils.DatabaseUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository {

    @Override
    public Category save(Category category) {
        String sql = "INSERT INTO Category (name, monthLimit, categoryType) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, category.getName());
            if (category.getMonthLimit() != null) {
                stmt.setBigDecimal(2, category.getMonthLimit());
            } else {
                stmt.setNull(2, Types.DECIMAL);
            }
            stmt.setString(3, category.getCategoryType());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    category.setId(rs.getLong(1));
                }
            }
            return category;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar categoria", e);
        }
    }

    @Override
    public void update(Category category) {
        String sql = "UPDATE Category SET name = ?, monthLimit = ?, categoryType = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            if (category.getMonthLimit() != null) {
                stmt.setBigDecimal(2, category.getMonthLimit());
            } else {
                stmt.setNull(2, Types.DECIMAL);
            }
            stmt.setString(3, category.getCategoryType());
            stmt.setLong(4, category.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar categoria", e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM Category WHERE id = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar categoria", e);
        }
    }

    @Override
    public Category findById(Long id) {
        String sql = "SELECT * FROM Category WHERE id = ?";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCategory(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar categoria", e);
        }
        return null;
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Category";
        try (Connection conn = DatabaseUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(mapResultSetToCategory(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todas as categorias", e);
        }
        return categories;
    }

    private Category mapResultSetToCategory(ResultSet rs) throws SQLException {
        Category category = new Category() {};
        category.setId(rs.getLong("id"));
        category.setName(rs.getString("name"));
        category.setMonthLimit(rs.getBigDecimal("monthLimit"));
        category.setCategoryType(rs.getString("categoryType"));
        return category;
    }
} 