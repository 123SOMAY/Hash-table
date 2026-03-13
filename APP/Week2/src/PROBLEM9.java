import java.util.*;

public class PROBLEM9 {
    static class Transaction {
        int id; double amount; String merchant; long time;
    }

    public List<int[]> findTwoSum(List<Transaction> txns, double target) {
        List<int[]> pairs = new ArrayList<>();
        Map<Double, Integer> complements = new HashMap<>();
        for (Transaction t : txns) {
            double diff = target - t.amount;
            if (complements.containsKey(diff)) {
                pairs.add(new int[]{complements.get(diff), t.id});
            }
            complements.put(t.amount, t.id);
        }
        return pairs;
    }

    public List<Transaction> detectDuplicates(List<Transaction> txns) {
        Map<String, Transaction> seen = new HashMap<>();
        List<Transaction> dups = new ArrayList<>();
        for (Transaction t : txns) {
            String key = t.amount + t.merchant;
            if (seen.containsKey(key)) dups.add(t);
            else seen.put(key, t);
        }
        return dups;
    }
}