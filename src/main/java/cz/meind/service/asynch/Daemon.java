package cz.meind.service.asynch;

import cz.meind.application.Application;
import cz.meind.service.Monitoring;

public class Daemon implements Runnable {
    @Override
    public void run() {
        Monitoring monitor = new Monitoring();
        monitor.setup();
        while (true) {
            try {
                Thread.sleep(1000);
                monitor.run();
            } catch (InterruptedException e) {
                Application.logger.error(Daemon.class, e);
            }
        }
    }

    public static void shutdown() {
        // TODO: Implement stopping the daemon logic here
        Application.logger.info(Daemon.class, "Shutting down");
        Application.server.getServerThread().interrupt();
        Application.logger.info(Daemon.class, "Interrupted server");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Application.logger.info(Daemon.class, "Proper shutdown failed");
            System.exit(1);
        }
        Application.logger.info(Daemon.class, "Shutdown completed");
    }
}
