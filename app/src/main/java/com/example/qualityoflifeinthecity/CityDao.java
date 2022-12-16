package com.example.qualityoflifeinthecity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CityDao {

    @Insert
    void addNewFavCity(City c);

    @Delete
    void deleteACity(City dc);

    @Query("select * from City")
    City[] getAllCities();

}