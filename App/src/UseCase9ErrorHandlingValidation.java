import java.util.*;

/**
 * Use Case 9: Error Handling & Validation
 * Goal: Ensure system reliability by validating inputs and handling errors gracefully.
 */

// --- Custom Domain Exception ---
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

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

// --- Validation Service ---
class ReservationValidator {
    /**
     * Validates guest input against business rules.
     * @throws InvalidBookingException if validation fails.
     */
    public void validate(String guestName, String roomType) throws InvalidBookingException {
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty."); // 
        }

        // Validating against specific allowed types (Case-Sensitive as per doc)
        List<String> validTypes = Arrays.asList("Single", "Double", "Suite");
        if (!validTypes.contains(roomType)) {
            throw new InvalidBookingException("Invalid room type selected."); // [cite: 375]
        }
    }
}

// --- Main Application Class ---
public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {
        System.out.println("Booking Validation");
        System.out.println("------------------");

        Scanner scanner = new Scanner(System.in);
        ReservationValidator validator = new ReservationValidator();

        try {
            System.out.print("Enter guest name: ");
            String name = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String type = scanner.nextLine();

            // Centralized Validation 
            validator.validate(name, type);

            // If valid, create reservation (Logic from Use Case 5)
            Reservation res = new Reservation(name, type);
            System.out.println("Success: Request for " + res.getGuestName() + " is valid and queued.");

        } catch (InvalidBookingException e) {
            // Handle domain-specific validation errors gracefully 
            System.out.println("Booking failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            scanner.close(); // 
        }
    }
}