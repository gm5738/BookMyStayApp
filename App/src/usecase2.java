// Version 2.1
// Use Case 2: Basic Room Types & Static Availability

abstract class Room {

    // Encapsulated attributes
    private String roomType;
    private int beds;
    private int size;
    private double price;

    // Constructor
    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    // Getter methods (Encapsulation)
    public String getRoomType() {
        return roomType;
    }

    public int getBeds() {
        return beds;
    }

    public int getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    // Common behavior
    public void displayRoomDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq ft");
        System.out.println("Price     : $" + price);
    }
}

// Single Room Class
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 100);
    }
}

// Double Room Class
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 180);
    }
}

// Suite Room Class
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 600, 350);
    }
}

// Main Application
public class usecase2 {

    public static void main(String[] args) {

        // Static availability variables
        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 2;

        // Polymorphism (Room reference)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        System.out.println("===== Book My Stay - Room Availability =====\n");

        single.displayRoomDetails();
        System.out.println("Available Rooms : " + singleRoomAvailability);
        System.out.println("----------------------------------");

        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms : " + doubleRoomAvailability);
        System.out.println("----------------------------------");

        suite.displayRoomDetails();
        System.out.println("Available Rooms : " + suiteRoomAvailability);
        System.out.println("----------------------------------");

        System.out.println("\nApplication Terminated.");
    }
}