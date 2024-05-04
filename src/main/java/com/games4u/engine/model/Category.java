package com.games4u.engine.model;

import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

/**
 * @author Renato Rojas
 * @since 28/04/24
 * Modela un nodo de tipo Category (categoría) en la base de datos
 */
@Node ("Category")
public class Category {
    @Id @GeneratedValue(UUIDStringGenerator.class)
    private final String id;
    private String mode;
    private int ageRestrict;

     /**
     * Constructor de la clase Category.
      * @param id ID de la categoría
     * @param mode         El modo respectivo a la categoría.
     * @param ageRestrict La restricción de edad de la categoría.
     */
    public Category(String id, String mode, int ageRestrict){
        this.id = id;
        this.mode = mode;
        this.ageRestrict = ageRestrict;
    }

    //Getters y setters
    public String getId() {
        return id;
    }

    public String getMode(){
        return mode;
    }

    public void setMode(String mode){
        this.mode = mode;
    }

    public int getAgeRestrict(){
        return ageRestrict;
    }

    public void setAgeRestrict(int ageRestrict){
        this.ageRestrict = ageRestrict;
    }

}
