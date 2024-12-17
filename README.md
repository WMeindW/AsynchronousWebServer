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
    - **Dispatch Server Thread**: Listens for incoming client connections.
    - **Daemon Thread**:
        - Accepts client requests.
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

## **Technology**

The project leverages the following technologies:

- **Java**: The core programming language used to implement the server, threading model, and socket communication.
- **Sockets**: Java Sockets (`ServerSocket` and `Socket`) are used for asynchronous communication between the server and
  clients.
- **Threads**: A multi-threaded architecture ensures concurrent request handling using a thread pool.
- **Thread Pool**: Manages worker threads efficiently, reducing thread creation overhead.
- **Jackson**: For parsing and serializing JSON objects from client requests.
- **HTML and JavaScript**: For the front-end, providing a client interface to send requests to the server and display
  responses.

---

## **Description**

The **AsynchronousWebServer** is a lightweight, high-performance server designed to handle multiple client connections
concurrently. Its primary use cases include serving JSON-based requests and returning responses efficiently using a
thread pool architecture.

### **Key Features**:

1. **Multi-threaded Design**:
    - Efficiently manages client requests using a thread pool.
    - Threads are reused, reducing overhead and improving performance.

2. **Socket-Based Communication**:
    - Establishes bi-directional communication with clients using Java Sockets.

3. **JSON Parsing**:
    - Uses **Jackson** to parse client request data and generate appropriate responses.

4. **Thread Safety and Resource Management**:
    - Threads are safely managed with synchronized blocks and thread-safe collections.
    - Shared resources are accessed efficiently without race conditions.

5. **Information Sharing**:
    - Request and response data are shared between threads using shared objects or thread-local storage.

6. **Client Integration**:
    - Simple HTML and JavaScript-based front-end for testing server requests and responses.

7. **Resource Management**:
    - Ensures threads are returned to the pool after task completion.
    - Implements clean-up mechanisms for graceful shutdown.

---

## **Workflow**

Below is the detailed server workflow based on the diagram:

1. **Initialization**:
    - The server starts and initializes logging.
    - Configuration settings are parsed.

2. **Server Dispatch**:
    - A **Server Thread** is dispatched to start listening for incoming client requests.

3. **Request Handling**:
    - A **Daemon Thread** accepts requests and parses incoming objects.
    - Requests are forwarded to a **Thread Pool**:
        - Handles request processing.
        - Logs activities.
        - Generates and sends responses back to clients.

4. **Thread Pool Management**:
    - Threads are dynamically assigned to process requests and returned to the pool after completion.

5. **Thread Communication**:
    - Shared objects or queues pass data between threads (e.g., parsed requests, responses).
    - Results and logs are communicated back to the main server thread.

6. **Clean Up**:
    - Ensures resources are freed, and connections are gracefully closed.

---

## **Setup & Usage**

### **Requirements**:

- Java 8 or higher
- Jackson library
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
- Introduce a configuration file for customizable server settings.
- Extend support for XML parsing.
- Add more advanced thread monitoring and scaling for large-scale workloads.

---

## **Contributing**

Contributions are welcome! Feel free to open an issue or submit a pull request.

---

## **License**

This project is licensed under the MIT License.

- [DanielLinda/AsynchronousWebServer](https://github.com/WMeindW/AsynchronousWebServer)