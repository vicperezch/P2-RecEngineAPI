package com.games4u.engine.model;

import org.springframework.data.neo4j.core.schema.*;

/**
 * @author Nils Muralles
 * @since 28/04/24
 * Modela un nodo de tipo Genre (género) en la base de datos
 */
@Node("Genre")
public class Genre {
    @Id
    private String name;

    /**
     * Constructor del nodo género
     * @param name Nombre del género
     */
    public Genre(String name) {
        this.name = name;
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
