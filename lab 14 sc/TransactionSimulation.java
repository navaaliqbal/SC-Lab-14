import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionSimulator {
    public static void main(String[] args) {
        SharedAccount sharedAccount = new SharedAccount(); // Shared bank account object

        // Create multiple user threads
        Thread user1 = new Thread(new User(sharedAccount));
        Thread user2 = new Thread(new User(sharedAccount));
        Thread user3 = new Thread(new User(sharedAccount));

        // Start the threads
        user1.start();
        user2.start();
        user3.start();

        // Wait for all threads to finish
        try {
            user1.join();
            user2.join();
            user3.join();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

        // Print the final balance
        System.out.println("Final Account Balance: " + sharedAccount.getCurrentBalance());
    }
}

// SharedAccount class with thread-safe operations
class SharedAccount {
    private AtomicInteger currentBalance = new AtomicInteger(0); // Atomic integer for thread-safe operations

    // Deposit method
    public void addFunds(int amount) {
        currentBalance.addAndGet(amount);
        System.out.println(Thread.currentThread().getName() + " deposited: " + amount);
    }

    // Withdraw method
    public void subtractFunds(int amount) {
        if (currentBalance.get() >= amount) {
            currentBalance.addAndGet(-amount);
            System.out.println(Thread.currentThread().getName() + " withdrew: " + amount);
        } else {
            System.out.println(Thread.currentThread().getName() + " insufficient funds to withdraw: " + amount);
        }
    }

    // Getter for currentBalance
    public int getCurrentBalance() {
        return currentBalance.get();
    }
}

// User class simulates random transactions
class User implements Runnable {
    private SharedAccount sharedAccount;

    public User(SharedAccount sharedAccount) {
        this.sharedAccount = sharedAccount;
    }

    @Override
    public void run() {
        Random randomGenerator = new Random();

        for (int transaction = 0; transaction < 10; transaction++) { // Each user performs 10 transactions
            int transactionType = randomGenerator.nextInt(2); // 0 for deposit, 1 for withdraw
            int transactionAmount = randomGenerator.nextInt(100) + 1; // Random amount between 1 and 100

            if (transactionType == 0) {
                sharedAccount.addFunds(transactionAmount);
            } else {
                sharedAccount.subtractFunds(transactionAmount);
            }

            try {
                Thread.sleep(200); // Simulate delay between transactions
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
}
