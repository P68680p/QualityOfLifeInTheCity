package com.example.qualityoflifeinthecity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonService {

    static ArrayList<City> getCityListFromJSON(String jsonString) {
        System.out.println("--------> inside of JsonService -> getCityListFromJSON");
        ArrayList<City> list = new ArrayList<>(0);
        try {
            JSONObject json = new JSONObject(jsonString).getJSONObject("_embedded");
            JSONArray jsonArray = json.getJSONArray("city:search-results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject name = jsonArray.getJSONObject(i);
                String city = name.getString("matching_full_name");
                JSONObject geoJson = name.getJSONObject("_links").getJSONObject("city:item");
                String geo = geoJson.getString("href");
                list.add(new City(geo, city));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    static String getUrbanAreaLinkFromJSON(String jsonString) { //geoname_id
        System.out.println("--------> inside of JsonService -> getUrbanAreaLinkFromJSON(String jsonString)");
        String ua = "";
        try {
            JSONObject jsonObject = new JSONObject(jsonString).getJSONObject("_links").getJSONObject("city:urban_area");
            ua = jsonObject.getString("href");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ua;
    }

    static DetailsObject fromJsonToDetailsLinks(String jsonString) {
        System.out.println("--------> inside of JsonService -> fromJsonToDetailsLinks(String jsonString)");
        System.out.println("--------> fromJsonToDetails parameter jsonString = " + jsonString);
        DetailsObject detObj = new DetailsObject();

        try {
            JSONObject jsonObjectImg = new JSONObject(jsonString).getJSONObject("_links").getJSONObject("ua:images");
            detObj.setCityImage_link(jsonObjectImg.getString("href"));

            JSONObject jsonObjectSal = new JSONObject(jsonString).getJSONObject("_links").getJSONObject("ua:salaries");
            detObj.setSalary_link(jsonObjectSal.getString("href"));

            JSONObject jsonObjectScor = new JSONObject(jsonString).getJSONObject("_links").getJSONObject("ua:scores");
            detObj.setScore_link(jsonObjectScor.getString("href"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(detObj.showDetailsLinks());
        return detObj;
    }

    static String fromJsonToImage(String jsonString) {
        String image = "";
        try {
            image = new JSONObject(jsonString).getJSONArray("photos").getJSONObject(0).getJSONObject("image").getString("mobile");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return image;
    }

    static ArrayList<Score> getScoreListFromJSON(String jsonString) {
        System.out.println("--------> inside of JsonService -> getScoreListFromJSON");
        ArrayList<Score> scoreList = new ArrayList<>(0);
        try {
            JSONArray json = new JSONObject(jsonString).getJSONArray("categories");
            for (int i = 0; i < json.length(); i++) {
                JSONObject catObj = json.getJSONObject(i);
                String name = catObj.getString("name");
                double score = catObj.getDouble("score_out_of_10");
                scoreList.add(new Score(name, score));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return scoreList;
    }

    static String getSummaryFromJSON(String jsonString) {
        //get summary from the same link scores
        String summary = "";
        try {
            summary = new JSONObject(jsonString).getString("summary");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return summary;
    }
}
