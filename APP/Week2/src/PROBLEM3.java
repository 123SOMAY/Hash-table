public class PROBLEM3 {
    private String[] spots;
    private long[] entryTimes;
    private int size;
    private static final String DELETED = "DELETED";

    public PROBLEM3(int capacity) {
        this.size = capacity;
        this.spots = new String[capacity];
        this.entryTimes = new long[capacity];
    }

    private int hash(String plate) {
        return Math.abs(plate.hashCode()) % size;
    }

    public int parkVehicle(String plate) {
        int index = hash(plate);
        int probes = 0;
        while (spots[index] != null && !spots[index].equals(DELETED)) {
            index = (index + 1) % size;
            probes++;
        }
        spots[index] = plate;
        entryTimes[index] = System.currentTimeMillis();
        return index;
    }

    public double exitVehicle(String plate) {
        int index = hash(plate);
        while (spots[index] != null) {
            if (spots[index].equals(plate)) {
                spots[index] = DELETED;
                long duration = (System.currentTimeMillis() - entryTimes[index]) / 1000;
                return (duration / 3600.0) * 5.0;
            }
            index = (index + 1) % size;
        }
        return 0.0;
    }
}