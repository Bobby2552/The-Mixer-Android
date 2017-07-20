package com.bobby2552.themixer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bns on 6/21/2017.
 */

public class Shared {
    // It is important not to duplicate drinks in memory. Use the same object.
    public static ArrayList<Drink> drinks;
    public static ArrayList<Cocktail> cocktails;
    public static boolean firstTimeAddingList;

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
        Gson gson = new Gson();
        String cocktails = gson.toJson(Shared.cocktails);
        String drinks = gson.toJson(Shared.drinks);
        Log.d("TAG", cocktails);
        Log.d("TAG", drinks);

    }

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
                drinks.add(new Drink("Coca-Cola", (short) 0, true));
                drinks.add(new Drink("Bourbon", (short) 0, false));
            }
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}







