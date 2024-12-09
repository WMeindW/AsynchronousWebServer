package cz.meind.service.asynch;

import cz.meind.application.Application;
import cz.meind.service.Parser;


import java.io.*;
import java.net.Socket;

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
        try {
            System.out.println(Parser.parseRequest(client.getInputStream()));


            client.close();
        } catch (IOException e) {
            Application.logger.error(Handler.class, e);
        }
        close();
    }

    private void close() {
        Application.server.releaseHandler(this);
        Thread.currentThread().interrupt();
    }

}
