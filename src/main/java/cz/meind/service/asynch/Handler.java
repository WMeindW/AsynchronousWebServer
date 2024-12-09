package cz.meind.service.asynch;

import cz.meind.application.Application;
import cz.meind.dto.Request;
import cz.meind.dto.Response;
import cz.meind.service.Parser;


import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
            File file = new File((Application.publicFilePath + request.getPath()));
            OutputStream outputStream = client.getOutputStream();

            if (file.exists() && !file.isDirectory()) {
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    PrintWriter headerWriter = new PrintWriter(outputStream, true);
                    headerWriter.println("HTTP/1.1 200 OK");
                    headerWriter.println("Content-Type: " + Application.server.contentTypes.get(file.getName().split("\\.")[1]));
                    headerWriter.println("Content-Length: " + file.length());
                    headerWriter.println();

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            } else {
                PrintWriter headerWriter = new PrintWriter(outputStream, true);
                headerWriter.println("HTTP/1.1 404 Not Found");
                headerWriter.println("Content-Type: text/plain");
                headerWriter.println();
                headerWriter.println("404 Soubor nenalezen");
            }
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
