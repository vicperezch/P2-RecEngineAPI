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
        MockitoAnnotations.openMocks(this);
        recommendationService = new RecommendationService(gameRepository, userRepository);
    }

    @Test
    void testRecommendForNonexistentUser() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        List<Game> recommendedGames = recommendationService.recommend("nonexistent@example.com",15);

        assertNull(recommendedGames);
    }


}
