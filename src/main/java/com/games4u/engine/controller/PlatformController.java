package com.games4u.engine.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.games4u.engine.model.Platform;
import com.games4u.engine.service.PlatformService;

/**
 * @author Diego Flores
 * @since 28/04/24
 * Controlador que contiene la funcionalidad CRUD de las plataformas
 */
@RestController
@RequestMapping("api/v1/platforms")
public class PlatformController {
    private final PlatformService platformService;

    /**
     * Constructor del controlador para las plataformas
     * @param genreService Servicio que maneja las plataformas
     */
    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }

    /**
     * Read: Devuelve todas las plataformas en la base de datos
     * @return Lista con todas las plataformas
     */
    @GetMapping("/")
    public ResponseEntity<List<Platform>> getAllCourses() {
        return new ResponseEntity<>(platformService.findAllGenres(), HttpStatus.OK);
    }

    /**
     * Read: Devuelve una plataforma en específico a través de su nombre
     * @param name Nombre de la plataforma que se busca
     * @return Nodo con la plataforma deseada
     */
    @GetMapping("/{id}")
    public ResponseEntity<Platform> getGenre(@PathVariable String id) {
        Optional<Platform> platform = platformService.findByName(id);

        if (platform.isPresent()) {
            return new ResponseEntity<>(platform.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    /**
     * Create: Agrega un nodo de tipo plataforma a la base de datos
     * @param plataforma Plataforma que sea desea agregar
     * @return Nodo guardado
     */
    @PostMapping("/add")
    public ResponseEntity<Platform> savePlatform(@RequestBody Platform platform) {
        Platform savedPlatform = platformService.save(platform);
        return new ResponseEntity<>(savedPlatform, HttpStatus.OK);
    }

    /**
     * Delete: Elimina un nodo en base al id
     * @param id Platform que se va a eliminar
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Platform> deleteGameById(@PathVariable String id) {
        Optional<Platform> platform = platformService.findByName(id);

        if (platform.isPresent()) {
            platformService.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
