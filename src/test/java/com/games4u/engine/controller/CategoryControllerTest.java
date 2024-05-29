package com.games4u.engine.controller;

import com.games4u.engine.model.Category;
import com.games4u.engine.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    private Category category1;
    private Category category2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category1 = new Category("1", null);
        category1.setMode("Category1");

        category2 = new Category("2", null);
        category2.setMode("Category2");
    }


    @Test
    void testGetCategoryNotFound() throws Exception {
        when(categoryService.findById("1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/{id}", "1"))
                .andExpect(status().isNotFound());
    }


    @Test
    void testDeleteCategoryByIdNotFound() throws Exception {
        when(categoryService.findById("1")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/{id}", "1"))
                .andExpect(status().isNotFound());
    }
}
