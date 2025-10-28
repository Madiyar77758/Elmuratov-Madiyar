package synchronization;

public class BankTransferTest {
    public static void main(String[] args) throws InterruptedException {
        BankAccount accountA = new BankAccount("BANK-A", 1000.0);
        BankAccount accountB = new BankAccount("BANK-B", 1000.0);
        BankAccount accountC = new BankAccount("BANK-C", 1000.0);

        System.out.println("Начальное состояние:");
        System.out.println(accountA);
        System.out.println(accountB);
        System.out.println(accountC);
        System.out.println();

        // Потоки для перевода денег между счетами
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                if (accountA.transfer(accountB, 25)) {
                    System.out.println(Thread.currentThread().getName() + ": Перевел 25.00 с " + accountA.getAccountNumber() + " на " + accountB.getAccountNumber() + " выполнено успешно.");
                } else {
                    System.out.println(Thread.currentThread().getName() + ": Не удалось получить блокировку для счета " + accountA.getAccountNumber());
                }
                try { Thread.sleep(10); } catch (InterruptedException e) {}
            }
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                if (accountB.transfer(accountA, 40)) {
                    System.out.println(Thread.currentThread().getName() + ": Перевел 40.00 с " + accountB.getAccountNumber() + " на " + accountA.getAccountNumber() + " выполнено успешно.");
                } else {
                    System.out.println(Thread.currentThread().getName() + ": Не удалось получить блокировку для счета " + accountB.getAccountNumber());
                }
                try { Thread.sleep(10); } catch (InterruptedException e) {}
            }
        }, "Thread-2");

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                if (accountA.transfer(accountC, 20)) {
                    System.out.println(Thread.currentThread().getName() + ": Перевел 20.00 с " + accountA.getAccountNumber() + " на " + accountC.getAccountNumber() + " выполнено успешно.");
                } else {
                    System.out.println(Thread.currentThread().getName() + ": Не удалось получить блокировку для счета " + accountA.getAccountNumber());
                }
                try { Thread.sleep(10); } catch (InterruptedException e) {}
            }
        }, "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("\nКонечное состояние:");
        System.out.println(accountA);
        System.out.println(accountB);
        System.out.println(accountC);

        // Проверка: сумма всех счетов должна оставаться 3000
        double total = accountA.getBalance() + accountB.getBalance() + accountC.getBalance();
        System.out.println("\nОбщая сумма: " + total + " (должно быть 3000.0)");
    }
}
