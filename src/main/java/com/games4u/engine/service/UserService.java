package com.games4u.engine.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.games4u.engine.model.User;
import com.games4u.engine.repository.UserRepository;
/**
 * @author Juan Solís
 * @since 28/04/24
 * Servicio que accede a los usuarios de la base de datos
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * Constructor del servicio
     * @param userRepository Repositorio donde se almacenan los usuarios
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Devuelve todos los usuarios existentes en la base de datos
     * @return Lista con todos los nodos de usuario
     */
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Devuelve un usuario en específico a través de su id (email)
     * @param email Email del usuario que se busca
     * @return Nodo con el usuario deseado
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findById(email);
    }

    /**
     * Agrega un nodo de tipo usuario a la base de datos
     * @param user Usuario que sea desea agregar
     * @return Nodo guardado
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Elimina un nodo en base al id
     * @param name Usuario que se va a eliminar
     */
    public void deleteById(String email) {
        userRepository.deleteById(email);
    }
}