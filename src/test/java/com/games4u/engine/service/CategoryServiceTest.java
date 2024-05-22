package com.games4u.engine.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*; // Importa todos los métodos estáticos de Mockito

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach; // Importa la anotación @BeforeEach de JUnit 5
import org.junit.jupiter.api.Test; // Importa la anotación @Test de JUnit 5

import com.games4u.engine.model.Category;
import com.games4u.engine.repository.CategoryRepository;

import static org.junit.jupiter.api.Assertions.*; // Importa todos los métodos estáticos de JUnit 5 Assertions


public class CategoryServiceTest {
    private CategoryService categoryService;
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp(){
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void testDeleteById() {
   // Configurar el mock para que no haga nada cuando se llame deleteById
    doNothing().when(categoryRepository).deleteById(anyString());

    // Llamar al método deleteById del servicio
    categoryService.deleteById("1");

    // Verificar que el método deleteById del mock fue llamado con el parámetro correcto
    verify(categoryRepository).deleteById("1");
    }

    @Test
    void testFindAllCategories() {
    when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

    // Llamar al método findAllCategories del servicio
    List<Category> categories = categoryService.findAllCategories();

    // Verificar que la lista devuelta es vacía
    assertTrue(categories.isEmpty());

    // Verificar que el método findAll del mock fue llamado
    verify(categoryRepository).findAll();
    }

    @Test
    void testFindById() {
    // Configurar el mock para devolver un objeto Category cuando se llame findById
    Category category = new Category(null, null);
    when(categoryRepository.findById(anyString())).thenReturn(Optional.of(category));

    // Llamar al método findById del servicio
    Optional<Category> foundCategory = categoryService.findById("1");

    // Verificar que el objeto devuelto no está vacío
    assertTrue(foundCategory.isPresent());

    // Verificar que el método findById del mock fue llamado con el parámetro correcto
    verify(categoryRepository).findById("1");
    }

    @Test
    void testSave() {
        Category category = new Category(null, null);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // Llamar al método save del servicio
        Category savedCategory = categoryService.save(category);

        // Verificar que el objeto devuelto no es null
        assertNotNull(savedCategory);

        // Verificar que el método save del mock fue llamado con el objeto correcto
        verify(categoryRepository).save(category);

        assertEquals(savedCategory,category);
    }
}
