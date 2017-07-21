package com.bobby2552.themixer;

import android.renderscript.Byte2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class EditDrink extends AppCompatActivity {

    CheckBox mixer;
    EditText name;
    EditText position;
    Button submit;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_drink);

        mixer = (CheckBox) findViewById(R.id.drinkMixerEdit);
        name = (EditText) findViewById(R.id.drinkNameEdit);
        position = (EditText) findViewById(R.id.drinkPositionEdit);
        submit = (Button) findViewById(R.id.submitDrinkEdit);
        delete = (Button) findViewById(R.id.deleteDrinkEdit);

        if (Shared.editDrink == null) {
            Shared.editDrink = new Drink();
        } else {
            mixer.setChecked(Shared.editDrink.isMixer());
            name.setText(Shared.editDrink.getName());
            position.setText(Shared.editDrink.getPosition() + "");
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drink drink;
                if (Shared.editDrink.getID() != 0) {
                    //Drink already has an ID, set it.
                    drink = new Drink(Shared.editDrink.getID());
                } else {
                    //Generate random ID
                    drink = new Drink();
                }
                drink.setName(name.getText().toString());
                drink.setPosition(Byte.parseByte(position.getText().toString()));
                drink.setMixer(mixer.isChecked());
                Shared.drinks.add(drink);
                Shared.editDrink = null;
                Shared.write(getApplicationContext());
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared.editDrink = null;
                Shared.write(getApplicationContext());
                finish();
            }
        });
    }
}
