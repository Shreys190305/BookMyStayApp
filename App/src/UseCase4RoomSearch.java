import java.util.*;

// Abstract class
abstract class RoomUC4 {
    private String type;
    private int beds;
    private double price;

    public RoomUC4(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

// Room types
class SingleRoomUC4 extends RoomUC4 {
    public SingleRoomUC4() {
        super("Single Room", 1, 2000);
    }
}

class DoubleRoomUC4 extends RoomUC4 {
    public DoubleRoomUC4() {
        super("Double Room", 2, 3500);
    }
}

class SuiteRoomUC4 extends RoomUC4 {
    public SuiteRoomUC4() {
        super("Suite Room", 3, 6000);
    }
}

// Inventory
class RoomInventoryUC4 {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventoryUC4() {
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

// Search service
class RoomSearchServiceUC4 {
    private RoomInventoryUC4 inventory;
    private List<RoomUC4> rooms = new ArrayList<>();

    public RoomSearchServiceUC4(RoomInventoryUC4 inventory) {
        this.inventory = inventory;
        rooms.add(new SingleRoomUC4());
        rooms.add(new DoubleRoomUC4());
        rooms.add(new SuiteRoomUC4());
    }

    public void search() {
        System.out.println("\n--- Available Rooms ---\n");

        for (RoomUC4 room : rooms) {
            int available = inventory.getAvailability(room.getType());

            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available);
                System.out.println();
            }
        }
    }
}

// Main class
public class UseCase4RoomSearch {
    public static void main(String[] args) {

        System.out.println("=== Book My Stay (v4.0) ===");

        RoomInventoryUC4 inventory = new RoomInventoryUC4();
        RoomSearchServiceUC4 service = new RoomSearchServiceUC4(inventory);

        service.search();
    }
}