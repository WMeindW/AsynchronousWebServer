package cz.meind.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class Logger {

    private File logFile;

    private final Object lock;

    public Logger(String path) {
        lock = new Object();
        try {
            createLogFile(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createLogFile(String path) throws IOException {
        logFile = new File(path);
        if (!Files.exists(Path.of(logFile.getPath()))) {
            Files.createDirectories(Path.of(logFile.getParent()));
            Files.createFile(Path.of(logFile.getPath()));
        }

    }

    public void write(String content) {
        synchronized (lock) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                writer.write(content);
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void error(Class<?> c, String message) {
        System.out.println(c.getName() + " [" + LocalDateTime.now() + "] ERROR: " + message);
        write(c.getName() + " [" + LocalDateTime.now() + "] ERROR: " + message);
    }

    public void error(Class<?> c, Exception e) {
        System.out.println(c.getName() + " [" + LocalDateTime.now() + "] ERROR: " + e.toString());
        write(c.getName() + " [" + LocalDateTime.now() + "] ERROR: " + e);

    }

    public void info(Class<?> c, String message) {
        System.out.println(c.getName() + " [" + LocalDateTime.now() + "] INFO: " + message);
        write(c.getName() + " [" + LocalDateTime.now() + "] INFO: " + message);

    }

    public void warn(Class<?> c, String message) {
        System.out.println(c.getName() + " [" + LocalDateTime.now() + "] WARN: " + message);
        write(c.getName() + " [" + LocalDateTime.now() + "] WARN: " + message);
    }
}
