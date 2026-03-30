import java.util.*;

// Main class
public class UseCase10BookingCancellation {

    // Reservation class
    static class Reservation {
        private String reservationId;
        private String roomType;
        private String roomId;

        public Reservation(String reservationId, String roomType, String roomId) {
            this.reservationId = reservationId;
            this.roomType = roomType;
            this.roomId = roomId;
        }

        public String getReservationId() {
            return reservationId;
        }

        public String getRoomType() {
            return roomType;
        }

        public String getRoomId() {
            return roomId;
        }
    }

    // Inventory Service
    static class RoomInventory {
        private Map<String, Integer> inventory = new HashMap<>();

        public void addRoomType(String type, int count) {
            inventory.put(type, count);
        }

        public void increaseRoom(String type) {
            inventory.put(type, inventory.getOrDefault(type, 0) + 1);
        }

        public void displayInventory() {
            System.out.println("\nCurrent Inventory:");
            for (Map.Entry<String, Integer> e : inventory.entrySet()) {
                System.out.println(e.getKey() + " : " + e.getValue());
            }
        }
    }

    // Cancellation Service
    static class CancellationService {

        private Map<String, Reservation> confirmedBookings = new HashMap<>();
        private Stack<String> rollbackStack = new Stack<>();
        private RoomInventory inventory;

        public CancellationService(RoomInventory inventory) {
            this.inventory = inventory;
        }

        // Add confirmed booking (simulate existing booking)
        public void addBooking(Reservation r) {
            confirmedBookings.put(r.getReservationId(), r);
        }

        // Cancel booking
        public void cancelBooking(String reservationId) {

            System.out.println("\nCancelling Reservation: " + reservationId);

            // Validate existence
            if (!confirmedBookings.containsKey(reservationId)) {
                System.out.println("Error: Reservation not found or already cancelled.");
                return;
            }

            Reservation r = confirmedBookings.get(reservationId);

            // Push to rollback stack
            rollbackStack.push(r.getRoomId());

            // Restore inventory
            inventory.increaseRoom(r.getRoomType());

            // Remove booking (mark as cancelled)
            confirmedBookings.remove(reservationId);

            System.out.println("Cancellation successful for Room ID: " + r.getRoomId());
        }

        // Show rollback history
        public void displayRollbackStack() {
            System.out.println("\nRollback Stack (Recently Released Room IDs): " + rollbackStack);
        }
    }

    // Main method
    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Standard", 1);
        inventory.addRoomType("Deluxe", 1);

        // Initialize cancellation service
        CancellationService service = new CancellationService(inventory);

        // Simulate confirmed bookings
        service.addBooking(new Reservation("R101", "Standard", "ST100"));
        service.addBooking(new Reservation("R102", "Deluxe", "DE200"));

        // Perform cancellations
        service.cancelBooking("R101"); // valid
        service.cancelBooking("R101"); // invalid (already cancelled)
        service.cancelBooking("R999"); // invalid (not exists)

        // Final state
        inventory.displayInventory();
        service.displayRollbackStack();
    }
}