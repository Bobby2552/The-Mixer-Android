package com.bobby2552.themixer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by bns on 6/21/2017.
 */

public class Cocktail implements Serializable {
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

    public String getDrinkNames() {
        String names = "";
        Set<Drink> set = recipe.keySet();
        for (Drink drink : set) {
            names += recipe.get(drink) + " " + drink.getName() + ", ";
        }
        return names;
    }

    public HashMap getRecipe() {
        return recipe;
    }
}
