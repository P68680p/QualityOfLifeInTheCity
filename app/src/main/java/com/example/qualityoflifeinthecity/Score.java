package com.example.qualityoflifeinthecity;

import java.util.ArrayList;

public class Score {
//    String category_color;
    String category_name;
    double score_out_of_10;

    ArrayList<Score> list;

//    public Score() {
//    }

    public Score(String category_name, double score_out_of_10) {

        this.category_name = category_name;
        this.score_out_of_10 = score_out_of_10;
    }

//    public void toString(ArrayList<Score> list){
//        for(int i=0; i<list.size();i++){
//            Score s = list.get(i);
//            System.out.println(s.category_color +", "+ s.category_name+", "+s.score_out_of_10);
//        }
//    }

}
