package com;

import java.io.*;
import java.util.*;

public class FileProcessor implements Runnable {
    private String filename;
    private List<SalesRecord> results;
    private volatile boolean completed;
    private volatile String errorMessage;

    public FileProcessor(String filename) {
        this.filename = filename;
        this.results = new ArrayList<>();
        this.completed = false;
        this.errorMessage = null;
    }

    @Override
    public void run() {
        System.out.println("[" + Thread.currentThread().getName() + "] Начало обработки файла: " + filename);
        long startTime = System.currentTimeMillis();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            reader.readLine();  // Пропустить заголовок

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int productId = Integer.parseInt(parts[0]);
                    String productName = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    double price = Double.parseDouble(parts[3]);
                    String date = parts[4];

                    SalesRecord record = new SalesRecord(productId, productName, quantity, price, date);
                    results.add(record);
                }
            }
            completed = true;
        } catch (IOException | NumberFormatException e) {
            errorMessage = "Ошибка обработки файла: " + e.getMessage();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("[" + Thread.currentThread().getName() + "] Обработка завершена: " + filename + " за " + (endTime - startTime) + " мс");
    }

    public List<SalesRecord> getResults() { return results; }
    public boolean isCompleted() { return completed; }
    public String getErrorMessage() { return errorMessage; }
    public String getFilename() { return filename; }
}
