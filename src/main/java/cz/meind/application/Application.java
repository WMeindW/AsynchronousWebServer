package cz.meind.application;

import cz.meind.logger.Logger;
import cz.meind.service.Server;
import cz.meind.service.asynch.Daemon;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Application {
    public static Logger logger;

    public static Server server;

    public static Thread daemonThread;

    public static final String configFilePath = "src/main/resources/application.properties";

    public static String logFilePath = "log/log.txt";

    public static int port = 8088;

    public static int poolSize = 16;

    public static List<String> defaultHeaders;

    public static String publicFilePath = "src/main/resources/public";

    public static void run() {
        initializeLogger();
        initializeConfig();
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
            Application.logger.error(Application.class, e);
        }
        try {
            logFilePath = properties.getProperty("log.file.path");
            port = Integer.parseInt(properties.getProperty("server.port"));
            poolSize = Integer.parseInt(properties.getProperty("server.thread.pool.size"));
            defaultHeaders = List.of(properties.getProperty("server.default.headers").split(", "));
            publicFilePath  = properties.getProperty("server.public.file.path");
        } catch (Exception e) {
            Application.logger.error(Application.class, e);
        }

    }
}
