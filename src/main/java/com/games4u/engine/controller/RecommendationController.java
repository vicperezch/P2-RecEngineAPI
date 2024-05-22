package com.games4u.engine.controller;

import com.games4u.engine.model.Game;
import com.games4u.engine.service.RecommendationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }


    @GetMapping("/{email}")
    public ResponseEntity<List<Game>> getRecommendations(@PathVariable String email) {
        List<Game> recommendations = recommendationService.recommend(email);

        if (recommendations == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(recommendations, HttpStatus.OK);
    }
}
