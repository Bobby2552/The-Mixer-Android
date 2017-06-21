package com.bobby2552.themixer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bns on 6/21/2017.
 */

public class Shared {
    // It is important not to duplicate drinks in memory. Use the same object.
    public static ArrayList<Drink> drinks = new ArrayList<>();
    public static ArrayList<Cocktail> cocktails = new ArrayList<>();
    public static String[] getCocktailNames() {
        String[] names = new String[cocktails.size()];
        for (int i = 0; i < cocktails.size(); i++) {
            names[i] = cocktails.get(i).getName();
        }
        return names;
    }
}