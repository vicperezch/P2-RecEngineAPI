package com.games4u.engine.model;

import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

/**
 * @author Victor Pérez
 * @since 23/04/2023
 * Clase que modela un nodo de tipo Platform
 */
@Node("Platform")
public class Platform {
    @Id @GeneratedValue(UUIDStringGenerator.class)
    private final String id;
    private String name;

    /**
     * Constructor de clase
     * @param id ID de la plataforma
     * @param name Nombre de la plataforma
     */
    public Platform(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Setters y Getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }
}
