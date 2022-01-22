package de.eahjena.wi.mae.weatherapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import de.eahjena.wi.mae.weatherapp.databinding.ActivityMainBinding;
import de.eahjena.wi.mae.weatherapp.databinding.DetailViewBinding;

public class DetailsActivity extends AppCompatActivity {

    //DetailViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

    }
}

