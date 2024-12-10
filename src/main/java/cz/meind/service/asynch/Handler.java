package cz.meind.service.asynch;

import cz.meind.application.Application;
import cz.meind.dto.MonitoringRecord;
import cz.meind.dto.Request;
import cz.meind.dto.Response;
import cz.meind.service.Parser;


import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class Handler {

    private int id;

    private Socket client;

    private Thread thread;

    public void setId(int id) {
        this.id = id;
    }

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public Handler(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void handle(Socket c) {
        thread = new Thread(this::run);
        client = c;
        Application.logger.info(Handler.class, "Dispatching thread: " + thread.getClass() + " with id " + id + " and priority " + thread.getPriority());
        thread.start();
    }

    private void run() {
        long start = System.currentTimeMillis();
        try {
            Request request = Parser.parseRequest(client.getInputStream());
            Application.logger.info(Handler.class, "Handling request: " + request);
            if (request.getPath().endsWith("/")) request.setPath(request.getPath() + "index.html");
            Response response = new Response(new File((Application.publicFilePath + request.getPath())), client.getOutputStream());
            Application.logger.info(Handler.class, "Handling response: " + response);
            response.respond();
            client.close();
            Application.monitor.addRecord(new MonitoringRecord(false, id, "200 OK", System.currentTimeMillis() - start, new File((Application.publicFilePath + request.getPath())).length(), LocalDateTime.now().toString()));
        } catch (Exception e) {
            Application.logger.error(Handler.class, e);
            new ErrorHandler(e).handle(client);
        }
        close();
    }

    private void close() {
        Application.server.releaseHandler(this);
        Thread.currentThread().interrupt();
    }

}
