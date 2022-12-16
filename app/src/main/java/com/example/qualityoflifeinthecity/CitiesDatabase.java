package com.example.qualityoflifeinthecity;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 2,entities = {City.class})
public abstract class CitiesDatabase extends RoomDatabase {
    public abstract CityDao getDao();
}
