package com.bobby2552.themixer;

import java.io.Serializable;

/**
 * Created by bns on 6/21/2017.
 */

public class Drink implements Serializable {
    private String name;
    private boolean isMixer;
    public int quantity;
    private short position;
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

    public void setMixer(boolean mixer) {
        isMixer = mixer;
    }

    public Drink() {

    }

    public Drink(String name, short position, boolean isMixer) {

        this.name = name;
        this.position = position;
        this.quantity = 0;
        this.enabled = false;
        this.isMixer = isMixer;
    }
}
