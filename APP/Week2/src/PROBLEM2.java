import java.util.*;

public class PROBLEM2 {
    class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        PriorityQueue<String> topQueries = new PriorityQueue<>((a, b) ->
                counts.get(a).equals(counts.get(b)) ? b.compareTo(a) : counts.get(a) - counts.get(b));
    }

    private TrieNode root = new TrieNode();
    private Map<String, Integer> counts = new HashMap<>();

    public void updateFrequency(String query) {
        counts.put(query, counts.getOrDefault(query, 0) + 1);
        TrieNode curr = root;
        for (char c : query.toCharArray()) {
            curr = curr.children.computeIfAbsent(c, k -> new TrieNode());
            if (!curr.topQueries.contains(query)) {
                curr.topQueries.add(query);
            } else {
                curr.topQueries.remove(query);
                curr.topQueries.add(query);
            }
            if (curr.topQueries.size() > 10) curr.topQueries.poll();
        }
    }

    public List<String> search(String prefix) {
        TrieNode curr = root;
        for (char c : prefix.toCharArray()) {
            curr = curr.children.get(c);
            if (curr == null) return Collections.emptyList();
        }
        List<String> res = new ArrayList<>(curr.topQueries);
        res.sort((a, b) -> counts.get(b) - counts.get(a));
        return res;
    }
}