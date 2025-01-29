- **Member 1 (Adam) **: [GitHub Profile](https://github.com/Adam9hub)
- **Member 2 (Yulian) **: [GitHub Profile](https://github.com/Yulian1705)
- **Member 3 (Enea) **: [GitHub Profile](https://github.com/en-ea)
- **Member 4 (Niwhar) **: [GitHub Profile](https://github.com/ninoa2000)


Part 1: Component and Interface Concepts

A software component is a modular, reusable, and self-contained unit of software that encapsulates specific functionality and interacts with other components through well-defined interfaces.

## Key characteristics:
Modularity
Reusability
Encapsulation
Interoperability
Replaceability
Composability
Independence
Standardisation

an interface is a defined set of methods, functions, or properties that specify how different software components or systems interact with each other

Transportable interface supports modular design by allowing different objects to be transported without knowing their internal details. It promotes reusability, flexibility, and easy integration, making the system more scalable and maintainable.

Part 2: Code Analysis


## Modular Properties
Encapsulation – The class encapsulates SMTP communication details (e.g., smtpHost, smtpPort, and socket handling) while exposing only the send method for message transmission.
Reusability – Implements the Transportable interface, making it interchangeable with other transport mechanisms.
Abstraction – Hides lower-level socket communication details behind high-level methods (send and sendMessage).
Maintainability – Methods are self-contained, making it easy to update SMTP communication logic without affecting other components.

## Cohesive Properties
Single Responsibility – The class is focused solely on handling message transport, adhering to high cohesion.
Well-Defined Methods – Helper methods like sendMessage and smtpErrorExists break down complex tasks, improving readability and maintainability.
Tightly Related Functionalities – All methods work together for a single purpose: sending messages via SMTP.


Part 3: Implementation Challenge







