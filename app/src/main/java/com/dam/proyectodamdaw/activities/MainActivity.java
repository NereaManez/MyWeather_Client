package com.dam.proyectodamdaw.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.proyectodamdaw.Place;
import com.dam.proyectodamdaw.api.Connector;
import com.dam.proyectodamdaw.base.BaseActivity;
import com.dam.proyectodamdaw.base.CallInterface;
import com.dam.proyectodamdaw.R;

public class MainActivity extends BaseActivity implements CallInterface, View.OnClickListener {

    Place place;
    Root root;
    private RecyclerView recyclerView;
    private TextView city;

    private final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_main);

        place = (Place) getIntent().getExtras().getSerializable("place");

        city = findViewById(R.id.cityName);
        city.setText(place.getName());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (root == null) {
            showProgress();
            executeCall(this);
        }

    }

    @Override
    protected void executeCall(CallInterface callInterface) {
        super.executeCall(callInterface);
    }

    @Override
    public void doInBackground() {
        try {
            root = Connector.getConector().get(Root.class, "forecast?lang=es&units=metric&lat=" + place.getLat() + "&lon=" + place.getLon() + "&appid=f3b94afa643f49402fe82ea34ad02404");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doInUI() {
        hideProgress();
        recyclerView = findViewById(R.id.recyclerView);

        MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(this, root);
        myRecyclerViewAdapter.setOnClickListener(this);
        recyclerView.setAdapter(myRecyclerViewAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, VisualizeDataActivity.class);
        //intent.putExtra("info", root.list.get(recyclerView.getChildAdapterPosition(v)));
        intent.putExtra("info", root);
        intent.putExtra("position", recyclerView.getChildAdapterPosition(v));

        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("root", root);
        outState.putSerializable("place", place);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        root = (Root) savedInstanceState.getSerializable("root");
        place = (Place) savedInstanceState.getSerializable("place");
        if (root != null)
            doInUI();
    }
}