package com.library;
import java.util.ArrayList;
import java.util.List;

public class LibraryDemo {
    public static void main(String[] args) {

        List<Item> items = new ArrayList<>();

        // Добавляем объекты с уникальными данными
        items.add(new Book("The Art of Computer Programming", "Donald E. Knuth", 1968, true, 672, "Algorithms"));
        items.add(new Magazine("Time", "Henry Luce", 2024, true, 130, "Weekly"));
        items.add(new DVD("The Matrix", "Warner Bros.", 1999, true, 136, "Lana Wachowski, Lilly Wachowski"));

        System.out.println("=== Catalog ===");
        for (Item item : items) {
            System.out.println(item);
        }

        System.out.println("\n=== Borrow flow ===");
        for (Item item : items) {
            boolean success = item.borrowItem();
            System.out.printf("Attempt to borrow '%s' -> %s%n", item.getTitle(), success ? "Success" : "Already Borrowed");
        }

        items.get(0).returnItem();


        System.out.println("\n=== After return ===");
        for (Item item : items) {
            System.out.println(item);
        }
    }
}