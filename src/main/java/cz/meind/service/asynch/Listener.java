package cz.meind.service.asynch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener {
    public static void listen() {
        try {
            // Vytvoření serverového socketu na portu 8080
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Server běží na portu 8080");

            while (true) {
                // Akceptování příchozího připojení
                Socket clientSocket = serverSocket.accept();

                // Vytvoření streamů pro čtení a zápis
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String requestLine;
                System.out.println("Požadavek:");
                while ((requestLine = in.readLine()) != null && !requestLine.isEmpty()) {
                    System.out.println(requestLine);
                }

                // Odpověď (prostý HTML obsah)
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: text/html; charset=UTF-8");
                out.println("");  // Pustý řádek odděluje hlavičky od těla
                out.println("<html><body>");
                out.println("<h1>Vítejte na mém serveru!</h1>");
                out.println("<p>Tohle je jednoduchý webový server napsaný v Javě.</p>");
                out.println("</body></html>");

                // Zavření socketu
                in.close();
                out.close();
                clientSocket.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

