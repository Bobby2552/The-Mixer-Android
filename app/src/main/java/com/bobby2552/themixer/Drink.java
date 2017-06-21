package com.bobby2552.themixer;

/**
 * Created by bns on 6/21/2017.
 */

public class Drink {
    private String name;
    private int position;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Drink() {

    }

    public Drink(String name, int position, int id) {

        this.name = name;
        this.position = position;
        this.id = id;
    }
}
