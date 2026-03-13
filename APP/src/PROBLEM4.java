import java.util.*;

public class PROBLEM4 {
    private Map<String, Set<String>> nGramMap = new HashMap<>();
    private Map<String, Integer> docSizes = new HashMap<>();

    public void indexDocument(String docId, String content) {
        String[] words = content.split("\\s+");
        int count = 0;
        for (int i = 0; i <= words.length - 5; i++) {
            String ngram = String.join(" ", Arrays.copyOfRange(words, i, i + 5));
            nGramMap.computeIfAbsent(ngram, k -> new HashSet<>()).add(docId);
            count++;
        }
        docSizes.put(docId, count);
    }

    public void analyzeDocument(String content) {
        String[] words = content.split("\\s+");
        Map<String, Integer> matchCounts = new HashMap<>();
        int totalNgrams = 0;

        for (int i = 0; i <= words.length - 5; i++) {
            String ngram = String.join(" ", Arrays.copyOfRange(words, i, i + 5));
            totalNgrams++;
            if (nGramMap.containsKey(ngram)) {
                for (String docId : nGramMap.get(ngram)) {
                    matchCounts.put(docId, matchCounts.getOrDefault(docId, 0) + 1);
                }
            }
        }

        for (Map.Entry<String, Integer> entry : matchCounts.entrySet()) {
            double similarity = (entry.getValue() * 100.0) / totalNgrams;
            System.out.println("Doc: " + entry.getKey() + " Similarity: " + similarity + "%");
        }
    }
}