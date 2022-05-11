package com.dam.proyectodamdaw.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dam.proyectodamdaw.Parameters;
import com.dam.proyectodamdaw.R;
import com.dam.proyectodamdaw.base.ImageDownloader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class VisualizeDataActivity extends AppCompatActivity {

    //com.dam.proyectodamdaw.activities.List list;
    Root root;
    ImageView weatherImg;
    TextView date, hour, weatherText, tempText, maxTempText, minTempText, feelsLikeText, humidityText, windSpeedText, pressureText, seaLevelText;
    ImageView pre, post;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualize_data2);

        //list = (List) getIntent().getExtras().getSerializable("info");
        root = (Root) getIntent().getExtras().getSerializable("info");
        position = getIntent().getExtras().getInt("position");

        weatherImg = findViewById(R.id.vd_weatherImg);
        date = findViewById(R.id.vd_date);
        hour = findViewById(R.id.vd_hour);
        weatherText = findViewById(R.id.vd_weatherText);
        tempText = findViewById(R.id.vd_tempText);
        maxTempText = findViewById(R.id.vd_tempMaxText);
        minTempText = findViewById(R.id.vd_tempMinText);
        feelsLikeText = findViewById(R.id.vd_feelsLikeText);
        humidityText = findViewById(R.id.vd_humidityText);
        windSpeedText = findViewById(R.id.vd_windSpeedText);
        pressureText = findViewById(R.id.vd_pressureText);
        seaLevelText = findViewById(R.id.vd_seaLevelText);

        setTexts();

        pre = findViewById(R.id.pre);
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position > 0) {
                    position--;
                    setTexts();
                }
            }
        });

        post = findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < (root.list.size() - 1)) {
                    position++;
                    setTexts();
                }
            }
        });
    }

    private void setTexts() {
        String iconUrl = root.list.get(position).weather.get(0).icon;
        ImageDownloader.DownloadImage(getApplicationContext(), Parameters.URL_ICON_PRE + iconUrl + Parameters.URL_ICON_POST, weatherImg, weatherImg.getScaleType(), R.mipmap.ic_launcher);


        Date d_date = new Date(root.list.get(position).dt * 1000L);
        date.setText(new SimpleDateFormat("dd/MM/yyyy").format(d_date));
        hour.setText(new SimpleDateFormat("HH:mm").format(d_date));

        weatherText.setText(root.list.get(position).weather.get(0).description);
        tempText.setText(root.list.get(position).main.temp + "ºC");
        maxTempText.setText(root.list.get(position).main.temp_max + "ºC");
        minTempText.setText(root.list.get(position).main.temp_min + "ºC");
        feelsLikeText.setText(root.list.get(position).main.feels_like + "ºC");
        humidityText.setText(root.list.get(position).main.humidity + "%");
        windSpeedText.setText(root.list.get(position).wind.speed + "km/h");
        pressureText.setText(root.list.get(position).main.pressure + "");
        seaLevelText.setText(root.list.get(position).main.sea_level + "");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("root", root);
        outState.putInt("position", position);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        root = (Root) savedInstanceState.getSerializable("root");
        position = savedInstanceState.getInt("position");
    }
}