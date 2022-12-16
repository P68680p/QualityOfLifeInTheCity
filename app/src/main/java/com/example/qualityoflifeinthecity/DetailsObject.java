package com.example.qualityoflifeinthecity;

import java.lang.reflect.Array;

public class DetailsObject {

    private String cityImage_link;
    private String salary_link;
    private String score_link;
    private String image;
    private Score[] scoreslist = new Score[16];

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Score[] getScoreslist() {
        return scoreslist;
    }

    public void setScoreslist(Score[] scoreslist) {
        this.scoreslist = scoreslist;
    }

    public String getCityImage_link() {
        return cityImage_link;
    }

    public void setCityImage_link(String cityImage_link) {
        this.cityImage_link = cityImage_link;
    }

    public String getSalary_link() {
        return salary_link;
    }

    public void setSalary_link(String salary_link) {
        this.salary_link = salary_link;
    }

    public String getScore_link() {
        return score_link;
    }

    public void setScore_link(String score_link) {
        this.score_link = score_link;
    }

    public DetailsObject() {
        System.out.println("--------> inside of DetailsObject -> constructor");
    }

    public String showDetailsLinks() {
        return "**************************DetailsObject{" +
                "cityImage_link='" + cityImage_link + '\'' +
                ", salary_link='" + salary_link + '\'' +
                ", score_link='" + score_link + '\'' +
                '}';
    }
}
