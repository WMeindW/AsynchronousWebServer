package cz.meind.service.asynch;

import cz.meind.application.Application;
import cz.meind.dto.Request;
import cz.meind.dto.Response;
import cz.meind.service.Parser;


import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

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
            Request request = Parser.parseRequest(client.getInputStream());
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            HashMap<String,String> headers = new HashMap<>();
            String body;
            String code;
            File file = new File((Application.publicFilePath + request.getPath()));
            Response response;
            try {
                body = Files.readString(file.toPath());
                code = "200 OK";
                response = new Response(Application.server.contentTypes.get(file.getName().split("\\.")[1]), body, code);
            } catch (IOException e) {
                body = "Not found";
                code = "404 Not Found";
                response = new Response(Application.server.contentTypes.get("txt"), body, code);
                Application.logger.error(Handler.class, e);
            }

            out.println(response);
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
