package com.games4u.engine.model;

import java.util.List;
import java.util.ArrayList;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

/**
 * @author Victor Pérez
 * @since 23/04/2023
 * Clase que modela un nodo de tipo User
 */
@Node("User")
public class User {

    @Id
    private String email;
    private String name;
    private String password;

    @Relationship(type = "PLAYS", direction = Direction.OUTGOING)
    private List<Game> games = new ArrayList<>();


    /**
     * Constructor de clase
     * @param email Correo electrónico
     * @param name Nombre de usuario
     * @param password Contraseña
     */
    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public User(){}

    public void addGame(Game game){
        games.add(game);
    }

    // Setters y Getters
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
}
