package com.umbell.service;

import com.umbell.models.Category;
import com.umbell.repository.CategoryRepository;
import com.umbell.repository.CategoryRepositoryImpl;

import java.util.List;

/**
 * Serviço abstrato responsável por operações relacionadas a categorias.
 * Fornece funcionalidades comuns para criação, recuperação, atualização e exclusão de categorias.
 * Pode ser estendido por serviços mais específicos (como categorias de despesas ou receitas).
 * 
 * @author Richard Satilite
 */
public abstract class CategoryService {

    protected final CategoryRepository categoryRepository;

    /**
     * Construtor padrão que inicializa o repositório com sua implementação concreta.
     */
    public CategoryService() {
        this.categoryRepository = new CategoryRepositoryImpl();
    }

    /**
     * Cria uma nova categoria.
     * 
     * @param category a categoria a ser criada
     * @return a categoria persistida com ID atribuído
     */
    public Category createCategory(Category category) {
        // Add common validation logic here if needed
        return categoryRepository.save(category);
    }

    /**
     * Busca uma categoria pelo seu identificador.
     * 
     * @param id o identificador da categoria
     * @return a categoria correspondente, ou {@code null} se não encontrada
     */
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    /**
     * Retorna todas as categorias disponíveis.
     * 
     * @return uma lista de todas as categorias
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Atualiza uma categoria existente.
     * 
     * @param category a categoria com os dados atualizados
     */
    public void updateCategory(Category category) {
        // Add common validation logic here if needed
        categoryRepository.update(category);
    }

    /**
     * Remove uma categoria com base em seu identificador.
     * 
     * @param id o identificador da categoria a ser removida
     */
    public void deleteCategory(Long id) {
        categoryRepository.delete(id);
    }
}