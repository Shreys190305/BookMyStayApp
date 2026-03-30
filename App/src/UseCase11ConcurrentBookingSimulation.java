
import java.util.*;

// Main class
public class UseCase11ConcurrentBookingSimulation {

    // Shared Inventory Service
    static class RoomInventory {
        private Map<String, Integer> inventory = new HashMap<>();

        public RoomInventory() {
            inventory.put("Standard", 2);
        }

        // Critical section: synchronized method
        public synchronized boolean bookRoom(String guestName, String roomType) {
            int available = inventory.getOrDefault(roomType, 0);

            if (available > 0) {
                System.out.println(guestName + " is booking " + roomType + "...");
                inventory.put(roomType, available - 1);

                System.out.println("Booking confirmed for " + guestName +
                        " | Remaining " + roomType + ": " +
                        inventory.get(roomType));

                return true;
            } else {
                System.out.println("Booking failed for " + guestName +
                        " | No " + roomType + " rooms left");
                return false;
            }
        }

        public void displayInventory() {
            System.out.println("\nFinal Inventory State:");
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        }
    }

    // Guest Thread
    static class GuestBookingThread extends Thread {
        private String guestName;
        private String roomType;
        private RoomInventory inventory;

        public GuestBookingThread(String guestName,
                                  String roomType,
                                  RoomInventory inventory) {
            this.guestName = guestName;
            this.roomType = roomType;
            this.inventory = inventory;
        }

        @Override
        public void run() {
            inventory.bookRoom(guestName, roomType);
        }
    }

    // Main logic
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();

        // Simulate concurrent guests
        Thread guest1 = new GuestBookingThread("Alice", "Standard", inventory);
        Thread guest2 = new GuestBookingThread("Bob", "Standard", inventory);
        Thread guest3 = new GuestBookingThread("Charlie", "Standard", inventory);
        Thread guest4 = new GuestBookingThread("David", "Standard", inventory);

        // Start all threads simultaneously
        guest1.start();
        guest2.start();
        guest3.start();
        guest4.start();

        // Wait for completion
        try {
            guest1.join();
            guest2.join();
            guest3.join();
            guest4.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }

        // Final inventory check
        inventory.displayInventory();
    }
}