package com.flag.cinema;


import java.io.Serializable;

public class Movie implements Serializable {
   private String name, type, length,category,rating,trile,relaDate, date_time, description;
    int imgUrl, id, price;

    public Movie(String name, String type, String length, String category, String rating, String trile, String relaDate, String date_time, String description, int imgUrl, int id, int price) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.category = category;
        this.rating = rating;
        this.trile = trile;
        this.relaDate = relaDate;
        this.date_time = date_time;
        this.description = description;
        this.imgUrl = imgUrl;
        this.id = id;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setTrile(String trile) {
        this.trile = trile;
    }

    public void setRelaDate(String relaDate) {
        this.relaDate = relaDate;
    }

    public String getRating() {
        return rating;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLength() {
        return length;
    }


    public String getCategory() {
        return category;
    }



    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }





    public String getTrile() {
        return trile;
    }

    public String getRelaDate() {
        return relaDate;
    }
}
