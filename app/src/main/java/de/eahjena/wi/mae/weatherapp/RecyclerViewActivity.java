package de.eahjena.wi.mae.weatherapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

import de.eahjena.wi.mae.weatherapp.databinding.ActivityMainBinding;
import de.eahjena.wi.mae.weatherapp.databinding.ContentBinding;

public class RecyclerViewActivity extends AppCompatActivity {

    List<ContentModelClass> stationList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

        stationList = new ArrayList<>();
        recyclerView = findViewById(R.id.rvStations);

        GetData getData = new GetData();
        getData.execute();
    }

    public class GetData extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {

            String data = "";

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
                while ((line = bufferedReader.readLine()) != null) {

                    data = data + line;

                }

                return data;
            }
                 catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            return data;
        }

        //String is passed to postExecute -> analyzes String -> gets JSON Object -> passes to ModelClass -> passes to Adapterclass which displays into RecyclerView
        @Override
        protected void onPostExecute(String s){
            //super.onPostExecute(s);

            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONArray stations = jsonObject.getJSONArray("stations");
                for (int i = 0; i< stations.length(); i++){

                    JSONObject station_one = stations.getJSONObject(i);

                    ContentModelClass modelClass = new ContentModelClass();
                    modelClass.setS_name(station_one.getString("name"));
                    modelClass.setS_open(station_one.getString("price"));

                    stationList.add(modelClass);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            PutDataIntoRecyclerView(stationList);

        }
    }

    private void PutDataIntoRecyclerView(List<ContentModelClass> stationList){

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, stationList);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        recyclerView.setAdapter(recyclerAdapter);
    }
}

          /**  ContentBinding binding;  //für content.xml wäre es ContentBinding
        ArrayList<String> descrList;
        //to implement ListView
        ArrayAdapter<String> listAdapter;
        //to execute in MainThread:
        Handler mainHandler = new Handler();
        ProgressDialog progressDialog;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ContentBinding.inflate(getLayoutInflater());
            //setContentView(R.layout.content);
            setContentView(binding.getRoot());
            initializeDescrList();
            new getData().start();
            //binding.btnDataButton.setOnClickListener(new View.OnClickListener() {
            //    @Override
            //    public void onClick(View v) {

            //        setContentView(R.layout.content);
            //        new MainActivity.getData().start();

            //          }
            //      });

        }


        private void initializeDescrList() {

            descrList = new ArrayList<>();
            //now we pass the array list containing the descriptions as an argument to the layout
            listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, descrList);
            //testList is the id of our list defined in the .xml
            //binding.rvStations.setAdapter(listAdapter);

        }

        class getData extends Thread {

            //contains all the JSON data
            String data = "";

            @Override
            public void run() {

                //if user presses DataButton there should be shown a progress message
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        progressDialog = new ProgressDialog(RecyclerViewActivity.this);
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
                    while ((line = bufferedReader.readLine()) != null) {

                        data = data + line;
                    }

                    //if data is not empty we put it into JSON Object
                    if (!data.isEmpty()) {

                        JSONObject jsonObject = new JSONObject(data);
                        JSONArray weather = jsonObject.getJSONArray("stations");
                        //if the user presses the DataButton again the old data needs to be erased first:
                        descrList.clear();
                        for (int i = 0; i < weather.length(); i++) {
                            //we read the weather object by object and store each under the string descr
                            JSONObject descr = weather.getJSONObject(i);
                            JSONObject preis = weather.getJSONObject(i);
                            JSONObject open = weather.getJSONObject(i);
                            String description = descr.getString("name");
                            String price = preis.getString("price");
                            String opened = open.getString("isOpen");
                            if (opened == "false") {
                                opened = "nein";
                            } else {
                                opened = "ja";
                            }
                            //now we store all the weather descriptions in an ArrayList
                            descrList.add("Name: " + description + "\nPreis: " + price + "\nZur Zeit geöffnet? " + opened);

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
    }

*/