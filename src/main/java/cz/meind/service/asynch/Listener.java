package cz.meind.service.asynch;

import cz.meind.application.Application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Listener {
    private static ServerSocket server;

    private static void start() {
        try {
            server = new ServerSocket(Application.port);
            Application.logger.info(Listener.class, "Socket server started on port " + Application.port + ".");
        } catch (IOException e) {
            Application.logger.error(Listener.class, e);
        }

    }

    private static void run() throws IOException {
        start();
        while (true) {
            Socket clientSocket = server.accept();
            Application.logger.info(Listener.class,"Accepted client socket");
            Application.server.getHandler().handle(clientSocket);
        }
    }

    public static void listen() {
        try {
            Thread.sleep(1000);
            run();
        } catch (IOException e) {
            Application.logger.error(Listener.class, e);
        } catch (InterruptedException e) {
            Application.logger.error(Listener.class, e);
            System.exit(130);
        }
    }
}

