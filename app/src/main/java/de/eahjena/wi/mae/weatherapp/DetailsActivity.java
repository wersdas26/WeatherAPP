package de.eahjena.wi.mae.weatherapp;

import static de.eahjena.wi.mae.weatherapp.RecyclerViewActivity.EXTRA_CITY;
import static de.eahjena.wi.mae.weatherapp.RecyclerViewActivity.EXTRA_DIESEL;
import static de.eahjena.wi.mae.weatherapp.RecyclerViewActivity.EXTRA_E10;
import static de.eahjena.wi.mae.weatherapp.RecyclerViewActivity.EXTRA_E5;
import static de.eahjena.wi.mae.weatherapp.RecyclerViewActivity.EXTRA_HOUSE_NUMBER;
import static de.eahjena.wi.mae.weatherapp.RecyclerViewActivity.EXTRA_NAME;
import static de.eahjena.wi.mae.weatherapp.RecyclerViewActivity.EXTRA_OPEN;
import static de.eahjena.wi.mae.weatherapp.RecyclerViewActivity.EXTRA_STREET;
import static de.eahjena.wi.mae.weatherapp.RecyclerViewActivity.EXTRA_ZIP;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * we match the intent strings from RecyclerViewActivity to our textView in the detail_view.xml to
 * show all details to a petrol station depending on which one was clicked in the recyclerView
 */
public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

        Intent intent = getIntent();
        String StationName = intent.getStringExtra(EXTRA_NAME);
        String StationOpen = intent.getStringExtra(EXTRA_OPEN);
        String StationStreet = intent.getStringExtra(EXTRA_STREET);
        String StationHouseNumber = intent.getStringExtra(EXTRA_HOUSE_NUMBER);
        String StationZip = intent.getStringExtra(EXTRA_ZIP);
        String StationCity = intent.getStringExtra(EXTRA_CITY);
        String StationPriceE5 = intent.getStringExtra(EXTRA_E5);
        String StationPriceE10 = intent.getStringExtra(EXTRA_E10);
        String StationPriceDiesel = intent.getStringExtra(EXTRA_DIESEL);


        TextView textViewName = findViewById(R.id.tvd_station_name);
        TextView textViewOpen = findViewById(R.id.tvd_open);
        TextView textViewStreet = findViewById(R.id.tv_station_address);
        TextView textViewPriceE5 = findViewById(R.id.tv_price_e5);
        TextView textViewPriceE10 = findViewById(R.id.tv_price_e10);
        TextView textViewPriceDiesel = findViewById(R.id.tv_price_diesel);

        textViewName.setText(StationName);
        textViewOpen.setText(StationOpen);
        if ("Geöffnet".equals(StationOpen)){
            textViewOpen.setTextColor(Color.argb(100,0,200,0));
        }
        else {
            textViewOpen.setTextColor(Color.RED);
        }
        textViewStreet.setText("Adresse: "+StationStreet+" "+StationHouseNumber+"\n\t\t\t\t\t\t\t\t"+StationZip+" "+StationCity);
        textViewPriceE5.setText("E5: "+StationPriceE5+" €");
        textViewPriceE10.setText("E10: "+StationPriceE10+" €");
        textViewPriceDiesel.setText("Diesel: "+StationPriceDiesel+" €");

    }
}

