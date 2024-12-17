# **AsynchronousWebServer**

## **Threading**

The **AsynchronousWebServer** is designed to handle multiple client connections efficiently using threading. It employs
the following threading model:

- **Main Thread**: Initializes the logger, parses the configuration, and dispatches the server thread.

- **Server Thread**: Starts listening for client requests and launches a **Daemon Thread**.

- **Daemon Thread**: Handles background tasks like monitoring and proper shutdown procedure.

- **Thread Pool**: Threads are assigned dynamically to handle requests, log activities, and send responses. Threads are
  returned to the pool after task completion for reuse, ensuring resource optimization.

To ensure **safe thread handling**, synchronization mechanisms (e.g., `synchronized` blocks, thread-safe collections)
are employed when sharing resources like the thread pool or data between threads.

### **Safe Thread Handling**

- **Thread Safety**: Shared resources such as request queues, logging mechanisms, and response objects are protected
  using synchronized blocks or thread-safe collections (e.g., `ConcurrentHashMap`).

- **Graceful Handling**: Each thread is monitored, and exceptions or errors are handled gracefully to prevent unexpected
  crashes.

- **Clean Up**: Proper cleanup ensures threads are terminated correctly, freeing resources during shutdown.

### **Passing Information Between Threads**

- **Request Data**: Information such as parsed objects or request details is passed between threads via shared objects
  or message queues.

- **Thread Context**: Static storage can be used to pass context-specific data like user
  session information.

- **Thread Communication**: Worker threads callback responses to the daemon thread.

---

## **Structure**

The server follows a modular architecture:

1. **Initialization**:
    - Starts the server.
    - Configures and initializes logging mechanisms.
    - Parses configuration settings (e.g., port number, thread pool size).

2. **Core Workflow**:
    - **Dispatch Server Thread**:
        - Listens for incoming client connections.
        - Accepts client requests.
    - **Daemon Thread**:
        - Parses request objects (e.g., JSON or XML) using Jackson.
        - Assigns threads from a thread pool to handle requests.
    - **Request Handling**:
        - Threads process requests, log activities, and handle responses.

3. **Thread Pool**:
    - Dynamically assigns and reuses threads for handling requests.
    - Threads safely interact with shared resources like the logging system or response handler.

4. **Clean Up**:
    - Ensures proper shutdown and resource deallocation when the server stops.

---

## **Description**

The **AsynchronousWebServer** is a lightweight, high-performance server designed to handle multiple client connections
concurrently. Its primary use cases include serving JSON-based requests and returning responses efficiently using a
thread pool architecture.

---

## **Setup & Usage**

### **Requirements**:

- Java 17 or higher
- Web browser for front-end testing

### **Running the Server**:

1. Clone the repository:
   ```bash
   git clone https://github.com/WMeindW/AsynchronousWebServer.git
   cd AsynchronousWebServer
   ```
2. Compile the code:
   ```bash
   javac -cp .:jackson-core.jar AsynchronousWebServer.java
   ```
3. Run the server:
   ```bash
   java AsynchronousWebServer
   ```
4. Open the HTML front-end in a browser to send test requests.

---

## **Future Improvements**

- Implement HTTPS for secure communication.
- Add more robust error handling and logging.
- Extend support for XML parsing.
- Add more advanced thread monitoring and scaling for large-scale workloads.

---

## **Contributing**

Contributions are not welcome! Feel free to not open an issue nor submit a pull request.

---

## **License**

This project is licensed under the MIT License.

- [DanielLinda/AsynchronousWebServer](https://github.com/WMeindW/AsynchronousWebServer)