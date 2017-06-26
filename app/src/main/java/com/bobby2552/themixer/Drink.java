package com.bobby2552.themixer;

/**
 * Created by bns on 6/21/2017.
 */

public class Drink {
    private String name;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drink() {

    }

    public Drink(String name, int id) {

        this.name = name;
        this.id = id;
    }
}
