package de.eahjena.wi.mae.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * shows activity_main.xml
 * when App is started the GPS coordinates are shown + converted into address
 * there is a dropdown menu available where you can select the radius in which youd like to search for petrol stations
 * when nothing is selected from the dropdown menu the default is ????
 * in this class we set the onClickListener for the "Tankstellen abrufen" button on the bottom of the screen -> when it is clicked the RecyclerViewActivity class is called
 */
public class MainActivity extends AppCompatActivity {

    //ActivityMainBinding binding;  //für content.xml wäre es ContentBinding
    ArrayList<String> descrList;
    //to implement ListView
    ArrayAdapter<String> listAdapter;
    //to execute in MainThread:
    Handler mainHandler = new Handler();
    ProgressDialog progressDialog;
    Button locationButton;
    TextView locationTextView;
    Button dataButton;
    TextView addressTextView;
    final static String TAG = "onLocationReceived";
    static String locationLat;
    static String locationLong;
    static String spinnerRadius;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationButton = findViewById(R.id.location_button);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
                onLocationButtonClick();
            }

        });

        Spinner spinnerUmkreis = findViewById(R.id.spinnerUmkreis); //Dropdown-Menü
        String[] Umkreis = getResources().getStringArray(R.array.Umkreis);
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, Umkreis);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUmkreis.setAdapter(adapter);
        dataButton = findViewById(R.id.btn_DataButton);
        dataButton.setOnClickListener(new View.OnClickListener() {
            /**
             * when "Tankstellen abrufen" button is clicked the selected radius from spinner is transformed
             * to a string and put into the API Call + we call the RecyclerViewActivity class -> the content view
             */
            @Override
            public void onClick(View v) {
                spinnerRadius = spinnerUmkreis.getSelectedItem().toString();
                //Toast.makeText(getApplicationContext(),spinnerRadius,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                startActivity(intent);
            }
        });

    }
        @Override
        protected void onResume() {
            super.onResume();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        onLocationButtonClick();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

    }


        private void onLocationButtonClick() {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                updateLocation();
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String [] {Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }
            }

        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                updateLocation();
            }
        }

        @SuppressLint("MissingPermission")
        public void updateLocation() {
            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this::onLocationReceived);
        }

        public void onLocationReceived(Location location) {
            locationLat = String.valueOf(location.getLatitude());
            locationLong = String.valueOf(location.getLongitude());
            locationTextView = findViewById(R.id.location_text);
            locationTextView.setText(locationLat+" | "+locationLong);
            try {
                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                String address = addresses.get(0).getAddressLine(0);
                addressTextView = findViewById(R.id.address_text);
                addressTextView.setText(address);
            } catch (Exception e){
                //e.printStackTrace();
                Log.e(TAG, "Geocoder Exception", e);
            }
        }

    /**
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinnerUmkreis) {
            String valuefromSpinner = parent.getItemAtPosition(position).toString();

        }
    }

    /**
     *
     * @param parent
     */
    public void onNothingSelected(AdapterView<?> parent) {
        //TODO

    }
}







/* private void initializeDescrList() {

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
