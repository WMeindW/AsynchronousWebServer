package cz.meind.service.asynch;

import cz.meind.application.Application;
import cz.meind.service.Parser;


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
        try {
            System.out.println(Parser.parseRequest(client.getInputStream()));

            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html; charset=UTF-8");
            out.println("");  // Pustý řádek odděluje hlavičky od těla
            out.println("<html><body>");
            out.println("<h1>Vítejte na mém serveru!</h1>");
            out.println("<p>Tohle je jednoduchý webový server napsaný v Javě.</p>");
            out.println("</body></html>");
            out.close();
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
