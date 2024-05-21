package com.games4u.engine.controller;

import com.games4u.engine.model.Game;
import com.games4u.engine.service.GameService;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;


/**
 * @author Victor Pérez
 * @since 26/04/2024
 * Controlador que contiene la funcionalidad CRUD para los juegos
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1/games")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    /**
     * Obtiene todos los juegos en la base de datos
     * @return Lista con todos los juegos almacenados
     */
    @GetMapping("/all")
    public ResponseEntity<List<Game>> getAllGames() {
        return new ResponseEntity<>(gameService.findAllGames(), HttpStatus.OK);
    }


    /**
     * Obtiene un juego
     * @param id ID del juego a obtener
     * @return Nodo con el juego deseado
     */
    @GetMapping("/{id}")
    public ResponseEntity<Game> getGame(@PathVariable String id) {
        Optional<Game> game = gameService.findByName(id);

        if (game.isPresent()) {
            return new ResponseEntity<>(game.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    /**
     * Agrega un juego a la base de datos
     * @param game Juego a almacenar
     * @return Juego almacenado
     */
    @PostMapping("/add")
    public ResponseEntity<Game> saveGame(@RequestBody Game game) {
        Game savedGame = gameService.save(game);
        return new ResponseEntity<>(savedGame, HttpStatus.CREATED);
    }


    /**
     * Actualiza un juego en la base de datos
     * @param name ID (nombre) del juego a actualizar
     * @param updatedGame Instancia con los atributos nuevos
     * @return Juego actualizado
     */
    @PutMapping("/{name}")
    public ResponseEntity<Game> updateGame(@PathVariable String name, @RequestBody Game updatedGame) {
        Optional<Game> optionalGame = gameService.findByName(name);

        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();

            game.setDevelopers(updatedGame.getDevelopers());
            game.setReleased(updatedGame.getReleased());
            game.setRating(updatedGame.getRating());
            game.setCategories(updatedGame.getCategories());
            game.setGenre(updatedGame.getGenre());
            game.setPlatforms(updatedGame.getPlatforms());

            return new ResponseEntity<>(gameService.save(game), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    /**
     * Elimina un juego de la base de datos
     * @param name ID del juego a eliminar
     * @return Estado de la petición
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Game> deleteGameById(@PathVariable String name) {
        Optional<Game> game = gameService.findByName(name);

        if (game.isPresent()) {
            gameService.deleteByName(name);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
     
    /**
     * Devuelve los covers de los juegos que poseen uno
     * @return Arreglo de juegos con sus covers
     */
    @GetMapping("/covers")
    public ResponseEntity<Object[]> getCovers() {
        String url = "https://api.igdb.com/v4/games";
        RestTemplate restTemplate = new RestTemplate();
        List<Game> allGames = gameService.findAllGames();
        StringBuilder names = new StringBuilder("(");

        for (int i = 0; i < allGames.size(); i++) {
            names.append("\"" + allGames.get(i).getName() + "\"");
            if (i < allGames.size() - 1) {
                names.append(",");
            }
        }
        names.append(");");

        String apiQuery = "where name = " + names.toString() + "\nfields name,cover.url;" + "\nlimit 500;";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Client-ID", "");
        headers.set("Authorization", "");
   
        HttpEntity<String> request = new HttpEntity<>(apiQuery, headers);
        
        ResponseEntity<Object[]> response = restTemplate.exchange(url, HttpMethod.POST, request, Object[].class, HttpStatus.OK);
        return response;
    }
}
