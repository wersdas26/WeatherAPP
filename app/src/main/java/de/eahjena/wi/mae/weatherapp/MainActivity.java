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