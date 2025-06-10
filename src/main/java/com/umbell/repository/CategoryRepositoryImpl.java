package com.umbell.repository;

import com.umbell.models.Category;
import com.umbell.models.Income;
import com.umbell.models.Expenses;
import com.umbell.models.Investment;
import com.umbell.utilities.DatabaseUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository {

    private final Connection connection;

    public CategoryRepositoryImpl() {
        this.connection = DatabaseUtil.getConnection();
    }

    @Override
    public Category save(Category category) {
        String sql = "INSERT INTO Category (name, monthLimit, categoryType) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, category.getName());
            if (category.getMonthLimit() != null) {
                stmt.setBigDecimal(2, category.getMonthLimit());
            } else {
                stmt.setNull(2, Types.DECIMAL);
            }
            stmt.setString(3, category.getCategoryType());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating category failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    category.setId(generatedKeys.getLong(1));
                    // Save specific type details
                    saveSpecificCategoryDetails(category);
                } else {
                    throw new SQLException("Creating category failed, no ID obtained.");
                }
            }
            return category;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveSpecificCategoryDetails(Category category) throws SQLException {
        String sql = "";
        if (category instanceof Income) {
            Income income = (Income) category;
            sql = "INSERT INTO Income (category_id, source, frequency, tax) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, income.getId());
                stmt.setString(2, income.getSource());
                stmt.setString(3, income.getFrequency());
                stmt.setDouble(4, income.getTax());
                stmt.executeUpdate();
            }
        } else if (category instanceof Expenses) {
            Expenses expenses = (Expenses) category;
            sql = "INSERT INTO Expenses (category_id, fixed, payMethod, vendor) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, expenses.getId());
                stmt.setBoolean(2, expenses.isFixed());
                stmt.setString(3, expenses.getPayMethod());
                stmt.setString(4, expenses.getVendor());
                stmt.executeUpdate();
            }
        } else if (category instanceof Investment) {
            Investment investment = (Investment) category;
            sql = "INSERT INTO Investment (category_id, currentValue, expectReturn, maturityDate, institution, investmentType) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, investment.getId());
                stmt.setBigDecimal(2, investment.getCurrentValue());
                stmt.setBigDecimal(3, investment.getExpectReturn());
                stmt.setString(4, investment.getMaturityDate() != null ? investment.getMaturityDate().toString() : null);
                stmt.setString(5, investment.getInstitution());
                stmt.setString(6, investment.getInvestmentType());
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public Category findById(Long id) {
        String sql = "SELECT * FROM Category WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCategory(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Category";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(mapResultSetToCategory(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public void update(Category category) {
        String sql = "UPDATE Category SET name = ?, monthLimit = ?, categoryType = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, category.getName());
            if (category.getMonthLimit() != null) {
                stmt.setBigDecimal(2, category.getMonthLimit());
            } else {
                stmt.setNull(2, Types.DECIMAL);
            }
            stmt.setString(3, category.getCategoryType());
            stmt.setLong(4, category.getId());
            stmt.executeUpdate();
            updateSpecificCategoryDetails(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateSpecificCategoryDetails(Category category) throws SQLException {
        String sql = "";
        if (category instanceof Income) {
            Income income = (Income) category;
            sql = "UPDATE Income SET source = ?, frequency = ?, tax = ? WHERE category_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, income.getSource());
                stmt.setString(2, income.getFrequency());
                stmt.setDouble(3, income.getTax());
                stmt.setLong(4, income.getId());
                stmt.executeUpdate();
            }
        } else if (category instanceof Expenses) {
            Expenses expenses = (Expenses) category;
            sql = "UPDATE Expenses SET fixed = ?, payMethod = ?, vendor = ? WHERE category_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setBoolean(1, expenses.isFixed());
                stmt.setString(2, expenses.getPayMethod());
                stmt.setString(3, expenses.getVendor());
                stmt.setLong(4, expenses.getId());
                stmt.executeUpdate();
            }
        } else if (category instanceof Investment) {
            Investment investment = (Investment) category;
            sql = "UPDATE Investment SET currentValue = ?, expectReturn = ?, maturityDate = ?, institution = ?, investmentType = ? WHERE category_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setBigDecimal(1, investment.getCurrentValue());
                stmt.setBigDecimal(2, investment.getExpectReturn());
                stmt.setString(3, investment.getMaturityDate() != null ? investment.getMaturityDate().toString() : null);
                stmt.setString(4, investment.getInstitution());
                stmt.setString(5, investment.getInvestmentType());
                stmt.setLong(6, investment.getId());
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM Category WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Category mapResultSetToCategory(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        String categoryType = rs.getString("categoryType");
        
        Category category = null;
        switch (categoryType) {
            case "Income":
                Income income = new Income();
                income.setId(id);
                income.setName(name);
                // Load specific fields for Income
                String incomeSql = "SELECT * FROM Income WHERE category_id = ?";
                try (PreparedStatement incomeStmt = connection.prepareStatement(incomeSql)) {
                    incomeStmt.setLong(1, id);
                    try (ResultSet incomeRs = incomeStmt.executeQuery()) {
                        if (incomeRs.next()) {
                            income.setSource(incomeRs.getString("source"));
                            income.setFrequency(incomeRs.getString("frequency"));
                            income.setTax(incomeRs.getDouble("tax"));
                        }
                    }
                }
                category = income;
                break;
            case "Expenses":
                Expenses expenses = new Expenses();
                expenses.setId(id);
                expenses.setName(name);
                // Load specific fields for Expenses
                String expensesSql = "SELECT * FROM Expenses WHERE category_id = ?";
                try (PreparedStatement expensesStmt = connection.prepareStatement(expensesSql)) {
                    expensesStmt.setLong(1, id);
                    try (ResultSet expensesRs = expensesStmt.executeQuery()) {
                        if (expensesRs.next()) {
                            expenses.setFixed(expensesRs.getBoolean("fixed"));
                            expenses.setPayMethod(expensesRs.getString("payMethod"));
                            expenses.setVendor(expensesRs.getString("vendor"));
                        }
                    }
                }
                category = expenses;
                break;
            case "Investment":
                Investment investment = new Investment();
                investment.setId(id);
                investment.setName(name);
                // Load specific fields for Investment
                String investmentSql = "SELECT * FROM Investment WHERE category_id = ?";
                try (PreparedStatement investmentStmt = connection.prepareStatement(investmentSql)) {
                    investmentStmt.setLong(1, id);
                    try (ResultSet investmentRs = investmentStmt.executeQuery()) {
                        if (investmentRs.next()) {
                            investment.setCurrentValue(investmentRs.getBigDecimal("currentValue"));
                            investment.setExpectReturn(investmentRs.getBigDecimal("expectReturn"));
                            String maturityDateStr = investmentRs.getString("maturityDate");
                            if (maturityDateStr != null) {
                                investment.setMaturityDate(LocalDate.parse(maturityDateStr));
                            }
                            investment.setInstitution(investmentRs.getString("institution"));
                            investment.setInvestmentType(investmentRs.getString("investmentType"));
                        }
                    }
                }
                category = investment;
                break;
            default:
                throw new IllegalArgumentException("Tipo de categoria desconhecido: " + categoryType);
        }
        return category;
    }
} 