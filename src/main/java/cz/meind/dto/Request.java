package cz.meind.dto;

import java.util.HashMap;

public class Request {
    private HashMap<String,String> headers;

    private String path;

    public Request(HashMap<String,String> headers, String path) {
        this.headers = headers;
        this.path = path;
    }

    @Override
    public String toString() {
        return "Request{" + ", headers=" + headers + ", path='" + path + '\'' + '}';
    }
}
