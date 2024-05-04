package com.games4u.engine.model;

import org.springframework.data.neo4j.core.schema.*;
import java.util.List;

/**
 * @author Victor Pérez
 * @since 23/04/2023
 * Modela un nodo de tipo Game en la base de datos
 */
@Node("Game")
public class Game {
    @Id
    private final String name;
    private String developers;
    private int achievements;
    private String released;
    @Relationship(type = "BELONGS_TO", direction = Relationship.Direction.OUTGOING)
    private List<Genre> genres;
    @Relationship(type = "AVAILABLE_ON", direction = Relationship.Direction.OUTGOING)
    private List<Platform> platforms;
    @Relationship(type = "CLASSIFIED_AS", direction = Relationship.Direction.OUTGOING)
    private List<Category> categories;


    /**
     * Constructor de clase
     * @param name Título del juego
     * @param developers Nombre del desarrollador
     * @param achievements Número de logros
     * @param released Fecha de lanzamiento
     * @param genres Lista de géneros del juego
     * @param platforms Lista de plataformas donde está disponible
     * @param categories Listado de categorías a las que pertenece
     */
    public Game(String name, String developers, int achievements, String released, List<Genre> genres, List<Platform> platforms, List<Category> categories) {
        this.name = name;
        this.developers = developers;
        this.achievements = achievements;
        this.released = released;
        this.genres = genres;
        this.platforms = platforms;
        this.categories = categories;
    }


    // Setters y Getters
    public String getName() {
        return name;
    }

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

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
