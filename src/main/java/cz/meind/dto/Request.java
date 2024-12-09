package cz.meind.dto;

import java.nio.file.Path;
import java.util.HashMap;

public class Request {
    private HashMap<String,String> headers;

    private String path;

    public Request(HashMap<String,String> headers, String path) {
        this.headers = headers;
        this.path = path;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "Request{" + ", headers=" + headers + ", path='" + path + '\'' + '}';
    }
}
