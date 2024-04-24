package com.games4u.engine.service;

import com.games4u.engine.model.Game;
import com.games4u.engine.repository.GameRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }
}
