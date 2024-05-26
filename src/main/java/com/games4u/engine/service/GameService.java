package com.games4u.engine.service;

import com.games4u.engine.model.Game;
import com.games4u.engine.repository.GameRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private final GameRepository gameRepository;
    @Value("${CLIENT-ID}")
    private String clientID;
    @Value("${AUTHORIZATION}")
    private String authorization;

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

    public Object[] getGameCover(int start, int end) {
        String url = "https://api.igdb.com/v4/games";
        RestTemplate restTemplate = new RestTemplate();
        List<Game> allGames = findAllGames();
        StringBuilder names = new StringBuilder("(");

        for (int i = start; i < end; i++) {
            names.append("\"" + allGames.get(i).getName() + "\"");
            if (i < end - 1) {
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
