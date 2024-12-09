package cz.meind.service;

import cz.meind.application.Application;
import cz.meind.service.asynch.ErrorHandler;
import cz.meind.service.asynch.Handler;
import cz.meind.service.asynch.Listener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public Thread serverThread;

    private ConcurrentHashMap<Integer, Handler> pool;

    private ConcurrentHashMap<Integer, Handler> dispatched;


    public Server() {
        createPool();
        serverThread = new Thread(Listener::listen);
        serverThread.setName("server");
        serverThread.start();
        Application.logger.info(Listener.class, "Socket server started on port " + Application.port + ".");
    }

    public synchronized Handler getHandler() {
        if (pool.isEmpty()) return new ErrorHandler(new IllegalStateException("Server pool depleted"));
        Map.Entry<Integer, Handler> entry = pool.entrySet().iterator().next();
        pool.remove(entry.getKey());
        dispatched.put(entry.getKey(), entry.getValue());
        return entry.getValue();
    }

    public synchronized void releaseHandler(Handler handler) {
        dispatched.remove(handler.getId());
        pool.put(handler.getId(), handler);
    }

    private void createPool() {
        dispatched = new ConcurrentHashMap<>();
        pool = new ConcurrentHashMap<>();
        for (int i = 0; i < Application.poolSize; i++) {
            Handler handler = new Handler(i);
            pool.put(i, handler);
        }
    }
}
