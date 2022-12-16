package com.example.qualityoflifeinthecity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;

public class FavoriteCitiesActivity extends AppCompatActivity
        implements DBManager.DataBaseListener, CitiesAdapter.ItemListener {

    CitiesAdapter adapter;
    ArrayList<City> favCityList = new ArrayList<>(0);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_cities);
        this.setTitle("My Favorite Cities");

        RecyclerView favList = findViewById(R.id.favcitiesList);
        adapter = new CitiesAdapter(this, favCityList);
        favList.setAdapter(adapter);
        adapter.listener = this;
        favList.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(new CitiesAdapter.OnItemForDeleteClickListener() {
            @Override
            public void onItemClick(int position) {
                //delete item from DB
                ((MyApp) getApplication()).dbManager.deleteACity(favCityList.get(position));
                //delete from Arraylist
                favCityList.remove(position);
                //notify adapter
                adapter.notifyItemRemoved(position);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MyApp) getApplication()).dbManager.getDB(this);
        ((MyApp) getApplication()).dbManager.getAllCities();
        ((MyApp) getApplication()).dbManager.listener = this;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent i = new Intent(this, SearchCities.class);
                startActivity(i);
                break;
        }
        return true;
    }

    @Override
    public void gettingCitiesCompleted(City[] list) {
        favCityList = new ArrayList(Arrays.asList(list));
        adapter.list = favCityList;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClicked(int position) {
        Intent i = new Intent(this, DetailsActivity.class);
        i.putExtra("city", favCityList.get(position));
        startActivity(i);
    }

    //empty implementations
    @Override
    public void deletingCityCompleted() {

    }

    @Override
    public void insertingCityCompleted() {

    }
}