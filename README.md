

# Online-Chat-Application

A simple real-time chat application built with **Spring Boot** and **WebSockets**. This application allows users to join a shared chatroom and exchange public messages in real time. All messages are broadcast to every connected user-there are no private messages or separate chat rooms.

---

## **Features**

- Real-time public chat using WebSocket (STOMP)
- User connect/disconnect notifications
- Active user list broadcast to all clients
- Built with Spring Boot for easy deployment

---

## **Technology Stack**

- Java
- Spring Boot
- Spring WebSocket (STOMP, SockJS)
- Maven (build and run)
- Docker (optional, for containerization)

---

## **Getting Started**

### **Prerequisites**

- Java 8 or higher
- Maven


### **Running Locally**

Clone the repository:

```bash
git clone https://github.com/uthsare/Online-Chat-Application.git
cd Online-Chat-Application
```

Build and run with Maven:

```bash
mvn spring-boot:run
```

The server will start on port **8080** by default.

---

## **WebSocket Endpoint**

| Purpose | Endpoint/Topic |
| :-- | :-- |
| STOMP endpoint | `/ws` |
| Application destination prefix | `/app` |
| Subscribe to receive messages | `/topic/message` |
| Send messages to | `/app/message` |


---

## **Docker**

To build and run the application using Docker:

```bash
docker build -t online-chat-app .
docker run -p 8080:8080 online-chat-app
```


---

## **Usage**

You can connect to the WebSocket server using any STOMP-compatible client (e.g., JavaScript with SockJS, or tools like websocket.org Echo Test).

**Basic message flow:**

- Connect to `/ws` endpoint
- Subscribe to `/topic/message` to receive chat messages
- Send messages to `/app/message`

For a simple frontend, you may use any WebSocket/STOMP demo client or build your own!

---

## **Project Structure**

| File/Directory | Description |
| :-- | :-- |
| `src/main/java/com/uthsara/websocket_demo/` | Main source directory |
| `WebsocketDemoApplication.java` | Main Spring Boot entry point |
| `WebsocketConfig.java` | WebSocket/STOMP configuration |
| `WebsocketController.java` | Handles chat messaging and user events |
| `WebSocketSessionManager.java` | Manages active users |
| `Message.java` | Chat message model |


---

## **License**

This project is licensed under the **MIT License**.

