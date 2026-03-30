import java.io.*;
import java.util.*;

// Main class
public class UseCase12DataPersistenceRecovery {

    // Serializable system state
    static class SystemState implements Serializable {
        private static final long serialVersionUID = 1L;

        Map<String, Integer> inventory;
        List<String> bookingHistory;

        public SystemState() {
            inventory = new HashMap<>();
            bookingHistory = new ArrayList<>();
        }
    }

    // Persistence Service
    static class PersistenceService {

        private static final String FILE_NAME = "system_state.ser";

        // Save state to file
        public void saveState(SystemState state) {
            try (ObjectOutputStream out =
                         new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

                out.writeObject(state);
                System.out.println("System state saved successfully.");

            } catch (IOException e) {
                System.out.println("Error saving state: " + e.getMessage());
            }
        }

        // Load state from file
        public SystemState loadState() {
            try (ObjectInputStream in =
                         new ObjectInputStream(new FileInputStream(FILE_NAME))) {

                System.out.println("System state restored successfully.");
                return (SystemState) in.readObject();

            } catch (FileNotFoundException e) {
                System.out.println("No saved state found. Starting fresh.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error restoring state: " + e.getMessage());
            }

            return new SystemState(); // safe fallback
        }
    }

    // Main logic
    public static void main(String[] args) {

        PersistenceService service = new PersistenceService();

        // Simulate startup recovery
        SystemState state = service.loadState();

        // If fresh start, initialize sample data
        if (state.inventory.isEmpty()) {
            state.inventory.put("Standard", 5);
            state.inventory.put("Deluxe", 2);

            state.bookingHistory.add("R101 - Alice - Standard");
            state.bookingHistory.add("R102 - Bob - Deluxe");
        }

        // Display current state
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : state.inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("\nBooking History:");
        for (String booking : state.bookingHistory) {
            System.out.println(booking);
        }

        // Simulate shutdown save
        service.saveState(state);
    }
}