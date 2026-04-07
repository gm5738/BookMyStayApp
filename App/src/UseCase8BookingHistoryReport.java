import java.util.ArrayList;
import java.util.List;

/**
 * Use Case 8: Booking History & Reporting
 * Goal: Maintain an ordered audit trail of confirmed reservations for reporting.
 */

// --- Reservation Class (Data Model) ---
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

// --- Booking History Service ---
class BookingHistory {
    // List preserves the sequence of confirmed bookings for the audit trail
    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        this.confirmedReservations = new ArrayList<>();
    }

    /**
     * Adds a confirmed reservation to the history.
     */
    public void addRecord(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    /**
     * @return The full list of confirmed reservations.
     */
    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

// --- Booking Report Service ---
class BookingReportService {
    /**
     * Generates and displays a summary report from the booking history.
     */
    public void generateReport(BookingHistory history) {
        System.out.println("Booking History Report");
        System.out.println("----------------------");

        List<Reservation> records = history.getConfirmedReservations();

        if (records.isEmpty()) {
            System.out.println("No confirmed bookings found.");
            return;
        }

        for (Reservation res : records) {
            System.out.println("Guest: " + res.getGuestName() +
                    ", Room Type: " + res.getRoomType());
        }
    }
}

// --- Main Application Class ---
public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {
        System.out.println("Booking History and Reporting");

        // Initialize services
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulate confirming and storing bookings
        history.addRecord(new Reservation("Abhi", "Single"));
        history.addRecord(new Reservation("Subha", "Double"));
        history.addRecord(new Reservation("Vanmathi", "Suite"));

        // Generate the operational report
        reportService.generateReport(history);
    }
}