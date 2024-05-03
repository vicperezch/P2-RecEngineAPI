package com.games4u.engine.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.games4u.engine.model.Genre;
import com.games4u.engine.repository.GenreRepository;

/**
 * @author Nils Muralles
 * @since 28/04/24
 * Servicio que accede a los géneros de la base de datos
 */
@Service
public class GenreService {
    private final GenreRepository genreRepository;

    /**
     * Constructor del servicio
     * @param genreRepository Repositorio donde se almacenan los géneros
     */
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    /**
     * Devuelve todos los géneros existentes en la base de datos
     * @return Lista con todos los nodos de género
     */
    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    /**
     * Devuelve un género en específico a través de su id (nombre)
     * @param id ID del género que se busca
     * @return Nodo con el género deseado
     */
    public Optional<Genre> findById(String id) {
        return genreRepository.findById(id);
    }

    /**
     * Agrega un nodo de tipo género a la base de datos
     * @param genre Género que sea desea agregar
     * @return Nodo guardado
     */
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    /**
     * Elimina un nodo en base al id
     * @param id Género que se va a eliminar
     */
    public void deleteById(String id) {
        genreRepository.deleteById(id);
    }
}
