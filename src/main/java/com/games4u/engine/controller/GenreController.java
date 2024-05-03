package com.games4u.engine.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.games4u.engine.model.Genre;
import com.games4u.engine.service.GenreService;

/**
 * @author Nils Muralles
 * @since 28/04/24
 * Controlador que contiene la funcionalidad CURD de los géneros
 */
@RestController
@RequestMapping("api/v1/genres")
public class GenreController {
    private final GenreService genreService;

    /**
     * Constructor del controlador para los géneros
     * @param genreService Servicio que maneja los géneros
     */
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    /**
     * Read: Devuelve todos los géneros existentes en la base de datos
     * @return Lista con todos los géneros
     */
    @GetMapping("/")
    public ResponseEntity<List<Genre>> getAllCourses() {
        return new ResponseEntity<>(genreService.findAllGenres(), HttpStatus.OK);
    }

    /**
     * Read: Devuelve un género en específico a través de su nombre
     * @param id ID del género que se busca
     * @return Nodo con el género deseado
     */
    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenre(@PathVariable String id) {
        Optional<Genre> genre = genreService.findById(id);

        if (genre.isPresent()) {
            return new ResponseEntity<>(genre.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    /**
     * Create: Agrega un nodo de tipo género a la base de datos
     * @param genre Género que sea desea agregar
     * @return Nodo guardado
     */
    @PostMapping("/add")
    public ResponseEntity<Genre> saveGenre(@RequestBody Genre genre) {
        Genre savedGenre = genreService.save(genre);
        return new ResponseEntity<>(savedGenre, HttpStatus.CREATED);
    }

    /**
     * Delete: Elimina un nodo en base al id
     * @param id Género que se va a eliminar
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Genre> deleteGameById(@PathVariable String id) {
        Optional<Genre> genre = genreService.findById(id);

        if (genre.isPresent()) {
            genreService.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

}
