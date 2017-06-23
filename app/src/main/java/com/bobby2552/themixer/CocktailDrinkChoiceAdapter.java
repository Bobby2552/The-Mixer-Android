package com.bobby2552.themixer;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;

import com.bobby2552.themixer.Drink;
import com.bobby2552.themixer.R;

import java.util.AbstractMap;
import java.util.List;

public class CocktailDrinkChoiceAdapter extends ArrayAdapter<Drink> {

    public CocktailDrinkChoiceAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CocktailDrinkChoiceAdapter(Context context, int resource, List<Drink> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.cocktail_drink_choice_adapter, null);
        }

        Drink drink = getItem(position);

        if (drink != null) {
            final CheckBox name = (CheckBox) v.findViewById(R.id.drinkNameChoice);
            final EditText shots = (EditText) v.findViewById(R.id.shotIncrement);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shots.setEnabled(name.isChecked());
                }
            });

            if (name != null) {
                name.setText(drink.getName());
            }
        }


        return v;
    }

}