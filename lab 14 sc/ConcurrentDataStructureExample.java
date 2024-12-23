import java.util.concurrent.CopyOnWriteArrayList;

public class ConcurrentDataStructureExample {
    public static void main(String[] args) {
        // Create a thread-safe list
        CopyOnWriteArrayList<Integer> sharedList = new CopyOnWriteArrayList<>();

        // Create threads to add elements to the list
        Thread writer1 = new Thread(new ListWriter(sharedList, 1));
        Thread writer2 = new Thread(new ListWriter(sharedList, 2));
        Thread reader = new Thread(new ListReader(sharedList));

        // Start the threads
        writer1.start();
        writer2.start();
        reader.start();

        // Wait for threads to finish
        try {
            writer1.join();
            writer2.join();
            reader.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Task to write to the shared list
class ListWriter implements Runnable {
    private CopyOnWriteArrayList<Integer> list;
    private int multiplier;

    public ListWriter(CopyOnWriteArrayList<Integer> list, int multiplier) {
        this.list = list;
        this.multiplier = multiplier;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            list.add(i * multiplier);
            System.out.println("Added: " + (i * multiplier));
        }
    }
}

// Task to read from the shared list
class ListReader implements Runnable {
    private CopyOnWriteArrayList<Integer> list;

    public ListReader(CopyOnWriteArrayList<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Current List: " + list);
            try {
                Thread.sleep(500); // Simulate read delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

