package cz.meind.service.asynch;

import cz.meind.application.Application;
import cz.meind.dto.MonitoringRecord;


import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

public class ErrorHandler extends Handler {

    private Socket client;

    private final Exception e;

    private String path;

    public ErrorHandler(Exception e, String path) {
        super(-1);
        this.e = e;
        this.path = path;
    }

    public int getId() {
        return super.getId();
    }

    public void handle(Socket c) {
        client = c;
        run();
    }

    private void run() {
        long start = System.currentTimeMillis();
        try {
            Application.logger.error(Handler.class, "Handling error: " + e);
            PrintWriter out = new PrintWriter(client.getOutputStream());
            out.println("HTTP/1.1 500 Internal Server Error");
            out.println("Content-Type: text/html; charset=UTF-8");
            out.println("Server: " + Application.serverName);
            out.println("");
            out.println("<html><body>");
            out.println("<h1>Server failed with error 500!</h1>");
            out.println("<h2> " + e + "</h2>");
            out.println("</body></html>");
            Application.logger.info(Handler.class, "Handling error response: " + out);
            out.close();
            Application.monitor.addRecord(new MonitoringRecord(true, super.getId(), System.currentTimeMillis() - start, path));
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
