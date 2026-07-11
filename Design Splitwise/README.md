# 💰 Splitwise

A Java implementation of Splitwise supporting individual and group expenses, multiple expense splitting strategies, settlements, notifications, and thread-safe balance management.

## Features

- Individual Expenses
- Group Expenses
- Equal Split
- Exact Split
- Percentage Split
- Partial Settlements
- Expense History
- Pairwise Balance Tracking
- Observer-based Notifications

---

## Design Patterns

### ✅ Singleton Pattern

Single SplitwiseService instance manages all users, groups, expenses and balances.

### ✅ Strategy Pattern

Expense splitting logic is completely pluggable.

- Equal Split
- Exact Split
- Percentage Split

Adding a new split type requires creating another strategy without modifying existing code.

### ✅ Observer Pattern

Observers are notified whenever:

- New Expense is Added
- Settlement Happens

Current implementation demonstrates Email Notifications.

---

## Interesting Design Choice

### Pairwise Balance Graph

Instead of storing balances per user, the system models balances as a directed weighted graph.

```
Alice ───► Bob      100
Bob   ───► Charlie  200
Charlie ─► Alice    50
```

Internally:

```
balances
│
├── Alice
│      └── Bob -> 100
│
├── Bob
│      └── Charlie -> 200
│
└── Charlie
       └── Alice -> 50
```

Each directed edge represents **"fromUser owes toUser amount"**, enabling constant-time balance lookup and efficient settlement updates.

---

## Thread Safety

- ConcurrentHashMap for Users
- ConcurrentHashMap for Groups
- ConcurrentHashMap for Expenses
- CopyOnWriteArrayList for Observers
- Synchronized balance updates

---

## Concepts Demonstrated

- Object-Oriented Design
- SOLID Principles
- Graph-based Data Modeling
- Concurrency
- Immutable Expense Records
- Dependency Injection
- Clean Architecture

---

## Future Improvements

- Group-specific Balance Sheets
- Multiple Currencies
- Debt Simplification
- Persistent Storage
- Recurring Expenses
- Payment Gateway Integration