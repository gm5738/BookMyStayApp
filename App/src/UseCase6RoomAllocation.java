import java.util.*;

/**
 * Use Case 6: Reservation Confirmation & Room Allocation
 * Goal: Confirm booking requests by assigning rooms safely while ensuring 
 * inventory consistency and preventing double-booking. [cite: 194, 195]
 */

// --- Reservation Class (from Use Case 5) ---
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

// --- Booking Request Queue Class (from Use Case 5) ---
class BookingRequestQueue {
    private Queue<Reservation> requestQueue = new LinkedList<>();

    public void addRequest(Reservation reservation) { requestQueue.offer(reservation); }
    public Reservation getNextRequest() { return requestQueue.poll(); }
    public boolean hasPendingRequests() { return !requestQueue.isEmpty(); }
}

// --- Room Inventory Service (Mock) ---
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) { inventory.put(type, count); }
    public boolean isAvailable(String type) { return inventory.getOrDefault(type, 0) > 0; }
    public void reduceInventory(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

// --- Room Allocation Service ---
class RoomAllocationService {
    // Set ensures uniqueness of Room IDs across the system [cite: 212, 214]
    private Set<String> allocatedRoomIds;
    // Map tracks allocated IDs grouped by room type [cite: 216, 270]
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        this.allocatedRoomIds = new HashSet<>();
        this.assignedRoomsByType = new HashMap<>();
    }

    /**
     * Confirms a booking request by assigning a unique ID and updating inventory. [cite: 275, 276]
     */
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String type = reservation.getRoomType();

        if (inventory.isAvailable(type)) {
            String roomId = generateRoomId(type);

            // Atomic logic: Add to tracking and reduce inventory [cite: 218, 219]
            allocatedRoomIds.add(roomId);
            assignedRoomsByType.computeIfAbsent(type, k -> new HashSet<>()).add(roomId);
            inventory.reduceInventory(type);

            System.out.println("Booking confirmed for Guest: " + reservation.getGuestName() +
                    ", Room ID: " + roomId); [cite: 322]
        } else {
            System.out.println("Booking failed for " + reservation.getGuestName() +
                    ": No " + type + " rooms available.");
        }
    }

    /**
     * Generates a unique room ID based on the type and current allocation count. [cite: 283, 289]
     */
    private String generateRoomId(String roomType) {
        int nextId = assignedRoomsByType.getOrDefault(roomType, new HashSet<>()).size() + 1;
        return roomType + "-" + nextId;
    }
}

// --- Main Application Class ---
public class UseCase6RoomAllocation {
    public static void main(String[] args) {
        System.out.println("Room Allocation Processing"); [cite: 321]
        System.out.println("--------------------------");

        // Setup Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 5);
        inventory.addRoomType("Suite", 2);

        // Setup Request Queue (FIFO)
        BookingRequestQueue queue = new BookingRequestQueue();
        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Single"));
        queue.addRequest(new Reservation("Vanmathi", "Suite"));

        // Setup Allocation Service
        RoomAllocationService allocationService = new RoomAllocationService();

        // Process requests in FIFO order [cite: 222, 303]
        while (queue.hasPendingRequests()) {
            Reservation request = queue.getNextRequest();
            allocationService.allocateRoom(request, inventory);
        }
    }
}