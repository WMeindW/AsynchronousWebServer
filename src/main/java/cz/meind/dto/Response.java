package cz.meind.dto;

import cz.meind.application.Application;

import java.io.*;

public class Response {
    private final File file;

    private final OutputStream out;

    private String code;

    public String getCode() {
        return code;
    }

    public Response(File file, OutputStream out) {
        this.file = file;
        this.out = out;
    }

    public OutputStream getOut() {
        return out;
    }

    @Override
    public String toString() {
        return "Response{" + "file=" + file + ", out=" + out + '}';
    }

    //ChatGPT
    public void respond() throws IOException {
        if (file.exists() && !file.isDirectory()) {
            FileInputStream fileInputStream = new FileInputStream(file);
            PrintWriter headerWriter = new PrintWriter(out, true);
            headerWriter.println("HTTP/1.1 200 OK");
            headerWriter.println("Content-Type: " + Application.server.contentTypes.get(file.getName().split("\\.")[1]));
            headerWriter.println("Content-Length: " + file.length());
            headerWriter.println("Server: " + Application.serverName);
            headerWriter.println();
            code = "200 OK";
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

        } else {
            PrintWriter headerWriter = new PrintWriter(out, true);
            headerWriter.println("HTTP/1.1 404 Not Found");
            headerWriter.println("Content-Type: text/plain");
            headerWriter.println("Server: " + Application.serverName);
            headerWriter.println();
            headerWriter.println("404 Soubor nenalezen");
            code = "404 Not Found";
        }


    }
}
