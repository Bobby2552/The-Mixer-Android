package com.bobby2552.themixer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.internal.Streams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by bns on 6/21/2017.
 */

public class Shared {
    // It is important not to duplicate drinks in memory. Use the same object.
    public static ArrayList<Drink> drinks;
    public static ArrayList<Cocktail> cocktails;
    public static boolean firstTimeAddingList;
    final static String COCKTAILS_NAME = "cocktails";
    final static String DRINKS_NAME = "drinks";
    public static Drink editDrink = null;
    public static Cocktail editCocktail = null;

    public static String[] getCocktailNames() {
        String[] names = new String[cocktails.size()];
        for (int i = 0; i < cocktails.size(); i++) {
            names[i] = cocktails.get(i).getName();
        }
        return names;
    }

    public static String[] getDrinkNames() {
        String[] names = new String[drinks.size()];
        for (int i = 0; i < drinks.size(); i++) {
            names[i] = drinks.get(i).getName();
        }
        return names;
    }

    public static void sendMessage(Cocktail cocktail) {
        // TODO Add some real functionality in here.
        Log.d("TAG", "\t\t\t\t\t\t\t\t\t\tSENDING THE FOLLOWING......\n" + generateMessage(cocktail));
    }

    public static String generateMessage(Cocktail cocktail) {
        String message = "";
        message += 170;
        message += " ";
        Iterator iterator = cocktail.getRecipe().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry drink = (Map.Entry) iterator.next();
            // Send the instruction thrice * number of times for fallback.
            for (int i = 0; i < /*3 **/ (Integer) drink.getValue(); i++) {
                message += (Drink.decodeID((Integer) drink.getKey()).isMixer()) ? 1 : 0;
                message += (Drink.decodeID((Integer) drink.getKey()).getPosition());
                message += " ";
            }
        }
        message += 85;
        return message;
    }

    // Defecated
    public static void save(Context context) {
        try {
            //Save cocktails
            FileOutputStream fos = context.openFileOutput("cocktails", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(cocktails);
            os.close();
            fos.close();

            File file = new File("cocktails");
            Toast.makeText(context, "File Exists: " + file.exists(), Toast.LENGTH_SHORT).show();

            //Save drinks
            fos = context.openFileOutput("drinks", Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            os.writeObject(drinks);
            os.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void write(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();

        String cocktails = gson.toJson(Shared.cocktails);
        String drinks = gson.toJson(Shared.drinks);

        editor.putString(COCKTAILS_NAME, cocktails);
        editor.putString(DRINKS_NAME, drinks);
        editor.apply();
    }

    public static void read(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String cocktails = preferences.getString(COCKTAILS_NAME, "");
        String drinks = preferences.getString(DRINKS_NAME, "");

        Log.d("TAG", "Cocktails:\n\n" + cocktails + "\n\n");
        Log.d("TAG", "Drinks:\n\n" + drinks + "\n\n");

        Shared.cocktails = new ArrayList<Cocktail>();
        Shared.drinks = new ArrayList<Drink>();

        if (!drinks.equals("")) {
            try {
                JSONArray drinksFromJSON = new JSONArray(drinks);
                for (int i = 0; i < drinksFromJSON.length(); i++) {
                    JSONObject drink = drinksFromJSON.getJSONObject(i);

                    Shared.drinks.add(new Drink(drink.getString("name"), (byte) drink.getInt("position"),
                            drink.getBoolean("isMixer"), drink.getInt("id")));

                }
            } catch (Exception e) {
                Log.d("TAG", e.toString());
            }
        }
        else {
            Log.d("TAG", "\t\t\t\t\tADDING DEFAULT DRINKS...PREFS WERE EMPTY.");
            // TODO add support for adding drinks, so drinks won't be hardcoded.
            Shared.drinks.add(new Drink("Coca-Cola", (byte) 0, true, 4));
            Shared.drinks.add(new Drink("Bourbon", (byte) 0, false, 5));
            Shared.drinks.add(new Drink("Apple Juice", (byte) 1, true, 6));
            Shared.drinks.add(new Drink("Rum", (byte) 1, false, 7));
        }

        Log.d("TAG", "\t\t\t\tCONTENTS OF COCKTAILS IN PREFS...:\n" + cocktails);

        if (!cocktails.equals("")) {
            try {
                JSONArray cocktailsFromJSON = new JSONArray(cocktails);
                for (int i = 0; i < cocktailsFromJSON.length(); i++) {
                    JSONObject cocktail = cocktailsFromJSON.getJSONObject(i);
                    Cocktail toAdd = new Cocktail(cocktail.getString("name"));
                    JSONObject recipie = cocktail.getJSONObject("recipe");
                    Iterator<?> keys = recipie.keys();
                    boolean success = true;
                    while (keys.hasNext()) {
                        int id = Integer.parseInt((String) keys.next());
                        int quantity = recipie.getInt(id + "");
                        Drink drink = Drink.decodeID(id);
                        if (drink == null) {
                            Toast.makeText(context, "Drink ID not able to be matched. Deleting cocktail.", Toast.LENGTH_SHORT).show();
                            success = false;
                        } else {
                            toAdd.addDrink(Drink.decodeID(id), quantity);
                        }
                    }
                    if (success) {
                        Shared.cocktails.add(toAdd);
                    } else success = true;
                }
            } catch (Exception e) {
                Log.d("TAG", e.toString());
            }
        }

    }

    // Defecated
    public static void load(Context context) {
        try {
            File file = new File("cocktails");
            if (file.exists()) {
                Toast.makeText(context, "cocktails exists.", Toast.LENGTH_SHORT).show();
                //Load cocktails
                FileInputStream fis = context.openFileInput("cocktails");
                ObjectInputStream is = new ObjectInputStream(fis);
                cocktails = (ArrayList) is.readObject();
                is.close();
                fis.close();
            } else {
                cocktails = new ArrayList<>();
            }

            file = new File("drinks");
            if (file.exists()) {
                Toast.makeText(context, "drinks exists.", Toast.LENGTH_SHORT).show();
                //Load drinks
                FileInputStream fis = context.openFileInput("drinks");
                ObjectInputStream is = new ObjectInputStream(fis);
                drinks = (ArrayList) is.readObject();
                is.close();
                fis.close();
            } else {
                drinks = new ArrayList<>();
                drinks.add(new Drink("Coca-Cola", (byte) 0, true));
                drinks.add(new Drink("Bourbon", (byte) 0, false));
            }
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}







