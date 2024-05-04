package com.games4u.engine.service;

import com.games4u.engine.model.Game;
import com.games4u.engine.repository.GameRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }

    public Optional<Game> findByName(String name) {
        return gameRepository.findById(name);
    }

    public Game save(Game game) {
        return gameRepository.save(game);
    }

    public void deleteByName(String name) {
        gameRepository.deleteById(name);
    }
}
