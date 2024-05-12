package com.games4u.engine.controller;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.games4u.engine.model.User;
import com.games4u.engine.service.UserService;

/**
 * @author Nils Muralles
 * @since 04/05/24
 * Controlador que se encarga de la autenticación de usuarios
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1/users")
public class AuthController {
    private final UserService userService;
    
    /**
     * Constructor del controlador para la autenticación de usuarios
     * @param userService Servicio que maneja los usuarios
     */
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Verifica la coincidencia de las credenciales del usuario
     * @param id Id del usuario
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @return Usuario que se ha registrado
     */
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User userOnLogin) {
        String email = userOnLogin.getEmail();
        String password = userOnLogin.getPassword();

        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password) && user.getEmail().equals(email)) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {  
                return new ResponseEntity<>(user, HttpStatus.UNAUTHORIZED);
            }   
        } else {
            return new ResponseEntity<>(optionalUser.get(), HttpStatus.NOT_FOUND);
        }
    }
}
