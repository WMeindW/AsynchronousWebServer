package cz.meind.dto;

import java.util.HashMap;

public class Response {
    private String contentType;

    private String body;

    private String code;

    public Response(String contentType, String body, String code) {
        this.contentType = contentType;
        this.body = body;
        this.code = code;
    }

    @Override
    public String toString() {
        return "HTTP/1.1 " + code + " \nContent-Type: " + contentType + "\n\n" + body;
    }
}
