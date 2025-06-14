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

/**
 * Implementação da interface CategoryRepository.
 * Esta classe fornece a implementação concreta dos métodos definidos na interface CategoryRepository,
 * realizando operações de persistência para categorias no banco de dados.
 *
 * @author Richard Satilite
 */
public class CategoryRepositoryImpl implements CategoryRepository {

    /**
     * Salva uma nova categoria no banco de dados.
     *
     * @param category A categoria a ser salva
     * @return A categoria salva com seu código gerado
     */
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

    /**
     * Atualiza uma categoria existente no banco de dados.
     *
     * @param category A categoria a ser atualizada
     */
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

    /**
     * Exclui uma categoria do banco de dados.
     *
     * @param id O código da categoria
     */
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

    /**
     * Busca uma categoria pelo seu código.
     *
     * @param id O código da categoria
     * @return A categoria encontrada, ou null se não existir
     */
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

    /**
     * Busca todas as categorias no banco de dados.
     *
     * @return Uma lista de todas as categorias
     */
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