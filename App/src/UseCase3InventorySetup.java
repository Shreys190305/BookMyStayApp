import java.util.HashMap;
import java.util.Map;

public class UseCase3InventorySetup {

    // Inventory Class
    static class RoomInventory {

        private Map<String, Integer> inventory;

        // Constructor
        public RoomInventory() {
            inventory = new HashMap<>();
        }

        // Add room type
        public void addRoomType(String roomType, int count) {
            inventory.put(roomType, count);
        }

        // Get availability
        public int getAvailability(String roomType) {
            return inventory.getOrDefault(roomType, 0);
        }

        // Update availability
        public void updateAvailability(String roomType, int change) {
            int current = inventory.getOrDefault(roomType, 0);
            int updated = current + change;

            if (updated < 0) {
                System.out.println("Error: Cannot reduce below zero for " + roomType);
            } else {
                inventory.put(roomType, updated);
            }
        }

        // Display inventory
        public void displayInventory() {
            System.out.println("\nCurrent Room Inventory:");
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        }
    }

    // Main Method
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        inventory.addRoomType("Standard", 10);
        inventory.addRoomType("Deluxe", 5);
        inventory.addRoomType("Suite", 2);

        inventory.displayInventory();

        System.out.println("\nAvailable Deluxe Rooms: "
                + inventory.getAvailability("Deluxe"));

        System.out.println("\nBooking 2 Deluxe rooms...");
        inventory.updateAvailability("Deluxe", -2);

        inventory.displayInventory();

        System.out.println("\nAttempting invalid booking...");
        inventory.updateAvailability("Suite", -5);

        inventory.displayInventory();
    }
}