import java.util.*;

/**
 * Use Case 7: Add-On Service Selection
 * Extends the booking model to support optional services without modifying core logic.
 */

// --- Service Class ---
class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() { return serviceName; }
    public double getCost() { return cost; }
}

// --- Add-On Service Manager ---
class AddOnServiceManager {
    // Maps Reservation ID to a List of selected services (One-to-Many)
    private Map<String, List<Service>> servicesByReservation;

    public AddOnServiceManager() {
        this.servicesByReservation = new HashMap<>();
    }

    /**
     * Attaches a service to a specific reservation ID.
     */
    public void addService(String reservationId, Service service) {
        // computeIfAbsent ensures a list exists for the ID before adding
        servicesByReservation
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    /**
     * Calculates the total cost of all add-ons for a given reservation.
     */
    public double calculateTotalServiceCost(String reservationId) {
        List<Service> services = servicesByReservation.get(reservationId);
        if (services == null) return 0.0;

        double total = 0;
        for (Service s : services) {
            total += s.getCost();
        }
        return total;
    }
}

// --- Main Application Class ---
public class UseCase7AddOnServiceSelection {
    public static void main(String[] args) {
        System.out.println("Add-On Service Selection");
        System.out.println("------------------------");

        // Initialize Manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Assume a reservation ID generated from Use Case 6
        String reservationId = "Single-1";

        // Create available services
        Service breakfast = new Service("Breakfast", 500.0);
        Service spa = new Service("Spa", 1000.0);

        // Attach services to the reservation
        serviceManager.addService(reservationId, breakfast);
        serviceManager.addService(reservationId, spa);

        // Calculate and display costs
        double totalCost = serviceManager.calculateTotalServiceCost(reservationId);

        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);
    }
}