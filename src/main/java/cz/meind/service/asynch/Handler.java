package cz.meind.service.asynch;

import cz.meind.application.Application;


import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Handler {

    private int id;

    private Socket client;

    private Thread thread;


    public Handler(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void handle(Socket c) {
        System.out.println("Handling: " + id);
        thread = new Thread(this::run);
        client = c;
        thread.start();
    }

    private void run() {
        close();
    }

    private void close() {
        try {
            client.close();
            Application.server.releaseHandler(this);
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            Application.logger.error(Handler.class, e);
        }
    }

}
