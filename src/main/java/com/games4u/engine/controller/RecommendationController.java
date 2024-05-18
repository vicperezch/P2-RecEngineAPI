package com.games4u.engine.controller;

import com.games4u.engine.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }


    @GetMapping("/")
    public void getSim(@RequestParam String email) {

        recommendationService.recommendGames(email);
    }
}
