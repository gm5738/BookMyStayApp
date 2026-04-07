import java.util.*;

/**
 * Use Case 11: Concurrent Booking Simulation
 * Goal: Demonstrate thread safety and prevent race conditions during 
 * simultaneous room allocations.
 */

// --- Shared Room Inventory (Thread-Safe) ---
class RoomInventory {
    private final Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) { inventory.put(type, count); }

    // Synchronized method ensures only one thread can check/update at a time
    public synchronized boolean tryAllocate(String type) {
        int count = inventory.getOrDefault(type, 0);
        if (count > 0) {
            inventory.put(type, count - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("\nRemaining Inventory:");
        inventory.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}

// --- Booking Request (Data Model) ---
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

// --- Concurrent Processor Task ---
class ConcurrentBookingProcessor implements Runnable {
    private Queue<Reservation> queue;
    private RoomInventory inventory;
    private static int idCounter = 1;

    public ConcurrentBookingProcessor(Queue<Reservation> queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {
            Reservation res;
            // Synchronize access to the shared queue
            synchronized (queue) {
                if (queue.isEmpty()) break;
                res = queue.poll();
            }

            if (res != null) {
                if (inventory.tryAllocate(res.getRoomType())) {
                    System.out.println("Booking confirmed for Guest: " + res.getGuestName() +
                            ", Room ID: " + res.getRoomType() + "-" + (idCounter++));
                } else {
                    System.out.println("Booking failed for " + res.getGuestName() + ": Sold out.");
                }
            }
        }
    }
}

// --- Main Application Class ---
public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) {
        System.out.println("Concurrent Booking Simulation");
        System.out.println("-----------------------------");

        // 1. Initialize Shared Resources
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 2); // Low stock to test concurrency
        inventory.addRoomType("Double", 2);
        inventory.addRoomType("Suite", 1);

        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.add(new Reservation("Abhi", "Single"));
        bookingQueue.add(new Reservation("Subha", "Single"));
        bookingQueue.add(new Reservation("Vanmathi", "Double"));
        bookingQueue.add(new Reservation("Kural", "Suite"));
        bookingQueue.add(new Reservation("Deepa", "Single")); // This should fail

        // 2. Create Threads (Simulating multiple processors)
        Thread t1 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory));

        // 3. Start Processing
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Execution interrupted.");
        }

        // 4. Final State
        inventory.displayInventory();
    }
}