package com.games4u.engine.model;

import org.springframework.data.neo4j.core.schema.*;

/**
 * @author Renato Rojas
 * @since 28/04/24
 * Modela un nodo de tipo Category (categoría) en la base de datos
 */
@Node ("Category")
public class Category {
    @Id
    private String mode;
    private int age_restrict;

     /**
     * Constructor de la clase Category.
     * @param mode         El modo respectivo a la categoría.
     * @param age_restrict La restricción de edad de la categoría.
     */
    public Category(String mode, int age_restrict){
        this.mode = mode;
        this.age_restrict = age_restrict;
    }

    //Getters y setters
    public String getMode(){
        return mode;
    }
    public void setMode(String mode){
        this.mode = mode;
    }
    public int getAge_restrict(){
        return age_restrict;
    }
    public void setAge_restrict(int age_restrict){
        this.age_restrict = age_restrict;
    }

}
