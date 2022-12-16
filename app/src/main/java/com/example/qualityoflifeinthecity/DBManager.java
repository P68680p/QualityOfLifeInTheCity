package com.example.qualityoflifeinthecity;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

public class DBManager {

    interface DataBaseListener {
        void insertingCityCompleted();
        void gettingCitiesCompleted(City[] list);
        void deletingCityCompleted();
    }

    DataBaseListener listener;
    CitiesDatabase cityDB;
    Handler dbHandler = new Handler(Looper.getMainLooper());

    //create only one instance of DataBase
    CitiesDatabase getDB(Context context) {
        if (cityDB == null)
            cityDB = Room.databaseBuilder(context, CitiesDatabase.class, "city_db").fallbackToDestructiveMigration().build();
        return cityDB;
    }

    void insertNewCityAsync(City t) {
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                cityDB.getDao().addNewFavCity(t);
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // notify main thread
                        listener.insertingCityCompleted();
                    }
                });
            }
        });
    }

    void getAllCities() {
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                City[] list = cityDB.getDao().getAllCities();
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // notify main thread
                        listener.gettingCitiesCompleted(list);
                    }
                });
            }
        });
    }

    void deleteACity(City dc) {
        MyApp.executorService.execute((new Runnable() {
            @Override
            public void run() {
                cityDB.getDao().deleteACity(dc);
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.deletingCityCompleted();
                    }
                });
            }
        }));
    }
}
