package cz.meind.service;

import cz.meind.application.Application;
import cz.meind.service.asynch.Handler;
import cz.meind.service.asynch.Listener;

import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public Thread serverThread;

    private ConcurrentHashMap<Integer, Handler> pool;

    public Server() {
        createPool();
        serverThread = new Thread(Listener::listen);
        Application.logger.info(Listener.class, "Socket server started on port " + Application.port + ".");
        serverThread.start();
    }

    public synchronized Handler getHandler() {
        while (pool.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return pool.remove(pool.keySet().iterator().next());
    }

    private void createPool() {
        pool = new ConcurrentHashMap<>();
        for (int i = 0; i < Application.poolSize; i++) {
            Handler handler = new Handler();
            pool.put(i, handler);
        }
    }
}
