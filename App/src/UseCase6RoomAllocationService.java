import java.util.*;

// Main class
public class UseCase6RoomAllocationService {

    // Reservation (booking request)
    static class Reservation {
        private String guestName;
        private String roomType;

        public Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }
    }

    // Inventory Service
    static class RoomInventory {
        private Map<String, Integer> inventory = new HashMap<>();

        public void addRoomType(String type, int count) {
            inventory.put(type, count);
        }

        public int getAvailability(String type) {
            return inventory.getOrDefault(type, 0);
        }

        public void reduceRoom(String type) {
            int count = inventory.getOrDefault(type, 0);
            if (count > 0) {
                inventory.put(type, count - 1);
            }
        }

        public void displayInventory() {
            System.out.println("\nCurrent Inventory:");
            for (Map.Entry<String, Integer> e : inventory.entrySet()) {
                System.out.println(e.getKey() + " : " + e.getValue());
            }
        }
    }

    // Booking Queue (FIFO)
    static class BookingQueue {
        private Queue<Reservation> queue = new LinkedList<>();

        public void addRequest(Reservation r) {
            queue.offer(r);
        }

        public Reservation getNextRequest() {
            return queue.poll();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    // Booking Service (Allocation Logic)
    static class BookingService {

        private RoomInventory inventory;

        // Track all allocated room IDs (global uniqueness)
        private Set<String> allocatedRoomIds = new HashSet<>();

        // Map room type -> allocated IDs
        private Map<String, Set<String>> roomAllocations = new HashMap<>();

        public BookingService(RoomInventory inventory) {
            this.inventory = inventory;
        }

        // Generate unique room ID
        private String generateRoomId(String roomType) {
            String id;
            do {
                id = roomType.substring(0, 2).toUpperCase() + new Random().nextInt(1000);
            } while (allocatedRoomIds.contains(id));
            return id;
        }

        // Process booking request
        public void processReservation(Reservation r) {
            String type = r.getRoomType();

            System.out.println("\nProcessing request for " + r.getGuestName());

            if (inventory.getAvailability(type) <= 0) {
                System.out.println("No rooms available for type: " + type);
                return;
            }

            // Generate unique ID
            String roomId = generateRoomId(type);

            // Allocate
            allocatedRoomIds.add(roomId);

            roomAllocations
                    .computeIfAbsent(type, k -> new HashSet<>())
                    .add(roomId);

            // Update inventory immediately
            inventory.reduceRoom(type);

            // Confirm reservation
            System.out.println("Booking Confirmed!");
            System.out.println("Guest: " + r.getGuestName());
            System.out.println("Room Type: " + type);
            System.out.println("Room ID: " + roomId);
        }

        public void displayAllocations() {
            System.out.println("\nRoom Allocations:");
            for (Map.Entry<String, Set<String>> e : roomAllocations.entrySet()) {
                System.out.println(e.getKey() + " -> " + e.getValue());
            }
        }
    }

    // Main method
    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Standard", 2);
        inventory.addRoomType("Deluxe", 2);
        inventory.addRoomType("Suite", 1);

        // Create booking queue
        BookingQueue queue = new BookingQueue();
        queue.addRequest(new Reservation("Alice", "Deluxe"));
        queue.addRequest(new Reservation("Bob", "Deluxe"));
        queue.addRequest(new Reservation("Charlie", "Suite"));
        queue.addRequest(new Reservation("David", "Suite")); // should fail

        // Booking service
        BookingService service = new BookingService(inventory);

        // Process queue (FIFO)
        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();
            service.processReservation(r);
        }

        // Final state
        inventory.displayInventory();
        service.displayAllocations();
    }
}