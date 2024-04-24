package com.games4u.engine.model;

import org.springframework.data.neo4j.core.schema.*;

@Node("Game")
public class Game {

    @Id @GeneratedValue
    private Long id;
    private String developers;
    private int achievements;
    private String released;
    private String name;

    public Game() {}

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
