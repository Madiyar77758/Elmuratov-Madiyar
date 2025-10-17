package com.library;
public class Book extends Item {
    private int pageCount;
    private String genre;

    public Book(String title, String author, int year, boolean available, int pageCount, String genre) {
        super(title, author, year, available);
        this.pageCount = pageCount;
        this.genre = genre;
    }


    public int getPageCount() {
        return pageCount;
    }

    public String getGenre() {
        return genre;
    }


    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book: " + super.toString() + String.format(" | %d pages, genre: %s", pageCount, genre);
    }
}