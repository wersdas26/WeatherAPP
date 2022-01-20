/**package de.eahjena.wi.mae.weatherapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

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

public class ApiConnect extends AppCompatActivity {

    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;
    ActivityMainBinding binding;
    ArrayList<String> descrList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_main);
        setContentView(binding.getRoot()); //wenn man dies auskommentiert und stattdessen über layout auf activity_main zugreift funktioniert der button nicht mehr
        binding.btnDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new getData().start();

            }
        });

    }

    class getData extends Thread {

        private JSONObject rawData;

        //contains all the JSON data
        String data = "";

        @Override
        public void run() {

            //if user presses DataButton there should be shown a progress message
            mainHandler.post(new Runnable() {
                @Override
                public void run() {

                    progressDialog = new ProgressDialog(ApiConnect.this);
                    progressDialog.setMessage("Die Daten werden abgeholt");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                }
            });

            try {

                URL url = new URL("https://creativecommons.tankerkoenig.de/json/list.php?lat=54.092&lng=12.099&rad=5.5&sort=dist&type=e5&apikey=5fde221a-19b1-a8a1-1f7c-a032f0239719");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //to read the data we need:
                InputStream inputStream = httpURLConnection.getInputStream();
                //to read data from InputStream we need:
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                //while line that comes from bufferedReader is not empty we will put this data into the data String line per line until it is empty
                while ((line = bufferedReader.readLine()) != null) {

                    data = data + line;
                }

                // if data is filled --> paste data into json object
                if (!data.isEmpty()) {

                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray stations = jsonObject.getJSONArray("stations");
                    //if the user presses the DataButton again the old data needs to be erased first:
                    descrList.clear();
                    for (int i = 0; i < stations.length(); i++) {
                        //we read the weather object by object and store each under the string descr
                        JSONObject name = stations.getJSONObject(i);
                        String station_name = name.getString("name");
                        //now we store all the weather descriptions in an ArrayList
                        descrList.add("Aktuell ist es: " + name);
                    }
                }
            }catch(MalformedURLException e){
                        e.printStackTrace();
                    } catch(IOException e){
                        e.printStackTrace();
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }

*/
