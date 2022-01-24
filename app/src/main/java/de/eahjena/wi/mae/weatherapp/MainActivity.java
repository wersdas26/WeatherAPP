package de.eahjena.wi.mae.weatherapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import de.eahjena.wi.mae.weatherapp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerUmkreis;

    ActivityMainBinding binding;  //für content.xml wäre es ContentBinding
    ArrayList<String> descrList;
    //to implement ListView
    ArrayAdapter<String> listAdapter;
    //to execute in MainThread:
    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_main);
        setContentView(binding.getRoot()); //wenn man dies auskommentiert und stattdessen über layout auf activity_main zugreift funktioniert der button nicht mehr
        //initializeDescrList();
        binding.btnDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //setContentView(R.layout.content);
                //new getData().start();
                Intent intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                startActivity(intent);

            }
        });

        spinnerUmkreis = findViewById(R.id.spinnerUmkreis); //Dropdown-Menü

        String[] Umkreis = getResources().getStringArray(R.array.Umkreis);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Umkreis);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUmkreis.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinnerUmkreis) {
            String valuefromSpinner = parent.getItemAtPosition(position).toString();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}



   /** private void initializeDescrList() {

        descrList = new ArrayList<>();
        //now we pass the array list containing the descriptions as an argument to the layout
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,descrList);
        //testList is the id of our list defined in the .xml
        binding.testList.setAdapter(listAdapter);

    }

    class getData extends Thread{

        //contains all the JSON data
        String data = "";

        @Override
        public void run(){

            //if user presses DataButton there should be shown a progress message
            mainHandler.post(new Runnable() {
                @Override
                public void run() {

                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Die Daten werden abgeholt");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                }
            });

            try {
                URL url = new URL("https://creativecommons.tankerkoenig.de/json/list.php?lat=54.092&lng=12.099&rad=3&sort=dist&type=e5&apikey=5fde221a-19b1-a8a1-1f7c-a032f0239719");
                //API Key: 5fde221a-19b1-a8a1-1f7c-a032f0239719
                // Wetter API"https://api.openweathermap.org/data/2.5/weather?q=Jena&appid=be9602aaf7947a3d73acd26e36336e07&lang=de"
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //to read the data we need:
                InputStream inputStream = httpURLConnection.getInputStream();
                //to read data from InputStream we need:
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                //while line that comes from bufferedReader is not empty we will put this data into the data String line per line until it is empty
                while ((line = bufferedReader.readLine()) != null){

                    data = data + line;
                }

                //if data is not empty we put it into JSON Object
                if (!data.isEmpty()){

                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray weather = jsonObject.getJSONArray("stations");
                    //if the user presses the DataButton again the old data needs to be erased first:
                    descrList.clear();
                    for (int i = 0;i< weather.length();i++){
                        //we read the weather object by object and store each under the string descr
                        JSONObject descr = weather.getJSONObject(i);
                        JSONObject preis = weather.getJSONObject(i);
                        JSONObject open = weather.getJSONObject(i);
                        String description = descr.getString("name");
                        String price = preis.getString("price");
                        String opened = open.getString("isOpen");
                        if (opened == "false")
                        {
                            opened = "nein";
                        }
                        else {
                            opened = "ja";
                        }
                        //now we store all the weather descriptions in an ArrayList
                        descrList.add("Name: "+description +"\nPreis: "+ price +"\nZur Zeit geöffnet? "+ opened);

                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //when everything is done the ProgressDialog should disappear
            mainHandler.post(new Runnable() {
                @Override
                public void run() {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //the Adapter needs to be notified that the data changed after the button was pressed -> now data inside listView needs to be changed
                    listAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}*/
