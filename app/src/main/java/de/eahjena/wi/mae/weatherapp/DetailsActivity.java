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
        //binding = DetailViewBinding.inflate(getLayoutInflater());
        setContentView(R.layout.detail_view);
        //setContentView(binding.getRoot()); //wenn man dies auskommentiert und stattdessen über layout auf activity_main zugreift funktioniert der button nicht mehr
        /**initializeDescrList();
        binding.btnDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new MainActivity.getData().start();

            }
        });*/

    }
}

