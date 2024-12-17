package cz.meind.service;

import cz.meind.application.Application;
import cz.meind.service.asynch.ErrorHandler;
import cz.meind.service.asynch.Handler;
import cz.meind.service.asynch.Listener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public Thread serverThread;

    public ConcurrentHashMap<String, String> contentTypes = new ConcurrentHashMap<>();

    private ConcurrentHashMap<Integer, Handler> pool;

    private ConcurrentHashMap<Integer, Handler> dispatched;

    public Thread getServerThread() {
        return serverThread;
    }

    public ConcurrentHashMap<Integer, Handler> getDispatched() {
        return dispatched;
    }

    public ConcurrentHashMap<Integer, Handler> getPool() {
        return pool;
    }

    public Server(String test) {
        System.out.println(test);
        loadMimeTypes();
    }

    public Server() {
        createPool();
        loadMimeTypes();
        serverThread = new Thread(Listener::listen);
        serverThread.setName("server");
        serverThread.start();
    }

    public synchronized Handler getHandler() {
        if (pool.isEmpty()) return new ErrorHandler(new IllegalStateException("Server pool depleted"),"unknown");
        Map.Entry<Integer, Handler> entry = pool.entrySet().iterator().next();
        pool.remove(entry.getKey());
        dispatched.put(entry.getKey(), entry.getValue());
        return entry.getValue();
    }

    public synchronized void releaseHandler(Handler handler) {
        dispatched.remove(handler.getId());
        pool.put(handler.getId(), handler);
    }

    private void loadMimeTypes() {
        try {
            String mimes = Files.readString(Path.of("src/main/resources/mimes.properties"));
            for (String mime : mimes.split("\n")) {
                String[] split = mime.split("=");
                contentTypes.put(split[0].trim(), split[1].trim());
            }
        } catch (IOException e) {
            Application.logger.error(Server.class, e);
        }
    }

    private void createPool() {
        dispatched = new ConcurrentHashMap<>();
        pool = new ConcurrentHashMap<>();
        for (int i = 0; i < Application.poolSize; i++) {
            pool.put(i, new Handler(i));
        }
    }
}
