package com.umbell.repository;

import com.umbell.models.Category;
import java.util.List;

/**
 * Interface responsável por definir operações de persistência para categorias.
 * Esta interface define métodos para salvar, atualizar, excluir e buscar categorias no banco de dados.
 *
 * @author Richard Satilite
 */
public interface CategoryRepository {

    /**
     * Salva uma nova categoria no banco de dados.
     *
     * @param category A categoria a ser salva
     * @return A categoria salva com seu código gerado
     */
    Category save(Category category);

    /**
     * Atualiza uma categoria existente no banco de dados.
     *
     * @param category A categoria a ser atualizada
     * @return A categoria atualizada
     */
    void update(Category category);

    /**
     * Exclui uma categoria do banco de dados.
     *
     * @param id O código da categoria
     */
    void delete(Long id);

    /**
     * Busca uma categoria pelo seu código.
     *
     * @param id O código da categoria
     * @return A categoria encontrada, ou null se não existir
     */
    Category findById(Long id);

    /**
     * Busca todas as categorias
     *
     * @return Uma lista de categorias do tipo especificado
     */
    List<Category> findAll();
} 