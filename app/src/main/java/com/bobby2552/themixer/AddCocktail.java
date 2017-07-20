package com.bobby2552.themixer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.AbstractMap;
import java.util.List;

public class AddCocktail extends AppCompatActivity {

    Cocktail newCocktail = new Cocktail();
    public EditText cocktailName;
    public ListView drinkList;
    public Button createCocktail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cocktail);

        drinkList = (ListView) findViewById(R.id.drinkListAddCocktail);
        cocktailName = (EditText) findViewById(R.id.cocktailName);
        createCocktail = (Button) findViewById(R.id.createCocktail);
        createCocktail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCocktail.setName(cocktailName.getText().toString());
                for (int i = 0; i < Shared.drinks.size(); i++) {
                    Drink drink = getViewByPosition(i, drinkList);
                    if (drink.enabled) {
                        newCocktail.addDrink(drink, drink.quantity);
                    }
                }
                Shared.cocktails.add(newCocktail);
                Shared.save(getApplicationContext());
                finish();
            }
        });

        Shared.firstTimeAddingList = true;

        drinkList.setAdapter(new CocktailDrinkChoiceAdapter(this, R.layout.cocktail_drink_choice_adapter, Shared.drinks));
        Shared.firstTimeAddingList = false;
    }

    public Drink getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return (Drink) listView.getItemAtPosition(pos);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return (Drink) listView.getItemAtPosition(childIndex);
        }
    }
}
