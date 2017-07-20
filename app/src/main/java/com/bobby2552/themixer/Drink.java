package com.bobby2552.themixer;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by bns on 6/21/2017.
 */

public class Drink implements Serializable {
    private String name;
    private boolean isMixer;
    public int quantity;
    private int id;
    private byte position;
    public boolean enabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMixer() {
        return isMixer;
    }

    public byte getPosition() {
        return position;
    }

    public void setPosition(byte position) {
        this.position = position;
    }

    public void setMixer(boolean mixer) {
        isMixer = mixer;
    }

    public Drink() {

    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public static Drink decodeID(int id) {
        for (Drink drink : Shared.drinks) {
            if (drink.getID() == id) {
                return drink;
            }
        }
        return null;
    }

    public Drink(String name, byte position, boolean isMixer) {

        this.name = name;
        this.position = position;
        this.quantity = 0;
        this.enabled = false;
        this.isMixer = isMixer;
        Random random = new Random();
        this.id = random.nextInt(1000000000);
    }

    public Drink(String name, byte position, boolean isMixer, int id) {

        this.name = name;
        this.position = position;
        this.quantity = 0;
        this.enabled = false;
        this.isMixer = isMixer;
        this.id = id;
    }
}
