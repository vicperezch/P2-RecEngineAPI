package com.games4u.engine.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.games4u.engine.model.Category;
import com.games4u.engine.repository.CategoryRepository;

/**
 * @author Renato Rojas
 * @since 28/04/24
 * Servicio que accede a los géneros de la base de datos
 */
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * Constructor del servicio
     * @param categoryRepository Repositorio donde se almacenan los géneros
     */
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Obtiene una lista de todas las categorías.
     *
     * @return Lista de categorías.
     */
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

     /**
     * Busca una categoría por su nombre.
     *
     * @param name El nombre de la categoría a buscar.
     * @return Categoría buscada.
     */
    public Optional<Category> findByName(String name) {
        return categoryRepository.findById(name);
    }

    /**
     * Guarda una categoría en la base de datos.
     *
     * @param category La categoría a guardar.
     * @return La categoría guardada.
     */
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

     /**
     * Elimina una categoría por su nombre.
     *
     * @param name El nombre de la categoría a eliminar.
     */
    public void deleteById(String name) {
        categoryRepository.deleteById(name);
    }
}
