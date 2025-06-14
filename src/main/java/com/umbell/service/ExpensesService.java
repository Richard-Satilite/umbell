package com.umbell.service;

import com.umbell.models.Expenses;

/**
 * Serviço responsável por operações relacionadas a categorias do tipo despesas.
 * Estende a classe {@link CategoryService} e permite aplicar validações específicas para {@link Expenses}.
 * 
 * @author Richard Satilite
 */
public class ExpensesService extends CategoryService {

    /**
     * Construtor padrão que inicializa o serviço de despesas com a estrutura herdada de {@link CategoryService}.
     */
    public ExpensesService() {
        super();
    }

    /**
     * Cria uma nova categoria de despesa.
     * 
     * @param expenses a instância de despesa a ser criada
     * @return a despesa persistida
     */
    public Expenses createExpenses(Expenses expenses) {
        // Add specific validation for Expenses if needed
        return (Expenses) createCategory(expenses);
    }

    /**
     * Atualiza uma categoria de despesa existente.
     * 
     * @param expenses a despesa com os dados atualizados
     */
    public void updateExpenses(Expenses expenses) {
        // Add specific validation for Expenses if needed
        updateCategory(expenses);
    }
}