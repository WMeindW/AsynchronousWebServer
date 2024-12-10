package cz.meind.dto;

import java.time.LocalDateTime;

public record MonitoringRecord(int id, String code, long servingTime, long fileSize, LocalDateTime dateTime) {
}
