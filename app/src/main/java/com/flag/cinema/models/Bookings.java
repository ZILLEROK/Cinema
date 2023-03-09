package com.flag.cinema.models;

public class Bookings {
    private String id;
    private String title;
    private String date;
    private String seats;
    private String user;
    private String seat_index;
    private int movie_id;

    public Bookings(String id, String title, String date, String seats, String user, String seat_index, int movie_id) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.seats = seats;
        this.user = user;
        this.seat_index = seat_index;
        this.movie_id = movie_id;
    }

    public String getSeat_index() {
        return seat_index;
    }

    public void setSeat_index(String seat_index) {
        this.seat_index = seat_index;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

}
