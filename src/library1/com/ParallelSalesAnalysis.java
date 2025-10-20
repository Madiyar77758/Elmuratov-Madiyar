package com;

import java.util.*;

public class ParallelSalesAnalysis {
    public static void main(String[] args) throws InterruptedException {

        String[] filenames = {
                "sales_branch1.csv",
                "sales_branch2.csv",
                "sales_branch3.csv"
        };

        // Запускаем параллельную обработку файлов
        long parallelTime = processParallel(filenames);
        System.out.println("Параллельная обработка заняла: " + parallelTime + " мс");
    }

    public static long processParallel(String[] filenames) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        List<FileProcessor> processors = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        // Создаем процессор для каждого файла и запускаем потоки
        for (String filename : filenames) {
            FileProcessor processor = new FileProcessor(filename);
            processors.add(processor);

            // Создаем и запускаем поток для каждого файла
            Thread thread = new Thread(processor);
            threads.add(thread);
            thread.start();
        }

        // Ожидаем завершения всех потоков
        for (Thread thread : threads) {
            thread.join();
        }

        // Собираем статистику из всех процессоров
        SalesStatistics statistics = new SalesStatistics();
        for (FileProcessor processor : processors) {
            if (processor.isCompleted()) {
                processor.getResults().forEach(statistics::addRecord);
            } else {
                System.err.println("Ошибка обработки файла " + processor.getFilename() + ": " + processor.getErrorMessage());
            }
        }

        statistics.printReport();

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
