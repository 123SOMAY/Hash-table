import java.util.*;

public class PROBLEM3 {
    class DNSEntry {
        String ip;
        long expiryTime;
        DNSEntry(String ip, long ttlSeconds) {
            this.ip = ip;
            this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000);
        }
    }

    private Map<String, DNSEntry> cache = new LinkedHashMap<>(16, 0.75f, true);
    private int hits = 0;
    private int misses = 0;

    public String resolve(String domain) {
        DNSEntry entry = cache.get(domain);
        if (entry != null && System.currentTimeMillis() < entry.expiryTime) {
            hits++;
            return entry.ip;
        }
        misses++;
        cache.remove(domain);
        return fetchFromUpstream(domain);
    }

    private String fetchFromUpstream(String domain) {
        String mockIp = "172.217.14." + (new Random().nextInt(254));
        cache.put(domain, new DNSEntry(mockIp, 300));
        return mockIp;
    }

    public String getCacheStats() {
        double total = hits + misses;
        return "Hit Rate: " + (hits / total * 100) + "%";
    }
}