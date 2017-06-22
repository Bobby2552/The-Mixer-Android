package com.bobby2552.themixer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                //TODO Open up Inventory Editor Activity
            }
        });

    }
}
