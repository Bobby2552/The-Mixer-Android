package com.bobby2552.themixer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public ListView cocktailsList;
    public String cocktailJSON = "[{\"name\": \"Rum & Coke\", \"drinks\": [{\"id\": 0, \"units\": 2}, {\"id\": 2, \"units\": 4}]}]";
    public final String drinksJSON = "[" +
            "{\"name\": \"Rum\",        \"type\": \"liquor\",   \"position\": 0,    \"id\": 0}," +
            "{\"name\": \"Whiskey\",    \"type\": \"liquor\",   \"position\": 1,    \"id\": 1}," +
            "{\"name\": \"Coca-Cola\",  \"type\": \"mixer\",    \"position\": 0,    \"id\": 2}]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshArrays();

        System.out.println("Cocktails: " + Arrays.toString(Shared.getCocktailNames()));

        cocktailsList = (ListView) findViewById(R.id.cocktails);

        cocktailsList.setAdapter(new ArrayAdapter<String>(this, R.layout.cocktail_adapter, Shared.getCocktailNames()));

        cocktailsList.setTextFilterEnabled(true);

        cocktailsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) ((TextView) view).getText();

                Toast.makeText(getApplicationContext(), "Making a " + name, Toast.LENGTH_SHORT).show();
                // TODO Send command to Arduino with recipe
            }
        });
    }

    public void refreshArrays() {
        Shared.cocktails = new ArrayList<>();
        Shared.drinks = new ArrayList<>();
        JSONArray drinks = new JSONArray();
        try {
            drinks = new JSONArray(drinksJSON);
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "JSON Read error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        for (int i = 0; i < drinks.length(); i++) {
            try {
                JSONObject drink = drinks.getJSONObject(i);
                Shared.drinks.add(new Drink(drink.getString("name"), drink.getInt("position"), drink.getInt("id")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        JSONArray cocktails = new JSONArray();
        try {
            cocktails = new JSONArray(cocktailJSON);
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "JSON Read error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        for (int i = 0; i < cocktails.length(); i++) {
            try {
                JSONObject cocktailObject = cocktails.getJSONObject(i);
                Cocktail cocktail = new Cocktail();
                cocktail.setName(cocktailObject.getString("name"));
                JSONArray drinksArray = cocktailObject.getJSONArray("drinks");
                for (int j = 0; j < drinksArray.length(); j++) {
                    JSONObject drinkObject = drinksArray.getJSONObject(i);
                    // Find drink with same ID
                    int id = drinkObject.getInt("id");
                    System.out.println("ID: " + id);
                    Drink drink = null;
                    for (int k = 0; k < Shared.drinks.size(); k++) {
                        if (Shared.drinks.get(k).getId() == id) drink = Shared.drinks.get(k);
                    }
                    cocktail.addDrink(drink, drinkObject.getInt("shots"));
                }
                Shared.cocktails.add(cocktail);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_action_buttons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.inventory) {
            startActivity(new Intent(getApplicationContext(), Inventory.class));
            // Pull up the inventory page.
        } else if (id == R.id.addCocktail) {
            startActivity(new Intent(
                    getApplicationContext(), AddCocktail.class
            ));
        }

        return super.onOptionsItemSelected(item);
    }
}
