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
        System.out.println("-------> inside of FavoriteCitiesActivity -> onCreate(Bundle savedInstanceState)");
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
                System.out.println("-------> inside of FavoriteCitiesActivity -> onCreate(Bundle savedInstanceState) -> onItemClick(int position)");
                //delete item from DB
                System.out.println("----------------------> favCityList.get(position).city= " + favCityList.get(position).city);
                System.out.println("----------------------> favCityList.get(position).id= " + favCityList.get(position).id);
                ((MyApp) getApplication()).dbManager.deleteACity(favCityList.get(position));
                //now delete from Arraylist
                favCityList.remove(position);
                System.out.println("------->remove item from favoritelist");
                //then notify adapter
                adapter.notifyItemRemoved(position);
                System.out.println("------->notify adapter");

            }
        });
    }

    @Override
    protected void onResume() {
        System.out.println("--------> inside of FavoriteCitiesActivity -> onResume()");
        super.onResume();
        ((MyApp) getApplication()).dbManager.getDB(this);
        ((MyApp) getApplication()).dbManager.getAllCities();
        ((MyApp) getApplication()).dbManager.listener = this;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        System.out.println("--------> inside of FavoriteCitiesActivity -> onCreateOptionsMenu(Menu menu)");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        System.out.println("--------> inside of FavoriteCitiesActivity -> onOptionsItemSelected(MenuItem item)");
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
        System.out.println("--------> inside of FavoriteCitiesActivity -> gettingCitiesCompleted(City[] list)");
        favCityList = new ArrayList(Arrays.asList(list));
        adapter.list = favCityList;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClicked(int position) {
        System.out.println("-------> inside of FavoriteCitiesActivity -> onClicked(int pos)");
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