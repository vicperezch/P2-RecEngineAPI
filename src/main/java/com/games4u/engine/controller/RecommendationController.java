package com.games4u.engine.controller;

import com.games4u.engine.model.Game;
import com.games4u.engine.service.RecommendationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "https://games4u-54c04.web.app")
@RestController
@RequestMapping("api/v1/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Game>> getRecommendations(@PathVariable String email, @RequestParam int number) {
        List<Game> recommendations = recommendationService.recommend(email, number);

        if (recommendations == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(recommendations, HttpStatus.OK);
    }

    @PostMapping("/covers")
    public ResponseEntity<Object[]> getRecommendedGamesCovers(@RequestBody List<Game> recommendedGames){
        Object[] response = recommendationService.recommendedGamesCovers(recommendedGames);
        return new ResponseEntity<Object[]>(response, HttpStatus.OK);
    }
}
