package com.example.qualityoflifeinthecity;

import android.app.Application;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApp extends Application {

    static ExecutorService executorService = Executors.newFixedThreadPool(4);

    NetworkingService networkingService = new NetworkingService();

    DBManager dbManager = new DBManager();

    private ArrayList<Score> listOfScores;
    public ArrayList<Score> getList(){
        if (listOfScores == null) {
            listOfScores = new ArrayList<>(0);
        }
        return listOfScores;
    }

    public void setListOfScores(ArrayList<Score> listOfScores) {
        this.listOfScores = listOfScores;
    }
}
