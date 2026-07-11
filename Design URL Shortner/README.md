# 🔗 URL Shortener

A scalable URL Shortener built in Java following Low Level Design principles. The project focuses on extensibility through design patterns while keeping the architecture modular and easy to evolve.

## Features

- Shorten long URLs
- Redirect short URLs
- Multiple key generation strategies
- URL expiration
- Click analytics
- In-memory repository
- Pluggable architecture

## Design Patterns

- ✅ Strategy Pattern
    - Different key generation algorithms (Base62, UUID, Random)

- ✅ Observer Pattern
    - Analytics are notified whenever a short URL is accessed

- ✅ Repository Pattern
    - Storage abstraction

- ✅ Builder Pattern
    - Flexible construction of URL objects

## Design Highlights

- Strategy Pattern allows new key generation algorithms without modifying existing code.
- Observer Pattern decouples analytics from URL redirection.
- Repository abstraction makes switching from in-memory storage to databases straightforward.
- Expiration logic is encapsulated inside the domain model.

## Concepts Demonstrated

- Object-Oriented Design
- SOLID Principles
- Dependency Injection
- Clean Architecture
- Extensible System Design

## Future Improvements

- Redis Cache
- Persistent Database
- Rate Limiting
- QR Code Generation
- Custom Aliases
- Distributed ID Generation