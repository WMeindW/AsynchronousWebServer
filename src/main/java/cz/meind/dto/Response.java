package cz.meind.dto;

import java.util.HashMap;

public class Response {
    private HashMap<String,String> headers;

    private String path;

    private String body;

    public Response(HashMap<String, String> headers, String path, String body) {
        this.headers = headers;
        this.path = path;
        this.body = body;
    }

    @Override
    public String toString() {
        return "Response{" + ", headers=" + headers + ", path='" + path + '\'' + '}';
    }
}
