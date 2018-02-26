package com.example.lcom151_two.retrofit;

/**
 * Created by lcom151-two on 2/26/2018.
 */

public class Hero {

    private String name,realname,team,firstAppearance,createdBy,publisher,imageUrl,bio;

    public Hero(String name,String realname,String team,String firstAppearance,String createdBy,String publisher,String imageUrl,String bio){
        this.name=name;
        this.realname=realname;
        this.team=team;
        this.firstAppearance=firstAppearance;
        this.createdBy=createdBy;
        this.publisher=publisher;
        this.imageUrl=imageUrl;
        this.bio=bio;
    }

    public String getName() {
        return name;
    }

    public String getRealname() {
        return realname;
    }

    public String getFirstAppearance() {
        return firstAppearance;
    }

    public String getTeam() {
        return team;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getBio() {
        return bio;
    }
}
