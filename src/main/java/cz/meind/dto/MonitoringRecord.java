package cz.meind.dto;

import java.time.LocalDateTime;

public record MonitoringRecord(boolean error,int id, String code, long servingTime, long fileSize, String dateTime) {
}
