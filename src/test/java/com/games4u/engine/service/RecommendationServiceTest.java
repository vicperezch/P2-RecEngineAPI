package com.games4u.engine.service;

import com.games4u.engine.model.Game;
import com.games4u.engine.model.User;
import com.games4u.engine.model.Genre;
import com.games4u.engine.model.Category;
import com.games4u.engine.model.Platform;
import com.games4u.engine.repository.GameRepository;
import com.games4u.engine.repository.UserRepository;
import com.games4u.engine.service.RecommendationService;
import com.games4u.engine.util.Sorter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RecommendationServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private UserRepository userRepository;

    private RecommendationService recommendationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recommendationService = new RecommendationService(gameRepository, userRepository);
    }

    @Test
    void testRecommendWithInsufficientData() {
        User user = new User("user1@example.com", null, null, null, null, null, null, null, null);
        user.setLikedGames(Collections.emptyList());
        when(userRepository.findByEmail("user1@example.com")).thenReturn(Optional.of(user));

        List<Game> recommendedGames = recommendationService.recommend("user1@example.com");

        assertNotNull(recommendedGames);
        // Add assertions based on the expected behavior of initialRecommendation
    }

    @Test
    void testRecommendWithSufficientData() {
        User user = new User("user1@example.com", null, null, null, null, null, null, null, null);
        List<Game> likedGames = Arrays.asList(new Game("Game1", null, null, null, null, null, null), new Game("Game2", null, null, null, null, null, null), new Game("Game3", null, null, null, null, null, null),
                new Game("Game4", null, null, null, null, null, null), new Game("Game5", null, null, null, null, null, null), new Game("Game6", null, null, null, null, null, null),
                new Game("Game7", null, null, null, null, null, null), new Game("Game8", null, null, null, null, null, null), new Game("Game9", null, null, null, null, null, null), new Game("Game10", null, null, null, null, null, null));
        user.setLikedGames(likedGames);
        when(userRepository.findByEmail("user1@example.com")).thenReturn(Optional.of(user));

        List<Game> recommendedGames = recommendationService.recommend("user1@example.com");

        assertNotNull(recommendedGames);
    }

    @Test
    void testRecommendForNonexistentUser() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        List<Game> recommendedGames = recommendationService.recommend("nonexistent@example.com");

        assertNull(recommendedGames);
    }

    @Test
    void testInitialRecommendation() {
        User user = new User("user1@example.com", null, null, null, null, null, null, null, null);
        user.setLikedGenres(Arrays.asList(new Genre("Action", null)));
        user.setLikedCategories(Arrays.asList(new Category("Single-player", null)));
        user.setLikedPlatforms(Arrays.asList(new Platform("PC", null)));
        user.setGames(Collections.emptyList());

        List<String> gamesToCompare = Arrays.asList("Game1", "Game2");
        when(gameRepository.findByGenreSorted("Action")).thenReturn(gamesToCompare);
        when(gameRepository.findById("Game1")).thenReturn(Optional.of(new Game("Game1", null, null, null, null, null, null)));
        when(gameRepository.findById("Game2")).thenReturn(Optional.of(new Game("Game2", null, null, null, null, null, null)));

        List<Game> recommendedGames = recommendationService.initialRecommendation(user);

        assertNotNull(recommendedGames);
        assertTrue(recommendedGames.size() <= 14);
        // Add additional assertions based on the expected behavior
    }

    @Test
    void testBetterRecommendation() {
        User user = new User("user1@example.com", null, null, null, null, null, null, null, null);
        List<Game> likedGames = Arrays.asList(new Game("Game1", null, null, null, null, null, null), new Game("Game2", null, null, null, null, null, null), new Game("Game3", null, null, null, null, null, null));
        user.setLikedGames(likedGames);
        user.setGames(Collections.emptyList());

        List<String> gamesToCompare = Arrays.asList("Game4", "Game5");
        when(gameRepository.findByGenreSorted(anyString())).thenReturn(gamesToCompare);
        when(gameRepository.findById("Game4")).thenReturn(Optional.of(new Game("Game4", null, null, null, null, null, null)));
        when(gameRepository.findById("Game5")).thenReturn(Optional.of(new Game("Game5", null, null, null, null, null, null)));

        List<Game> recommendedGames = recommendationService.betterRecommendation(user);

        assertNotNull(recommendedGames);
        assertTrue(recommendedGames.size() <= 14);
        // Add additional assertions based on the expected behavior
    }
}
