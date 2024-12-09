package cz.meind.logger;

import java.io.File;
import java.io.IOException;

public class Logger {

    private File logFile;

    public Logger(String path) {
        try {
            createLogFile(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createLogFile(String path) throws IOException {
        logFile = new File(path);
        if (!logFile.mkdirs()) if (logFile.createNewFile()) throw new IOException("Could not create file");
    }

    public void error(String message) {
        System.out.println("ERROR: " + message);
    }

    public void info(String message) {
        System.out.println("INFO: " + message);
    }

    public void warn(String message) {
        System.out.println("WARN: " + message);
    }
}
