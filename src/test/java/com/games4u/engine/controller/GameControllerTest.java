package com.games4u.engine.controller;

import com.games4u.engine.controller.GameController;
import com.games4u.engine.model.Game;
import com.games4u.engine.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private RestTemplate restTemplate;

    private Game game1;
    private Game game2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        game1 = new Game("Game1", null, null, null, null, null, null);

        game2 = new Game("Game2", null, null, null, null, null, null);
    }



    @Test
    void testGetGameNotFound() throws Exception {
        when(gameService.findByName("Game1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/{id}", "Game1"))
                .andExpect(status().isNotFound());
    }


    @Test
    void testUpdateGameNotFound() throws Exception {
        when(gameService.findByName("Game1")).thenReturn(Optional.empty());

        mockMvc.perform(put("/{name}", "Game1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Game1\", \"rating\":\"9.5\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteGameByIdNotFound() throws Exception {
        when(gameService.findByName("Game1")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/{name}", "Game1"))
                .andExpect(status().isNotFound());
    }
}
