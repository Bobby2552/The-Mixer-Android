package com.bobby2552.themixer;

/**
 * Created by bns on 6/26/2017.
 */

public class Liquor extends Drink {
    private int position;

    public Liquor() {super();}

    public Liquor(String name, int position, int id) {
        super(name, id);
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
