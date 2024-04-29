package com.games4u.engine.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.games4u.engine.model.User;
import com.games4u.engine.service.UserService;

/**
 * @author Juan Solís
 * @since 28/04/24
 * Controlador que contiene la funcionalidad CURD de los usuarios
 */
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    /**
     * Constructor del controlador para los usuarios
     * @param userService Servicio que maneja los usuarios
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Read: Devuelve todos los usuarios existentes en la base de datos
     * @return Lista con todos los usuarios
     */
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllCourses() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    /**
     * Read: Devuelve un usuario en específico a través de su email
     * @param email Email del usuario que se busca
     * @return Nodo con el usuario deseado
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        Optional<User> user = userService.findByEmail(id);

        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    /**
     * Create: Agrega un nodo de tipo usuario a la base de datos
     * @param user Usuario que sea desea agregar
     * @return Nodo guardado
     */
    @PostMapping("/add")
    public ResponseEntity<User> saveGame(@RequestBody User user) {
        User savedUser = userService.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    /**
     * Delete: Elimina un nodo en base al email
     * @param email Usuario que se va a eliminar
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable String id) {
        Optional<User> user = userService.findByEmail(id);

        if (user.isPresent()) {
            userService.deleteById(id);
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    
}
