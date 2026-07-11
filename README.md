# 🏗️ Low Level Design (LLD)

A collection of Low Level Design (LLD) implementations in Java demonstrating Object-Oriented Programming, SOLID principles, design patterns, concurrency, and clean architecture.

## Projects

| Project | Design Patterns / Concepts |
|---------|----------------------------|
| 🔗 URL Shortener | Strategy, Observer, Repository, Builder, SOLID |
| 🚗 Parking Lot | Singleton, Strategy, Concurrency, SOLID |
| 💰 Splitwise | Strategy, Observer, Singleton, Thread Safety, SOLID |
| 🎬 BookMyShow | *Coming Soon* |
| 🛗 Elevator System | *Coming Soon* |
| 📚 Library Management | *Coming Soon* |
| 📝 Logging Framework | *Coming Soon* |
| 💾 LRU Cache | *Coming Soon* |

---

## 🔗 URL Shortener

A scalable URL Shortener built in Java supporting multiple key generation strategies, URL expiration, analytics, and extensible architecture.

### Features

- Shorten long URLs
- Redirect using short URLs
- Multiple key generation strategies (Base62, UUID, Random)
- URL expiration support
- Duplicate URL detection
- Click analytics
- In-memory repository
- Extensible architecture

### Design Patterns

- Strategy Pattern
- Observer Pattern
- Repository Pattern
- Builder Pattern

### Concepts

- Object-Oriented Programming
- SOLID Principles
- Clean Architecture
- Dependency Injection

---

## 🚗 Parking Lot System

A Parking Lot System supporting multiple floors, parking strategies, fee calculation, and thread-safe operations.

### Features

- Multi-floor parking
- Multiple vehicle and parking spot sizes
- Parking ticket generation
- Vehicle entry & exit
- Parking availability tracking
- Strategy-based spot allocation (Nearest First, Best Fit)
- Pluggable fee calculation
- Runtime strategy switching
- Thread-safe parking operations
- Retry mechanism for concurrent spot allocation

### Design Patterns

- Singleton Pattern
- Strategy Pattern

### Concepts

- Object-Oriented Programming
- SOLID Principles
- Concurrency
- Dependency Injection

---

## 💰 Splitwise

A Splitwise-inspired expense sharing system supporting individual and group expenses, multiple split strategies, settlements, notifications, and thread-safe balance management.

### Features

- Individual & Group Expenses
- Equal / Exact / Percentage Splits
- Partial Settlements
- Pairwise Balance Tracking
- Expense History
- Notifications using Observer Pattern
- Thread-safe Balance Updates

### Design Patterns

- Singleton Pattern
- Strategy Pattern
- Observer Pattern

### Concepts

- Object-Oriented Programming
- SOLID Principles
- Concurrency
- Immutable Expense Records
- Clean Architecture