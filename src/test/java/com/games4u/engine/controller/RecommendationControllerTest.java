package com.games4u.engine.controller;

import com.games4u.engine.controller.RecommendationController;
import com.games4u.engine.model.Game;
import com.games4u.engine.service.RecommendationService;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@WebMvcTest(RecommendationController.class)
public class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendationService recommendationService;

    private Game game1;
    private Game game2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        game1 = new Game("Game1", null, null, null, null, null, null);

        game2 = new Game("Game2", null, null, null, null, null, null);
    }

    @Test
    void testGetRecommendationsSuccess() throws Exception {
        List<Game> recommendations = Arrays.asList(game1, game2);
        when(recommendationService.recommend(anyString())).thenReturn(recommendations);

        mockMvc.perform(get("/{email}", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Game1")))
                .andExpect(jsonPath("$[1].name", is("Game2")));
    }

    @Test
    void testGetRecommendationsNotFound() throws Exception {
        when(recommendationService.recommend(anyString())).thenReturn(null);

        mockMvc.perform(get("/{email}", "test@example.com"))
                .andExpect(status().isNotFound());
    }
}
