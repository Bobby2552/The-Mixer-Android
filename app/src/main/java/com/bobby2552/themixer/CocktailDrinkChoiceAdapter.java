package com.bobby2552.themixer;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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
        Log.d("TAG", "I'm the constructor and I'm being called.");
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View v = convertView;
        Log.d("TAG", "I'm here!");

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.cocktail_drink_choice_adapter, null);
        }

        final Drink drink = getItem(position);

        Log.d("TAG", drink.toString());

        if (drink != null) {
            Log.d("TAG", "NAME: " + drink.getName() + ", QUANTITY: " + drink.quantity);
            final CheckBox name = (CheckBox) v.findViewById(R.id.drinkNameChoice);
            final EditText shots = (EditText) v.findViewById(R.id.shotIncrement);
            if (drink.quantity != 0) {
                shots.setText(drink.quantity + "");
            }
            name.setChecked(drink.enabled);
            shots.setHint((drink.isMixer()) ? "Ounces" : "Shots (1.5 oz)");
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shots.setEnabled(name.isChecked());
                    drink.enabled = name.isChecked();
                }
            });
            shots.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        drink.quantity = Integer.parseInt(shots.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            if (Shared.firstTimeAddingList) {
                name.performClick();
                name.performClick();
            }

            if (name != null) {
                name.setText(drink.getName());
            }
        }


        return v;
    }

}