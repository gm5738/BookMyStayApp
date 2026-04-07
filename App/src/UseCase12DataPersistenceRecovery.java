import java.io.*;
import java.util.*;

/**
 * Use Case 12: Data Persistence & System Recovery
 * Goal: Ensure system state survives application restarts by using 
 * File I/O for persistent storage.
 */

// --- Room Inventory Service (with Persistence Support) ---
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) { inventory.put(type, count); }
    public Map<String, Integer> getInventory() { return inventory; }

    public void displayInventory() {
        if (inventory.isEmpty()) {
            System.out.println("No valid inventory data found. Starting fresh.");
        } else {
            System.out.println("Current Inventory:");
            inventory.forEach((k, v) -> System.out.println(k + ": " + v));
        }
    }
}

// --- Persistence Service ---
class PersistenceService {
    /**
     * Saves room inventory to a file. Format: roomType-availableCount
     */
    public void saveInventory(RoomInventory inventory, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Integer> entry : inventory.getInventory().entrySet()) {
                writer.println(entry.getKey() + "-" + entry.getValue());
            }
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    /**
     * Loads room inventory from a file.
     */
    public void loadInventory(RoomInventory inventory, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("-");
                if (parts.length == 2) {
                    inventory.addRoomType(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }
}

// --- Main Application Class ---
public class UseCase12DataPersistenceRecovery {
    public static void main(String[] args) {
        System.out.println("System Recovery");
        System.out.println("---------------");

        String storageFile = "inventory_state.txt";
        RoomInventory inventory = new RoomInventory();
        PersistenceService persistence = new PersistenceService();

        // 1. Attempt to load existing state
        persistence.loadInventory(inventory, storageFile);

        // 2. If empty (first run), initialize default values
        if (inventory.getInventory().isEmpty()) {
            inventory.addRoomType("Single", 5);
            inventory.addRoomType("Double", 3);
            inventory.addRoomType("Suite", 2);
        }

        // 3. Display current state
        inventory.displayInventory();

        // 4. Save state for next restart
        persistence.saveInventory(inventory, storageFile);
    }
}