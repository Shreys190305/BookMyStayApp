import java.util.*;

class Bogie {
    private String name;
    private int capacity;

    // Constructor
    public Bogie(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    // Display
    @Override
    public String toString() {
        return name + " Bogie - Capacity: " + capacity;
    }
}

public class UseCase7SortBogiesByCapacity {
    public static void main(String[] args) {

        // Create list
        List<Bogie> bogies = new ArrayList<>();

        // Add bogies
        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 56));
        bogies.add(new Bogie("First Class", 24));

        // Sort using Comparator
        bogies.sort(Comparator.comparingInt(Bogie::getCapacity));

        // Display result
        System.out.println("Bogies Sorted by Capacity:");
        for (Bogie b : bogies) {
            System.out.println(b);
        }
    }
}