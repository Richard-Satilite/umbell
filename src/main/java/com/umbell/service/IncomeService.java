package com.umbell.service;

import com.umbell.models.Income;

/**
 * Serviço responsável por operações relacionadas a categorias do tipo receita.
 * Estende {@link CategoryService} e permite aplicar validações específicas para {@link Income}.
 * 
 * @author Richard Satilite
 */
public class IncomeService extends CategoryService {

    /**
     * Construtor padrão que inicializa o serviço de receitas com a estrutura herdada de {@link CategoryService}.
     */
    public IncomeService() {
        super();
    }

    /**
     * Cria uma nova categoria de receita.
     * 
     * @param income a instância de receita a ser criada
     * @return a receita persistida
     */
    public Income createIncome(Income income) {
        // Add specific validation for Income if needed
        return (Income) createCategory(income);
    }

    /**
     * Atualiza uma categoria de receita existente.
     * 
     * @param income a receita com os dados atualizados
     */
    public void updateIncome(Income income) {
        // Add specific validation for Income if needed
        updateCategory(income);
    }
}