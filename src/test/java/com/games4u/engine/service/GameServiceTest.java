package com.games4u.engine.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*; // Importa todos los métodos estáticos de Mockito

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach; // Importa la anotación @BeforeEach de JUnit 5
import com.games4u.engine.model.Category;
import com.games4u.engine.model.Game;
import com.games4u.engine.repository.CategoryRepository;
import com.games4u.engine.repository.GameRepository;

import static org.junit.jupiter.api.Assertions.*; // Importa todos los métodos estáticos de JUnit 5 Assertions

public class GameServiceTest {
    @Mock
    private GameRepository gameRepository;

    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameRepository = mock(GameRepository.class);
        gameService = new GameService(gameRepository);
    }

    @Test
    void testDeleteByName() {
        doNothing().when(gameRepository).deleteById("Game1");

        gameService.deleteByName("Game1");

        verify(gameRepository, times(1)).deleteById("Game1");
    }

    @Test
    void testFindAllGames() {
        List<Game> games = Arrays.asList(new Game("Game1", null, null, null, null, null, null), new Game("Game2", null, null, null, null, null, null));
        when(gameRepository.findAll()).thenReturn(games);

        List<Game> foundGames = gameService.findAllGames();

        assertEquals(2, foundGames.size());
        assertEquals("Game1", foundGames.get(0).getName());
        assertEquals("Game2", foundGames.get(1).getName());
    }

    @Test
    void testFindByName() {
        Game game = new Game("Game1", null, null, null, null, null, null);
        when(gameRepository.findById("Game1")).thenReturn(Optional.of(game));

        Optional<Game> foundGame = gameService.findByName("Game1");

        assertTrue(foundGame.isPresent());
        assertEquals("Game1", foundGame.get().getName());
    }

    @Test
    void testSave() {
        Game game = new Game("NewGame", null, null, null, null, null, null);
        when(gameRepository.save(game)).thenReturn(game);

        Game savedGame = gameService.save(game);

        assertNotNull(savedGame);
        assertEquals("NewGame", savedGame.getName());
    }
}
