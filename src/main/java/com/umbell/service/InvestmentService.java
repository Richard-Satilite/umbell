package com.umbell.service;

import com.umbell.models.Investment;

/**
 * Serviço responsável por operações relacionadas a categorias do tipo investimento.
 * Estende {@link CategoryService} e permite aplicar validações específicas para {@link Investment}.
 * 
 * @author Richard Satilite
 */
public class InvestmentService extends CategoryService {

    /**
     * Construtor padrão que inicializa o serviço de investimentos com a estrutura herdada de {@link CategoryService}.
     */
    public InvestmentService() {
        super();
    }

    /**
     * Cria uma nova categoria de investimento.
     * 
     * @param investment a instância de investimento a ser criada
     * @return o investimento persistido
     */
    public Investment createInvestment(Investment investment) {
        // Add specific validation for Investment if needed
        return (Investment) createCategory(investment);
    }

    /**
     * Atualiza uma categoria de investimento existente.
     * 
     * @param investment o investimento com os dados atualizados
     */
    public void updateInvestment(Investment investment) {
        // Add specific validation for Investment if needed
        updateCategory(investment);
    }
}