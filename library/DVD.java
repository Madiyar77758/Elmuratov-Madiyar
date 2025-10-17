package com.library;

public class DVD extends Item {
    private int duration;
    private String director;

    public DVD(String title, String author, int year, boolean available, int duration, String director) {
        super(title, author, year, available);
        this.duration = duration;
        this.director = director;
    }

    public int getDuration() {
        return duration;
    }

    public String getDirector() {
        return director;
    }


    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return "DVD: " + super.toString() + String.format(" | %d min, director: %s", duration, director);
    }
}