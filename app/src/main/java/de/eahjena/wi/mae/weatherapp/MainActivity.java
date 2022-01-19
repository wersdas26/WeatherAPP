package de.eahjena.wi.mae.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;

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

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
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
        initializeDescrList();
        binding.DataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new getData().start();

            }
        });

    }

    private void initializeDescrList() {

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
                URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=Berlin&appid=be9602aaf7947a3d73acd26e36336e07&lang=de");
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
                    JSONArray weather = jsonObject.getJSONArray("weather");
                    //if the user presses the DataButton again the old data needs to be erased first:
                    descrList.clear();
                    for (int i = 0;i< weather.length();i++){
                        //we read the weather object by object and store each under the string descr
                        JSONObject descr = weather.getJSONObject(i);
                        String description = descr.getString("description");
                        //now we store all the weather descriptions in an ArrayList
                        descrList.add(description);

                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //when everything is done the ProgessDialog should dissappear
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
}