import java.util.*;

/**
 * Use Case 10: Booking Cancellation & Inventory Rollback
 * Goal: Safely reverse system state changes (inventory and allocation) 
 * using a Stack to track rollback history.
 */

// --- Room Inventory Service (Enhanced) ---
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) { inventory.put(type, count); }
    public int getAvailableCount(String type) { return inventory.getOrDefault(type, 0); }

    public void restoreInventory(String type) {
        inventory.put(type, inventory.get(type) + 1);
    }
}

// --- Cancellation Service ---
class CancellationService {
    // Map to store active bookings: ReservationID -> RoomType
    private Map<String, String> activeBookings;
    // Stack to maintain rollback history (Most Recent First)
    private Stack<String> rollbackHistory;

    public CancellationService() {
        this.activeBookings = new HashMap<>();
        this.rollbackHistory = new Stack<>();
    }

    /**
     * Registers a confirmed booking so it can be managed for cancellation.
     */
    public void registerBooking(String reservationId, String roomType) {
        activeBookings.put(reservationId, roomType);
    }

    /**
     * Cancels a booking and restores inventory.
     */
    public void cancelBooking(String reservationId, RoomInventory inventory) {
        if (activeBookings.containsKey(reservationId)) {
            String roomType = activeBookings.remove(reservationId);

            // Restore inventory
            inventory.restoreInventory(roomType);

            // Record in rollback history
            rollbackHistory.push(reservationId);

            System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
        } else {
            System.out.println("Error: Reservation ID " + reservationId + " not found.");
        }
    }

    /**
     * Displays recently cancelled reservations (LIFO).
     */
    public void showRollbackHistory() {
        System.out.println("\nRollback History (Most Recent First):");
        if (rollbackHistory.isEmpty()) {
            System.out.println("No cancellations recorded.");
            return;
        }

        // Creating a copy to display without destroying the stack
        Stack<String> tempStack = (Stack<String>) rollbackHistory.clone();
        while (!tempStack.isEmpty()) {
            System.out.println("Released Reservation ID: " + tempStack.pop());
        }
    }
}

// --- Main Application Class ---
public class UseCase10BookingCancellation {
    public static void main(String[] args) {
        System.out.println("Booking Cancellation");
        System.out.println("--------------------");

        // Setup Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 5);

        // Setup Cancellation Service
        CancellationService cancellationService = new CancellationService();

        // Simulate a confirmed booking (Single-1)
        cancellationService.registerBooking("Single-1", "Single");

        // Perform Cancellation
        cancellationService.cancelBooking("Single-1", inventory);

        // Display results
        cancellationService.showRollbackHistory();
        System.out.println("Updated Single Room Availability: " + inventory.getAvailableCount("Single"));
    }
}