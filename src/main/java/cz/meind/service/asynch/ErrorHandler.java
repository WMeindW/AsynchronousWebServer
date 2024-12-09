package cz.meind.service.asynch;

import cz.meind.application.Application;
import cz.meind.service.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ErrorHandler extends Handler {

    private Socket client;

    private Thread thread;

    private Exception e;

    public ErrorHandler(Exception e) {
        super(-1);
        this.e = e;
    }

    public void handle(Socket c) {
        System.out.println("Handling: " + super.getId());
        thread = new Thread(this::run);
        client = c;
        thread.start();
    }

    private void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            // Přijetí HTTP požadavkuOdpověď (prostý HTML obsah)
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html; charset=UTF-8");
            out.println("");  // Pustý řádek odděluje hlavičky od těla
            out.println("<html><body>");
            out.println("<h1>Error!</h1>");
            out.println("<h2>" + e + "</h2>");
            out.println("</body></html>");

            // Zavření socketu
            in.close();
            out.close();
        } catch (IOException e) {
            Application.logger.error(Handler.class, e);
        }
        close();
    }

    private void close() {
        try {
            client.close();
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            Application.logger.error(Handler.class, e);
        }
    }
}
