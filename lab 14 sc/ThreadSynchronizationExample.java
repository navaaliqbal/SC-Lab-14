class Counter {
    private int count = 0;

    // Synchronized method to increment the counter
    public synchronized void increment() {
        count++;
    }

    // Method to get the final counter value
    public int getCount() {
        return count;
    }
}

class CounterIncrementer extends Thread {
    private Counter counter;

    public CounterIncrementer(Counter counter) {
        this.counter = counter;
    }

    public void run() {
        for (int i = 0; i < 100; i++) {
            counter.increment();
            try {
                Thread.sleep(10); // Optional: Simulate context switching
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

public class ThreadSynchronizationExample {
    public static void main(String[] args) {
        Counter counter = new Counter(); // Shared resource

        // Create three threads
        Thread thread1 = new CounterIncrementer(counter);
        Thread thread2 = new CounterIncrementer(counter);
        Thread thread3 = new CounterIncrementer(counter);

        // Start the threads
        thread1.start();
        thread2.start();
        thread3.start();

        // Wait for all threads to finish
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        // Print the final counter value
        System.out.println("Final Counter Value: " + counter.getCount());
    }
}
