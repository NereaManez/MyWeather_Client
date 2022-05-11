package com.dam.proyectodamdaw.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dam.proyectodamdaw.Place;
import com.dam.proyectodamdaw.R;

public class AddCityActivity extends AppCompatActivity {

    private Button anyadir;
    private EditText nombre, lon, lat, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        anyadir = findViewById(R.id.anyadir_ciudad);
        nombre = findViewById(R.id.nombre);
        lon = findViewById(R.id.lon);
        lat = findViewById(R.id.lat);
        url = findViewById(R.id.url);

        anyadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("nombre", nombre.getText().toString());
                intent.putExtra("lon", Float.parseFloat(lon.getText().toString()));
                intent.putExtra("lat", Float.parseFloat(lat.getText().toString()));
                intent.putExtra("url", url.getText().toString());
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("nombre", nombre.getText().toString());
        outState.putString("lon", lon.getText().toString());
        outState.putString("lat", lat.getText().toString());
        outState.putString("url", url.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        nombre.setText(savedInstanceState.getString("nombre"));
        lon.setText(savedInstanceState.getString("lon"));
        lat.setText(savedInstanceState.getString("lat"));
        url.setText(savedInstanceState.getString("url"));
    }
}