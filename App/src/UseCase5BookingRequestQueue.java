import java.util.LinkedList;
import java.util.Queue;

// Main class
public class UseCase5BookingRequestQueue {

    // Reservation class (represents a booking request)
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

        @Override
        public String toString() {
            return "Guest: " + guestName + ", Room Type: " + roomType;
        }
    }

    // Booking Request Queue
    static class BookingQueue {
        private Queue<Reservation> queue;

        public BookingQueue() {
            queue = new LinkedList<>();
        }

        // Add booking request (enqueue)
        public void addRequest(Reservation reservation) {
            queue.offer(reservation);
            System.out.println("Request added: " + reservation);
        }

        // View all requests (without removing)
        public void displayQueue() {
            System.out.println("\nCurrent Booking Requests (FIFO Order):");
            if (queue.isEmpty()) {
                System.out.println("No pending requests.");
                return;
            }
            for (Reservation r : queue) {
                System.out.println(r);
            }
        }
    }

    // Main method
    public static void main(String[] args) {

        BookingQueue bookingQueue = new BookingQueue();

        // Simulating booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Deluxe"));
        bookingQueue.addRequest(new Reservation("Bob", "Standard"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite"));
        bookingQueue.addRequest(new Reservation("David", "Deluxe"));

        // Display queue (FIFO order preserved)
        bookingQueue.displayQueue();
    }
}
