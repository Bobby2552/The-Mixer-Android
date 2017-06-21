package com.bobby2552.themixer;

import java.util.HashMap;

/**
 * Created by bns on 6/21/2017.
 */

public class Cocktail {
    // Drink type, Quantity of shots (1.5 oz)
    private HashMap<Drink, Integer> recipe;
    private String name;

    public Cocktail(String name) {
        recipe = new HashMap<>();
        this.name = name;
    }

    public Cocktail() {
        recipe = new HashMap<>();
    }

    public void addDrink(Drink drink, Integer shots) {
        recipe.put(drink, shots);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap getRecipe() {
        return recipe;
    }
}
