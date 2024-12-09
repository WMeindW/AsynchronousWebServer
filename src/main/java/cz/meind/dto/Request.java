package cz.meind.dto;

import java.net.URI;
import java.net.URL;
import java.util.List;

public class Request {
    private URL host;

    private List<String> headers;

    private String path;

    public Request(URL host, List<String> headers, String path) {
        this.host = host;
        this.headers = headers;
        this.path = path;
    }

    @Override
    public String toString() {
        return "Request{" +
                "host=" + host +
                ", headers=" + headers +
                ", path='" + path + '\'' +
                '}';
    }
}
