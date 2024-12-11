package cz.meind.dto;


public record MonitoringRecord(boolean error,int id, long servingTime, String path) {
}
