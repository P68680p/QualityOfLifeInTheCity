package com.example.qualityoflifeinthecity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkingService {
    // fetch all cities
    // fetch quality of life in one city

    interface NetworkingListener {
        void getSearchCitiesIsCompleted(String json);
        void getUrbanAreaFromGeonameIdIsCompleted(String geoname);
        void getDetailsLinksFromUrbanAreaIsCompleted(String ua);
        void getImgLinkFromDetail(String img);
        void getScorLinkFromDetail(String scor);
        void gettingImageIsCompleted(Bitmap image);
    }

    NetworkingListener listener;
    Handler handler = new Handler(Looper.getMainLooper());

    //URLs
    String cityURLString = "https://api.teleport.org/api/cities/?search=";

    void getAllCities(String query) {
        String fullString = cityURLString + query;
        connect(fullString, "");
    }

    void getUrbanArea(String geoname_id){
        connect(geoname_id, "callFromGeoname");
    }

    void getDetailsLinksFromUrbanArea(String urbanArea) {
        connect(urbanArea, "callFromUrban");
    }

    void getImgLinkFromDetailsLinks(String imgLink){
        connect(imgLink,"callImgFromDetail");
    }

    void getScoreFromLink(String scoreLink){
        connect(scoreLink,"callScoreFromDetail");
    }

    void connect(String urlString, String myCallBack) {
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    int value = 0;
                    URL url = new URL(urlString);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    StringBuffer buffer = new StringBuffer();
                    while ((value = in.read()) != -1) {
                        buffer.append((char) value);
                    }
                    // the json content is ready to returned back
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (myCallBack=="callFromGeoname"){
                                listener.getUrbanAreaFromGeonameIdIsCompleted(buffer.toString());
                            }
                            else if(myCallBack=="callFromUrban"){
                                listener.getDetailsLinksFromUrbanAreaIsCompleted(buffer.toString());
                            }
                            else if(myCallBack=="callImgFromDetail"){
                                listener.getImgLinkFromDetail(buffer.toString());
                            }
                            else if(myCallBack=="callScoreFromDetail"){
                                listener.getScorLinkFromDetail(buffer.toString());
                            }
                            else
                            {
                                listener.getSearchCitiesIsCompleted(buffer.toString());
                            }
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }
            }
        });
    }

    void gettingImage(String icon) {
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    int value = 0;
                    URL url = new URL(icon);
                    InputStream in = url.openStream();
                    Bitmap imageData = BitmapFactory.decodeStream(in);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.gettingImageIsCompleted(imageData);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
