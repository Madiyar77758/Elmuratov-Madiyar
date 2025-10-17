package com.library;

public class Magazine extends Item {
    private int issueNumber;
    private String frequency;

    public Magazine(String title, String author, int year, boolean available, int issueNumber, String frequency) {
        super(title, author, year, available);
        this.issueNumber = issueNumber;
        this.frequency = frequency;
    }


    public int getIssueNumber() {
        return issueNumber;
    }

    public String getFrequency() {
        return frequency;
    }


    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "Magazine: " + super.toString() + String.format(" | issue #%d, frequency: %s", issueNumber, frequency);
    }
}