package com;

import java.util.*;

public class SalesStatistics {
    private int totalRecords;
    private double totalRevenue;
    private Map<String, Integer> productQuantities;
    private Map<String, Double> productRevenues;

    public SalesStatistics() {
        this.totalRecords = 0;
        this.totalRevenue = 0.0;
        this.productQuantities = new HashMap<>();
        this.productRevenues = new HashMap<>();
    }

    public void addRecord(SalesRecord record) {
        totalRecords++;
        totalRevenue += record.getTotalAmount();

        productQuantities.merge(record.getProductName(), record.getQuantity(), Integer::sum);
        productRevenues.merge(record.getProductName(), record.getTotalAmount(), Double::sum);
    }

    public void merge(SalesStatistics other) {
        totalRecords += other.totalRecords;
        totalRevenue += other.totalRevenue;

        other.productQuantities.forEach((product, qty) -> productQuantities.merge(product, qty, Integer::sum));
        other.productRevenues.forEach((product, revenue) -> productRevenues.merge(product, revenue, Double::sum));
    }

    public void printReport() {
        System.out.println("\n=== ОТЧЕТ ПО ПРОДАЖАМ ===");
        System.out.println("Всего записей обработано: " + totalRecords);
        System.out.printf("Общая выручка: %.2f тг\n", totalRevenue);

        System.out.println("\n--- Топ 5 товаров по количеству ---");
        productQuantities.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> System.out.printf("%s: %d шт.\n", entry.getKey(), entry.getValue()));

        System.out.println("\n--- Топ 5 товаров по выручке ---");
        productRevenues.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> System.out.printf("%s: %.2f тг\n", entry.getKey(), entry.getValue()));
    }
}
