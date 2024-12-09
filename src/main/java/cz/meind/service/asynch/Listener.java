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
        } catch (IOException e) {
            Application.logger.error(Listener.class, e);
        }

    }

    private static void run() throws IOException {
        start();
        while (true) {
            Socket clientSocket = server.accept();
            System.out.println("Request");
            Application.server.getHandler().handle(clientSocket);
        }
    }

    public static void listen() {
        try {
            run();
        } catch (IOException e) {
            Application.logger.error(Listener.class, e);
        }
    }
}

