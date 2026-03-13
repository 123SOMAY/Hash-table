import java.util.*;
import java.util.stream.Collectors;

public class PROBLEM5 {
    private Map<String, Integer> pageViews = new HashMap<>();
    private Map<String, Set<String>> uniqueVisitors = new HashMap<>();
    private Map<String, Integer> trafficSources = new HashMap<>();

    public void processEvent(String url, String userId, String source) {
        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);
        uniqueVisitors.computeIfAbsent(url, k -> new HashSet<>()).add(userId);
        trafficSources.put(source, trafficSources.getOrDefault(source, 0) + 1);
    }

    public void getDashboard() {
        List<Map.Entry<String, Integer>> topPages = pageViews.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toList());

        for (Map.Entry<String, Integer> entry : topPages) {
            int unique = uniqueVisitors.get(entry.getKey()).size();
            System.out.println(entry.getKey() + " - " + entry.getValue() + " views (" + unique + " unique)");
        }
    }
}