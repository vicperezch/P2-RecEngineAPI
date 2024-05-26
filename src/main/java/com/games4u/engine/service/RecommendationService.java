package com.games4u.engine.service;

import com.games4u.engine.model.*;
import com.games4u.engine.util.Sorter;
import com.games4u.engine.repository.GameRepository;
import com.games4u.engine.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author Victor Pérez
 * @since 20/05/2024
 */
@Service
public class RecommendationService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    @Value("${CLIENT-ID}")
    private String clientID;
    @Value("${AUTHORIZATION}")
    private String authorization;

    public RecommendationService(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }


    /**
     * Evalúa el usuario y selecciona el algoritmo a utilizar
     * @param email Email del usuario a recomendar
     * @return Lista de recomendaciones
     */
    public List<Game> recommend(String email, int numberOfRecommendations) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.getLikedGames() == null || user.getLikedGames().size() < 10) {
                return initialRecommendation(user, numberOfRecommendations);

            }

            return betterRecommendation(user, numberOfRecommendations);
        }

        return null;
    }


    /**
     * Recomienda juegos cuando el usuario no tiene suficientes datos
     * @param user Usuario a recomendar
     * @return Lista de recomendaciones
     */
    private List<Game> initialRecommendation(User user, int numberOfRecommendations) {
        Map<Game, Double> similarities = new HashMap<>();

        List<String> userGenres = user.getLikedGenres().stream().map(Genre::getName).toList();
        List<String> userCategories = user.getLikedCategories().stream().map(Category::getMode).toList();
        List<String> userPlatforms = user.getLikedPlatforms().stream().map(Platform::getName).toList();

        // Busca cada juego en la base de datos y compara con los gustos del usuario
        for (String genre: userGenres) {
            List<Game> gamesToCompare = gameRepository.findByGenreSorted(genre, user.getEmail());

            for (Game game : gamesToCompare) {

                double similarityScore = getSimilarity(game, userGenres, userCategories, userPlatforms);
                similarities.put(game, similarityScore);
            }
        }

        // Verifica que cumple con la cantidad solicitada
        if (similarities.keySet().size() < numberOfRecommendations) {
            List<Game> games = gameRepository.findGamesSortedByRating(user.getEmail());
            int count = 0;

            while (similarities.keySet().size() < numberOfRecommendations) {
                Game game = games.get(count);

                double similarityScore = getSimilarity(game, userGenres, userCategories, userPlatforms);
                similarities.put(game, similarityScore);

                count++;
            }
        }

        List<Game> recommendedGames = Sorter.sortByValue(similarities);

        return recommendedGames.subList(0, numberOfRecommendations);
    }


    /**
     * Obtiene una lista de juegos a recomendar
     * @param user Usuario al que se le recomendará
     */
    private List<Game> betterRecommendation(User user, int numberOfRecommendations) {
        List<Game> userLikedGames = user.getLikedGames();

        // Obtener las preferencias del usuario
        List<String> genres = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        List<String> platforms = new ArrayList<>();
        for (Game userLikedGame : userLikedGames) {
            genres.add(userLikedGame.getGenre().getName());
            categories.addAll(userLikedGame.getCategories().stream().map(Category::getMode).toList());
            platforms.addAll(userLikedGame.getPlatforms().stream().map(Platform::getName).toList());
        }

        genres = Sorter.getTopOccurrences(genres);
        categories = Sorter.getTopOccurrences(categories);
        platforms = Sorter.getTopOccurrences(platforms);

        // Calcula un coeficiente para cada juego
        Map<Game, Double> gameCoefficients = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            List<Game> gamesToCompare = gameRepository.findByGenreSorted(genres.get(i), user.getEmail());

            for (Game game: gamesToCompare) {

                double similarityScore = getSimilarity(game, genres, categories, platforms);
                gameCoefficients.put(game, similarityScore);
            }
        }

        // Verifica que cumple con la cantidad solicitada
        if (gameCoefficients.keySet().size() < numberOfRecommendations) {
            List<Game> games = gameRepository.findGamesSortedByRating(user.getEmail());
            int count = 0;

            while (gameCoefficients.keySet().size() < numberOfRecommendations) {
                Game game = games.get(count);

                double similarityScore = getSimilarity(game, genres, categories, platforms);
                gameCoefficients.put(game, similarityScore);

                count++;
            }
        }

        // Ordena los juegos según el coeficiente
        List<Game> recommendedGames = Sorter.sortByValue(gameCoefficients);

        return recommendedGames.subList(0, numberOfRecommendations);
    }


    /**
     * Determina la similaridad entre un juego y los gustos del usuario
     * @param game Juego a comparar
     * @param genres Géneros preferidos del usuario
     * @param categories Categorias preferidas del usuario
     * @param platforms Plataformas preferidas del usuario
     * @return coeficiente de similaridad de 0 a 1
     */
    private double getSimilarity(Game game, List<String> genres, List<String> categories, List<String> platforms) {
        double intersection = 0;
        double total = 1;

        if (genres.contains(game.getGenre().getName())) {
            intersection++;
        }

        for (Category category: game.getCategories()) {
            if (categories.contains(category.getMode())) {
                intersection++;
            }
        }

        for (Platform platform: game.getPlatforms()) {
            if (platforms.contains(platform.getName())) {
                intersection++;
            }
        }

        total += game.getCategories().size();
        total += game.getPlatforms().size();

        return intersection / total;
    }

    /**
     * Obtiene las covers de las recomendaciones
     * @param recommendedGames Juegos recomendados
     * @return Covers de los juegos recomendados
     */
    public Object[] recommendedGamesCovers(List<Game> recommendedGames) {
        String url = "https://api.igdb.com/v4/games";
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder names = new StringBuilder("(");

        for (int i = 0; i < recommendedGames.size(); i++) {
            names.append("\"" + recommendedGames.get(i).getName() + "\"");
            if (i < recommendedGames.size() - 1) {
                names.append(",");
            }
        }
        names.append(");");

        String apiQuery = "where name = " + names.toString() + "\nfields name,cover.url;" + "\nlimit 481;";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Client-ID", clientID);
        headers.set("Authorization", authorization);
   
        HttpEntity<String> request = new HttpEntity<>(apiQuery, headers);
        
        ResponseEntity<Object[]> response = restTemplate.exchange(url, HttpMethod.POST, request, Object[].class, HttpStatus.OK);
        return response.getBody();
    }
}
