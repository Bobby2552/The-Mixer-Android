package com.bobby2552.themixer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by bns on 6/21/2017.
 */

public class Cocktail implements Serializable {
    // Drink ID, Quantity
    private HashMap<Integer, Integer> recipe;
    private String name;

    public Cocktail(String name) {
        recipe = new HashMap<>();
        this.name = name;
    }

    public Cocktail() {
        recipe = new HashMap<>();
    }

    public void addDrink(Drink drink, Integer shots) {
        recipe.put(drink.getID(), shots);
    }

    public static Cocktail getCocktailFromName(String name) {
        for (Cocktail cocktail : Shared.cocktails) {
            if (cocktail.getName().equals(name)) {
                return cocktail;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDrinkNames() {
        String names = "";
        Set<Integer> set = recipe.keySet();
        for (Integer drinkID : set) {
            names += recipe.get(drinkID) + " " + Drink.decodeID(drinkID).getName() + ", ";
        }
        return names;
    }

    public HashMap getRecipe() {
        return recipe;
    }
}
