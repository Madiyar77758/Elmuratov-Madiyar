package synchronization;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class BankAccount {
    private final ReentrantLock lock = new ReentrantLock();
    private final String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    // Метод для перевода денег между счетами с защитой от deadlock
    public boolean transfer(BankAccount to, double amount) {
        BankAccount firstLock = this.hashCode() < to.hashCode() ? this : to;
        BankAccount secondLock = this.hashCode() < to.hashCode() ? to : this;

        boolean firstLockAcquired = false;
        boolean secondLockAcquired = false;

        try {
            // Захват блокировок
            firstLockAcquired = firstLock.lock.tryLock(1, TimeUnit.SECONDS);
            if (!firstLockAcquired) return false;

            secondLockAcquired = secondLock.lock.tryLock(1, TimeUnit.SECONDS);
            if (!secondLockAcquired) return false;

            if (this.balance >= amount) {
                this.balance -= amount;
                to.balance += amount;
                return true;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (secondLockAcquired) secondLock.lock.unlock();
            if (firstLockAcquired) firstLock.lock.unlock();
        }
        return false;
    }

    public double getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public String toString() {
        return String.format("Account[%s]: %.2f", accountNumber, getBalance());
    }
}
