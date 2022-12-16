package com.example.qualityoflifeinthecity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements NetworkingService.NetworkingListener {

    DetailsObject det_obj;
    Score scr;
    ImageView cityImg;
    TextView descText;
    Button buttonToScore;
    City c;
    ArrayList<Score> list;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("--------> inside of DetailsActivity -> onCreate(Bundle savedInstanceState)");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        cityImg = findViewById(R.id.cityImage);
        descText = findViewById(R.id.cityDesc);
        buttonToScore = findViewById(R.id.buttonDetail);

        if (getIntent().hasExtra("city")) {
//            System.out.println("--------> inside of DetailsActivity -> onCreate(Bundle savedInstanceState) -> if");
            c = getIntent().getParcelableExtra("city");
            this.setTitle(c.city + " " + c.country);
//            System.out.println("-------> c.id = " + c.id);
//            System.out.println("-------> c.geoname_id = " + c.geoname_id);

            ((MyApp) getApplication()).networkingService.listener = this;
            ((MyApp) getApplication()).networkingService.getUrbanArea(c.geoname_id);
        }


        buttonToScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailsActivity.this, ScoreActivity.class);
//                i.putExtra("city", list);
                startActivity(i);
//                Toast. makeText(getApplicationContext(),"Hello Javatpoint",Toast. LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        System.out.println("--------> inside of DetailsActivity -> onResume()");
        super.onResume();

    }

    @Override
    public void getUrbanAreaFromGeonameIdIsCompleted(String json) {
//        System.out.println("--------> inside of DetailsActivity -> getUrbanAreaFromGeonameIdIsCompleted(String json)");
//        System.out.println("-----------> getUrbanAreaFromGeonameIdIsCompleted(String json) parametr json = " + json);
        String urban_area_link = JsonService.getUrbanAreaLinkFromJSON(json);
//        System.out.println("-------> getUrbanAreaFromGeonameIdIsCompleted(String json) String urban_area = " + urban_area_link);
        ((MyApp) getApplication()).networkingService.getDetailsLinksFromUrbanArea(urban_area_link);
    }

    @Override
    public void getDetailsLinksFromUrbanAreaIsCompleted(String json) {
//        System.out.println("-------->getDetailsLinksFromUrbanArea callFromUrban json= " + json);
        det_obj = JsonService.fromJsonToDetailsLinks(json);
        ((MyApp) getApplication()).networkingService.getImgLinkFromDetailsLinks(det_obj.getCityImage_link());
        ((MyApp) getApplication()).networkingService.getScoreFromLink(det_obj.getScore_link());
    }

    @Override
    public void getImgLinkFromDetail(String img) {
//        System.out.println("-------->getImgLinkFromDetail parameter= " + img);
        det_obj.setImage(JsonService.fromJsonToImage(img));
        ((MyApp) getApplication()).networkingService.gettingImage(det_obj.getImage());

    }

    @Override
    public void getScorLinkFromDetail(String scor) {
        ArrayList<Score> srcList = JsonService.getScoreListFromJSON(scor);
        String summary = JsonService.getSummaryFromJSON(scor);
        String clearSummary = summary.replaceAll("<.*?>", "");
        descText.setText(clearSummary);
      ((MyApp) getApplication()).setListOfScores(srcList);
// *********************************************************

//        s.toString(list);
    }

    @Override
    public void gettingImageIsCompleted(Bitmap image) {
//        System.out.println("--------> inside of DetailsActivity -> gettingImageIsCompleted(Bitmap image)");
        cityImg.setImageBitmap(image);
    }

    //empty implementations
    @Override
    public void getSearchCitiesIsCompleted(String json) {
    }

    @Override
    public void getSalLinkFromDetail(String sal) {
    }
}