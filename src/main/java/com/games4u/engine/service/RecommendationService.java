package com.games4u.engine.service;

import com.games4u.engine.model.*;
import com.games4u.engine.util.Sorter;
import com.games4u.engine.repository.GameRepository;
import com.games4u.engine.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public RecommendationService(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }


    /**
     * Obtiene una lista de juegos a recomendar
     * @param email Email del usuario a quien se le recomendará
     */
    public List<String> recommendGames(String email) {
        List<Game> userLikedGames = getUserLikedGames(email);

        // Obtiene los géneros principales del usuario
        List<String> genres = new ArrayList<>();
        for (Game userLikedGame : userLikedGames) {
            genres.add(userLikedGame.getGenre().getName());
        }

        genres = Sorter.getTopOccurrences(genres);

        // Calcula un coeficiente para cada juego
        Map<String, Double> gameCoefficients = new HashMap<>();
        for (Game game: userLikedGames) {
            for (int i = 0; i < 3; i++) {
                List<Game> gamesToCompare = gameRepository.findBySortedGenre(genres.get(i));
                for (Game game2 : gamesToCompare) {
                    double sim = getSimilarity(game, game2);
                    if (gameCoefficients.get(game2.getName()) == null || sim > gameCoefficients.get(game2.getName())) {
                        gameCoefficients.put(game2.getName(), sim);
                    }
                }
            }
        }

        // Ordena los juegos según el coeficiente
        List<String> recommendedGames = Sorter.sortByValue(gameCoefficients);
        for (Game userGame : userLikedGames) {
            recommendedGames.remove(userGame.getName());
        }

        return recommendedGames;
    }


    /**
     * Obtiene los juegos de un usuario
     * @param email Email del usuario
     * @return Lista de juegos que posee
     */
    private List<Game> getUserLikedGames(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            return user.getLikedGames();
        }

        return null;
    }


    /**
     * Determina la similaridad entre dos nodos a través de sus vecinos
     * @param game1 Primer juego a comparar
     * @param game2 Segundo juego a comparar
     * @return coeficiente de similaridad de 0 a 1
     */
    private double getSimilarity(Game game1, Game game2) {
        double intersection = 0;
        double union = 2;

        if (game1.getGenre().equals(game2.getGenre())) {
            intersection++;
            union--;
        }

        List<Platform> platforms1 = game1.getPlatforms();
        List<Platform> platforms2 = game2.getPlatforms();

        for (Platform platform : platforms1) {
            for (Platform platform2 : platforms2) {
                if (platform.getName().equals(platform2.getName())) {
                    intersection++;
                    union--;
                }
            }

            union++;
        }

        List<Category> categories1 = game1.getCategories();
        List<Category> categories2 = game2.getCategories();

        for (Category category : categories1) {
            for (Category category2 : categories2) {
                if (category.getMode().equals(category2.getMode())) {
                    intersection++;
                    union--;
                }
            }

            union++;
        }

        union += categories2.size();
        union += platforms2.size();

        return intersection / union;
    }
}
