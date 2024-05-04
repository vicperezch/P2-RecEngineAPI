package com.games4u.engine.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.games4u.engine.model.Platform;
import com.games4u.engine.repository.PlatformRepository;

/**
 * @author Diego Flores
 * @since 28/04/24
 * Servicio que accede a las plataformas de la base de datos
 */
@Service
public class PlatformService {
    private final PlatformRepository platformRepository;

    /**
     * Constructor del servicio
     * @param platformRepository Repositorio donde se almacenan las plataformas
     */
    public PlatformService(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }

    /**
     * Devuelve todos las plataformas existentes en la base de datos
     * @return Lista con todos los nodos de plataforma
     */
    public List<Platform> findAllPlatforms() {
        return platformRepository.findAll();
    }

    /**
     * Devuelve una plataforma en específico a través de su id (nombre)
     * @param id ID de la plataforma que se busca
     * @return Nodo con la plataforma deseada
     */
    public Optional<Platform> findById(String id) {
        return platformRepository.findById(id);
    }

    /**
     * Agrega un nodo de tipo plataforma a la base de datos
     * @param platform Género que sea desea agregar
     * @return Nodo guardado
     */
    public Platform save(Platform platform) {
        return platformRepository.save(platform);
    }

    /**
     * Elimina un nodo en base al id
     * @param id Plataforma que se va a eliminar
     */
    public void deleteById(String id) {
        platformRepository.deleteById(id);
    }
}
