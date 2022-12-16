package com.example.qualityoflifeinthecity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class SearchCities extends AppCompatActivity implements NetworkingService.NetworkingListener,
        SearchCitiesAdapter.ItemClickedListener, DBManager.DataBaseListener {

    RecyclerView searchCityList;
    SearchCitiesAdapter search_adapter;
    ArrayList<City> list = new ArrayList<>(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cities);

        ((MyApp) getApplication()).networkingService.listener = this;
        searchCityList = findViewById(R.id.search_cities_list);
        search_adapter = new SearchCitiesAdapter(this, list);
        this.setTitle("Search For Cities....");
        search_adapter.search_listener = this;
        ((MyApp) getApplication()).dbManager.listener = this;
        ((MyApp) getApplication()).dbManager.getDB(this);   //create one instance DB
        searchCityList.setAdapter(search_adapter);
        searchCityList.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.city_search_menu, menu);
        MenuItem searchViewMenu = menu.findItem(R.id.city_searchview);

        SearchView searchView = (SearchView) searchViewMenu.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() >= 3) {
                    ((MyApp) getApplication()).networkingService.getAllCities(newText);
                } else {
                    search_adapter.list = new ArrayList<>(0);
                    search_adapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public void getSearchCitiesIsCompleted(String json) {
        // json is a string ==> Json Array of Strings
        // for Recycler view I need ArrayList <City>
        list = JsonService.getCityListFromJSON(json);
        search_adapter.list = list;
        search_adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(int post) {
        // show an alert
        // save the city
        showAlert(list.get(post));
    }

    void showAlert(City city) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hello!");
        builder.setMessage("Would you like to save " + city.city + " to the list of your favorite cities?");
        builder.setNegativeButton("No", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ((MyApp) getApplication()).dbManager.insertNewCityAsync(city);
            }
        });
        builder.create().show();
    }

    @Override
    public void insertingCityCompleted() {
        finish();
    }

    //empty implementations
    @Override
    public void gettingCitiesCompleted(City[] list) {
    }

    @Override
    public void deletingCityCompleted() {
    }

    @Override
    public void gettingImageIsCompleted(Bitmap image) {
    }

    @Override
    public void getDetailsLinksFromUrbanAreaIsCompleted(String json) {
    }

    @Override
    public void getImgLinkFromDetail(String img) {
    }

    @Override
    public void getScorLinkFromDetail(String scor) {
    }

    @Override
    public void getUrbanAreaFromGeonameIdIsCompleted(String json) {
    }
}