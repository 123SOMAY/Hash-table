import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PROBLEM2 {
    private ConcurrentHashMap<String, AtomicInteger> stock = new ConcurrentHashMap<>();
    private Map<String, Queue<Integer>> waitingLists = new HashMap<>();

    public void addProduct(String productId, int count) {
        stock.put(productId, new AtomicInteger(count));
        waitingLists.put(productId, new LinkedList<>());
    }

    public int checkStock(String productId) {
        return stock.getOrDefault(productId, new AtomicInteger(0)).get();
    }

    public synchronized String purchaseItem(String productId, int userId) {
        AtomicInteger currentStock = stock.get(productId);
        if (currentStock != null && currentStock.get() > 0) {
            currentStock.decrementAndGet();
            return "Success, " + currentStock.get() + " units remaining";
        } else {
            Queue<Integer> wl = waitingLists.get(productId);
            wl.add(userId);
            return "Added to waiting list, position #" + wl.size();
        }
    }
}
