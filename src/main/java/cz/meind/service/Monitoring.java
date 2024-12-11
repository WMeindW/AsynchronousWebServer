package cz.meind.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.meind.application.Application;
import cz.meind.dto.MonitoringRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Monitoring {
    private final List<MonitoringRecord> records = Collections.synchronizedList(new LinkedList<>());

    public Monitoring() {
        try {
            setup();
        } catch (IOException e) {
            Application.logger.error(Monitoring.class, e);
        }
    }

    private void setup() throws IOException {
        Application.monitor = this;
        if (Files.notExists(Path.of(Application.publicFilePath + "/monitor/data.json"))) {
            Files.createDirectories(Path.of(Application.publicFilePath + "/monitor/"));
            Files.createFile(Path.of(Application.publicFilePath + "/monitor/data.json"));
        }
    }

    private synchronized List<MonitoringRecord> clear() {
        List<MonitoringRecord> list = new LinkedList<>(records);
        records.clear();
        return list;
    }

    public synchronized void addRecord(MonitoringRecord record) {
        if (record.servingTime() < 500) records.add(record);
    }

    public void run() {
        List<MonitoringRecord> list = clear();
        ObjectMapper objectMapper = new ObjectMapper();
        if (list.isEmpty()) return;
        try {
            for (MonitoringRecord record : list) {
                Files.writeString(Path.of(Application.publicFilePath + "/monitor/data.json"), objectMapper.writeValueAsString(record) + ",\n", StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            Application.logger.error(Monitoring.class, e);
        }
    }
}
