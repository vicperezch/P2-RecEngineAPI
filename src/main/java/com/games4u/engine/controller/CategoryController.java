package com.games4u.engine.controller;

import com.games4u.engine.model.Category;
import com.games4u.engine.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Renato Rojas
 * @since 28/04/2024
 * Controlador que contiene la funcionalidad CRUD para los juegos
 */

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    /**
     * Constructor del controlador para las categorías
     * @param categoryService Servicio que maneja las categorías
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    /**
     * Obtiene todas las categorías en la base de datos
     * @return Lista con todas las categorías almacenadas
     */
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCourses() {
        return new ResponseEntity<>(categoryService.findAllCategories(), HttpStatus.OK);
    }


    /**
     * Obtiene una categoría.
     * @param id ID de la categoría a obtener
     * @return Nodo con la categoría deseada
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable String id) {
        Optional<Category> category = categoryService.findById(id);

        if (category.isPresent()) {
            return new ResponseEntity<>(category.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    /**
     * Agrega una categoría a la base de datos
     * @param category Categoría a almacenar
     * @return Categoría almacenada
     */
    @PostMapping("/add")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        Category savedCategory = categoryService.save(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    /**
     * Elimina una categoría de la base de datos
     * @param id ID de la categoría a eliminar
     * @return Estado de la petición
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategoryById(@PathVariable String id) {
        Optional<Category> category = categoryService.findById(id);

        if (category.isPresent()) {
            categoryService.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
