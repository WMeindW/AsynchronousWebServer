package cz.meind.service;

import cz.meind.application.Application;
import cz.meind.service.asynch.Listener;

public class Server {
    public Thread serverThread;
    public Server() {
        serverThread = new Thread(Listener::listen);
        Application.logger.info(Listener.class,"Socket server started.");
        serverThread.start();
    }
}
