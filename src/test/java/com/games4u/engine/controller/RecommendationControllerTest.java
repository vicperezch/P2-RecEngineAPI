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
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        MockitoAnnotations.openMocks(this);
        game1 = new Game("Game1", null, null, null, null, null, null);

        game2 = new Game("Game2", null, null, null, null, null, null);
    }

   
    @Test
    void testGetRecommendationsNotFound() throws Exception {
        when(recommendationService.recommend(anyString())).thenReturn(null);

        mockMvc.perform(get("/{email}", "test@example.com"))
                .andExpect(status().isNotFound());
    }
}
