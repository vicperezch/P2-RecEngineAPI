package com.games4u.engine.model;

import org.springframework.data.neo4j.core.schema.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Victor Pérez
 * @since 23/04/2023
 * Clase que modela un nodo de tipo Platform
 */
@Node("Platform")
public class Platform {
    @Id
    private String name;

    /**
     * Constructor de clase
     * @param name Nombre de la plataforma
     */
    public Platform(String name) {
        this.name = name;
    }


    // Setters y Getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
