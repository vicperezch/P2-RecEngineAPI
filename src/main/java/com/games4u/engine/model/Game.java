package com.games4u.engine.model;

import org.springframework.data.neo4j.core.schema.*;

/**
 * @author Victor Pérez
 * @since 23/04/2023
 * Modela un nodo de tipo Game en la base de datos
 */
@Node("Game")
public class Game {
    @Id
    private String name;
    private String developers;
    private int achievements;
    private String released;


    /**
     * Constructor de clase
     * @param name Título del juego
     * @param developers Nombre del desarrollador
     * @param achievements Número de logros
     * @param released Fecha de lanzamiento
     */
    public Game(String name, String developers, int achievements, String released) {
        this.name = name;
        this.developers = developers;
        this.achievements = achievements;
        this.released = released;
    }

    public Game (){}


    // Setters y Getters
    public String getDevelopers() {
        return developers;
    }

    public void setDevelopers(String developers) {
        this.developers = developers;
    }

    public int getAchievements() {
        return achievements;
    }

    public void setAchievements(int achievements) {
        this.achievements = achievements;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
