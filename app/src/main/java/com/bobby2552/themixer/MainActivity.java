package com.bobby2552.themixer;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

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
        Shared.read(getApplicationContext());
        Shared.write(getApplicationContext());

        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> bluetoothDevices;

        if (adapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth device not availible", Toast.LENGTH_SHORT);
            finish();
        } else {
            if (adapter.isEnabled()) {

            } else {
                //Ask to the user turn the bluetooth on
                Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnBTon, 1);
            }
        }

        bluetoothDevices = adapter.getBondedDevices();
        for (BluetoothDevice device : bluetoothDevices) {
            if (device.getAddress().equals(Shared.BLUETOOTH_ADDRESS)) {
                Intent i = new Intent()
            }
        }

        System.out.println("Cocktails: " + Arrays.toString(Shared.getCocktailNames()));

        cocktailsList = (ListView) findViewById(R.id.cocktails);

        cocktailsList.setAdapter(new ArrayAdapter<String>(this, R.layout.cocktail_adapter, Shared.getCocktailNames()));

        cocktailsList.setTextFilterEnabled(true);

        cocktailsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) ((TextView) view).getText();
                Shared.editCocktail = Cocktail.getCocktailFromName(name);
                Shared.cocktails.remove(Shared.editCocktail);

                startActivity(new Intent(getApplicationContext(), AddCocktail.class));
                return true;
            }
        });

        cocktailsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) ((TextView) view).getText();

                Toast.makeText(getApplicationContext(), "Making a " + name, Toast.LENGTH_SHORT).show();
                Shared.sendMessage(Cocktail.getCocktailFromName(name));
            }
        });
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(ledControl.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Shared.read(getApplicationContext());
        Shared.write(getApplicationContext());

        cocktailsList = (ListView) findViewById(R.id.cocktails);

        cocktailsList.setAdapter(new ArrayAdapter<String>(this, R.layout.cocktail_adapter, Shared.getCocktailNames()));

        cocktailsList.setTextFilterEnabled(true);
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
