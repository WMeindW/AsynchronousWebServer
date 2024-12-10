package cz.meind.service.asynch;

import cz.meind.application.Application;
import cz.meind.dto.Request;
import cz.meind.dto.Response;
import cz.meind.service.Parser;


import java.io.*;
import java.net.Socket;

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
        Application.logger.info(Handler.class,"Dispatching thread: " + thread.getClass() + " with id " + id + " and priority " + thread.getPriority());
        thread.start();
    }

    private void run() {
        try {
            Request request = Parser.parseRequest(client.getInputStream());
            Application.logger.info(Handler.class,"Handling request: " + request);
            Response response = new Response(new File((Application.publicFilePath + request.getPath())),client.getOutputStream());
            Application.logger.info(Handler.class,"Handling response: " + response);
            response.respond();
            client.close();
        } catch (IOException e) {
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
