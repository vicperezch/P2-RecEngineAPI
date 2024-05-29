package com.games4u.engine.controller;

import com.games4u.engine.controller.GenreController;
import com.games4u.engine.model.Genre;
import com.games4u.engine.service.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    private Genre genre1;
    private Genre genre2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        genre1 = new Genre("1", null);
        genre1.setName("Action");

        genre2 = new Genre("2", null);
        genre2.setName("Adventure");
    }

  


    @Test
    void testGetGenreNotFound() throws Exception {
        when(genreService.findById("1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/{id}", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteGenreByIdNotFound() throws Exception {
        when(genreService.findById("1")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/{id}", "1"))
                .andExpect(status().isNotFound());
    }
}
