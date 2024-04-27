package com.games4u.engine.controller;

import com.games4u.engine.model.Game;
import com.games4u.engine.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Victor Pérez
 * @since 26/04/2024
 * Controlador que contiene la funcionalidad CRUD para los juegos
 */
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
    @GetMapping("/")
    public ResponseEntity<List<Game>> getAllCourses() {
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

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    /**
     * Agrega un juego a la base de datos
     * @param game Juego a almacenar
     * @return Juego almacenado
     */
    @PostMapping("/add")
    public ResponseEntity<Game> saveGame(@RequestBody Game game) {
        Game savedGame = gameService.save(game);
        return new ResponseEntity<>(savedGame, HttpStatus.OK);
    }


    /**
     * Actualiza un juego en la base de datos
     * @param id ID del juego a actualizar
     * @param updatedGame Instancia con los atributos nuevos
     * @return Juego actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable String id, @RequestBody Game updatedGame) {
        Optional<Game> oldGame = gameService.findByName(id);

        if (oldGame.isPresent()) {
            Game game = oldGame.get();

            game.setAchievements(updatedGame.getAchievements());
            game.setDevelopers(updatedGame.getDevelopers());
            game.setReleased(updatedGame.getReleased());

            return new ResponseEntity<>(gameService.save(game), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }


    /**
     * Elimina un juego de la base de datos
     * @param id ID del juego a eliminar
     * @return Estado de la petición
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Game> deleteGameById(@PathVariable String id) {
        Optional<Game> game = gameService.findByName(id);

        if (game.isPresent()) {
            gameService.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
