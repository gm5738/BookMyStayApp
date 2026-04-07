import java.util.*;

// Room domain object
class Room {

    private String roomType;
    private double price;
    private String amenities;

    public Room(String roomType, double price, String amenities) {
        this.roomType = roomType;
        this.price = price;
        this.amenities = amenities;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public String getAmenities() {
        return amenities;
    }
}

// Inventory state holder
class Inventory {

    private Map<String, Integer> roomAvailability = new HashMap<>();

    public void addRoom(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllAvailability() {
        return roomAvailability;
    }
}

// Search Service (READ ONLY)
class SearchService {

    private Inventory inventory;
    private Map<String, Room> roomCatalog;

    public SearchService(Inventory inventory, Map<String, Room> roomCatalog) {
        this.inventory = inventory;
        this.roomCatalog = roomCatalog;
    }

    public void searchAvailableRooms() {

        System.out.println("\nAvailable Rooms:\n");

        for (String roomType : inventory.getAllAvailability().keySet()) {

            int available = inventory.getAvailability(roomType);

            if (available > 0) {

                Room room = roomCatalog.get(roomType);

                System.out.println("Room Type: " + room.getRoomType());
                System.out.println("Price: $" + room.getPrice());
                System.out.println("Amenities: " + room.getAmenities());
                System.out.println("Available Rooms: " + available);
                System.out.println("----------------------");
            }
        }
    }
}

public class usecase4 {

    public static void main(String[] args) {

        // Room catalog
        Map<String, Room> rooms = new HashMap<>();

        rooms.put("Standard", new Room("Standard", 100, "WiFi, TV"));
        rooms.put("Deluxe", new Room("Deluxe", 180, "WiFi, TV, Mini Bar"));
        rooms.put("Suite", new Room("Suite", 300, "WiFi, TV, Mini Bar, Jacuzzi"));

        // Inventory setup
        Inventory inventory = new Inventory();

        inventory.addRoom("Standard", 5);
        inventory.addRoom("Deluxe", 0);
        inventory.addRoom("Suite", 2);

        // Search Service
        SearchService searchService = new SearchService(inventory, rooms);

        // Guest searches rooms
        searchService.searchAvailableRooms();
    }
}