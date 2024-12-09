package cz.meind.application;

import cz.meind.logger.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Application {
    public static Logger logger;

    public static final String configFilePath = "src/main/resources/application.properties";

    public static String logFilePath;

    public static int port;

    public static void run() {
        System.out.println("Starting the application...");
        initializeConfig();
        initializeLogger();
    }

    private static void initializeLogger() {
        logger = new Logger(logFilePath);
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
