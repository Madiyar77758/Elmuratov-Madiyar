package com.library;

public class Item {
    private String title;
    private String author;
    private int year;
    private boolean available;

    public Item(String title, String author, int year, boolean available) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.available = available;
    }

    public Item() {
        this("Untitled", "Unknown", 2000, true);
    }


    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public boolean isAvailable() {
        return available;
    }

    // Сеттеры
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return String.format("%s — %s (%d) | %s",
                title, author, year, available ? "available" : "borrowed");
    }

    public boolean borrowItem() {
        return false;
    }

    public void returnItem() {
    }
}
