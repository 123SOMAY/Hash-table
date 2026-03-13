import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PROBLEM6 {
    private final Map<String, TokenBucket> clients = new ConcurrentHashMap<>();

    static class TokenBucket {
        private final long maxTokens;
        private final long refillRate;
        private long tokens;
        private long lastRefillTime;

        TokenBucket(long maxTokens, long refillRate) {
            this.maxTokens = maxTokens;
            this.tokens = maxTokens;
            this.refillRate = refillRate;
            this.lastRefillTime = System.currentTimeMillis();
        }

        synchronized boolean allow() {
            refill();
            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        }

        private void refill() {
            long now = System.currentTimeMillis();
            long delta = (now - lastRefillTime) / 1000;
            if (delta > 0) {
                tokens = Math.min(maxTokens, tokens + (delta * refillRate));
                lastRefillTime = now;
            }
        }

        synchronized long getTokens() { return tokens; }
        synchronized long getNextReset() { return (3600 - ((System.currentTimeMillis() - lastRefillTime) / 1000)); }
    }

    public String checkRateLimit(String clientId) {
        TokenBucket bucket = clients.computeIfAbsent(clientId, k -> new TokenBucket(1000, 1));
        if (bucket.allow()) {
            return "Allowed (" + bucket.getTokens() + " remaining)";
        }
        return "Denied (0 remaining, retry after " + bucket.getNextReset() + "s)";
    }
}