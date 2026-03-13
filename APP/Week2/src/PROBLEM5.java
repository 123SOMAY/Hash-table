import java.util.*;

public class PROBLEM5 {
    private LinkedHashMap<String, String> l1 = new LinkedHashMap<>(100, 0.75f, true) {
        protected boolean removeEldestEntry(Map.Entry eldest) { return size() > 10000; }
    };
    private Map<String, String> l2 = new HashMap<>();
    private Map<String, Integer> counts = new HashMap<>();

    public String getVideo(String videoId) {
        if (l1.containsKey(videoId)) return l1.get(videoId);

        if (l2.containsKey(videoId)) {
            String data = l2.get(videoId);
            int count = counts.getOrDefault(videoId, 0) + 1;
            counts.put(videoId, count);
            if (count > 5) l1.put(videoId, data);
            return data;
        }

        String data = "DB_DATA_" + videoId;
        l2.put(videoId, data);
        counts.put(videoId, 1);
        return data;
    }
}