package cz.meind.service.asynch;

import cz.meind.application.Application;
import cz.meind.dto.Request;
import cz.meind.service.Parser;


import java.io.*;
import java.net.Socket;

public class ErrorHandler extends Handler {

    private Socket client;

    private final Exception e;

    public Socket getClient() {
        return client;
    }

    public void setClient(Socket client) {
        this.client = client;
    }


    public ErrorHandler(Exception e) {
        super(-1);
        this.e = e;
    }

    public int getId() {
        return super.getId();
    }

    public void handle(Socket c) {
        client = c;
        run();
    }

    private void run() {
        try {
            Application.logger.error(Handler.class, "Handling error: " + e);
            PrintWriter out = new PrintWriter(client.getOutputStream());
            out.println("HTTP/1.1 500 OK");
            out.println("Content-Type: text/html; charset=UTF-8");
            out.println("");
            out.println("<html><body>");
            out.println("<h1>Server failed with error 500!</h1>");
            out.println("<h2> " + e + "</h2>");
            out.println("</body></html>");
            Application.logger.info(Handler.class, "Handling error response: " + out);
            out.close();
            client.close();
        } catch (Exception e) {
            Application.logger.error(Handler.class, e);
        }
        close();
    }

    private void close() {
        Thread.currentThread().interrupt();
    }

}
