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
 * when nothing is selected from the dropdown menu the default is 5km
 * in this class we set the onClickListener for the "Tankstellen abrufen" button on the bottom of the screen
 *     -> when it is clicked the RecyclerViewActivity class is called
 */
public class MainActivity extends AppCompatActivity {

    Button locationButton;
    TextView locationTextView;
    Button dataButton;
    TextView addressTextView;
    final static String TAG = "onLocationReceived";
    final static String TAG2 = "LocationButton";
    static String locationLat;
    static String locationLong;
    static String spinnerRadius;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Test
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
             * to a string and put into the API Call + we call the RecyclerViewActivity class -> the content layout.xml opens
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
                        Log.e(TAG2, "Exception onLocationButtonClick()", e);
                    }
                }
            });
            thread.start();

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
            locationLat = String.valueOf((location.getLatitude()));
            locationLong = String.valueOf((location.getLongitude()));
            locationTextView = findViewById(R.id.location_text);
            locationTextView.setText("Lat: " + locationLat + " | Lng: " + locationLong);
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







/*
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

*/