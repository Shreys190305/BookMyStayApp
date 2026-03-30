import java.util.*;

// Main class
public class UseCase8BookingHistoryReport {

    // Reservation class (confirmed booking)
    static class Reservation {
        private String reservationId;
        private String guestName;
        private String roomType;

        public Reservation(String reservationId, String guestName, String roomType) {
            this.reservationId = reservationId;
            this.guestName = guestName;
            this.roomType = roomType;
        }

        public String getReservationId() {
            return reservationId;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }

        @Override
        public String toString() {
            return "Reservation ID: " + reservationId +
                    ", Guest: " + guestName +
                    ", Room Type: " + roomType;
        }
    }

    // Booking History (stores confirmed bookings)
    static class BookingHistory {
        private List<Reservation> history;

        public BookingHistory() {
            history = new ArrayList<>();
        }

        // Add confirmed reservation
        public void addReservation(Reservation reservation) {
            history.add(reservation);
            System.out.println("Added to history: " + reservation.getReservationId());
        }

        // Get all reservations
        public List<Reservation> getAllReservations() {
            return history;
        }
    }

    // Reporting Service
    static class BookingReportService {

        // Display all bookings
        public void displayAllBookings(List<Reservation> reservations) {
            System.out.println("\n--- Booking History ---");

            if (reservations.isEmpty()) {
                System.out.println("No bookings found.");
                return;
            }

            for (Reservation r : reservations) {
                System.out.println(r);
            }
        }

        // Generate summary report
        public void generateSummary(List<Reservation> reservations) {
            System.out.println("\n--- Booking Summary Report ---");

            Map<String, Integer> roomTypeCount = new HashMap<>();

            for (Reservation r : reservations) {
                roomTypeCount.put(
                        r.getRoomType(),
                        roomTypeCount.getOrDefault(r.getRoomType(), 0) + 1
                );
            }

            for (Map.Entry<String, Integer> entry : roomTypeCount.entrySet()) {
                System.out.println(entry.getKey() + " Bookings: " + entry.getValue());
            }

            System.out.println("Total Bookings: " + reservations.size());
        }
    }

    // Main method
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulate confirmed bookings
        history.addReservation(new Reservation("DEL101", "Alice", "Deluxe"));
        history.addReservation(new Reservation("STD102", "Bob", "Standard"));
        history.addReservation(new Reservation("DEL103", "Charlie", "Deluxe"));
        history.addReservation(new Reservation("STE104", "David", "Suite"));

        // Display booking history
        reportService.displayAllBookings(history.getAllReservations());

        // Generate summary report
        reportService.generateSummary(history.getAllReservations());
    }
}