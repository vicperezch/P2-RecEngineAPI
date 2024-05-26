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
@CrossOrigin(origins = "https://games4u-54c04.web.app")
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
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    /**
     * Read: Devuelve un usuario en específico a través de su email
     * @param id ID del usuario que se busca
     * @return Nodo con el usuario deseado
     */
    @GetMapping("/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);

        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /**
     * Create: Agrega un nodo de tipo usuario a la base de datos
     * @param user Usuario que sea desea agregar
     * @return Nodo guardado
     */
    @PostMapping("/add")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User savedUser = userService.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }


    @PutMapping("/{email}")
    public ResponseEntity<User> updateUserByEmail(@RequestBody User updatedUser, @PathVariable String email){
        Optional<User> optionalUser = userService.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            user.setGames(updatedUser.getGames());
            user.setLikedGenres(updatedUser.getLikedGenres());
            user.setLikedGames(updatedUser.getLikedGames());
            user.setLikedPlatforms(updatedUser.getLikedPlatforms());
            user.setLikedCategories(updatedUser.getLikedCategories());

            userService.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    /**
     * Delete: Elimina un nodo en base al email
     * @param id Usuario que se va a eliminar
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable String id) {
        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            userService.deleteById(id);
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    
}
