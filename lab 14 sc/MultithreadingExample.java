class PrintNumbers extends Thread {
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Number: " + i);
            try {
                Thread.sleep(100); // Sleep for 100ms to simulate concurrent execution
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

class PrintSquares extends Thread {
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Square: " + (i * i));
            try {
                Thread.sleep(100); // Sleep for 100ms to simulate concurrent execution
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

public class MultithreadingExample {
    public static void main(String[] args) {
        // Creating threads
        PrintNumbers thread1 = new PrintNumbers();
        PrintSquares thread2 = new PrintSquares();
        
        // Starting threads
        thread1.start();
        thread2.start();
        
        // Waiting for threads to finish execution
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        
        System.out.println("Both threads have finished execution.");
    }
}
