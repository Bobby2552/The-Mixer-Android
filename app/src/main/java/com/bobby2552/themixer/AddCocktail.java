package com.bobby2552.themixer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import java.util.AbstractMap;
import java.util.List;

public class AddCocktail extends AppCompatActivity {

    Cocktail newCocktail = new Cocktail();
    public EditText cocktailName;
    public ListView drinkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cocktail);

        drinkList = (ListView) findViewById(R.id.drinkListAddCocktail);
        cocktailName = (EditText) findViewById(R.id.cocktailName);

        drinkList.setAdapter(new CocktailDrinkChoiceAdapter(this, R.layout.cocktail_drink_choice_adapter, Shared.drinks));
    }
}
