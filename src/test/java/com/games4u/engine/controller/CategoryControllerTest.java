package com.games4u.engine.controller;

import com.games4u.engine.model.Category;
import com.games4u.engine.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

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
        MockitoAnnotations.initMocks(this);
        category1 = new Category(null, null);
        category1.setId("1");
        category1.setName("Category1");

        category2 = new Category(null, null);
        category2.setId("2");
        category2.setName("Category2");
    }

    @Test
    void testGetAllCategories() throws Exception {
        List<Category> categories = Arrays.asList(category1, category2);
        when(categoryService.findAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Category1")))
                .andExpect(jsonPath("$[1].name", is("Category2")));
    }

    @Test
    void testGetCategorySuccess() throws Exception {
        when(categoryService.findById("1")).thenReturn(Optional.of(category1));

        mockMvc.perform(get("/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Category1")));
    }

    @Test
    void testGetCategoryNotFound() throws Exception {
        when(categoryService.findById("1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/{id}", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSaveCategory() throws Exception {
        when(categoryService.save(any(Category.class))).thenReturn(category1);

        mockMvc.perform(post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\",\"name\":\"Category1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Category1")));
    }

    @Test
    void testDeleteCategoryByIdSuccess() throws Exception {
        when(categoryService.findById("1")).thenReturn(Optional.of(category1));
        doNothing().when(categoryService).deleteById("1");

        mockMvc.perform(delete("/{id}", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteCategoryByIdNotFound() throws Exception {
        when(categoryService.findById("1")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/{id}", "1"))
                .andExpect(status().isNotFound());
    }
}
