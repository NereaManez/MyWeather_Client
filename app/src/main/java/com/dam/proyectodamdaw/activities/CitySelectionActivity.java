package com.dam.proyectodamdaw.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.dam.proyectodamdaw.Parameters;
import com.dam.proyectodamdaw.Place;
import com.dam.proyectodamdaw.R;
import com.dam.proyectodamdaw.api.Connector;
import com.dam.proyectodamdaw.base.BaseActivity;
import com.dam.proyectodamdaw.base.CallInterface;
import com.dam.proyectodamdaw.base.ImageDownloader;
import com.dam.proyectodamdaw.base.ThemeSetup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class CitySelectionActivity extends BaseActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, CallInterface {

    private static final int ACTIVITY_CODE = 1234;
    private ArrayAdapter<Place> adapter;
    private Place selectedPlace;
    private ImageView imageView;
    private Button button;
    private FloatingActionButton anyadir;
    private Spinner spinner;
    ArrayList<Place> places = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selection);

        ThemeSetup.applyPreferenceTheme(getApplicationContext());

        imageView = findViewById(R.id.cityImage);
        button = findViewById(R.id.buscar);
        button.setOnClickListener(this);
        anyadir = findViewById(R.id.anyadir);
        anyadir.setOnClickListener(this);
        spinner = findViewById(R.id.citySpinner);

        executeCall(this);
        /*
        places.add(new Place("Valencia", 39.4590962f, -0.3646892f, "https://e00-elmundo.uecdn.es/assets/multimedia/imagenes/2022/02/22/16455200722788.jpg"));
        places.add(new Place("Madrid", 40.4378698f, -3.8196191f, "https://media.istockphoto.com/photos/madrid-city-skyline-gran-via-street-twilight-spain-picture-id1059076792?b=1&k=20&m=1059076792&s=170667a&w=0&h=GjjyWsu21ysot4WgNtFN9TK1K93j4vc12kArpfOjX70="));
        places.add(new Place("Barcelona", 41.3926467f, 2.0701492f, "https://res.cloudinary.com/hello-tickets/image/upload/c_limit,f_auto,q_auto,w_1300/v1613390330/yr7lbmhskelyavtfhxjd.jpg"));
        places.add(new Place("La Pobla de Vallbona", 39.5940799f, -0.5594087f, "https://comarcalcv.com/wp-content/uploads/2017/09/La-Pobla-de-Vallbona-1280x720.jpg"));
        places.add(new Place("Oyarzun", 43.3074317f, -1.8816381f, "https://static2.diariovasco.com/www/multimedia/202007/09/media/cortadas/0807-paseo-gipuzkoa-oiartzun-017-k04D-U110739809010n7B-1248x770@Diario%20Vasco.jpg"));
        */
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedPlace = (Place) parent.getSelectedItem();
        ImageDownloader.DownloadImage(selectedPlace.getImg(), imageView);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        parent.setSelection(0);

        selectedPlace = (Place) parent.getSelectedItem();
        ImageDownloader.DownloadImage(getApplicationContext(), selectedPlace.getImg(), imageView, imageView.getScaleType(), R.mipmap.ic_launcher);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == button.getId()) {
            Intent intent = new Intent(CitySelectionActivity.this, MainActivity.class);
            intent.putExtra("place", selectedPlace);

            startActivity(intent);
        } else if (v.getId() == anyadir.getId()) {
            Intent intent = new Intent(CitySelectionActivity.this, AddCityActivity.class);

            startActivityForResult(intent, ACTIVITY_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_CODE) {
            if (resultCode == RESULT_CANCELED)
                Toast.makeText(this, "Cancelado por el usuario", Toast.LENGTH_SHORT).show();
            else if (resultCode == RESULT_OK) {
                String nombre = data.getExtras().getString("nombre");
                Float lon = data.getExtras().getFloat("lon");
                Float lat = data.getExtras().getFloat("lat");
                String url = data.getExtras().getString("url");

                places.add(new Place(nombre, lon, lat, url));
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Creando " + nombre, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putSerializable("places", places);
        outState.putSerializable("place", selectedPlace);
        outState.putInt("spinnerPos", places.indexOf(spinner.getSelectedItem()));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //places = (ArrayList<Place>) savedInstanceState.getSerializable("places");
        selectedPlace = (Place) savedInstanceState.getSerializable("place");
        spinner.setSelection((int) savedInstanceState.getInt("spinnerPos"));
    }

    @Override
    public void doInBackground() {
        places = new ArrayList<>(Connector.getConector().getAsList(Place.class, "/city/all"));
    }

    @Override
    public void doInUI() {
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, places);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
    }
}