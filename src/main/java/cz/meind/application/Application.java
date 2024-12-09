package cz.meind.application;

import cz.meind.logger.Logger;
import cz.meind.service.Server;
import cz.meind.service.asynch.Daemon;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Application {
    public static Logger logger;

    public static Server server;

    public static Thread daemonThread;

    public static final String configFilePath = "src/main/resources/application.properties";

    public static String logFilePath;

    public static int port;

    public static void run() {
        initializeConfig();
        initializeLogger();
        initializeServer();
        initializeDaemon();
    }

    private static void initializeDaemon() {
        daemonThread = new Thread(new Daemon());
        daemonThread.setDaemon(true);
        Application.logger.info(Daemon.class, "Starting daemon.");
    }

    private static void initializeServer() {
        Application.logger.info(Server.class, "Starting server.");
        server = new Server();
    }

    private static void initializeLogger() {
        logger = new Logger(logFilePath);
        logger.message("https://github.com/WMeindW \n\n\nDaniel Linda, cz.meind.AsynchronousWebServer");
        logger.info(Application.class, "Starting application.");
    }

    private static void initializeConfig() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(configFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logFilePath = properties.getProperty("log.file.path");
        port = Integer.parseInt(properties.getProperty("server.port"));
    }
}
