import java.util.HashMap;
import java.util.Map;

// Main class (case-sensitive)
public class UseCase9ErrorHandlingValidation {

    // Custom Exception
    static class InvalidBookingException extends Exception {
        public InvalidBookingException(String message) {
            super(message);
        }
    }

    // Inventory Validator + Service
    static class BookingValidator {

        private Map<String, Integer> inventory;

        public BookingValidator() {
            inventory = new HashMap<>();

            // Initial room availability
            inventory.put("Standard", 2);
            inventory.put("Deluxe", 1);
            inventory.put("Suite", 1);
        }

        // Validate and process booking
        public void bookRoom(String roomType) throws InvalidBookingException {

            // Validate room type
            if (!inventory.containsKey(roomType)) {
                throw new InvalidBookingException(
                        "Invalid room type: " + roomType);
            }

            // Validate availability
            int available = inventory.get(roomType);

            if (available <= 0) {
                throw new InvalidBookingException(
                        "No rooms available for: " + roomType);
            }

            // Safe inventory update
            inventory.put(roomType, available - 1);

            System.out.println("Booking confirmed for " + roomType);
        }

        // Display inventory
        public void displayInventory() {
            System.out.println("\nCurrent Inventory:");
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        }
    }

    // Main method
    public static void main(String[] args) {

        BookingValidator validator = new BookingValidator();

        try {
            validator.bookRoom("Deluxe");
            validator.bookRoom("Deluxe"); // should fail
        } catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            validator.bookRoom("Luxury"); // invalid type
        } catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Program continues safely
        validator.displayInventory();
    }
}