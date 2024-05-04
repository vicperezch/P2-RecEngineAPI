package com.games4u.engine.model;

import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

/**
 * @author Nils Muralles
 * @since 28/04/24
 * Modela un nodo de tipo Genre (género) en la base de datos
 */
@Node("Genre")
public class Genre {
    @Id @GeneratedValue(UUIDStringGenerator.class)
    private String id;
    private String name;

    /**
     * Constructor del nodo género
     * @param id ID del género
     * @param name Nombre del género
     */
    public Genre(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    /**
     * Devuelve el nombre del género
     * @return Nombre del geénro
     */
    public String getName() {
        return name;
    }

    /**
     * Actualiza el nombre del género
     * @param name Nombre del género
     */
    public void setName(String name) {
        this.name = name;
    }

    
}
