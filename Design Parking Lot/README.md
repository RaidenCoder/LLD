# 🚗 Parking Lot System

A thread-safe Parking Lot System built using Java and object-oriented design principles. The project demonstrates strategy-based parking allocation, pluggable fee calculation, and concurrent parking operations.

## Features

- Multi-floor parking
- Multiple vehicle types
- Multiple parking spot sizes
- Parking ticket generation
- Vehicle entry & exit
- Dynamic parking availability
- Strategy-based parking allocation
- Pluggable fee calculation

## Design Patterns

- ✅ Singleton Pattern
    - Single ParkingLot instance

- ✅ Strategy Pattern
    - Nearest First Allocation
    - Best Fit Allocation
    - Hourly Fee Calculation

## Design Highlights

- Thread-safe Singleton implementation using Double Checked Locking.
- Concurrent ticket management using ConcurrentHashMap.
- Retry mechanism prevents race conditions while allocating parking spots.
- Allocation and fee calculation are completely decoupled using Strategy Pattern.

## Concepts Demonstrated

- Object-Oriented Design
- SOLID Principles
- Concurrency
- Dependency Injection
- Clean Code
- Thread Safety

## Future Improvements

- Reservation System
- Electric Vehicle Charging
- Dynamic Pricing
- Payment Gateway
- Admin Dashboard