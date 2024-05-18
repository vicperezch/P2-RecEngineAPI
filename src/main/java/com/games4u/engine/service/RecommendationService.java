package com.games4u.engine.service;

import com.games4u.engine.model.Category;
import com.games4u.engine.model.Game;
import com.games4u.engine.model.Platform;
import com.games4u.engine.model.User;
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


    public void recommendGames(String email) {
        List<Game> userLikedGames = getUserLikedGames(email);
        Map<String, Integer> frequencies = new HashMap<>();

        for (Game game: userLikedGames) {
            Integer j = frequencies.get(game.getGenre().getName());
            if (j == null) {
                frequencies.put(game.getGenre().getName(), 1);

            } else {
                frequencies.put(game.getGenre().getName(), j + 1);
            }
        }

        PriorityQueue<String> heap = new PriorityQueue<>((a, b) -> frequencies.get(b) - frequencies.get(a));
        heap.addAll(frequencies.keySet());

        List<String> topGenres = new ArrayList<>();
        for (int i = 0; i < 3 && !heap.isEmpty(); i++) {
            topGenres.add(heap.poll());
        }

        List<Game> gamesWithTopGenre1 = gameRepository.findBySortedGenre(topGenres.get(0));
        List<Game> gamesWithTopGenre2 = gameRepository.findBySortedGenre(topGenres.get(1));
        List<Game> gamesWithTopGenre3 = gameRepository.findBySortedGenre(topGenres.get(2));

        for (Game game: userLikedGames) {
            for (Game game2: gamesWithTopGenre1) {
                getSimilarity(game, game2);
            }
        }
    }


    /**
     * Obtiene los juegos de un usuario
     * @param email Email del usuario
     * @return Lista de juegos que posee
     */
    public List<Game> getUserLikedGames(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            return user.getLikedGames();
        }

        return null;
    }


    public double getSimilarity(Game game1, Game game2) {
        double intersection = 0;
        double union = 1;

        if (game1.getGenre().equals(game2.getGenre())) {
            intersection++;
        }

        List<Platform> platforms1 = game1.getPlatforms();
        List<Platform> platforms2 = game2.getPlatforms();

        for (Platform platform : platforms1) {
            for (Platform platform2 : platforms2) {
                if (platform.getName().equals(platform2.getName())) {
                    intersection++;
                }
            }
        }

        List<Category> categories1 = game1.getCategories();
        List<Category> categories2 = game2.getCategories();

        for (Category category : categories1) {
            for (Category category2 : categories2) {
                if (category.equals(category2)) {
                    intersection++;
                    System.out.println("si");
                }
            }
        }

        union += platforms1.size();
        union += categories2.size();
        union += platforms2.size();
        union += categories1.size();

        return intersection / union;
    }
}
