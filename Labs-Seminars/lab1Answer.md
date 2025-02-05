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
Create a UML component diagram for the Email Transport system.
   1. A UML component diagram for the Email Transport system might include:
   2. Components: Transport, Message, Transportable, SMTP Server.
   3. Interfaces: Transportable (implemented by Transport).
   4. Relationships: Transport component depends on the Transportable interface and communicates with the SMTP Server component.

Implement an alternative transport mechanism (e.g., REST-based email sending) using the Transportable interface.

public class RestTransport implements Transportable {
    private String restEndpoint;

    public RestTransport(String restEndpoint) {
        this.restEndpoint = restEndpoint;
    }

    @Override
    public void send(Message msg) throws TransportException {
        // Implementation for sending message via REST
        try {
            // REST client code to send message
        } catch (Exception e) {
            throw new TransportException("Failed to send message via REST", e);
        }
    }
}


Discuss how the component design facilitates potential reuse in different systems.

The component design facilitates reuse by decoupling the transport mechanism from the rest of the system. By adhering to the Transportable interface, different transport mechanisms (e.g., SMTP, REST) can be easily swapped in or out without modifying the core system. This makes the system more flexible and adaptable to different environments or requirements. Additionally, the modular nature of the components allows them to be reused in different systems that require similar functionality, reducing development time and effort.







