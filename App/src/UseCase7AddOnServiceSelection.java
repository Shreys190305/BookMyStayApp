import java.util.*;

// Main class
public class UseCase7AddOnServiceSelection {

    // Add-On Service class
    static class AddOnService {
        private String serviceName;
        private double cost;

        public AddOnService(String serviceName, double cost) {
            this.serviceName = serviceName;
            this.cost = cost;
        }

        public String getServiceName() {
            return serviceName;
        }

        public double getCost() {
            return cost;
        }

        @Override
        public String toString() {
            return serviceName + " (Rs. " + cost + ")";
        }
    }

    // Add-On Service Manager
    static class AddOnServiceManager {

        // reservationId -> list of services
        private Map<String, List<AddOnService>> reservationServices;

        public AddOnServiceManager() {
            reservationServices = new HashMap<>();
        }

        // Add service to reservation
        public void addService(String reservationId, AddOnService service) {
            reservationServices
                    .computeIfAbsent(reservationId, k -> new ArrayList<>())
                    .add(service);

            System.out.println(service.getServiceName() +
                    " added to Reservation ID: " + reservationId);
        }

        // Display services
        public void displayServices(String reservationId) {
            List<AddOnService> services =
                    reservationServices.getOrDefault(reservationId, new ArrayList<>());

            System.out.println("\nSelected Services for Reservation ID: " + reservationId);

            if (services.isEmpty()) {
                System.out.println("No add-on services selected.");
                return;
            }

            for (AddOnService service : services) {
                System.out.println(service);
            }
        }

        // Calculate total additional cost
        public double calculateTotalCost(String reservationId) {
            List<AddOnService> services =
                    reservationServices.getOrDefault(reservationId, new ArrayList<>());

            double total = 0;

            for (AddOnService service : services) {
                total += service.getCost();
            }

            return total;
        }
    }

    // Main method
    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "DEL101";

        // Add services
        manager.addService(reservationId,
                new AddOnService("Breakfast", 500));

        manager.addService(reservationId,
                new AddOnService("Airport Pickup", 1200));

        manager.addService(reservationId,
                new AddOnService("Spa Access", 1500));

        // Display services
        manager.displayServices(reservationId);

        // Display total cost
        double totalCost = manager.calculateTotalCost(reservationId);

        System.out.println("\nTotal Additional Cost: Rs. " + totalCost);
    }
}