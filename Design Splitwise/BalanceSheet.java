import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class BalanceSheet {
    // balances[A][B] = how much A owes B (positive = owes, negative = is owed)
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, Double>> balances;

    public BalanceSheet() {
        this.balances = new ConcurrentHashMap<>();
    }

    //Increase the debt from formUserId to toUserId by amount
    //Also mirrors the update: toUserId's record shows they are owed
    public synchronized void updateBalance(String fromUserId, String toUserId, double amount) {
        // Forward direction: frowmuser owes toUser more
        ConcurrentHashMap<String, Double> fromMap = balances.computeIfAbsent(fromUserId, k -> new ConcurrentHashMap<>());
        if(fromMap.containsKey(toUserId)) {
            fromMap.put(toUserId, fromMap.get(toUserId) + amount);
        } else {
            fromMap.put(toUserId, amount);
        }

        // Mirror direction: toUser is owed more by fromUser (negative amount)
        ConcurrentHashMap<String, Double> toMap = balances.computeIfAbsent(toUserId, k -> new ConcurrentHashMap<>());
        if(toMap.containsKey(fromUserId)) {
            toMap.put(fromUserId, toMap.get(fromUserId) - amount);
        } else {
            toMap.put(fromUserId, -amount);
        }
    } 

    // Reduces the debt from fromUserId to toUserId by amount
    // If the settlement exceeds the debt, it creates a reverse debt
    public synchronized void settleUp(String fromUserId, String toUserId, double amount) {
        // Settlement is the reverse of a balance update
        updateBalance(fromUserId, toUserId, -amount);
    }

    // Returns how much userId1 owes userId2
    // Positive = userId1 owes userId2, Negative = userId2 owes userId1
    public double getBalance(String userId1, String userId2) {
        ConcurrentHashMap<String, Double> userBalances = balances.get(userId1);
        if (userBalances == null) return 0.0;
        return userBalances.getOrDefault(userId2, 0.0);
    }

    // Returns a snapshot of all balances for a user (defensive copy)
    public Map<String, Double> getBalancesForUser(String userId) {
        ConcurrentHashMap<String, Double> userBalances = balances.get(userId);
        if (userBalances == null) return new HashMap<>();
        return new HashMap<>(userBalances);
    }
}
