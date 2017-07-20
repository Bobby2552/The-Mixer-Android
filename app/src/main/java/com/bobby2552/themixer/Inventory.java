package com.bobby2552.themixer;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
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

public class Inventory extends AppCompatActivity {

    public ListView drinkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);


        drinkList = (ListView) findViewById(R.id.drinks);

        drinkList.setAdapter(new ArrayAdapter<String>(this, R.layout.drink_inventory_adapter, Shared.getDrinkNames()));

        drinkList.setTextFilterEnabled(true);

        drinkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) ((TextView) view).getText();
                Shared.editDrink = Drink.decodeName(name);

                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                LayoutInflater inflater = getLayoutInflater();

                builder.setView(inflater.inflate(R.layout.dialog_drink, null))
                        .setPositiveButton("Update/Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO make the edits.
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();

            }
        });

    }
}
