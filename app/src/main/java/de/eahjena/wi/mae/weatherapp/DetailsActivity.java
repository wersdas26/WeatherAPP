package de.eahjena.wi.mae.weatherapp;

import static de.eahjena.wi.mae.weatherapp.RecyclerViewActivity.EXTRA_NAME;
import static de.eahjena.wi.mae.weatherapp.RecyclerViewActivity.EXTRA_OPEN;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.eahjena.wi.mae.weatherapp.databinding.ActivityMainBinding;
import de.eahjena.wi.mae.weatherapp.databinding.DetailViewBinding;

public class DetailsActivity extends AppCompatActivity {

    //DetailViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

        Intent intent = getIntent();
        String StationName = intent.getStringExtra(EXTRA_NAME);
        String StationOpen = intent.getStringExtra(EXTRA_OPEN);

        TextView textViewName = findViewById(R.id.tvd_station_name);
        TextView textViewOpen = findViewById(R.id.tvd_open);

        textViewName.setText(StationName);
        textViewOpen.setText(StationOpen);

    }
}

