import java.util.LinkedList;
import java.util.Queue;

/**
 * Use Case 5: Booking Request (FIFO)
 * This system handles multiple booking requests fairly by preserving arrival order[cite: 11].
 * It decouples request intake from room allocation[cite: 37].
 */

// --- Reservation Class ---
class Reservation {
    private String guestName; // Name of the guest making the booking [cite: 74]
    private String roomType;  // Requested room type [cite: 75]

    /**
     * Creates a new booking request capturing intent[cite: 65, 78].
     */
    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType; // [cite: 85]
    }

    public String getGuestName() { return guestName; } // [cite: 89]
    public String getRoomType() { return roomType; }   // [cite: 91]
}

// --- Booking Request Queue Class ---
class BookingRequestQueue {
    // Queue structure to store requests and preserve order [cite: 41, 112]
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        // Uses LinkedList to implement the Queue interface [cite: 120]
        this.requestQueue = new LinkedList<>();
    }

    /**
     * Adds a booking request to the end of the queue[cite: 116, 121].
     */
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    /**
     * Retrieves and removes the next request (the earliest arrival)[cite: 123, 127].
     */
    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    /**
     * Checks if there are pending requests in the queue[cite: 131, 134].
     */
    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}

// --- Main Application Class ---
public class UseCase5BookingRequestQueue {
    public static void main(String[] args) {
        // Display application header [cite: 164]
        System.out.println("Booking Request Queue");
        System.out.println("---------------------");

        // Initialize the booking queue [cite: 166]
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Create booking requests representing guest intent [cite: 167, 168, 169]
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        // Add requests to the queue in arrival order [cite: 171, 172, 173]
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Process and display queued requests in FIFO order [cite: 174, 175]
        System.out.println("Processing Requests in FIFO Order:");
        while (bookingQueue.hasPendingRequests()) {
            Reservation current = bookingQueue.getNextRequest();
            System.out.println("Guest: " + current.getGuestName() +
                    " | Requested Room: " + current.getRoomType());
        }
    }
}