package com.games4u.engine.model;

import java.util.List;
import java.util.ArrayList;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

/**
 * @author Victor Pérez
 * @since 23/04/2023
 * Clase que modela un nodo de tipo User
 */
@Node("User")
public class User {
    @Id @GeneratedValue(UUIDStringGenerator.class)
    private final String id;
    private String email;
    private String name;
    private String password;
    @Relationship(type = "PLAYS", direction = Relationship.Direction.OUTGOING)
    private List<Game> games;
    @Relationship(type = "LIKES", direction = Relationship.Direction.OUTGOING)
    private List<Game> likedGames;
    @Relationship(type = "LIKES", direction = Relationship.Direction.OUTGOING)
    private List<Genre> likedGenres;
    @Relationship(type = "LIKES", direction = Relationship.Direction.OUTGOING)
    private List<Platform> likedPlatforms;
    @Relationship(type = "LIKES", direction = Relationship.Direction.OUTGOING)
    private List<Category> likedCategories;

    /**
     * Constructor de clase
     * @param id ID del usuario
     * @param email Correo electrónico
     * @param name Nombre de usuario
     * @param password Contraseña
     * @param games Juegos en su biblioteca
     * @param likedGames Juegos marcados con un like
     * @param likedGenres Géneros que le gustan al usuario
     * @param likedPlatforms Plataformas que le gustan al usuario
     * @param likedCategories Categorías que le gustan al usuario
     */
    public User(String id, String email, String name, String password, List<Game> games, List<Game> likedGames, List<Genre> likedGenres, List<Platform> likedPlatforms, List<Category> likedCategories) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.games = games;
        this.likedGames = likedGames;
        this.likedGenres = likedGenres;
        this.likedPlatforms = likedPlatforms;
        this.likedCategories = likedCategories;
    }

    public Game addGameToLibrary(Game game){
        if (games == null) {
            games = new ArrayList<>();
        }

        if (!games.contains(game)) {
            games.add(game);
            return game;
        }

        throw new IllegalArgumentException("The game is already in the user's library!");
    }

    public Game addLikedGame(Game game){
        if (likedGames == null) {
            likedGames = new ArrayList<>();
        }

        if (!likedGames.contains(game)) {
            likedGames.add(game);
            return game;
        }

        throw new IllegalArgumentException("The user already liked the game!");
    }

    // Setters y Getters
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public List<Game> getLikedGames() {
        return likedGames;
    }

    public void setLikedGames(List<Game> likedGames) {
        this.likedGames = likedGames;
    }

    public List<Genre> getLikedGenres() {
        return likedGenres;
    }

    public void setLikedGenres(List<Genre> likedGenres) {
        this.likedGenres = likedGenres;
    }

    public List<Platform> getLikedPlatforms() {
        return likedPlatforms;
    }

    public void setLikedPlatforms(List<Platform> likedPlatforms) {
        this.likedPlatforms = likedPlatforms;
    }

    public List<Category> getLikedCategories() {
        return likedCategories;
    }

    public void setLikedCategories(List<Category> likedCategories) {
        this.likedCategories = likedCategories;
    }
}
